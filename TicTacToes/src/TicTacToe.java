import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener{
	
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons = new JButton[9];
	boolean player1_turn;
	JButton playAgainButton = new JButton("Play Again");
	
	
	
	
	
	
	TicTacToe(){
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free" , Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Tic-tac-toe");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);
		
		playAgainButton.setFont(new Font("Ink Free", Font.BOLD, 30));
	    playAgainButton.setFocusable(false);
	    playAgainButton.addActionListener(this);
	    
	    title_panel.add(playAgainButton, BorderLayout.SOUTH);
		
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(150,150,150));
		
		for(int i =0; i<9; i++) {
			buttons[i]=new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli", Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
			
			
			
		}
		
		
		
		
		title_panel.add(textfield);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		
		firstTurn();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == playAgainButton) {
	        resetGame();
	    } 
		else {
		for(int i =0; i<9;i++) {
			if(e.getSource()==buttons[i]){
				if(player1_turn) {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(255,0,0));
						buttons[i].setText("X");
						player1_turn=false;
						textfield.setText("0 turn:");
						check();
					}
				}
				else {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(0,0,255));
						buttons[i].setText("O");
						player1_turn=true;
						textfield.setText("X turn:");
						check();
					}
					
				}
				
			}
		}
		}
		
	}
	
	public void resetGame() {
	    for (int i = 0; i < 9; i++) {
	        buttons[i].setEnabled(true);
	        buttons[i].setText("");
	        buttons[i].setBackground(new JButton().getBackground());
	    }
	    firstTurn();
	}
	
	public void firstTurn() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(random.nextInt(2) == 0) {
			player1_turn = true;
			textfield.setText("X turn");
			
			
		}
		else {
			player1_turn = false;
			textfield.setText("0 turn");
			
		}
		
	}
	
	public void check() {
		
		if(
				(buttons[0].getText()=="X") &&
				(buttons[1].getText()=="X") &&
				(buttons[2].getText()=="X")
				) {
			xWins(0,1,2);
			
		}
		
		if(
				(buttons[3].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[5].getText()=="X")
				) {
			xWins(3,4,5);
			
		}
		if(
				(buttons[6].getText()=="X") &&
				(buttons[7].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(6,7,8);
			
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[3].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
			xWins(6,3,6);
			
		}
		if(
				(buttons[1].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[7].getText()=="X")
				) {
			xWins(1,4,7);
			
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[5].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(2,5,8);
			
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(0,4,8);
			
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
			xWins(2,4,6);
			
		}
		
		if(
				(buttons[0].getText()=="O") &&
				(buttons[1].getText()=="O") &&
				(buttons[2].getText()=="O")
				) {
			oWins(0,1,2);
			
		}
		
		if(
				(buttons[3].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[5].getText()=="O")
				) {
			oWins(3,4,5);
			
		}
		if(
				(buttons[6].getText()=="O") &&
				(buttons[7].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(6,7,8);
			
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[3].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
			oWins(6,3,6);
			
		}
		if(
				(buttons[1].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[7].getText()=="O")
				) {
			oWins(1,4,7);
			
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[5].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(2,5,8);
			
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(0,4,8);
			
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
			oWins(2,4,6);
			
		}
		
		if (isBoardFull() && !textfield.getText().contains("wins")) {
	        for (JButton button : buttons) {
	            button.setEnabled(false);
	        }
	        textfield.setText("Tie!");
	    }
		
		
		
		
	}
	
	
	/* isBoardFull() breakDown: for (JButton button : buttons): This loop iterates over each button in the buttons array. Each JButton represents a cell on the Tic-Tac-Toe board.

if (button.getText().equals("")): For each button, this line checks if the text of the button is an empty string (""). The getText() method returns the current text of the button. In the context of your game, a button's text is either "X", "O", or "" (empty). If the text is "", it means no player has marked that cell yet.

return false: If any button is found to be empty (its text is ""), the method immediately returns false, indicating that the board is not yet full.

return true: If the loop completes without finding any empty cells, the method returns true, indicating that all cells on the board are filled.

So, in the context of your game, "" is used to check if a cell in the game board is still unmarked and available for a player to make a move.
*/
	
	public boolean isBoardFull() {
	    for (JButton button : buttons) {
	        if (button.getText().equals("")) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	public void xWins(int a, int b, int c){
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i =0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("X wins");
		
		
		playAgainButton.setEnabled(true);
	}
	
public void oWins(int a, int b, int c){
	buttons[a].setBackground(Color.GREEN);
	buttons[b].setBackground(Color.GREEN);
	buttons[c].setBackground(Color.GREEN);
	
	for(int i =0;i<9;i++) {
		buttons[i].setEnabled(false);
	}
	textfield.setText("O wins");
	
	playAgainButton.setEnabled(true);
	}
	
	
	
	
	
	
	
	

}

