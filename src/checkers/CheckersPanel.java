package checkers;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
//import checkers.kingPiece

public class CheckersPanel extends JPanel {
	/**
	 * 
	 */

	private CheckersModel model;
	
	// instantiation of GUI elements
	private JButton[][]board;	// board is just a series of squares with buttons on top, essentially
	private JLabel currentPlayer;
	
	private JPanel panelMain;	// main panel with a panel containing the board laid on top. Provides
	private JPanel panelBoard;	// margin space for labels and such
	
	private JButton undo;
	private JMenuBar menusBar;
	private JMenu fileMenu;
	private JMenuItem newGameItem;
	private JMenuItem quitItem;
	private JMenuItem saveGameItem;
	private JMenuItem loadGameItem;

	//JLabel
	private JLabel timerLabel;
	private TimerListener myTimerListener;
	private Timer myTimer;
	private int runTime = 300000;
	
	// this is our button listener. For properties, see "buttonListener" method
	private ButtonListener buttonListener = new ButtonListener();
	
	
	// this variable assists with assigning icons, see displayBoard() method
	private ICheckersPiece iSquare;
	private ImageIcon blackKing, whiteKing, blackPawn, whitePawn;
	private static final int BOARD_ROWS = 8;
	private static final int BOARD_COLS = 8;
	
	
	private static final int NULL_VAL = 9;
	
	int invalidMoveCount = 0;
	
	public CheckersPanel() {
		model = new CheckersModel();
		
		/* **************************************************** */
		
		 blackKing = new ImageIcon("C:\\Users\\User\\workspace\\CIS 350 - Speed Checkers\\king_black.jpg");		// instantiates all image icons to their appropriate images
		 whiteKing = new ImageIcon("C:\\Users\\User\\workspace\\CIS 350 - Speed Checkers\\king_white.jpg");
		 blackPawn = new ImageIcon("C:\\Users\\User\\workspace\\CIS 350 - Speed Checkers\\pawn_black.jpg");
		 whitePawn = new ImageIcon("C:\\Users\\User\\workspace\\CIS 350 - Speed Checkers\\pawn_white.jpg");
		 
		
		/* **************************************************** */
		
		// instantiate GUI objects
		setLayout(new GridBagLayout());						// sets layout for GUI
		GridBagConstraints loc = new GridBagConstraints();
		
		panelMain = new JPanel();			// upper panel for labels and utility buttons
		loc.gridx = 0;
		loc.gridy = 0;
		loc.gridwidth = 4;
		add(panelMain, loc);
				
		panelBoard = new JPanel();			// lower panel for the board
		loc.gridx = 0;
		loc.gridy = 10;
		add(panelBoard, loc);
		
		undo = new JButton("Undo");		// instantiate undo button, add action listener and add to panelMain
		loc.gridx = 2;
		loc.gridy = 0;
		undo.addActionListener(buttonListener);
		panelMain.add(undo, loc);
		
		currentPlayer = new JLabel("Current Player: " + model.currentPlayer());	// label that displays current player
		loc.gridx = 3;
		panelMain.add(currentPlayer, loc);

		timerLabel = new JLabel(new SimpleDateFormat("mm:ss:SSS").format(runTime) );
		myTimerListener = new TimerListener();
		myTimer = new Timer(1, myTimerListener);
		myTimer.start();
		loc.gridx = 1;
		panelMain.add(timerLabel, loc);

		/* **********************************************************/
		/*
		 * FILE MENU ITEMS
		 */
		// initialize menu bar
		menusBar = new JMenuBar();
		
		// initialize drop down
		fileMenu = new JMenu("File");
		menusBar.add(fileMenu);
		
		// menu items
		quitItem = new JMenuItem("Quit");
		newGameItem = new JMenuItem("New Game");
		saveGameItem = new JMenuItem("Save Game");
		loadGameItem = new JMenuItem("Load Game");
		fileMenu.add(newGameItem);
		fileMenu.add(saveGameItem);
		fileMenu.add(loadGameItem);
		fileMenu.add(quitItem);
		
		menusBar.add(fileMenu);
		
		
		//panelMain.setJMenuBar(menusBar); // cannot get JMenuBar to add to frame
		
		fileMenu.addActionListener(buttonListener);
		quitItem.addActionListener(buttonListener);
		newGameItem.addActionListener(buttonListener);
		saveGameItem.addActionListener(buttonListener);
		loadGameItem.addActionListener(buttonListener);
		
		/* ******************************************************* */
		panelBoard.setLayout(new GridLayout(8,8)); // not totally sure what this does but StackOverflow told me it would help and it did
		board = new JButton[BOARD_ROWS][BOARD_COLS];				   // instantiates all buttons, sets their background colors to either white or dark gray, adds their action listeners and changes size
		for (int row = 0; row < BOARD_ROWS; row++) {
			for (int col = 0; col < BOARD_COLS; col++) {
				board[row][col] = new JButton(" ");
				if (col % 2 == 0 && row % 2 == 0 || col % 2 != 0 && row % 2 != 0)
					board[row][col].setBackground(Color.WHITE);
				if (col % 2 == 0 && row % 2 != 0 || col % 2 != 0 && row % 2 == 0)
					board[row][col].setBackground(Color.GRAY);
				board[row][col].addActionListener(buttonListener);
				board[row][col].setPreferredSize(new Dimension(100, 100));

				panelBoard.add(board[row][col], loc);        // add all buttons to board
			}
		}
		displayBoard();
	}
	/* ************************************************************* */
	/*
	 * Method for returning the JMenuBar item
	 * Used to add menu bar to frame
	 */
	public JMenuBar getJMenuBar(){
		return menusBar;
	}
	/* ************************************************************* */
	/*
	 * Deactivates all the buttons
	 * Used after game is complete
	 */
	public void deactivateButtons(){
		for(int row = 0; row < BOARD_ROWS; row++){
			for(int col = 0; col < BOARD_COLS; col++){
				board[row][col].setEnabled(false);
			}
		}
	}
	/* ************************************************************* */

	/*
	 * PRIMARY METHOD FOR UPDATING ALL VISUALS
	 */
	private void displayBoard(){		
		//iterate through board
		for(int row = 0; row < BOARD_ROWS; row++){
			for (int col = 0; col < BOARD_COLS; col++){
				// iSquare is a temp variable used to check each spot on the board
				iSquare = model.pieceAt(row, col);	// temp object
				
				
				// so if that temp spot is a BLACK single piece, then we set the icon to the black single piece
				if(iSquare instanceof SinglePiece && iSquare.player() == Player.BLACK){	// setting icons to appropriate pieces
					board[row][col].setIcon(blackPawn);
				}
				
				// if that temp spot is a red single piece, then we set the icon to the white single piece (red in future)
				else if(iSquare instanceof SinglePiece && iSquare.player() == Player.RED){
					board[row][col].setIcon(whitePawn);
				}
				
				else if(iSquare instanceof KingPiece && iSquare.player() == Player.BLACK){
					board[row][col].setIcon(blackKing);
				} 
				
				 else if(iSquare instanceof KingPiece && iSquare.player() == Player.RED){
					board[row][col].setIcon(whiteKing);
				} 
				
				else{
					board[row][col].setIcon(null);
				}
				
				
				//OTHERWISE just set the square to either a white or gray tile
				if(col%2 == 0 && row%2 == 0 || col%2 != 0 && row%2 != 0)	
					board[row][col].setBackground(Color.WHITE);
				if(col%2 == 0 && row%2 != 0 || col%2 != 0 && row%2 == 0)
					board[row][col].setBackground(Color.GRAY);
				
				// if first click has been done, highlights piece clicked with blue, 
				// also high lights optional moves
				if(model.getTempRows() != NULL_VAL && model.getTempCols() != NULL_VAL){					
					Move m;
					board[model.getTempRows()][model.getTempCols()].setBackground(Color.CYAN);
					for(int i=0; i< BOARD_ROWS; i++){
						for(int j=0; j<BOARD_COLS; j++){
							m = new Move(model.getTempRows(), model.getTempCols(), i, j);
							if(model.isValidMove(m) == true){
								board[i][j].setBackground(Color.CYAN);
							}
						}
					}
				}
				
			}
		}
	}

	/*************************************************************************************************
	 * 	Timer listener
	 */
	private class TimerListener implements  ActionListener{
		public void actionPerformed(ActionEvent event) {
			runTime = runTime - 2;
			//System.out.println( currentPlayer.getText() + ":" + new SimpleDateFormat("mm:ss").format(runTime));
			timerLabel.setText("Time Remaining: " + new SimpleDateFormat("mm:ss").format(runTime));


			if(runTime <= 0){
				deactivateButtons();
				JOptionPane.showMessageDialog(null,"TIMES UP! \n\n DRAW");
				myTimer.stop();
			}
		}
	}
	
	/* ***********************************************************************************************/
	
	// button listener class
	private class ButtonListener implements ActionListener {		// button listener
		public void actionPerformed(ActionEvent event) {
			
			if(quitItem == event.getSource()){
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to quit the game? "
						+ "\n This will erase any progress you have made.", "QUIT", JOptionPane.YES_NO_OPTION);
				
				if(reply == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
			
			else if(newGameItem == event.getSource()){
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you would like to start a new game? "
						+ "\n This will erase any progress you have made.", "NEW GAME", JOptionPane.YES_NO_OPTION);
				
				if(reply == JOptionPane.YES_OPTION){
					model = new CheckersModel();
					for(int row = 0; row < BOARD_ROWS; row ++){
						for(int col = 0; col < BOARD_COLS; col++){
							board[row][col].setEnabled(true);
							displayBoard();
						}
					}
				}
				
			}
			
			else if(saveGameItem == event.getSource()){
				JOptionPane.showMessageDialog(null, "This feature has not yet been implemented. \n\nWe apologize for the inconvenience.");
			}
			
			else if(loadGameItem == event.getSource()){
				JOptionPane.showMessageDialog(null, "This feature has not yet been implemented. \n\nWe apologize for the inconvenience.");
			}
			
			else if(undo == event.getSource()){
				JOptionPane.showMessageDialog(null, "This feature has not yet been implemented. \n\nWe apologize for the inconvenience.");
			}
			
			// if not quit item or newGameItem then this
			else{ 
				// iterate through board
				for (int row = 0; row < BOARD_ROWS; row++){ 					
				for (int col = 0; col < BOARD_COLS; col++){
					// find the button that was clicked
					if (board[row][col] == event.getSource()){				
						// check if temp variables have been set to anything, NULL_VAL means they have not been
						if(model.getTempRows() == NULL_VAL && model.getTempCols() == NULL_VAL){
							// checks that an actual piece is being clicked on, and that piece belongs to current player
							if(model.pieceAt(row, col) != null){			
								if(model.pieceAt(row, col).player() == model.currentPlayer()){
									// stores the first click info into the temp variables
									// we will use these later when calling our move
									model.setTempRows(row);						
									model.setTempCols(col);
									displayBoard();
								}
							}
							
						}
						else{	
							// so in this case we know our temp variables are already storing information
							// (temp vars != 9), makes the move, updates display
							
							// generates move object with temp info and current click info
							if(row == model.getTempRows() && col == model.getTempCols()){
								model.setTempCols(NULL_VAL);
								model.setTempRows(NULL_VAL);
								displayBoard();
								return;
							}
							Move m1 = new Move(model.getTempRows(), model.getTempCols(), row, col);
							
							// calls isValidMove on this move
							if(model.isValidMove(m1) == true){
								// if it passes, move is actually made
								model.move(m1);
								model.checkForCrowns();
								// checks if the game is complete
								if(model.isComplete() == true){
									JOptionPane.showMessageDialog(null, model.currentPlayer() + " has been checkmated! \nGAME OVER!");
									JOptionPane.showMessageDialog(null, model.nextPlayer()+ " has won!");
									deactivateButtons();
								}
								// update board/GUI
								displayBoard();
								currentPlayer.setText("Current Player: " + model.currentPlayer());
							}
							
							else{
								if(invalidMoveCount < 2){
									JOptionPane.showMessageDialog(null, "It looks like this isn't a valid move in Checkers.");
									invalidMoveCount++;
								}
								else{
									JOptionPane.showMessageDialog(null, "It looks like this isn't a valid move in Checkers." + 
								"\n\nIf you would like a refresher on the rules please visit: " + "\nhttps://www.itsyourturn.com/t_helptopic2030.html");
									invalidMoveCount = 0;
								}
								
								model.setTempRows(NULL_VAL);					// if the move trying to be made is not valid, de-selects the first-clicked piece      
								model.setTempCols(NULL_VAL);
								displayBoard();
							}
							
							
						}
					}
				}
			}
				displayBoard();
			}
		}
	}
	
	
	
	

}