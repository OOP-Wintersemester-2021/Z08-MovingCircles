import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

/**
 * In dieser GraphicsApp-Anwendung werden zwei Kreise unterschiedlicher Größe animiert. Beide Objekte
 * bewegen sich hintereinander auf der gleichen Kreisbahn um den Mittelpunkt der Zeichenfläche. Der
 * "vordere" Kreis wechselt beim Übertritt in ein anderes Kreisviertel die Farbe.
 */

public class MovingCirclesApp extends GraphicsApp {

    private static final Color RED = new Color(234, 49, 63); // "Selbstgemischter" RGB-Farbe (rot)
    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color GREEN = new Color(76, 149, 80); // "Selbstgemischter" RGB-Farbe (grün)
    private static final Color BLUE = new Color(53, 129, 184); // "Selbstgemischter" RGB-Farbe (blau)
    private static final Color CREAM = new Color(241, 255, 250); // "Selbstgemischter" RGB-Farbe (creme)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    // Breite der Zeichenfläche in Pixel
    private static final int WINDOW_WIDTH = 500;
    // Höhe der Zeichenfläche in Pixel
    private static final int WINDOW_HEIGHT = 500;
    // Radius des "fliehenden" Kreises
    private static final int CIRCLE_RADIUS = 15;
    // Relative Größe des "verfolgenden" Kreises
    private static final float HUNTING_CIRCLE_RADIUS_RATIO = 1.1f;
    // Radius der Kreisbahn (um den Mittelpunkt)
    private static final int CIRCLE_MOVEMENT_RADIUS = 200;
    // Vorsprung des "fliehenden" Kreises in Grad
    private static final int FLEEING_CIRCLE_LEAD_IN_DEGREES = 45;

    // Variable für den ersten, "fliehenden" Kreis
    private Circle fleeingCircle;
    // Variable für den zweiten, "verfolgenden" Kreises
    private Circle huntingCircle;
    // Aktuelle Position des "fliehenden" Kreises auf der Kreisbahn
    private double currentCirclePositionInDegree = 0;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        fleeingCircle = new Circle(0, 0, CIRCLE_RADIUS, RED);
        huntingCircle = new Circle(0, 0, CIRCLE_RADIUS * HUNTING_CIRCLE_RADIUS_RATIO, GREY);
    }

    @Override
    public void draw() {
        drawBackground(CREAM);
        currentCirclePositionInDegree = calculateUpdatedCirclePositionInDegree(currentCirclePositionInDegree);
        updateAndDrawCircle(fleeingCircle, currentCirclePositionInDegree, true);
        updateAndDrawCircle(huntingCircle, currentCirclePositionInDegree, false);
    }

    /**
     * Die Methode gibt, basieren auf dem übergebenen Parameter, die nächste Position des Kreises in Grad
     * auf der gedachten Kreisbahn um den Mittelpunkt zurück. Die maximal mögliche Wert liegt bei 360.
     * Der Wert im Parameter wird um 1 erhöht und zurückgegeben. Wird der Maximalwert dabei überschritten,
     * wird 0 zurückgegeben.
     *
     * Hinweis: Der Methodennamen ist hinsichtlich der Länge hart an der Grenze zur schlechten Lesbarkeit.
     *
     * @param lastCirclePositionInDegree Die aktuelle Position auf der Kreisbahn in Grad
     * @return Die nächste valide Position auf der Kreisbahn in Grad
     */
    private double calculateUpdatedCirclePositionInDegree(double lastCirclePositionInDegree) {
        double updatedCirclePositionInDegree = lastCirclePositionInDegree + 1;
        // Wenn durch die Inkrementierung des Werts im Parameter eine Zahl größer 360 erzeugt wird ...
        if (updatedCirclePositionInDegree > 360) {
            // ... wird der Wert auf 0 zurückgesetzt.
            updatedCirclePositionInDegree = 0;
        }
        return updatedCirclePositionInDegree;
    }

    /**
     * Die Methode versetzt den übergebenen Kreis an die angegebene Position auf der Kreisbahn. Falls
     * es sich um den fliehenden Kreis handelt, wird zusätzlich dessen Farbe auf Basis des Kreissegments,
     * in dem dieser sich aktuell befindet, angepasst. Im Anschluss werden die Kreise gezeichnet.
     *
     * Hinweis: In Zukunft werden wir versuchen, solche Methoden auf anderem Wege umzusetzen. Eine Methode sollte keine
     * Änderungen an einem der übergebenen Parameter durchführen. Das gilt insbesondere für Objekte (Ausnahmen
     * bestätigen die Regel). Sobald wir eigene Klassen definieren können, können wir besser Lösungen umsetzen.
     * Auch bei primitiven Datentypen als Parameter sollte deren direkte Manipulation vermieden werden. Das führt in der
     * Regel auch zu lesbarerem Code (Vgl. calculateUpdatedCirclePositionInDegree).
     *
     * @param circle Der Kreis, der aktualisiert und gezeichnet werden soll
     * @param circlePositionInDegree Die Position auf der Kreisbahn (in Grad) an die der übergebene Kreis versetzt werden soll
     * @param isFleeingCircle Ein Hinweis, ob es sich um den fliehenden oder den verfolgenden Kreis handelt
     */
    private void updateAndDrawCircle(Circle circle, double circlePositionInDegree, boolean isFleeingCircle) {
        // Ein fliehender Kreis ...
        if (isFleeingCircle) {
            // ... wird neu positioniert ...
            updateCirclePosition(circle, circlePositionInDegree);
            // ... und farblich angepasst.
            updateCircleColor(circle);
        // Andere Kreise ...
        } else {
            // ... werden nur neu positioniert.
            updateCirclePosition(circle, circlePositionInDegree - FLEEING_CIRCLE_LEAD_IN_DEGREES);
        }
        // Alle Kreise werden im Anschluss gezeichnet.
        circle.draw();
    }

    /**
     * Verändert die Position des übergebenen Kreises auf der Zeichenfläche. Der Kreis wird an der durch den
     * zweiten Parameter definierten Position auf der gedachten Kreisbahn um den Mittelpunkt der Zeichenfläche
     * positioniert.
     *
     * @param circle Der Kreis, dessen Position angepasst werden soll
     * @param degree Die Position auf der Kreisbahn (in Grad) an die der übergebene Kreis versetzt werden soll
     */
    private void updateCirclePosition(Circle circle, double degree) {
        /*
         * Die neue x-Position wird mithilfe der Cosinus-Funktion berechnet. Dazu muss die Positionsangabe von Grad
         * in Bogenmaß umgerechnet werden. Für die Positionierung um den Mittelpunkt der Zeichenfläche wird die berechnete
         * Position auf die x-Koordinate des Mittelpunkts addiert.
         */
        double x = (WINDOW_HEIGHT / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.cos(Math.toRadians(degree)));
        // Die neue x-Position wird mithilfe der Sinus-Funktion berechnet.
        double y = (WINDOW_WIDTH / 2.0) + (CIRCLE_MOVEMENT_RADIUS * Math.sin(Math.toRadians(degree)));
        // Die neuen Koordinaten werden zum Setzen der Position des Kreises verwendet (die Methode verlangt float-Werte).
        circle.setPosition((float) x, (float) y);
    }

    /**
     * Die Methode bestimmt, in welchem Teilabschnitt der Kreisbahn sich der übergebene Kreis befindet und passt dessen
     * Farbe entsprechend an.
     *
     * @param circle Der Kreis, dessen Farbe angepasst werden soll
     */
    private void updateCircleColor(Circle circle) {
        // Der Kreis befindet sich im nordöstlichen Segment der Kreisbahn ...
        if (circle.getYPos() < WINDOW_HEIGHT / 2.0 && circle.getXPos() > WINDOW_WIDTH / 2.0) {
            // ... und wird rot eingefärbt.
            circle.setColor(RED);
        }
        // Der Kreis befindet sich im südöstlichen Segment der Kreisbahn
        if (circle.getYPos() > WINDOW_HEIGHT / 2.0 && circle.getXPos() > WINDOW_WIDTH / 2.0) {
            // ... und wird gelb eingefärbt.
            circle.setColor(YELLOW);
        }
        // Der Kreis befindet sich im südwestlichen Segment der Kreisbahn
        if (circle.getYPos() > WINDOW_HEIGHT / 2.0 && circle.getXPos() < WINDOW_WIDTH / 2.0) {
            // ... und wird grün eingefärbt.
            circle.setColor(GREEN);
        }
        // Der Kreis befindet sich im nordwestlichen Segment der Kreisbahn
        if (circle.getYPos() < WINDOW_HEIGHT / 2.0 && circle.getXPos() < WINDOW_WIDTH / 2.0) {
            // ... und wird blau eingefärbt.
            circle.setColor(BLUE);
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("MovingCirclesApp");
    }
}