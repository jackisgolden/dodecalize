
import javax.sound.midi.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import java.io.*;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

//import java.util.ArrayList;

public class Player extends Thread {
	private File file;
	private Visualizer RootParrent;
	private AudioDispatcher dispatcher;
	private Mixer mixer;

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

	public void run() {    //
		File midiFile = file;
		String name = midiFile.getName();
		if(name.substring(name.lastIndexOf(".") + 1).equals("mid")) {
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
		else if(name.substring(name.lastIndexOf(".") + 1).equals("mp3")) {
			//Player player = new Player();
			//run the mp3 listener
//			try{
//				if(dispatcher != null)
//				{
//					dispatcher.stop();
//				}
//				
//				float sampleRate = 44100;
//				int bufferSize = 1024;
//				int overlap = 0;
//				
//				final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
//				final DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//			
//				TargetDataLine line;
//				line = (TargetDataLine) mixer.getLine(dataLineInfo);
//				final int numberOfSamples = bufferSize;
//				line.open(format, numberOfSamples);
//				line.start();
//				final AudioInputStream stream = new AudioInputStream(line);
//
//				JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
//				// create a new dispatcher
//				dispatcher = new AudioDispatcher(audioStream, bufferSize,
//						overlap);
//			}catch(LineUnavailableException lua) {System.printstacktrace(lau);};

		}
		// Play once

		//insert live audio compatibility here
	}

}