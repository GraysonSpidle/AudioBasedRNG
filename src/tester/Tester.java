package tester;

import java.util.Random;

import core.SeedGenerator;

public class Tester {
	
	public static class Coin {
		private Random rng;
		
		public Coin(long seed) {
			rng = new Random(seed);
		}
		
		public boolean flip() {
			return rng.nextBoolean();
		}
		
		public boolean[] flip(int amount) {
			boolean[] output = new boolean[amount];
			for (int i = 0; i < amount; i++) {
				output[i] = rng.nextBoolean();
			}
			return output;
		}
		
	}
	
	public static void main(String[] args) {
		long seed = SeedGenerator.generateSeed();
		
		System.out.println("Seed: " + seed);

		for (int i : generateIntegers(seed, 5, 10)) {
			System.out.println(i);
		}
	}
	
	public static int generateInteger(long seed, int digits) {
		int min = 1, max = 1;
		for (int i = digits - 1; i > 0; i--) {
			min *= 10;
			max *= 10;
		}
		max = max * 10 - min;
		return min + new Random(seed).nextInt(max);
	}
	
	public static int[] generateIntegers(long seed, int digits, int amount) {
		int[] output = new int[amount];
		int min = 1, max = 1;
		for (int i = digits - 1; i > 0; i--) {
			min *= 10;
			max *= 10;
		}
		max = max * 10 - min;
		
		Random rng = new Random(seed);
		for (int i = 0; i < amount; i++) {
			output[i] = min + rng.nextInt(max);
		}
		return output;
	}
		
	public static void coinToss(long seed, int amount) {
		Coin coin = new Coin(seed);
		int heads = 0;
		int tails = 0;
		for (boolean isHeads : coin.flip(amount)) {
			if (isHeads) {
				heads++;
			} else {
				tails++;
			}
		}
		
		System.out.println("Seed: " + seed);
		System.out.println("Amount: " + amount);
		System.out.println("Heads: " + heads);
		System.out.println("Tails: " + tails);
		System.out.println("Heads Ratio: " + (double) heads / (double) (heads + tails));
	}
	
	public static void printSeeds(int amount, long millis) {
		for (int i = 0; i < amount; i++) {
			System.out.println(SeedGenerator.generateSeed(millis));
		}
	}
	
	

}
