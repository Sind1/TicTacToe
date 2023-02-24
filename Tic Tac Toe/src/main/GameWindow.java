package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

public class GameWindow extends JPanel implements ActionListener{

	private JFrame frame;
	private JLabel wins, games;
	private int turn = 1;
	
	final JButton[] tile = new JButton[9];
	final String valX = "X";
	final String valO = "O";

	public GameWindow() {
		initialize();
		setWins();
		setGames();
		random();
	}
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	private void initialize() {
		
		this.setPreferredSize(new Dimension(600,600));;
		
		GridLayout gridLayout = new GridLayout(4, 3);
		this.setLayout(gridLayout);
		
		JButton again = new JButton("Play again");
		again.setBackground(new Color(203, 205, 255));
		again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame f = new Frame();
				f.add(new GameWindow());
			}
		});
		this.add(again);
		
		wins = new JLabel("");
		wins.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(wins);
		
		games = new JLabel("");
		games.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(games);
		
		for (int i=0; i < 9 ; i++) {
			tile[i] = new JButton();
            tile[i].setFont(new Font("Lucida Grande", Font.PLAIN, 62));
            tile[i].setFocusable(false);
            tile[i].addActionListener(this);
            this.add(tile[i]);
		}

	}	

	@Override
	public void actionPerformed(ActionEvent e) {
	
		for (int i=0; i<9;i++) {
			if (e.getSource() == tile[i]) {
				if(tile[i].getText().isEmpty()) {
					if(turn == 1) {
						tile[i].setText(valX);
						tile[i].setForeground(Color.BLACK);
					}
				}
			}
		}
		int ran;
	    Random random = new Random();
	    while (true) {
	        ran = random.nextInt(9);
	        if (tile[ran].getText().isEmpty()) {
	            tile[ran].setText(valO);
	            tile[ran].setForeground(Color.RED);
	            break;
	        }
	    }
		checkWinner();
		
	}
	public int random() {
		
		int CPU = (int) (Math.random() * 4);
		return CPU;

	}
	private void checkWinner() {
		if(checkLine(valX)) {
			return;
		}
		if(checkLine(valO)) {
			return;
		}
		if(checkDraw()) {
			return;
		}
	}
	public boolean checkDraw() {
        int i = 0;
        while (!tile[i].getText().isEmpty()) {
            if (i == tile.length - 1) {
                Arrays.stream(tile).forEach(t -> t.setEnabled(false));
                
                
                con = Connector.ConnectTo();
                try {
                	int win = 1;
        			
        			ps = con.prepareStatement("INSERT INTO score (games) VALUES (?)");
        			ps.setInt(1, win);
        			ps.executeUpdate();
        		}
        		catch(SQLException e) {
        			e.printStackTrace();
                }
                break;
            }
            i++;
        }

        return i == tile.length - 1;
    }
	
	private boolean checkLine(String markeris) {
		
		boolean allThree = false;
	
		if(!allThree) allThree = checkDirection(0,1,2,markeris);
		if(!allThree) allThree = checkDirection(3,4,5,markeris);
		if(!allThree) allThree = checkDirection(6,7,8,markeris);
		
		if(!allThree) allThree = checkDirection(0,3,6,markeris);
		if(!allThree) allThree = checkDirection(1,4,7,markeris);
		if(!allThree) allThree = checkDirection(2,5,8,markeris);
		
		if(!allThree) allThree = checkDirection(0,4,8,markeris);
		if(!allThree) allThree = checkDirection(2,4,6,markeris);
		
		return allThree;
	}
	private boolean checkDirection(int A, int B, int C, String mark) {
		
		if (mark.equals(tile[A].getText()) && mark.equals(tile[B].getText())&& mark.equals(tile[C].getText())) {
			setWin(A,B,C,mark);
			return true;
		}
		return false;
		
	}
	public void setWin(int a, int b, int c, String mark) {
		tile[a].setForeground(Color.GREEN);
		tile[b].setForeground(Color.GREEN);
		tile[c].setForeground(Color.GREEN);
		
		JOptionPane.showMessageDialog(null, mark+"'s wins");
		
		con = Connector.ConnectTo();
		try {
			int win = 1;
			
			ps = con.prepareStatement("INSERT INTO score (wins, games) VALUES (?, ?)");
			ps.setInt(1, win);
			ps.setInt(2, win);
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		setGames();
		setWins();
	
		
	}
	public void setGames() {
		con = Connector.ConnectTo();
		try {
			ps = con.prepareStatement("SELECT COUNT(games)FROM score");
			rs = ps.executeQuery("SELECT COUNT(games)FROM score");
			rs.next();
			int game = rs.getInt(1);
			games.setText("Total games: "+game+"");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		

		
	}
	public void setWins() {
		con = Connector.ConnectTo();
		try {
			ps = con.prepareStatement("SELECT COUNT(wins)FROM score");
			rs = ps.executeQuery("SELECT COUNT(wins)FROM score");
			rs.next();
			int winnings = rs.getInt(1);
			wins.setText("Total wins: "+winnings+"");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		

		
	}
	
}
