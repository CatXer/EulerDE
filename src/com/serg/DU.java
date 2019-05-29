package com.serg;

public class DU {

	// y(2);-y(1)+exp(-x)
	private double a, b, c;
	int n;

	double yXval[], yYval[], zXval[], zZval[];

	public DU(double a, double b, double c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	private double getY(double x, double y, int index) {
		return zZval[index];
	}

	private double getZ(double x, double y, int index) {
		return -getY(x, y, index) + Math.exp(-x);
	}

	public void Euler(double t0, double y0, double yy0, double tn, double h) {

		this.n = (int) ((tn - t0) / h);

		zXval = new double[n];
		zZval = new double[n];

		zXval[0] = t0;
		zZval[0] = yy0;

		yXval = new double[n];
		yYval = new double[n];

		yXval[0] = t0;
		yYval[0] = y0;


	}

	public double getYi(int i) {
		if (i >= 0 && i <= n)
			return yYval[i];
		return 0.0;
	}

	public double getZi(int i) {
		if (i >= 0 && i <= n)
			return zZval[i];
		return 0.0;
	}

	public int getN() {
		return n;
	}
}
