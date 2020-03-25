import processing.core.PApplet;
import sojamo.drop.*;
import java.util.*;
import java.io.*;

public class Visualizer extends PApplet {
	private static final long serialVersionUID = 1L;// need a list with time as connection point.

	public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	public SDrop drop;

	ArrayList<Note> Notes = new ArrayList<Note>();

	final long creation_time = System.nanoTime();
	long last_time = System.nanoTime();

	public static void main(String[] args) {
		PApplet.main("Visualizer");
	}

	public void setup() {
		// size(800, 800);
		size(displayWidth, displayHeight);
		noStroke();
		drop = new SDrop(this);
	}

	public boolean sketchFullScreen() {
		return true;
	}

	public void draw() { // traverse the song
		background(0);
		// fill(0); // draw black circle
		// ellipse(getWidth() / 2, getHeight() / 2, getHeight(), getHeight()); //draw
		// black circle
		fill(255);
		for (int i = 0; i < Notes.size(); i++) {
			Note n = Notes.get(i);
			n.update(System.nanoTime());
			n.draw();
		}

		fill(255);
		for (int i = 0; i < 12; i++)
			text(NOTE_NAMES[i], (float) ((getHeight() / 2 - 10) * Math.sin(2 * PI * i / 12) + getWidth() / 2),
					(float) ((getHeight() / 2 - 10) * -Math.cos(2 * PI * i / 12) + getHeight() / 2));

		// draw lines between active notes
//		for(Note n : Notes)
//			if(n.r > 10)
//				for(Note x : Notes)
//					if(x.r > 10)
//						line(n.x, n.y, x.x, x.y);

		if (Notes.size() > 70) {
			Notes.remove(0);
		}
	}

	public void noteTrigger(int key, int channel, Boolean state) {
		int color = (int) map(key, 0, 127, 0, 255);
		
		int grayScale = (int)(255.0 / (1.0 + Math.pow(Math.E, -1.0 * (color - 220.0 / 2.0) / 14.0)));
		
		if (state) {
			Note test = new Note(this, grayScale, key);
			Notes.add(test);
		}
	}

	public void dropEvent(DropEvent theDropEvent) {
		if (theDropEvent.isFile()) {
			File myFile = theDropEvent.file();
			Player MidiPlayer = new Player(myFile, this);
			MidiPlayer.start();
		}
	}
}