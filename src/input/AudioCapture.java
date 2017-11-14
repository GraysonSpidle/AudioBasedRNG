package input;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioCapture extends Thread {

	public final File DUMP;
	private long recordTime = 500;
	
	public AudioCapture() {
		recordTime = 500;
		DUMP = new File("audioDump.wav");
	}
	
	public AudioCapture(long millis) {
		recordTime = millis;
		DUMP = new File("audioDump.wav");
	}
	
	public AudioCapture(File audioDump) {
		recordTime = 500;
		DUMP = audioDump;
	}
	
	public AudioCapture(long millis, File audioDump) {
		recordTime = millis;
		DUMP = audioDump;
	}
	
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	public TargetDataLine line;

	AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	@Override
	public void run() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Line is not supported.");
				return;
			}
			
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			AudioInputStream ais = new AudioInputStream(line);
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(recordTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						line.stop();
						line.close();
					}
				}
			}.start();
			
			AudioSystem.write(ais, fileType, DUMP);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
