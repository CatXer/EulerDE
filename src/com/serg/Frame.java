package com.serg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame {

	private DU du;
	private int width;
	private int height;
	private JTextField tfA;
	private JTextField tfB;
	private JTextField tfC;
	private JTextField tfD;

	private double pdg = 40.0;

	private int curOp1 = 0;
	private int curOp2 = 0;
	private String[] op = new String[] { "+", "-", "*", "/" };

	private double X0 = 0;
	private double X1 = 3;
	private double Y0 = -1;
	private double Y1 = 1;

	public Frame() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (screenSize.getWidth() / 1.5f);
		height = (int) (screenSize.getHeight() / 1.5f);
		JFrame f = new JFrame("EulerDU");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		f.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.cyan);
		topPanel.setLayout(new FlowLayout());
		Font font1 = new Font("SansSerif", Font.BOLD, 20);

		tfA = new JTextField("1", 2);
		tfA.setFont(font1);
		tfA.setHorizontalAlignment(JTextField.RIGHT);

		Label ltyy = new Label("y''");
		ltyy.setFont(font1);

		JButton op1 = new JButton("+");
		op1.setFont(font1);
		op1.setHorizontalAlignment(JTextField.CENTER);
		op1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (curOp1 < op.length - 1)
					curOp1++;
				else
					curOp1 = 0;
				op1.setText(op[curOp1]);
			}
		});
		op1.setBackground(Color.cyan);

		tfB = new JTextField("1", 2);
		tfB.setFont(font1);
		tfB.setHorizontalAlignment(JTextField.RIGHT);

		Label lty = new Label("y'");
		lty.setFont(font1);

		JButton op2 = new JButton("+");
		op2.setFont(font1);
		op2.setHorizontalAlignment(JTextField.CENTER);
		op2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (curOp2 < op.length - 1)
					curOp2++;
				else
					curOp2 = 0;
				op2.setText(op[curOp2]);
			}
		});
		op2.setBackground(Color.cyan);

		tfC = new JTextField("1", 2);
		tfC.setFont(font1);
		tfC.setHorizontalAlignment(JTextField.RIGHT);

		Label lt = new Label("y");
		lt.setFont(font1);

		Label ltEq = new Label("=");
		ltEq.setFont(font1);

		tfD = new JTextField("0", 6);
		tfD.setFont(font1);
		tfD.setHorizontalAlignment(JTextField.CENTER);

		topPanel.add(tfA);
		topPanel.add(ltyy);
		topPanel.add(op1);
		topPanel.add(tfB);
		topPanel.add(lty);
		topPanel.add(op2);
		topPanel.add(tfC);
		topPanel.add(lt);
		topPanel.add(ltEq);
		topPanel.add(tfD);

		// Paint Panel;
		JPanel graphPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				int width = getWidth();
				int height = getHeight();

				double h = 0.1;

				if (Y0 > Y1) {
					Y0 = Y0 + Y1;
					Y1 = Y0 - Y1;
					Y0 = Y0 - Y1;
				}
				if (X0 > X1) {
					X0 = X0 + X1;
					X1 = X0 - X1;
					X0 = X0 - X1;
				}
				int nx = (int) ((X1 - X0) / h);
				int ny = (int) ((Y1 - Y0) / h);

				double Dx = (width - 2 * pdg) / nx;
				double Dy = (height - 2 * pdg) / ny;

				double X0G = 0;
				double Y0G = 0;

				///////////

				double GraphXY = pdg;
				double MathXY = X0;
				// x//
				g2d.setFont(new Font("TimesRoman", Font.BOLD, 15));
				for (int i = 0; i <= nx; i++) {
					g2d.draw(new Line2D.Double(GraphXY, pdg, GraphXY, height - pdg + 5));
					if (i % 2 == 0)
						g2d.drawString(String.format("%.1f", MathXY), (float) (GraphXY - 10), (float) (height - 15));
					if (MathXY > -0.001 && MathXY < 0.001)
						X0G = GraphXY;
					GraphXY += Dx;
					MathXY += h;

				}

				GraphXY = pdg;
				MathXY = Y1;
				// y//
				for (int i = 0; i <= ny; i++) {
					g2d.draw(new Line2D.Double(pdg - 5, GraphXY, width - pdg, GraphXY));
					if (i % 2 == 0)
						g2d.drawString(String.format("%.1f", MathXY), (float) (pdg - 30), (float) (GraphXY + 5));
					if (MathXY > -0.001 && MathXY < 0.001)
						Y0G = GraphXY;
					GraphXY += Dy;
					MathXY -= h;

				}

				double y = Y1, y1 = Y0, x = X0, tmpX, tmpY, tmpY1;
				h = 0.01;
				int n = (int) ((X1 - X0) / h);
				double oneX = (width - 2 * pdg) / (X1 - X0);
				double oneY = (height - 2 * pdg) / (Y1 - Y0);
				System.out.println(oneX);
				System.out.println(oneY);
				for (int i = 1; i <= n + 1; i++) {
					y = y + h * y1;
					y1 = y1 + h * (-y + Math.exp(-x));

					System.out.println(String.format("X: %.3f    Y: %.3f    Z: %.3f", x, y, y1));

					tmpX = x;
					tmpY = y;
					tmpY1 = y1;

					x += h;
					i++;

					y = y + h * y1;
					y1 = y1 + h * (-y + Math.exp(-x));

					System.out.println(String.format("X: %.3f    Y: %.3f    Z: %.3f", x, y, y1));

					x += h;

					g2d.draw(new Line2D.Double(X0G + tmpX * oneX, Y0G - tmpY * oneY, X0G + x * oneX, Y0G - y * oneY));
					g2d.draw(new Line2D.Double(X0G + tmpX * oneX, Y0G - tmpY1 * oneY, X0G + x * oneX, Y0G - y1 * oneY));

				}
				/*
				 * Shape ySh = new Ellipse2D.Double(X0G + x * (width - 2 * pdg) / (float) (X1 -
				 * X0) - 2, Y0G + y * (height - 2 * pdg) /(float) (Y1 - Y0) - 2, 4.0, 4.0);
				 * Shape y1Sh = new Ellipse2D.Double(X0G + x * (width - 2 * pdg) / (float) (X1 -
				 * X0) - 2, Y0G + y1 * (height - 2 * pdg) / (float) (Y1 - Y0) - 2, 4.0, 4.0);
				 */
			}

		};

		f.add(topPanel, BorderLayout.PAGE_START);
		f.add(graphPane, BorderLayout.CENTER);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void addDU(DU du) {
		this.du = du;
	}

}