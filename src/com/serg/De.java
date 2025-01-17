package com.serg;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class De {

	// y(2);-y(1)+exp(-x)

	private String y1part;
	private double xVal[], yVal[], y1Val[];

	private int n = 0;
	private double x0, x1;
	private double yMax = 0;
	private double yMin = 0;

	public De(String de) {
		y1part = de.replaceAll("\\s", "").substring(de.indexOf(';') + 1);
	}

	public void Euler(double x0, double x1, double y0, double y10, double h) {
		this.x0 = x0;
		this.x1 = x1;
		// ---------- //
		if (x0 > x1) {
			x0 = x0 + x1;
			x1 = x0 - x1;
			x0 = x0 - x1;
		}

		this.n = (int) ((x1 - x0) / h) + 1;

		xVal = new double[n];
		yVal = new double[n];
		y1Val = new double[n];

		xVal[0] = x0;
		yVal[0] = y0;
		y1Val[0] = y10;

		if (yVal[0] > y1Val[0]) {
			yMax = yVal[0];
			yMin = y1Val[0];
		} else {
			yMax = y1Val[0];
			yMin = yVal[0];
		}
		
		double yVal2 = y0;
		double y1Val2 = y10;
		double xVal2 = x0;

		System.out.println(String.format("X:%.1f  Y:%.1f  Y1:%.1f", xVal[0], yVal[0], y1Val[0]));
		for (int i = 1; i < n; i++) {
			yVal[i] = yVal[i - 1] + h * y1Val[i - 1];
			y1Val[i] = y1Val[i - 1] + h * getY1(xVal[i - 1], yVal[i - 1]);
			xVal[i] = xVal[i - 1] + h;
			System.out.println(String.format("X:%.1f  Y:%.1f  Y1:%.1f", xVal[i], yVal[i], y1Val[i]));
			if (yVal[i] > y1Val[i]) {
				yMax = yVal[i] > yMax ? yVal[i] : yMax;
				yMin = y1Val[i] < yMin ? y1Val[i] : yMin;
			} else {
				yMax = y1Val[i] > yMax ? y1Val[i] : yMax;
				yMin = yVal[i] < yMin ? yVal[i] : yMin;
			}
			for (int j = 0; j < 2; j++) {
				y1Val2 = y1Val2 + getY1(xVal2, yVal2) * (h / 2);
				yVal2 = yVal2 + y1Val2 * (h / 2);
				xVal2 = xVal2 + h / 2;
			}

		}
		System.out.println("Error y is :" + Math.abs(yVal[n - 1] - yVal2)/3);
		System.out.println("Error y` is :" + Math.abs(y1Val[n - 1] - y1Val2)/3);

	}

	public double getYmax() { return yMax; }

	public double getYmin() { return yMin; }

	public double getXmin() { return x0; }

	public double getXmax() { return x1; }

	public double getX(int i) {
		return xVal[i];
	}

	public double getY(int i) {
		return yVal[i];
	}

	public double getY1(int i) {
		return y1Val[i];
	}

	private double getY1(double x, double y) {
		double y1 = 0;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String yp = y1part.replaceAll("x_0", x + "").replaceAll("y_0", y + "");
		try {
			y1 = (double) engine.eval(yp);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return y1;
	}

	public int getN() { return n; }
}
