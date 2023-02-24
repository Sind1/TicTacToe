package main;

import javax.swing.*;

public class Frame extends JFrame {

	public Frame() {
		
		this.add(new GameWindow());
        this.setTitle("Tic Tac Toe");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
	}

}
