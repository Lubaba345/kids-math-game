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
Run in VS Code :

Open the project folder in VS Code:

kids-math-game

Then open:

Terminal → New Terminal

Make sure the terminal is in the project root:

C:\Users\User\OneDrive\Downloads\kids-math-game

If you are currently inside the src folder, go back one level:

cd ..

You can confirm the current folder with:

pwd

Compile the Java Files

Run:

javac -d out src\main\java\com\mycompany\kidsgame\Game.java src\main\java\com\mycompany\kidsgame\KidsGame.java src\main\java\com\mycompany\kidsgame\KidsGameGUI.java src\main\java\com\mycompany\kidsgame\Player.java

If there are no errors, the project compiled successfully.

Run the GUI Version

Run:

java -cp out com.mycompany.kidsgame.KidsGameGUI

This opens the graphical Kids Math Game.
## Author

Lubaba Karim
