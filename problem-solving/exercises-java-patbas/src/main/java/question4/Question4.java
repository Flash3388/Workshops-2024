package question4;

public class Question4 {

    // this program calculates information about neighboring cells of a given cell in a matrix.
    // the matrix is composed of rows and columns (like a table) where each cell is either true or false.
    // given a specific cell (e.g. row 0, col 1) countSurroundingTrueCells calculates
    // the amount of neighboring cells that have true in them (this cell has 5 neighbors, with 3 of them being true).
    //
    // the main runs a few examples with this method.
    // but there are problems

    public static void main(String[] args) {
        boolean[][] cells = {
           //col  0      1      2      3
                {true,  false, false, true}, //  row 0
                {false, true,  true,  true}, //  row 1
                {false, false, true,  false}, // row 2
                {true,  false, true,  true} //   row 3
        };

        // expected: 3
        System.out.println(countSurroundingTrueCells(cells, 1, 1));

        // expected: 1
        System.out.println(countSurroundingTrueCells(cells, 0, 0));

        // expected: 3
        System.out.println(countSurroundingTrueCells(cells, 1, 3));
    }

    private static int countSurroundingTrueCells(
            boolean[][] cells,
            int row,
            int col) {
        int count = 0;
        int rowCount = cells.length;
        int colCount = cells[0].length;

        int rowStart = Math.max(0, row - 1);
        int colStart = Math.max(0, col - 1);
        int rowEnd = Math.min(row + 1, rowCount);
        int colEnd = Math.min(col + 1, colCount);

        for (int i = rowStart; i <= rowEnd; i++)  {
            for (int j = colStart; j <= colEnd; j++) {
                if (i == row && j == col) {
                    continue;
                }

                if (cells[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }
}
