
import javax.sound.midi.*;

import java.io.*;
//import java.util.ArrayList;

public class Player extends Thread {
	private File file;
	private Visualizer RootParrent;

	public class myListener implements Receiver {
		public void send(MidiMessage message, long timeStamp) {
			if (message instanceof ShortMessage) {
				ShortMessage sm = (ShortMessage) message;
				int channel = sm.getChannel();
				int command = sm.getCommand();

				if (command == ShortMessage.NOTE_ON) {
//                    String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
//                    int note = sm.getData1() % 12;
//                    String name = NOTE_NAMES[note];
					int rawKey = sm.getData1();
					Boolean State = true;
					if (sm.getData2() == 0)
						State = false;
					else
						State = true;
					RootParrent.noteTrigger(rawKey, channel, State);
				} else if (command == ShortMessage.NOTE_OFF) {
					int rawKey = sm.getData1();
					RootParrent.noteTrigger(rawKey, channel, false);
				}
			}
		}

		public void close() {
			System.out.println("O");
		}
	}

	Player(File file, Visualizer Parrent) {
		this.file = file;
		this.RootParrent = Parrent;
	}

	public void run() {
		File midiFile = file;

		// Play once
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.setSequence(MidiSystem.getSequence(midiFile));
			sequencer.open();

			Receiver receiver = new myListener();

			Transmitter transmitter = sequencer.getTransmitter();

			transmitter.setReceiver(receiver);

			sequencer.start();

			while (true) {
				if (sequencer.isRunning()) {
					try {
						Thread.sleep(1000); // Check every second
					} catch (InterruptedException ignore) {
						break;
					}
				} else
					break;
			}
			// Close the MidiDevice & free resources
			sequencer.stop();
			sequencer.close();
		} catch (MidiUnavailableException mue) {
			System.out.println("Midi device unavailable!");
		} catch (InvalidMidiDataException imde) {
			System.out.println("Invalid Midi data!");
		} catch (IOException ioe) {
			System.out.println("I/O Error!");
		}
	}

}