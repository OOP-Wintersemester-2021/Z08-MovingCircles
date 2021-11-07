import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;


public class MovingCirclesApp extends GraphicsApp {

    private static final Color RED = new Color(234, 49, 63); // "Selbstgemischter" RGB-Farbe (rot)
    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color GREEN = new Color(76, 149, 80); // "Selbstgemischter" RGB-Farbe (grün)
    private static final Color BLUE = new Color(53, 129, 184); // "Selbstgemischter" RGB-Farbe (blau)
    private static final Color CREAM = new Color(241, 255, 250); // "Selbstgemischter" RGB-Farbe (creme)


    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final int CIRCLE_RADIUS = 15;
    private static final int CIRCLE_MOVEMENT_RADIUS = 200;

    private Circle movingCircle;
    private double currentCirclePositionInDegree = 0;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        movingCircle = createCircle();
    }

    private Circle createCircle() {
        int x = WINDOW_WIDTH / 2;
        int y = (WINDOW_HEIGHT - (2 * CIRCLE_MOVEMENT_RADIUS)) / 2;
        return new Circle(x, y, CIRCLE_RADIUS, RED);
    }

    @Override
    public void draw() {
        drawBackground(CREAM);
        updateAndDrawCircle(movingCircle);
    }

    private void updateAndDrawCircle(Circle circle) {
        updateCirclePosition(circle);
        updateCircleColor(circle);
        circle.draw();
    }

    private void updateCirclePosition(Circle circle) {
        currentCirclePositionInDegree++;
        if (currentCirclePositionInDegree > 360) {
            currentCirclePositionInDegree = 0;
        }
        double x = (WINDOW_HEIGHT / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.cos(Math.toRadians(currentCirclePositionInDegree)));
        double y = (WINDOW_WIDTH / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.sin(Math.toRadians(currentCirclePositionInDegree)));
        circle.setPosition((float) x, (float) y);
    }

    private void updateCircleColor(Circle circle) {
        if (movingCircle.getYPos() < WINDOW_HEIGHT / 2.0 && movingCircle.getXPos() > WINDOW_WIDTH / 2.0) {
            movingCircle.setColor(RED);
        }
        if (movingCircle.getYPos() > WINDOW_HEIGHT / 2.0 && movingCircle.getXPos() > WINDOW_WIDTH / 2.0) {
            movingCircle.setColor(YELLOW);
        }
        if (movingCircle.getYPos() > WINDOW_HEIGHT / 2.0 && movingCircle.getXPos() < WINDOW_WIDTH / 2.0) {
            movingCircle.setColor(GREEN);
        }
        if (movingCircle.getYPos() < WINDOW_HEIGHT / 2.0 && movingCircle.getXPos() < WINDOW_WIDTH / 2.0) {
            movingCircle.setColor(BLUE);
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("MovingCirclesApp");
    }
}