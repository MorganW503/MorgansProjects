package checkersGame;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;



public class Checkers extends JPanel
{

	public static final int FRAME_SIZE = 720;


	public static void main(String[] args) 
	{
		
		
		JFrame frame=new JFrame("Checkers!");
		frame.setSize(FRAME_SIZE, FRAME_SIZE);
		
		
		frame.setLocationRelativeTo(null);
		Board panel = new Board();
		panel.setLocation(200 ,200 );
		frame.add(panel);
		frame.setVisible(true);
		
		

	}//end main
	
	
	

	public Checkers() 
	{
		/*
		setLayout(null);
		setPreferredSize(new Dimension(500, 500));
		setBackground(Color.blue);
		*/
		Board board= new Board();
		
		add(board);
		//add(newGameButton);
		
		
		//board.setBounds(20,20,164,164);
		//newGameButton.setBounds(210,60,120,30);
		//message.setBounds(0,200,350,30);
		
	}//end of connstructor
	
	
	
	

}//end full public class checkers
