# GameOfFifteen

This is a learning project which objective is to design a Java application for solving the "Game of Fifteen”.

![Game image](http://www.murderousmaths.co.uk/games/loyd/15%20puzzle%20wood.gif)

The Game of Fifteen is a puzzle played on a square 4x4 board with 15 numbered tiles that slide and one empty space.

The goal of this puzzle is to arrange the board’s tiles using the empty space in order from smallest to largest, 
left to right, top to bottom, with an empty space in board’s bottom-right corner.
Not all board configurations are solvable (in reality only a half of them can be solved). You can read more about it [here](https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html).



##### INSTRUCTIONS
Run GameLauncher with file path as argument, so it can read the game configuration from the file and try to solve it.

If you run it without arguments, it will generate a solvable configuration and will solve it.

##### INPUT
The initial configuration in the input file must contain 4 lines. Each line should contain 4 numbers separated by space.
The file must contain all numbers (can be in random order) from 0 to 15 inclusive, where 0 defines an empty cell.

#####  OUTPUT
The result will be printed to a file. 
The first part of the result should be the initial configuration.
Then: 
- If successful configuration is unattainable, then −1 will be printed to the output file.
- If the solution is attainable, the number of tiles movements needed to solve the game will be printed to the file. In the next line the corresponding sequence of moves (where *Left* means that empty cell moved to the left etc.) will be printed followed by board configuration at a given game state.