/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-12
*
*	Handles reading and writing to files.
*/


package tempo;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import tempo.goal.Goal;
import tempo.goal.Goals;

import tempo.graph.GraphData;

import tempo.Present;
import tempo.QuestionSlider;

import tempo.journal.JournalEntry;


public class FileManager {
	private static final String QUESTIONS_DEFAULT = "50,50,50,50,50";

	private static final String prefix = calculatePrefix();
	//private static final String prefix = "";
	private static final SimpleDateFormat dateFormat =
		new SimpleDateFormat("yyyy-MM-dd");

	public static String calculatePrefix() {
		return new File(FileManager.class.getProtectionDomain()
			.getCodeSource().getLocation().getPath())
			.getParent().replace((CharSequence)"%20", " ") + "/";
	}

	public static void init() {
		createIfNeeded("tempo_data", true);
		createIfNeeded("tempo_data/goals", true);
		createIfNeeded("tempo_data/best", true);
		createIfNeeded("tempo_data/daily.csv", false);
		createIfNeeded("tempo_data/today.csv", false);
		createIfNeeded("tempo_data/questions.csv",
			false, QUESTIONS_DEFAULT);

		int days = calculateDays();

		if (days > 0) {
			compress();
			insertUnknown(days);
			appendNewLineOnQuestions();
		}

		appendGoals();
	}

	private static void compress() {
		try {
			File today = new File(prefix + "tempo_data/today.csv");
			Scanner scanner = new Scanner(today);

			int[] append = new int[3];
			for (int i = 0; i < append.length; i++) {
				append[i] = 0;
			}

			while (scanner.hasNextLine()) {
				String[] strings =scanner.nextLine().split(",");
				int hour, minute;
				int ampm;

				hour = Integer.parseInt(strings[1]);
				minute = Integer.parseInt(strings[2]);
				ampm = (strings[3].equals("am")) ? 1 : 0;

				Calendar start = Calendar.getInstance();
				start.set(Calendar.HOUR, hour);
				start.set(Calendar.MINUTE, minute);
				start.set(Calendar.AM_PM, ampm);

				hour = Integer.parseInt(strings[4]);
				minute = Integer.parseInt(strings[5]);
				ampm = (strings[6].equals("am")) ? 1 : 0;

				Calendar end = Calendar.getInstance();
				end.set(Calendar.HOUR, hour);
				end.set(Calendar.MINUTE, minute);
				end.set(Calendar.AM_PM, ampm);

				append[Integer.parseInt(strings[7])] +=
					(end.getTime().getTime() -
					start.getTime().getTime()) / 60000;
			}

			scanner.close();

			String out = "";
			for (int i = 0; i < append.length - 1; i++) {
				out += append[i] + ",";
			}
			out += append[append.length - 1];

			appendFile(new File(
				prefix + "tempo_data/daily.csv"), out);

			new PrintWriter(today); // clears the file
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void appendFile(File file, String data) {
		try {
			String initial = getTextFromFile(file);
			PrintWriter writer = new PrintWriter(file);

			writer.print(initial);

			if (!initial.equals("")) {
				writer.println();
			}

			writer.print(data);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int calculateDays() {
		try {
			File opened = new File(prefix +"tempo_data/opened.txt");
			Date lastDate;
			if (opened.exists()) {
				lastDate = dateFormat.parse(
					new Scanner(opened).nextLine());
				Date now = getTodayAtMidnight();

				return daysDifference(lastDate, now);
			}

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private static Date getTodayAtMidnight() {
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		return today.getTime();
	}

	public static int daysDifference(Date start, Date end) {
		return (int)Math.round((end.getTime() -
			start.getTime()) / 86400000.0);
	}

	private static void insertUnknown(int days) {
		append(new File(prefix + "tempo_data/daily.csv"), days, 3, 0);
		append(new File(prefix + "tempo_data/questions.csv"),days,5,50);
	}

	private static void appendGoals() {
		try {
			File[] files = new File(prefix + "tempo_data/goals")
								.listFiles();

			Date today = getTodayAtMidnight();

			for (int i = 0; i < files.length; i++) {
				Scanner scan = new Scanner(files[i]);

				String part1 = scan.nextLine();
				part1 += "\n" + scan.nextLine();

				String start = scan.nextLine();
				part1 += "\n" + start;

				int days = 0;
				try {
					days = daysDifference(
						dateFormat.parse(start), today);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (days <= 0) {
					scan.close();
					continue;
				}

				String doneToday = scan.nextLine();

				String part2 = "\n" + scan.nextLine();
				part2 += "\n" + scan.nextLine();
				part2 += "\n" + scan.nextLine();

				String type = scan.nextLine();
				part2 += "\n" + type;

				part2 += "\n" + scan.nextLine();

				int datapoints = 0;
				String lastLine = null;

				while (scan.hasNextLine()) {
					lastLine = scan.nextLine();
					part2 += "\n" + lastLine;
					datapoints++;
				}

				scan.close();

				String repeat = "\n";
				if (type.equals("each") || lastLine == null) {
					repeat += "0.0";
				} else {
					repeat += lastLine;
				}

				for (; datapoints < days; datapoints++) {
					part2 += repeat;
				}

				PrintWriter writer = new PrintWriter(files[i]);
				writer.println(part1);
				if (datapoints > days) {
					writer.print(doneToday);
				} else {
					writer.print("false");
				}
				writer.print(part2);

				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTextFromFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			String string;

			if (!scanner.hasNextLine()) {
				return "";
			}

			string = scanner.nextLine();

			while (scanner.hasNextLine()) {
				string += "\n" + scanner.nextLine();
			}

			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static void append(File file, int amount, int size, int val) {
		String string = "";
		for (int i = 0; i < size - 1; i++) {
			string += val + ",";
		}
		string += val;

		String append = "";
		for (int i = 0 ; i < amount - 1; i++) {
			append += string + "\n";
		}

		appendFile(file, append);
	}

	public static void createIfNeeded(String path, boolean isDir) {
		createIfNeeded(path, isDir, "");
	}

	public static void createIfNeeded(	String path,
						boolean isDir,
						String initialData)
	{
		File file = new File(prefix + path);

		if (isDir) {
			file.mkdir();
		} else if (!file.exists()) {
			try {
				PrintWriter writer = new PrintWriter(file);
				writer.print(initialData);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static GraphData read(String filename) {
		try {
			File file = new File(prefix + "tempo_data/" + filename);

			if (file.exists()) {
				Scanner scan = new Scanner(file);
				String[] nums;

				ArrayList<double[]> list =
					new ArrayList<double[]>();

				if (scan.hasNextLine()) {
					nums = scan.nextLine().split(",");

					double[] arr = stringToDouble(nums);
					list.add(stringToDouble(nums));
				}

				while (scan.hasNextLine()) {
					nums = scan.nextLine().split(",");
					list.add(stringToDouble(nums));
				}

				return new GraphData(
					list.toArray(new double[0][0]));
			}

			return new GraphData(new double[0][0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static double[] stringToDouble(String[] in) {
		double[] out = new double[in.length];

		for (int i = 0; i < in.length; i++) {
			out[i] = Double.parseDouble(in[i]);
		}

		return out;
	}

	public static double[] listToDouble(ArrayList<Double> list) {
		double[] out = new double[list.size()];

		for (int i = 0; i < out.length; i++) {
			out[i] = list.get(i);
		}

		return out;
	}

	public static ArrayList<Goal> readGoals() {
		File dir = new File(prefix + "tempo_data/goals");
		File[] files = dir.listFiles();
		ArrayList<Goal> list = new ArrayList<Goal>();

		for (int i = 0; i < files.length; i++) {
			Scanner scan = null;

			try {
				scan = new Scanner(files[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String version = scan.nextLine();
			String name = scan.nextLine();
			String start = scan.nextLine();
			String today = scan.nextLine();
			String action = scan.nextLine();
			String amount = scan.nextLine();
			String noun = scan.nextLine();
			String type = scan.nextLine();
			String dat = scan.nextLine();

			ArrayList<Double> doubles = new ArrayList<Double>();
			while (scan.hasNextLine()) {
				doubles.add(
					Double.parseDouble(scan.nextLine()));
			}

			double[] dbls = listToDouble(doubles);
			double[][] ddbl = new double[dbls.length][1];

			for (int j = 0; j < ddbl.length; j++) {
				ddbl[j][0] = dbls[j];
			}

			Object obj = null;
			int itype;

			if (type.equals("by")) {
				itype = Goals.TYPE_CUMULATIVE;

				try {
					obj = dateFormat.parse(dat);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				itype = Goals.TYPE_REPETATIVE;
				obj = dat;
			}

			try {
				list.add(new Goal(
					name,
					dateFormat.parse(start),
					action,
					Double.parseDouble(amount),
					noun,
					itype,
					obj,
					new GraphData(ddbl),
					Boolean.parseBoolean(today)
				));
			} catch (Exception e) {
				e.printStackTrace();
			}

			scan.close();
		}

		return list;
	}

	public static void save() {
		generateOpenedFile();
		saveToday();
		saveQuestions();
		saveBestPartOfDay();
		saveGoals();
	}

	private static void generateOpenedFile() {
		try {
			PrintWriter writer = new PrintWriter(
				new File(prefix + "tempo_data/opened.txt"));

			writer.println(dateFormat.format(new Date()));

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveToday() {
		try {
			ArrayList<TaskDisplay> list = TaskDisplay.getList();

			PrintWriter writer = new PrintWriter(new File(
				prefix + "tempo_data/today.csv"));

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getParent().getParent()!=null) {
					TaskDisplay task = list.get(i);
					String line = task.getTitle() + "," +
						task.getStartHour() + "," +
						task.getStartMinute() + "," +
						task.getStartAm() + "," +
						task.getEndHour() + "," +
						task.getEndMinute() + "," +
						task.getEndAm() + "," +
						task.getGroup();

					writer.println(line);
				}
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveQuestions() {
		try {
			ArrayList<QuestionSlider> sliders =Present.getSliders();

			File file = new File(prefix+"tempo_data/questions.csv");
			Scanner scanner = new Scanner(file);

			String initial = "";
			String last = null;
			if (scanner.hasNextLine()) {
				last = scanner.nextLine();
			}

			while (scanner.hasNextLine()) {
				initial += last;
				last = "\n" + scanner.nextLine();
			}

			scanner.close();

			String out =
				sliders.get(0).getValue() + "," +
				sliders.get(1).getValue() + "," +
				sliders.get(2).getValue() + "," +
				sliders.get(3).getValue() + "," +
				sliders.get(4).getValue();

			PrintWriter writer = new PrintWriter(file);

			if (initial.equals("")) {
				writer.print(initial);
			} else {
				writer.println(initial);
			}

			writer.print(out);

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveBestPartOfDay() {
		try {
			String text = Present.getBestPartOfDay();

			PrintWriter writer = new PrintWriter(
				prefix + "tempo_data/best/" +
				dateFormat.format(new Date()) + ".txt");

			writer.print(text);

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveGoals() {
		for (int i = 0; i < Goals.size(); i++) {
			Goal g = Goals.get(i);

			try {
				PrintWriter writer = new PrintWriter(
					new File(String.format(
					"%stempo_data/goals/%04d.txt",
					prefix, i)));

				writer.println("Version 0");
				writer.println(g.getName());
				writer.println(dateFormat.format(g.getStart()));
				writer.println(g.isDoneToday());
				writer.println(g.getAction());
				writer.println(g.getAmount());
				writer.println(g.getNoun());

				if (g.getType() == Goals.TYPE_CUMULATIVE) {
					writer.println("by");
					writer.println(dateFormat.format(
						(Date)g.getTypeData()));
				} else if (g.getType() ==Goals.TYPE_REPETATIVE){
					writer.println("each");
					writer.println(g.getTypeData());
				}

				GraphData graphData = g.getGraphData();
				if (graphData.size() > 0) {
					for (	int j = 0;
						j < graphData.size() - 1;
						j++)
					{
						writer.println(
							graphData.get(j, 0));
					}

					writer.print(graphData.get(
						graphData.size() - 1, 0));
				}

				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<TaskDisplay> loadToday() {
		try {
			ArrayList<TaskDisplay> out=new ArrayList<TaskDisplay>();

			Scanner scanner = new Scanner(
				new File(prefix + "tempo_data/today.csv"));

			while (scanner.hasNextLine()) {
				String[] cells = scanner.nextLine().split(",");

				out.add(new TaskDisplay(
					cells[0],
					new Time(
						Integer.parseInt(cells[1]),
						Integer.parseInt(cells[2]),
						cells[3].equals("am") ? 0 : 1
					),
					new Time(
						Integer.parseInt(cells[4]),
						Integer.parseInt(cells[5]),
						cells[6].equals("am") ? 0 : 1
					),
					Integer.parseInt(cells[7])
				));
			}

			scanner.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int[] loadQuestions() {
		try {
			Scanner scanner = new Scanner(
				new File(prefix + "tempo_data/questions.csv"));

			String lastLine = null;
			while (scanner.hasNextLine()) {
				lastLine = scanner.nextLine();
			}

			if (lastLine == null) {
				return new int[0];
			}

			String[] cells = lastLine.split(",");
			int[] out = new int[cells.length];
			for (int i = 0; i < out.length; i++) {
				out[i] = Integer.parseInt(cells[i]);
			}

			scanner.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String loadBestPartOfDay() {
			return loadString(new File(prefix + "tempo_data/best/" +
				dateFormat.format(new Date()) + ".txt"));
	}

	public static String loadString(File f) {
		try {
			byte[] dat = new byte[4096];
			int size = new FileInputStream(f).read(dat);

			if (size == -1) {
				size = 0;
			}

			return new String(dat, 0, size, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}

	private static void appendNewLineOnQuestions() {
		appendFile(	new File(prefix + "tempo_data/questions.csv"),
				QUESTIONS_DEFAULT);
	}

	public static void deleteGoal(int index) {
		try {
			File cur = new File(String.format(
				"%stempo_data/goals/%04d.txt", prefix, index));

			File next = new File(String.format(
				"%stempo_data/goals/%04d.txt", prefix,index+1));

			if (!cur.exists()) {
				return;
			}

			if (!next.exists()) {
				Files.delete(cur.toPath());
				return;
			}

			int i = index;
			while (true) {
				cur = new File(String.format(
					"%stempo_data/goals/%04d.txt",
					prefix, i));

				next = new File(String.format(
					"%stempo_data/goals/%04d.txt",
					prefix, i + 1));

				if (next.exists()) {
					Files.copy(next.toPath(), cur.toPath(),
							StandardCopyOption
							.REPLACE_EXISTING);
				} else {
					Files.delete(cur.toPath());
					break;
				}

				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JournalEntry[] loadJournal() {
		try {
			File[] files = new File(prefix + "tempo_data/best")
								.listFiles();
			ArrayList<JournalEntry> list =
						new ArrayList<JournalEntry>();

			for (int i = 0; i < files.length; i++) {
				if (new Scanner(files[i]).hasNextLine()) {
					String name = files[i].getName();
					name =name.substring(0,name.length()-4);
					list.add(new JournalEntry(
						loadString(files[i]),
						dateFormat.parse(name)));
				}
			}

			return list.toArray(new JournalEntry[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JournalEntry[0];
	}
}
