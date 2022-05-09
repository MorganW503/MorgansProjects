package checkersGame;

public class CheckersMove 
{
	public class jumpMove
	{
		int startRow, startCol, 
		endRow, endCol,
		jumpedRow, jumpedCol;
		
		jumpMove(int r1, int c1, int r2, int c2)
	{
		int r3=(r1+r2)/2;
		int c3=(c1+c2)/2;
		startRow=r1;
		startCol=c1;
		endRow=r2;
		endCol=c2;
		jumpedCol=c3;
		
	}
	}
	int startRow, startCol, 
		endRow, endCol;
		
	
	CheckersMove(int r1, int c1, int r2, int c2)
	{
		startRow=r1;
		startCol=c1;
		endRow=r2;
		endCol=c2;
		
	}
	
	
	
	
	
}
