/**
 * Note class
 * Represents a note event.
 * 
 * @author jackgolden
 *
 */
public class Note {
    Visualizer parent;

    static final double PI = 3.14159265;
    private int note, color;
    float r, x, y; // represents position in z direction
    double elapseTime = 0;

    long creationTime = System.nanoTime();
    // The note has not ended as soon as it created, so it's default value is
    // negative one.
    long endTime = -1;

    /**
     * Constructor
     * 
     * @param p
     *            parent
     * @param Color
     * @param note
     */
    Note(Visualizer p, int Color, int note) {
        parent = p;
        this.color = Color;
        this.note = note;

        r = parent.getHeight() / 2 - 10;
    }


    /**
     * Display notes with respect to time.
     * Old notes should appear farther away.
     */
    void draw() {
        parent.fill(color);
        // Polar coordinates converted to cartesian coordinates.
        x = (float)(r * Math.sin(2 * PI * note / 12) + parent.getWidth() / 2);
        y = (float)(r * -Math.cos(2 * PI * note / 12) + parent.getHeight() / 2);
        parent.ellipse((float)(r * Math.sin(2 * PI * note / 12) + parent
            .getWidth() / 2), (float)(r * -Math.cos(2 * PI * note / 12) + parent
                .getHeight() / 2), r / 10, r / 10);
    }


    /**
     * Updates the radius.
     * 
     * @param currentTime
     */
    void update() {
        double elapsedTime = (System.nanoTime() - creationTime)
            / 100000000000.0;// //seconds since creation
        r *= 1 / (elapsedTime + 1);
    }


    void kill() {
        endTime = System.nanoTime();
    }


    float getX() {
        return x;
    }


    float getY() {
        return y;
    }

}
