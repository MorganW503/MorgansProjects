package checkersGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CheckersData extends JPanel
{

	static final int EMPTY=0, 
			RED=1, REDKING=2, 
			BLACK=3, BLACKKING=4;
	
	static int[][] board;
	
	//checkers data constructor
	
	CheckersData()
	{
		board =new int [8][8];
		setupGame();
	}
	
	
	
	//setting up the game
	static final void setupGame() 
	{
			for (int col=0; col<8;col++) 
			{
				for (int row=0; row<8;row++) 
				{
					if ((row+col)%2==0)
					{
						if(col<3) 
						{
							board[row][col]=RED;
						}
						
						else if (col>4) 
						{
							board[row][col]=BLACK;
						}
						else
						{
							board[row][col]=EMPTY;
						}
					}
					else 
					{
					board[row][col]=EMPTY;
					}
				}
			}
		
	}//end of setting up the game with the checkers positions
	
	/*CheckersMove[] getPossibleMoves(int player) 
	{
		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();
		CheckersMove[]arrayOfMoves= new CheckersMove[moves.size()];
		
		for (int row=0; row<8;row++) 
		{
			for(int col=0;col<8; col++) 
			{
				if(board[row][col]==player) 
				{
					if (canMove(player, row, col, row+1, col+1)) 
					{
						moves.add(new CheckersMove(row, col, row+1, col+1));
					}
					if (canMove(player, row, col, row+1, col-1)) 
					{
						moves.add(new CheckersMove(row, col, row+1, col+1));
					}
				}
			}
		}
		
		//building container to return array of moves
		for (int i=0;i< moves.size();i++) 
		{
			arrayOfMoves[i]=moves.get(i);
		}
		return arrayOfMoves;
		
	}//end get possible moves*/
	
	
	
	//move piece 
	static void doMove(int r1, int c1, int r2, int c2 ) 
	{
		board[r2][c2]=board[r1][c1];
		board[r1][c1]=EMPTY;
		
	}
	
	//move piece  and remove jumped piece
	static void doJump (int r1, int c1, int r2, int c2, int r3, int c3) 
	{
		board[r2][c2]=board[r1][c1];
		board[r1][c1]=EMPTY;
		board[r3][c3]=EMPTY;
	}
	
	//check if it can jump
	boolean canJump(int player, int r1, int c1, int r2, int c2) 
	{
		int r3= (r1+r2)/2;
		int c3= (c1+c2)/2;
		
		if	(r2<0||r2>7||c2<0||c2>7) //check if its on the board
		{
			return (false); 
		}
		if (board[r2][c2] !=EMPTY) //check if space is taken
		{
			return (false);
		}
		if (player== board[r1][c1]) //Red player
		{				//
			if ((board[r1][c1]==RED && c1<c2)||(board[r1][c1]==BLACK&& c1>c2)&& Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2 )
			{
				if (board[r3][c3]==player||board[r3][c3]==player+1||board[r3][c3]==EMPTY) 
				{
					return false;
				}
				else 
				{
				return true;
				}
			}
			else 
			{
				return false;
			}
		}
		else if (board[r1][c1]==player+1) 
		{											//check that the piece is moving the right way
			if (board[r3][c3]==player||board[r3][c3]==player+1||board[r3][c3]==EMPTY) 
			{
				return false;
			}
			else 
			{
			return true;
			}
		}
		else 
		{
			return false;
		}
		
	}//END CAN JUMP CHECK
	
	//check to see if it cam move
	boolean canMove(int player, int r1, int c1, int r2, int c2) 
	{
		if	(r2<0||r2>7||c2<0||c2>7) //check if its on the board
		{
			return (false); 
		}
		if (board[r2][c2] !=EMPTY) //check if space is taken
		{
			
			return (false);
		}
		
		
		else
		{				
				if(( (board[r1][c1]==RED&& c1<c2) || (board[r1][c1]==BLACK && c1>c2) )  && Math.abs(r2-r1)==1 && Math.abs(c2-c1)==1 )
				{
				
					return true;
					
				}
				
				if(board[r1][c1]==REDKING||board[r1][c1]==BLACKKING && Math.abs(r2-r1)==1 && Math.abs(c2-c1)==1)
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
			
			
			
	}//end of can move check
	
	
	
	
	
	
	public void winCheck(int player)
	{
		int totalRedMoves=0;
		int totalBlackMoves=0;
		int redPieces=0;
		int blackPieces=0;
		
		for (int row=0;row<8;row++)
		{
			for(int col=0; col<8;col++)
			{
				
				switch (board[row][col])
				{
				case RED:
					redPieces++;
					totalRedMoves = totalRedMoves+checkMove(player, row, col)
										+checkJump(player, row, col);
					break;
				
				case BLACK:
					blackPieces++;
					totalBlackMoves = totalBlackMoves+checkMove(player, row, col)
									+checkJump(player, row, col);
					break;
				
				case REDKING:
					totalRedMoves=totalRedMoves+checkMove(player, row, col);
					redPieces++;
					break;
					
				case BLACKKING:
				blackPieces++;
				totalBlackMoves=totalBlackMoves+checkMove(player, row, col)+checkJump(player, row, col);
					break;
					
				}
			}
		}			
		if (player==BLACK &&(totalBlackMoves==0 || blackPieces==0))
		{
			Board.doEndSequence(RED);
		}
		if (player==RED && (totalRedMoves==0||redPieces==0))
		{
			Board.doEndSequence(BLACK);
		}
		
		
		
	}  //win check
	
	
	public boolean checkKing(int row, int col) 
	{
		if (board[row][col]==RED && col==7 ) 
		{
			board[row][col]=REDKING;
			Board.message.setText("Nice king! Black, it is your turn, please select a piece");
			Board.currentPlayer=BLACK;
			Board.jumpFlag=false;
			Board.selectedRow=-1;
			return true;
		}
		else if (board[row][col]==BLACK && col==0)
		{
			board[row][col]=BLACKKING;
			Board.message.setText("Nice king! Red, it is your turn, please select a piece");
			Board.currentPlayer=RED;
			Board.jumpFlag=false;
			Board.selectedRow=-1;
			return true;
		}
		else 
		{
			return false;
		}
	}
	int checkJump(int player,int r1, int c1)
	{
		
		int jumpsAvailable=0;
		if (Board.jumpFlag && board[r1][c1]==player||board[r1][c1]==player+2)
		{
			
			if(board[r1][c1]==RED)
			{
				if (canJump(player, r1, c1, r1+2, c1+2))
				{
					jumpsAvailable++;
					
				}
				if (canJump(player, r1, c1, r1-2, c1+2))
						
				{
					jumpsAvailable++;
					
				}
				
			}
			else if (board[r1][c1]== BLACK)
			{
				if (canJump(player, r1, c1, r1+2, c1-2))
				{
					jumpsAvailable++;
					
				}
				if (canJump(player, r1, c1, r1-2, c1-2))
						
				{
					jumpsAvailable++;
					
				}
				
			}
			else if (board[r1][c1]== BLACKKING||board[r1][c1]==REDKING)
			{
				if (canJump(player, r1, c1, r1+2, c1-2))
				{
					jumpsAvailable++;
					
				}
				if (canJump(player, r1, c1, r1+2, c1+2))
						
				{
					jumpsAvailable++;
					
				}
				if (canJump(player, r1, c1, r1-2, c1-2))
					
				{
					jumpsAvailable++;
					
				}
				if (canJump(player, r1, c1, r1-2, c1+2))
					
				{
					jumpsAvailable++;
					
				}
			}
		}
		return jumpsAvailable;
	}// end of checkJump
	int checkMove(int player, int r1, int c1)
	{
		
		int possibleMoves=0;
		if(board[r1][c1]==RED)
		{
			if (canMove(player, r1, c1, r1+1, c1+1))
			{
				possibleMoves++;
			}
			if (canMove(player, r1, c1, r1-1, c1+1))
			{
				possibleMoves++;
			}
		}
		else if (board[r1][c1]==BLACK)
		{
			if (canMove(player, r1, c1, r1+1, c1-1))
			{
				possibleMoves++;
			}
			if (canMove(player, r1, c1, r1-1, c1-1))
					
			{
				possibleMoves++;
			}
		}
		else if (board[r1][c1]==REDKING|| board[r1][c1]==BLACKKING)	
		{
				
					if (canMove(player, r1, c1, r1+1, c1-1))
					{
						possibleMoves++;
					}
					if (canMove(player, r1, c1, r1-1, c1-1))
					{
						possibleMoves++;
					}
					if(canMove(player, r1, c1, r1+1, c1+1))
					{
						possibleMoves++;
					}
					if(canMove(player, r1, c1, r1-1, c1+1))
					{
						possibleMoves++;
					}
			
		}
			
		return possibleMoves;
		
	}// end of checkMove
	
	
	// way to get piecetype from the board
	int pieceAt(int row, int col) {
		
		return board[row][col];
	}
	
	
}
