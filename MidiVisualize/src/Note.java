

public class Note {
	Visualizer parent;

	static final double PI = 3.14159265;
	private int note, color;
	float r, x, y; // represents position in z direction 

	long creationTime = System.nanoTime();

	Note(Visualizer p, int Color, int note) { // constructor
		parent = p;
		this.color = Color;
		this.note = note;

		r = parent.getHeight() / 2 - 10;
	}

	void draw() { // display note with respect to time
		parent.fill(color);
		x = (float) (r * Math.sin(2 * PI * note / 12) + parent.getWidth() / 2);
		y = (float) (r * -Math.cos(2 * PI * note / 12) + parent.getHeight() / 2);
		parent.ellipse((float) (r * Math.sin(2 * PI * note / 12) + parent.getWidth() / 2),
				(float) (r * -Math.cos(2 * PI * note / 12) + parent.getHeight() / 2), r / 10, r / 10);
	}

	void update(long currentTime) {
		double elapsedTime = (currentTime - creationTime) / 100000000000.0;// //seconds since creation
		r *= 1 / (elapsedTime + 1);
	}
}