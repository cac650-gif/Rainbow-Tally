# Rainbow Tally

## About the Project

Rainbow Tally is a team-based scoreboard application built in Java using Java Swing. It is meant to provides a colorful graphical interface for tracking scores, managing multiple teams, and identifying the leading team during a game.

## Features

- Set up 2–5 teams with custom names
- Add points and undo scoring mistakes.
- Display scores using tally marks grouped by five
- Track the leading team and identify ties
- Choose a winning score of 5, 10, 15, or 20 points
- Display a winner announcement when a team reaches the winning score
- Start a new game with different teams

## Technologies and Concepts
- Java
- Java Swing
- Classes and objects
- Arrays, loops, and conditional statements
- Methods and event listeners

## Running the Project

# Requirements

- Java Development Kit (JDK) installed
- Visual Studio Code (VS Code), or another code editor with access to a terminal

# Steps

1. Download or clone this repository to your computer
2. Open the project folder in VS Code
3. Open the VS Code terminal by selecting **Terminal → New Terminal** from the menu
4. Compile the Java files by entering:
   javac Main.java Team.java Game.java RainbowTallyGUI.java
5. Run the application:
   java Main
6. Follow the setup prompts to choose the number of teams, enter team names, and select the winning score

**Note:** Java Swing is included with the standard JDK, so no separate GUI library is needed.


## Project Files

- `Main.java` — Starts the application
- `Team.java` — Stores team names and scores
- `Game.java` — Checks the current leader and whether teams are tied
- `RainbowTallyGUI.java` — Builds the scoreboard interface and handles user interactions
