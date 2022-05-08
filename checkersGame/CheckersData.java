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
	void doJump (int r1, int c1, int r2, int c2, int r3, int c3) 
	{	
		System.out.println(" we just jumped over a "+ board[r3][c3]);
		board[r3][c3]=EMPTY;
		
		board[r2][c2]=board[r1][c1];
		board[r1][c1]=EMPTY;
	}
	
	//check if it can jump
	public static boolean canJump(int player, int r1, int c1, int r2, int c2) 
	{
		int r3= (r1+r2)/2;
		int c3= (c1+c2)/2;
		
		if	(r2<0||r2>7||c2<0||c2>7) //check if its on the board
		{
			return false; 
		}
		if (board[r2][c2] !=EMPTY||board[r3][c3]==EMPTY) //check if space is taken or if jumped space is empty
		{
			return false;
		}
		if (player== board[r1][c1]) 
		{				//
			if ((board[r1][c1]==RED && c1<c2)||(board[r1][c1]==BLACK&& c1>c2)&& Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2 )
			{
				if (board[r3][c3]==player||board[r3][c3]-1==player) 
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
				return  false;
			}
		}
		else if ((board[r1][c1]==REDKING|| board[r1][c1]==BLACKKING) && Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2) 
		{											//check that the piece is moving the right way
			if (player==RED&& board[r3][c3]!=RED||board[r3][c3]!=REDKING) 
			{
				return true;
			}
			else if (player==BLACK&& board[r3][c3]!=BLACK && board[r3][c3]!=BLACKKING) 
			{
				return true;
			}
			else
			{
			return false;
			}
		}
		else 
		{
			return false;
		}
		
	}//END CAN JUMP CHECK
	
	//check to see if it cam move
	static boolean canMove(int player, int r1, int c1, int r2, int c2) 
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
				
				else if(board[r1][c1]==REDKING||board[r1][c1]==BLACKKING && Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2)
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
	}//end of can move check
	
	// this is a non returning meathod to see if someone has won.
	public static void winCheck(int player)
	{
		int totalRedMoves=0;
		int totalBlackMoves=0;
		int redPieces=0;
		int blackPieces=0;
		
		
		for (int row=0;row<8;row++)
		{
			for(int col=0; col<8;col++)
			{
				int moves=0;
				switch (board[row][col])
				{
				case RED:
					redPieces++;
					moves=checkMove(player, row, col)+checkJump(player, row, col);
					totalRedMoves = totalRedMoves+moves;
					break;
				
				case BLACK:
					blackPieces++;
					moves=checkMove(player, row, col)+checkJump(player, row, col);
					totalBlackMoves = totalBlackMoves+moves;
							
					break;
				
				case REDKING:
					moves=checkMove(player, row, col)+checkJump(player, row, col);
					totalRedMoves = totalRedMoves+moves;
					redPieces++;
					break;
					
				case BLACKKING:
				blackPieces++;
				moves=checkMove(player, row, col)+checkJump(player, row, col);
				totalBlackMoves = totalBlackMoves+moves;
					break;
					
				}
			}
		}			
		if (player==BLACK &&(totalBlackMoves==0 || blackPieces==0))
		{
			Board.gameInProgress=false;
			Board.doEndSequence(RED);
		}
		if (player==RED && (totalRedMoves==0||redPieces==0))
		{
			Board.gameInProgress=false;
			Board.doEndSequence(BLACK);
		}
		
		
		
	}  //win check
	
	//this checks for if a piece needs to be kinged after a move.
	// if it does, it turnes into a king and ends the turn.
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
		else if (board[row][col]==REDKING||board[row][col]==BLACKKING)
		{
			return false;
		}
		else 
		{
			return false;
		}
	}
	
	// this is called only to see if a piece has options, 
	//not as  a check for if the attempted move was possible
	static int checkJump(int player,int r1, int c1)
	{
		int rpos=r1+2, cpos=c1+2, rneg=r1-2, cneg=c1-2;
		int jumpsAvailable=0;
		if (rpos>7||cpos>7||rneg<0||cneg<0) 
		{
			return 0;
		}
		else if (board[r1][c1]==player||board[r1][c1]==player+1)
		{
			if(board[r1][c1]==RED)
			{
				if (canJump(player, r1, c1, rpos, cpos))
				{
					jumpsAvailable++;
				}
				if (canJump(player, r1, c1, rneg, cpos))
				{
					jumpsAvailable++;
				}
			}
			else if (board[r1][c1]== BLACK)
			{
				if (canJump(player, r1, c1, rpos, cneg))
				{
					jumpsAvailable++;
				}
				if (canJump(player, r1, c1, rneg, cneg))
				{
					jumpsAvailable++;
				}
				
			}
			//this is becuse the Red king and Black king move the same
			else if (board[r1][c1]== BLACKKING||board[r1][c1]==REDKING)
			{
				if (canJump(player, r1, c1, rpos, cneg))
				{
					jumpsAvailable++;
				}
				if (canJump(player, r1, c1, rpos, cpos))
				{
					jumpsAvailable++;
				}
				if (canJump(player, r1, c1, rneg, cneg))
				{
					jumpsAvailable++;
				}
				if (canJump(player, r1, c1, rpos, cpos))
				{
					jumpsAvailable++;
				}
			}
			return jumpsAvailable;
		}
		
		else
		{
			return 0;
		}
	}// end of checkJump
	
	//this is a check to see if there are any possible moves of a piece
	//not as a check to see if the clicked square is a move
	static int checkMove(int player, int r1, int c1)
	{
		int rpos=r1+1, cpos=c1+1, rneg=r1-1, cneg=c1-1;
		int possibleMoves=0;
			
		if (rpos>7||cpos>7||rneg<0||cneg<0)
		{
			return 0;
		}
		else 
		{
			if(board[r1][c1]==RED)
			{
				if (canMove(player, r1, c1, rpos, cpos))
				{
					possibleMoves++;
				}
				if (canMove(player, r1, c1, rneg, cpos))
				{
					possibleMoves++;
				}
			}
			else if (board[r1][c1]==BLACK)
			{
				if (canMove(player, r1, c1, rpos, cneg))
				{
					possibleMoves++;
				}
				if (canMove(player, r1, c1, rneg, cneg))
						
				{
					possibleMoves++;
				}
			}
			else if (board[r1][c1]==REDKING|| board[r1][c1]==BLACKKING)	
			{
					
				if (canMove(player, r1, c1, rpos, cneg))
				{
					possibleMoves++;
				}
				if (canMove(player, r1, c1, rneg, cneg))
				{
					possibleMoves++;
				}
				if(canMove(player, r1, c1, rpos, cpos))
				{
					possibleMoves++;
				}
				if(canMove(player, r1, c1, rneg, cpos))
				{
					possibleMoves++;
				}
		
			}
			
		return possibleMoves;
		}
		
	}// end of checkMove
	
	
	// way to get piecetype from the board
	int pieceAt(int row, int col) {
		
		return board[row][col];
	}
	
	
}
