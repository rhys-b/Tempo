# Tempo
YYC Hackathon 2021 Submission (Rhys Byers & Andy Chen)

With the theme this year based around mental health, particularly during the COVID pandemic, it seemed like a perfect time to make a goal planner. Setting and managing goals is critical for positive mental health, and one of Tempo's purposes is to help manage goals. Additionally, it tracks and graphically demonstrates how the user is spending their time, allowing the user to make concious decisions toward a more balanced lifestyle. Tempo runs on any desktop environment that can run the Java Runtime.

## Compiling

FlatLAF must either be made part of the classpath for javac, or extracted directly into the default package.

If flatlaf-1.6.jar is left compressed, then on
Mac/Linux compile with:

`javac -cp .:flatlaf-1.6.jar Tempo.java`

Windows compile with:

`javac -cp .;flatlaf-1.6.jar Tempo.java`

If flatlaf-1.6.jar is extracted so the 'com' directory is in the default package, then compile with:

`javac Tempo.java`

**Precompiled binaries are available as .jar (for Linux and Mac) and .exe (for Windows) under the "Releases" section.** *(These require Java 8 or later to run.)*


## Running

If flatlaf-1.6.jar is left compressed, then:

On Mac/Linux run with:

`java -cp .:flatlaf-1.6.jar Tempo`

on Windows run with:

`java -cp .;flatlaf-1.6.jar Tempo`

If flatlaf-1.6.jar was extracted in the compile step, then run with:

`java Tempo`

## External Libraries
+ [FlatLAF](https://www.formdev.com/flatlaf), which styles the GUI components according to the [Carbon Theme](https://github.com/luisfer0793/theme-carbon).
+ Java Default Library
