package question5;

@SuppressWarnings({"ExplicitArrayFilling", "ForLoopReplaceableByForEach"})
public class Question5 {

    // this program implements line drawing for the console.
    // instead of drawing directly to the console, this program
    // buffers the drawing to allow drawing flexibility. This basically
    // means that drawing is done to a matrix which represents the console.
    // the matrix is 10x15 (10 rows, 15 columns), with each cell being a single
    // character.
    // the program creates this display, sets all the content to empty
    // spaces, draws some stuff into the display and then prints the display.
    //
    // but there are problems
    //
    // your output should show two lines like this:
    // *
    //  *
    //   *
    //
    //
    //     *
    //     *
    //     *
    //     *
    //

    public static void main(String[] args) {
        char[][] display = new char[10][15];
        clearDisplay(display);

        drawLine(
                new Point(0, 0),
                new Point(2, 2),
                display
        );
        drawLine(
                new Point(5, 5),
                new Point(5, 8),
                display
        );

        drawDisplay(display);
    }

    private static void drawLine(Point start, Point end, char[][] display) {
        int dx = end.getX() - start.getX();
        int dy = start.getY() - end.getY();

        int step = Math.max(Math.abs(dx), Math.abs(dy));
        int xIncrement = dx / step;
        int yIncrement = dy / step;

        assert xIncrement == 0 || Math.abs(xIncrement) == 1;
        assert yIncrement == 0 || Math.abs(yIncrement) == 1;

        int x = start.getX();
        int y = start.getY();

        for (int i = 0; i < step; i++) {
            display[y][x] = '*';

            x += yIncrement;
            y += yIncrement;
    }

    private static void clearDisplay(char[][] display) {
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display.length; j++) {
                display[j][i] = ' ';
            }
        }
    }

    private static void drawDisplay(char[][] display) {
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                System.out.print(display[i][j]);
            }
            System.out.println();
        }
    }
}
