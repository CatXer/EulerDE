package com.serg;

public class Starter {
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Frame().setVisible(true);
			}
		});
	}
}
