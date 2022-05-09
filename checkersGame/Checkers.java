package checkersGame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;



public class Checkers extends JPanel
{

	public static final int FRAME_SIZE = 720;
	public static final int ROWSHIFT= 80;
	public static final  int COLSHIFT=40;
	
	
	public static void main(String[] args) 
	{
		
		
		JFrame frame=new JFrame("Checkers!");
		frame.setSize(FRAME_SIZE+2*ROWSHIFT, FRAME_SIZE+2*COLSHIFT);
		
		
		frame.setLocationRelativeTo(null);
		Board panel = new Board();
		panel.setLocation(0 ,0);
		frame.add(panel);
		
		
	
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

	}//end main
	
	
	

	public Checkers() 
	{
		
//		Board board= new Board();
//		
//		add(board);
//		
//		
		
	}//end of connstructor
	
	
	
	

}//end full public class checkers
