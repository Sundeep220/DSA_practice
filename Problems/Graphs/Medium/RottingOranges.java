package Problems.Graphs.Medium;

import java.util.LinkedList;
import java.util.Queue;

public class RottingOranges {
    // Problem: https://leetcode.com/problems/rotting-oranges/
    class Solution {

        // Represents a cell in the grid along with the time when it becomes rotten
        static class Cell {
            int row;
            int col;
            int minute;

            Cell(int row, int col, int minute) {
                this.row = row;
                this.col = col;
                this.minute = minute;
            }
        }

        public int orangesRotting(int[][] grid) {

            int rows = grid.length;           // number of rows in the grid
            int cols = grid[0].length;        // number of columns in the grid

            // Queue for multi-source BFS (all initially rotten oranges)
            Queue<Cell> bfsQueue = new LinkedList<>();

            // Count of fresh oranges present in the grid
            int freshCount = 0;

            // Step 1: Initialize BFS
            // - Add all rotten oranges to the queue
            // - Count fresh oranges
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {

                    if (grid[row][col] == 2) {
                        // Rotten orange → BFS starting point
                        bfsQueue.offer(new Cell(row, col, 0));
                    }
                    else if (grid[row][col] == 1) {
                        // Fresh orange
                        freshCount++;
                    }
                }
            }

            // If there are no fresh oranges, no time is required
            if (freshCount == 0) return 0;

            // Possible directions: left, up, right, down
            int[][] directions = {
                    {0, -1},   // left
                    {-1, 0},   // up
                    {0, 1},    // right
                    {1, 0}     // down
            };

            int maxMinutesElapsed = 0;

            while (!bfsQueue.isEmpty()) {

                Cell currentCell = bfsQueue.poll();
                int currentRow = currentCell.row;
                int currentCol = currentCell.col;
                int currentMinute = currentCell.minute;

                // Track the maximum time required to rot oranges
                maxMinutesElapsed = Math.max(maxMinutesElapsed, currentMinute);

                // Visit all 4-directionally adjacent cells
                for (int[] direction : directions) {

                    int adjacentRow = currentRow + direction[0];
                    int adjacentCol = currentCol + direction[1];

                    // Check grid boundaries and freshness
                    if (adjacentRow >= 0 && adjacentRow < rows &&
                            adjacentCol >= 0 && adjacentCol < cols &&
                            grid[adjacentRow][adjacentCol] == 1) {

                        // Rot the fresh orange
                        grid[adjacentRow][adjacentCol] = 2;

                        // Decrease fresh orange count
                        freshCount--;

                        // Add newly rotten orange to BFS queue
                        bfsQueue.offer(
                                new Cell(adjacentRow, adjacentCol, currentMinute + 1)
                        );
                    }
                }
            }
            
            return freshCount == 0 ? maxMinutesElapsed : -1;
        }
    }


}
