package core;

import java.io.IOException;

import input.AudioCapture;
import input.WavFile;
import input.WavFileException;

/**
 * Generates seeds based on the data recorded from the microphone
 * @author Grayson Spidle
 */
public class SeedGenerator {
	
	public static long generateSeed() {
		double[] buffer;
		try {
			AudioCapture cap = new AudioCapture();
			cap.start();
			cap.join();
			WavFile wav = WavFile.openWavFile(cap.DUMP);
			int bufferSize = (int) wav.getNumFrames() * wav.getNumChannels();
			buffer = new double[bufferSize];
			wav.readFrames(buffer, bufferSize);
			return getSeed(convertDoubleToLong(getAverage(buffer)));
		} catch (InterruptedException | IOException | WavFileException e) {
			e.printStackTrace();
			return 0;
		} finally {
			buffer = null;
		}
	}
	
	public static long generateSeed(long millis) {
		double[] buffer;
		try {
			AudioCapture cap = new AudioCapture(millis);
			cap.start();
			cap.join();
			WavFile wav = WavFile.openWavFile(cap.DUMP);
			int bufferSize = (int) wav.getNumFrames() * wav.getNumChannels();
			buffer = new double[bufferSize];
			wav.readFrames(buffer, bufferSize);
			return getSeed(convertDoubleToLong(getAverage(buffer)));
		} catch (InterruptedException | IOException | WavFileException e) {
			e.printStackTrace();
			return 0;
		} finally {
			buffer = null;
		}
	}
	
	private static double getAverage(double[] arg) {
		double sum = 0;
		for (double d : arg) {
			sum += d;
		}
		return (double) sum / (double) arg.length;
	}
	
	private static long getSeed(double average) {
		return convertDoubleToLong((double) average / (double) System.currentTimeMillis());
	}
	
	private static long convertDoubleToLong(double average) {
		double seed = average;
		while (seed % 1d != 0d) {
			seed *= 10d;
		}
		return (long) seed;
	}

}
