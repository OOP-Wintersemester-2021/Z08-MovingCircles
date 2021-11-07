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
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final int CIRCLE_RADIUS = 15;
    private static final int CIRCLE_MOVEMENT_RADIUS = 200;
    private static final int FLEEING_CIRCLE_LEAD_IN_DEGREES = 45;

    private Circle fleeingCircle;
    private Circle huntingCircle;
    private double currentCirclePositionInDegree = 0;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        fleeingCircle = new Circle(0, 0, CIRCLE_RADIUS, RED);
        huntingCircle = new Circle(0, 0, CIRCLE_RADIUS * 1.1f, GREY);
    }

    @Override
    public void draw() {
        drawBackground(CREAM);
        currentCirclePositionInDegree = updateCirclePositionInDegree(currentCirclePositionInDegree);
        updateAndDrawCircle(fleeingCircle, currentCirclePositionInDegree, true);
        updateAndDrawCircle(huntingCircle, currentCirclePositionInDegree, false);
    }

    private double updateCirclePositionInDegree(double lastCirclePositionInDegree) {
        lastCirclePositionInDegree++;
        if (lastCirclePositionInDegree > 360) {
            lastCirclePositionInDegree = 0;
        }
        return lastCirclePositionInDegree;
    }

    private void updateAndDrawCircle(Circle circle, double circlePositionInDegree, boolean isFleeingCircle) {
        if (isFleeingCircle) {
            updateCirclePosition(circle, circlePositionInDegree);
            updateCircleColor(circle);
        } else {
            updateCirclePosition(circle, circlePositionInDegree - FLEEING_CIRCLE_LEAD_IN_DEGREES);
        }
        circle.draw();
    }

    private void updateCirclePosition(Circle circle, double degree) {
        double x = (WINDOW_HEIGHT / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.cos(Math.toRadians(degree)));
        double y = (WINDOW_WIDTH / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.sin(Math.toRadians(degree)));
        circle.setPosition((float) x, (float) y);
    }

    private void updateCircleColor(Circle circle) {
        if (circle.getYPos() < WINDOW_HEIGHT / 2.0 && circle.getXPos() > WINDOW_WIDTH / 2.0) {
            circle.setColor(RED);
        }
        if (circle.getYPos() > WINDOW_HEIGHT / 2.0 && circle.getXPos() > WINDOW_WIDTH / 2.0) {
            circle.setColor(YELLOW);
        }
        if (circle.getYPos() > WINDOW_HEIGHT / 2.0 && circle.getXPos() < WINDOW_WIDTH / 2.0) {
            circle.setColor(GREEN);
        }
        if (circle.getYPos() < WINDOW_HEIGHT / 2.0 && circle.getXPos() < WINDOW_WIDTH / 2.0) {
            circle.setColor(BLUE);
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("MovingCirclesApp");
    }
}