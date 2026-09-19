# Kids Math Game

A Java math game for children with both a simple command-line version and a Swing graphical interface.

## Features

- Single-player and multiplayer support
- Up to 10 players
- Addition, subtraction, multiplication, and division questions
- Four game modes:
  - **Make a Wish** — choose the number of questions
  - **No Mistakes** — the round ends after the first incorrect answer
  - **Take Chances** — each player gets three lives
  - **Time Trial** — answer as many questions as possible before time runs out
- Score tracking
- Player ranking and final results
- Java Swing graphical interface

## Project Structure

```text
kids-math-game/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── mycompany/
│                   └── kidsgame/
│                       ├── Game.java
│                       ├── KidsGame.java
│                       ├── KidsGameGUI.java
│                       └── Player.java
├── pom.xml
├── README.md
└── .gitignore
```

## Requirements

- Java 17 or newer
- Maven 3.8 or newer

## Run the GUI Version

From the repository root:

```bash
mvn clean compile
mvn exec:java
```

The main GUI class is:

```text
com.mycompany.kidsgame.KidsGameGUI
```

## Run the Command-Line Version

Compile the project:

```bash
mvn clean compile
```

Then run:

```bash
java -cp target/classes com.mycompany.kidsgame.KidsGame
```

## Build

Create the Maven build:

```bash
mvn clean package
```

Generated files will appear inside `target/`.

## GitHub Notes

The repository should contain the Java source files and `pom.xml`.

Generated files should not be committed, including:

- `*.class`
- `target/`

These are ignored by the included `.gitignore`.

## Author

Lubaba Karim
