package com.serg;

public class Starter {
	public static void main(String[] args) {
		DU d = new DU(1, 2, 3);
		double h = 0.1;
		double t0 = 0;
		d.Euler(t0, 1, -1, 3, h);

		int n = d.getN();
		double x = t0;
		for (int i = 0; i < n; i++) {
			System.out.println("X : " + x + "\t Y : " + d.getYi(i) + "\t Z : " + d.getZi(i));
			x += h;
		}

		// Frame f = new Frame();

	}
}
