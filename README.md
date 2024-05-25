# Minesweeper Solver

Minesweeper Solver is a Java application designed to solve Minesweeper puzzles using constraint satisfaction and backtracking algorithms. It both proved the GUI for a user to play Minesweeper as well as buttons to run an algorithm with an efficient way to solve Minesweeper boards, aiming for optimal solutions.

## Installation

Before running the Minesweeper Solver, ensure you have the following installed:

- Java Development Kit (JDK) (21 or later)
- Apache Maven
- JavaFX SDK

To build and run the application, follow these steps:

Clone this repository to your local machine:

```bash
git clone https://github.com/ShayanHaghighi/MinesweeperSolver.git
```
Navigate to the project directory:

```bash

cd MinesweeperSolver
```
Build the project using Maven:

```bash
mvn package
```
Run the application:

```bash
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -jar target/MinesweeperSolver-1.0-SNAPSHOT.jar
```
Replace $PATH_TO_FX with the path to your JavaFX's lib file.

## Usage

- Launch the application.
- Select the board size and mine count.
- Click on cells to reveal them, right click to flag.
- Click the 'solve' button to reveal probabilities of mines being under each square.
- Hold shift to observe probabilities of squares being mines.
- Click the 'finish' button to watch algorithm completely solve board.
- Enjoy solving Minesweeper puzzles efficiently!

## License

This project is licensed under the MIT License. See the [LICENSE file](https://github.com/ShayanHaghighi/MinesweeperSolver/blob/main/LICENSE) for details.

## Contact

For any questions or suggestions, feel free to [open an issue](https://github.com/ShayanHaghighi/chessEngine/issues/new) (keep in mind this project is not yet finished).

Happy coding
