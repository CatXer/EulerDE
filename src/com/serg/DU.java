package com.serg;

import java.util.HashMap;

public class DU {

	// y(2);-y(1)+exp(-x)
	private double a, b, c, t0 = 0, y0 = 1, yy0 = 0, tn = 3, h = 0.01, n;

	HashMap<Double, Double> zVal;
	HashMap<Double, Double> yVal;

	public DU(double a, double b, double c, double t0, double y0, double yy0, double tn, double h) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.t0 = t0;
		this.y0 = y0;
		this.yy0 = yy0;
		this.tn = tn;
		this.h = h;

		n = (tn - t0) / h;
		Euler();
	}

	private void Euler() {

	}

	public double getYi(int i) {

		return 0.0;
	}

	public double getZi(int i) {

		return 0.0;
	}
}
