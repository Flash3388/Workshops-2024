package question5;

@SuppressWarnings({"ExplicitArrayFilling", "ForLoopReplaceableByForEach"})
public class Question5 {


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
        int dy = end.getY() - start.getY();

        int step = Math.max(Math.abs(dx), Math.abs(dy));
        int xIncrement = dx / step;
        int yIncrement = dy / step;

        assert xIncrement == 0 || Math.abs(xIncrement) == 1;
        assert yIncrement == 0 || Math.abs(yIncrement) == 1;

        int x = start.getX();
        int y = start.getY();

        for (int i = 0; i <= step; i++) {
            display[y][x] = '*';

            x += xIncrement;
            y += yIncrement;
        }
    }

    private static void clearDisplay(char[][] display) {
        for (int i = 0; i < display.length; i++) {
            for (int j = 0; j < display[i].length; j++) {
                display[i][j] = ' ';
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
