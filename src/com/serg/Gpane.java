package com.serg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gpane extends JPanel {

	private De de;
	private boolean wait = false;
	private double pdg = 30.0;
	private double xStep;
	private double yStep;
	private double dx = 20;
	private double dy = 20;

	private double x0;
	private double x1;
	private double yMin;
	private double yMax;

	private int nx;
	private int ny;

	private Frame parent;

	private String calc = "Calculation";
	private int calcK = 0;

	public Gpane(Frame parent) {
		this.parent = parent;
		setBackground(Color.white);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.drawRect(0, 0, getWidth(), getHeight());

		if (de != null) {

			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("TimesRoman", Font.BOLD, 15));

			double xgLength = nx * dx;
			double ygLength = ny * dy;

			double xTxtY = 2 * pdg + ygLength + 2;
			double yTxtX = pdg - 10;

			// x //
			double axisX = 2 * pdg;
			double axisY = pdg;
			double x = x0;

			for (int i = 0; i <= nx; i++) {
				if (i % 2 == 0) {
					g2d.draw(new Line2D.Double(axisX, axisY, axisX, 1.3 * pdg + ygLength));
					g2d.drawString(String.format("%.1f", x), (float) (axisX - 10), (float) (xTxtY));
				} else
					g2d.draw(new Line2D.Double(axisX, axisY, axisX, 1.2 * pdg + ygLength));

				axisX += dx;
				x += xStep;

			}
			// y //

			axisX = 2 * pdg;
			axisY = pdg;
			x = yMax;

			for (int i = 0; i <= ny; i++) {
				if (i % 2 == 0) {
					g2d.draw(new Line2D.Double(1.7 * pdg, axisY, 2 * pdg + xgLength, axisY));
					g2d.drawString(String.format("%.1f", x), (float) (yTxtX), (float) (axisY + 5));
				} else
					g2d.draw(new Line2D.Double(1.8 * pdg, axisY, 2 * pdg + xgLength, axisY));
				axisY += dy;
				x -= yStep;

			}

			double xZero = 2 * pdg;
			double yZero = pdg + ygLength;

			g2d.draw(new Ellipse2D.Double(xZero - 2.5, yZero - 2.5, 5, 5));

			double oneX = xgLength / (nx * xStep);
			double oneY = ygLength / (ny * yStep);

			int n = de.getN();
			double tmpX = de.getX(0);
			double tmpY = de.getY(0);
			double tmpY1 = de.getY1(0);

			g2d.draw(new Ellipse2D.Double(xZero - ((tmpX - x0) * oneX) - 2.5, yZero - ((tmpY1 - yMin) * oneY) - 2.5, 5,
					5));

			for (int i = 1; i < n; i++) {
				g2d.setColor(Color.RED);
				g2d.draw(new Line2D.Double(xZero + ((tmpX - x0) * oneX), yZero - ((tmpY - yMin) * oneY),
						xZero + ((de.getX(i) - x0) * oneX), yZero - ((de.getY(i) - yMin) * oneY)));
				g2d.setColor(Color.BLUE);
				g2d.draw(new Line2D.Double(xZero + ((tmpX - x0) * oneX), yZero - ((tmpY1 - yMin) * oneY),
						xZero + ((de.getX(i) - x0) * oneX), yZero - ((de.getY1(i) - yMin) * oneY)));
				tmpX = de.getX(i);
				tmpY = de.getY(i);
				tmpY1 = de.getY1(i);
			}
		} else if (wait) {

			g2d.setColor(Color.BLACK);
			g2d.drawString(calc, (float) (getWidth() / 2 - pdg), (float) (getHeight() / 2));
			calc += " .";
			calcK++;
			if (calcK == 4) {
				calcK = 0;
				calc = "Calculation";
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		} else {
			g2d.drawRect(0, 0, getWidth(), getHeight());
		}

	}

	public void showWait() {
		de = null;
		wait = true;
		repaint();
	}

	public void addEq(De de) {
		this.de = de;
		wait = false;

		// add size proportiaonal ->
		dx = 20;
		dy = 20;
		xStep = 0.1;
		yStep = 0.1;

		yMin = de.getYmin() == Double.NEGATIVE_INFINITY ? -500 : de.getYmin();

		yMax = de.getYmax() == Double.POSITIVE_INFINITY ? 500 : de.getYmax();

		x0 = (int) (de.getXmin() % 1 == 0 ? de.getXmin() : de.getXmin() - 1);
		x1 = (int) (de.getXmax() % 1 == 0 ? de.getXmax() : de.getXmax() + 1);

		yMin = (int) (yMin % 1 == 0 ? yMin : yMin - 1);
		yMax = (int) (yMax % 1 == 0 ? yMax : yMax + 1);

		while (true) {
			nx = (int) ((x1 - x0) / xStep);
			ny = (int) ((yMax - yMin) / yStep);

			if ((int) ((nx + 1) * dx + 2 * pdg + parent.getWidth() - getWidth()) < parent.getScreenWidth()
					&& (int) ((ny + 1) * dy + 2 * pdg + parent.getHeight() - getHeight()) < parent.getScreenHeight()) {
				if (ny % 2 != 0) {
					yMax++;
					continue;
				}
				break;
			}

			if (ny / 10 != 0
					&& (int) ((ny + 1) * dy + 2 * pdg + parent.getHeight() - getHeight()) > parent.getScreenHeight()) {
				yStep *= 5;
			}
			if ((int) ((nx + 1) * dx + 2 * pdg + parent.getWidth() - getWidth()) > parent.getScreenWidth()
					&& nx / 10 != 0) {
				xStep *= 5;
			} else if (yStep < 2 && nx / 10 != 0) {
				xStep *= 5;
			}
		}
		parent.setSize((int) ((nx + 1) * dx + 2 * pdg + parent.getWidth() - getWidth()),
				(int) ((ny + 1) * dy + 2 * pdg + parent.getHeight() - getHeight()));

		repaint();
	}

}
