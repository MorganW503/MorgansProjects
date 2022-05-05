package checkersGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class Board extends JPanel implements MouseListener  , ActionListener
{
	
	static int cellSize=((Checkers.FRAME_SIZE)/8);
	final int checkerSize=(int) (cellSize*.8);
	public final int cShift=Checkers.COLSHIFT;
	public final int rShift=Checkers.ROWSHIFT;
	
	
	
	public static JLabel message;
	public static JLabel winMessage;
	
	
	CheckersData board;
	static boolean jumpFlag=false;
	public static int currentPlayer;
	
	public static int selectedRow=-1;
	public static int selectedCol=-1;
	
	
	
	
	
	
	
	/*
	 * main board constructor
	 * 
	 * */
	Board()
	{
		setBackground(Color.gray);
		addMouseListener(this);
		message=new JLabel(" ", JLabel.CENTER);
		winMessage=new JLabel (" ");
		winMessage.setFont(new Font("Sefir", Font.BOLD, 40));
		message.setFont(new Font ("Sefir",Font.BOLD, 14));
		message.setForeground(Color.green);
		board = new CheckersData();
		
		JButton newGameButton =new JButton ("New Game");
		newGameButton.setLayout(null);
		newGameButton.addActionListener(this);
		
		newGameButton.setBounds(10, 10, 30, 50);
		newGameButton.setBackground(Color.BLUE);
		newGameButton.setBorderPainted(true);
		add(newGameButton);
		repaint();
		
	}// end board constructor
	
			
		
	@Override
	public void actionPerformed(ActionEvent evt)
	{
			
			startGame();
			repaint();
		
	}
	
	private void startGame() 
	{
		currentPlayer=CheckersData.RED;
		selectedRow=-1;
		add(message);
		message.setText("It is currently the red players turn please select a piece");
		
	}
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		
		//newGameButton.setBackground(Color.BLUE);
//		newGameButton.setColor(Color.BLUE);
//		((Graphics) newGameButton).fillRect(Checkers.FRAME_SIZE/2,Checkers.FRAME_SIZE/2 , 50, 60);
		
		for (int row=0; row<8;row++) 
		{
			for(int col=0;col<8; col++) 
			{
				int rowPos=row*cellSize+rShift;
				int colPos=col*cellSize+cShift;
				int checkerRowPos=row*cellSize+rShift+8;
				int checkerColPos=col*cellSize+cShift+8;
				int kRowPos=checkerRowPos+(cellSize/2)-20;
				int kColPos=checkerColPos+(cellSize/2);
				
				if ((row+col)%2==0) 
				{
					g.setColor(Color.white);
					g2.fillRect(rowPos,colPos, cellSize, cellSize);
				}
				else 
				{
					g.setColor(Color.black);
					g2.fillRect(rowPos,colPos, cellSize, cellSize);
					
				}//done painting board
				//all cases for piece designation
				switch (board.pieceAt(row, col)) 
				{
				case CheckersData.RED:
					g.setColor(Color.RED);
					g.fillOval(checkerRowPos,checkerColPos, checkerSize, checkerSize);
					break;
				
				case CheckersData.BLACK:
					g.setColor(Color.BLACK);
					g.fillOval(checkerRowPos,checkerColPos, checkerSize, checkerSize);
					break;
				
				case CheckersData.REDKING:
					g.setColor(Color.BLACK);
					g.fillOval(checkerRowPos, checkerColPos, checkerSize, checkerSize);
					
					g.setColor(Color.RED);
					g.fillOval(checkerRowPos+6,checkerColPos+6, checkerSize-12, checkerSize-12);
					
					g.setColor(Color.white);
					g.setFont(new Font("Serif", Font.BOLD, 30));
					g.drawString("K", kRowPos, kColPos);
					break;
				
				case CheckersData.BLACKKING:
					g.setColor(Color.RED);
					g.fillOval(checkerRowPos,checkerColPos, checkerSize, checkerSize);
					
					g.setColor(Color.BLACK);
					g.fillOval(checkerRowPos+5,checkerColPos+5, checkerSize-10, checkerSize-10);
					
					g.setColor(Color.white);
					g.setFont(new Font("Serif", Font.BOLD, 30));
					g.drawString("K", kRowPos, kColPos);
					break;
				
				}// end switch
				
			}
		}
		
		
		
		
	}//painting on the panel
	
	/*
	 * this checks that the mouse press was in the frame,
	 * as well as convert the press into row, column coordinates
	 * */
		
	public void mousePressed (MouseEvent evt) 
	{
		
		int clickX =evt.getX();
		int clickY=evt.getY();
		int leftBound=rShift;
		int rightBound=cellSize*8+rShift;
		int topBound=cShift;
		int bottomBound= cellSize*8+cShift;
		int row, col;
	
		
		//makes sure the click is on the board.
		if (clickX> leftBound && clickX<rightBound && clickY>topBound && clickY<bottomBound)
		{
			row= (clickX-rShift)/(cellSize);
			col=(clickY-cShift)/(cellSize);
		
		
			if(!jumpFlag) 
			{
				
				if (board.pieceAt(row, col)!=CheckersData.EMPTY || selectedRow>=0)
				{
						doClickSquare(row, col);
				}
				else 
				{
					
					message.setText("this is an Empty space at " + row + " , " + col);
					selectedRow=-1;
				}
			}
			else
			{
				if(board.canJump(currentPlayer, selectedRow, selectedCol, row,col))
				{
					doClickSquare(row, col);
				}
				message.setText("You cannot move here, please jump to the available space with the same piece.");
			}
		}
		
	}
	public void mouseReleased(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}

	
	
	void doClickSquare(int row, int col) 
	{
		
		
		add(message);
		
		if (selectedRow<0) 
		{
			
			if (currentPlayer==CheckersData.RED && board.pieceAt(row, col)==CheckersData.REDKING)
			{
				message.setText("you have selected your Red king at "+ row+ " , "+ col);
				selectedRow=row;
				selectedCol=col;
			}
			
			else if (currentPlayer==CheckersData.BLACK && board.pieceAt(row, col)==CheckersData.BLACKKING)
			{
				message.setText("you have selected your Black king at "+ row+ " , "+ col);
				selectedRow=row;
				selectedCol=col;
			}
			else if (currentPlayer==board.pieceAt(row, col))
			{
				message.setText("you have selected your piece at "+ row + " , "+ col);
				selectedRow=row;
				selectedCol=col;
			}
			else 
			{
				message.setText("You cannot select that piece, please select your own piece");
				selectedRow=-1;
			}
		}
		
		
		else // if there is a piece selected
		{
			if (board.canMove(currentPlayer, selectedRow, selectedCol, row, col))
			{
				System.out.println(" can move");
				board.doMove(selectedRow, selectedCol, row, col);
				if (!board.checkKing(row, col))
				{
					message.setText("you have moved your piece from "+ selectedRow +" , " + selectedCol + " to " + row +" , "+ col);
					selectedRow=-1;

				
					
					if (currentPlayer== CheckersData.RED||currentPlayer== CheckersData.REDKING) 
					{
						currentPlayer=CheckersData.BLACK;
						message.setText("Black Player, it is now your turn. Please select a piece");
						
					}
					else if(currentPlayer==CheckersData.BLACK||currentPlayer==CheckersData.BLACKKING)
					{
						currentPlayer=CheckersData.RED;
						message.setText("Red Player, it is now your turn. Please select a piece");
					}
				}
				
				repaint();
			}// check if it can move there
			
			
			else if (board.canJump(currentPlayer, selectedRow, selectedCol, row, col))
			{
				board.doJump(selectedRow, selectedCol, row, col, (selectedRow+row)/2, (selectedCol+col)/2);
				
					
				
				if (!board.checkKing(row, col))
				{
					
					jumpFlag=true;
					selectedRow=row;
					selectedCol=col;
					
					
						if (board.checkJump(currentPlayer, selectedRow, selectedCol)>0)
						{
							message.setText("you have another jump! you must do it");
							
						}//end check if next you can make another jump
						
						
						else  // if they can't make another jump
						{
							if (currentPlayer==CheckersData.BLACK)
							{
								currentPlayer= CheckersData.RED;
								message.setText("Red: it it now your turn. pleasemselect a piece");
							}
							if (currentPlayer==CheckersData.RED)
							{
								currentPlayer= CheckersData.BLACK;
								message.setText("Black: it is your turn, select a piece");
							}
								
							selectedRow=-1;
							jumpFlag=false;
						}	
				}
				
					
				repaint();
			}// end check if can jump
			
			else 
			{
				message.setText("You cannot move there please select a different piece to move");
				selectedRow=-1;
			}	
			
		}// end else 
		
		
		
				
		
		
		//void endGameCheck() 
		//{
	//		if (int board.contains(CheckersData.RED))
		//}
		
		
		//message.setText("there was a click at  " +row + " , "+ col);
		
		
			//moveOptions=board.getPossibleMoves(currentPlayer);
		
	}// end doclickSquare
	
	public static void doEndSequence(int player) 
	{
		if (player==CheckersData.RED)
		{
			winMessage.setForeground(Color.RED);
			winMessage.setText("RED WINS");
			currentPlayer=0;
		}
		else if ( player==CheckersData.BLACK)
		{
			winMessage.setForeground(Color.BLACK);
			winMessage.setText("BLACK WINS");
			currentPlayer=0;
		}
		
	}
	
	
	

	
	
	
}//end public class Board
