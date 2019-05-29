package com.serg;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {

	private ArrayList<DU> duL = new ArrayList<>();

	
	public Frame() {
		JFrame f = new JFrame("EulerDU");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				Shape theCircle = new Ellipse2D.Double(centerX - radius, centerY - radius, 2.0 * radius, 2.0 * radius);
				g2d.draw(theCircle);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(320, 240);
			}
		});
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public void addDU(DU du) {
		duL.add(du);
	}

}