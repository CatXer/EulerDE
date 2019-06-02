package com.serg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {

	private JTextField inpEq;
	private JTextField y0;
	private JTextField y1;
	private JTextField x0;
	private JTextField xN;
	private JTextField h;

	private JButton solve;
	private Gpane gPanel;

	private int screenWidth;
	private int screenHeight;
	// y(0) = 1
	// y1(0) = -1
	// x0 = 0
	// xN = 3
	// h = 0.01

	// y0=[1;-1];x0=0;x=0:0.1:3;

	public Frame() {
		super("EulerDE");
		setUIFont(new FontUIResource(new Font("Arial", Font.BOLD, 20)));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int) (screenSize.getWidth() / 1.5f), (int) (screenSize.getHeight() / 1.5f));
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.cyan);
		inpEq = new JTextField("y_2; Math.exp(-(x_1)) - (y_1)", 30);
		solve = new JButton("Solve!");
		solve.addActionListener(this);
		topPanel.add(inpEq);
		topPanel.add(solve);

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.cyan);
		leftPanel.setLayout(new GridLayout(5, 2, 5, 10));

		JLabel ly0 = new JLabel("y0=");
		y0 = new JTextField(4);
		JLabel ly1 = new JLabel("y`0=");
		y1 = new JTextField(4);
		JLabel lx0 = new JLabel("x0=");
		x0 = new JTextField(4);
		JLabel lxN = new JLabel("xN=");
		xN = new JTextField(4);
		JLabel lh = new JLabel("h=");
		h = new JTextField(4);

		leftPanel.add(ly0);
		leftPanel.add(y0);
		leftPanel.add(ly1);
		leftPanel.add(y1);
		leftPanel.add(lx0);
		leftPanel.add(x0);
		leftPanel.add(lxN);
		leftPanel.add(xN);
		leftPanel.add(lh);
		leftPanel.add(h);

		gPanel = new Gpane(this);

		// Размещаем нашу панель в панели содержимого
		getContentPane().add(topPanel, BorderLayout.PAGE_START);
		getContentPane().add(gPanel, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		// Устанавливаем оптимальный размер окна
		pack();
		// Открываем окно
		// setVisible(true);
	}

	public static void setUIFont(FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
				Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String equat = inpEq.getText();
		if (!equat.equals("") && equat.contains("y_2") && equat.contains("x_1")) {
			if (isNuber(y0.getText()) && isNuber(y1.getText()) && isNuber(x0.getText()) && isNuber(xN.getText())
					&& isNuber(h.getText())) {
				double x_0 = Double.parseDouble(x0.getText());
				double x_N = Double.parseDouble(xN.getText());
				double y_0 = Double.parseDouble(y0.getText());
				double y_1 = Double.parseDouble(y1.getText());
				double h_ = Double.parseDouble(h.getText());
				gPanel.showWait();
				new Thread() {
					@Override
					public void run() {
						De de = new De(equat);
						de.Euler(x_0, x_N, y_0, y_1, h_);
						gPanel.addEq(de);
					}
				}.start();

			} else {
				JOptionPane.showMessageDialog(null, "Check your param input.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Check your equation input.");
		}
	}

	public static boolean isNuber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public int getScreenWidth() { return screenWidth; }

	public int getScreenHeight() { return screenHeight; }

}
