package checkers;

import javax.swing.JFrame;

import checkers.CheckersPanel;
// test comment
// this class is the one that runs the GUI. It establishes all the 'panels' and sets them up and packs them
// all actual GUI content is contained in the CheckersPanel class
public class CheckersGUI {
	public static void main(String[] args){
		JFrame frame = new JFrame("Checkers");		// sets up GUI
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CheckersPanel panel = new CheckersPanel();
		frame.getContentPane().add(panel); 
		frame.setJMenuBar(panel.getJMenuBar());
		
		frame.pack();
		frame.setVisible(true);
	}

}
	
