import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class GraphicsTest extends JFrame {
	GameBoard currentGameBoard = new GameBoard();
    public GraphicsTest() {

        setTitle("My Gui");
        setSize(500, 500);
        //CHANGE
        
        // Create JButton and JPanel
        JButton button = new JButton("Click here!");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.setBounds(50, 50, 450, 50);
        // Add button to JPanel
      	 JButton x6 = new JButton("I am x6");
       	 JButton x7 = new JButton("I am x7");
       	 JButton x8 = new JButton("I am x8");
       	 JButton x9 = new JButton("I am x9");
       	 JButton x10 = new JButton("I am x10");
       	 //panel.add(x6, "North");
       	 panel.add(x7, "South");
       	 panel.add(x8, "East");
       	 panel.add(x9, "West");
       	 panel.add(x10, "Center");
        //panel.add(button, "East");
        
        // And JPanel needs to be added to the JFrame itself!
        //this.getContentPane().add(panel,"South");
        this.addMouseListener(listener);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void playMove() {
    		int column = currentGameBoard.threatDetect();
    		if(column == -1) {
    			Random rand = new Random();
        		column = rand.nextInt(6);
    		}else {
    			System.out.println(column);
    		}
    		Piece.Type type = Piece.Type.MINE;
		currentGameBoard.playMove(new Piece(type, column, currentGameBoard.getLowestRow(column)));
		color = false;
		repaint();
		
    }
    MouseListener listener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        		int[] column = getHalfRow(columnSpacing, e.getX());
        		int[] row = getHalfRow(rowSpacing, e.getY());
        		int lowestRow = currentGameBoard.getLowestRow(column[0]);
        		Piece.Type type;
        		if(color) {
//        			type = Piece.Type.MINE;
//        			currentGameBoard.playMove(new Piece(type, column[0], lowestRow));
//        			color = false;
//        			System.out.println(currentGameBoard.threatDetect());
        		}else{
        			
        			type = Piece.Type.OPPONENT;
        			Piece thisPiece = new Piece(type, column[0], lowestRow);
        			currentGameBoard.updateOpponentPieceArray(thisPiece);
        			currentGameBoard.playMove(thisPiece);
        			color = true;
        			repaint();
        			
        		}
        		
        		
        		//System.out.println(column[0]);
        		//System.out.println(lowestRow);
        		//printBoard();        		
        		circleCoords[0] = column[1] - 20;
        		circleCoords[1] = row[1] - 20;
        		
        }
    };
    private int getAverage(int[] arr) {
    		int retInt = (arr[1] + arr[0]) / 2;
    		return retInt;
    }
    private int[] getHalfRow(int[][] arr, int currentCoord) {
		int currentColumn = -1; 
		int[] retVar = new int[2];
		for(int i = 0; i < arr.length; i++) {
			int firstNum = arr[i][0];
			int secondNum = arr[i][1];
			if(firstNum < currentCoord && currentCoord < secondNum) {
				currentColumn = i;
				//System.out.println("CLICKEDY CLICKEDY CLICK " + i);
			}
		}
		retVar[0] = currentColumn;
		if(currentColumn != -1) {
			retVar[1] = getAverage(arr[currentColumn]);
		}
		return retVar;
    }
    //6 rows, 7 columns
    private Boolean gridDrawn = false;
    private Boolean color = false;
    private int[] circleCoords = {0,0};
    public void paint(Graphics g) {
    		if(!gridDrawn) {
    			g.setColor(hex2Rgb("#EDF7F6"));
    			g.fillRect(0,0,500,500);
    			g.setColor(hex2Rgb("#26547C")); //dark blue
        		drawOuterGrid(g, 50, 450);
        		drawColumns(g, 50, 450,7);
        		drawRows(g, 50, 450,6);
        		//g.setColor(hex2Rgb("#91C4F2")); light blue
        		g.setColor(hex2Rgb("#EDF7F6"));
        		//drawInnerCircles(g);
        		gridDrawn = true;
    		}
    		for(int columnCount = 0; columnCount < currentGameBoard.getArray().length; columnCount++) {
    			for(int rowCount = 0; rowCount < currentGameBoard.getArray()[columnCount].length; rowCount++) {
    				Piece currentPiece = currentGameBoard.getArray()[columnCount][rowCount];
    				if(currentPiece.getType() == Piece.Type.EMPTY) {
    					g.setColor(hex2Rgb("#EDF7F6"));
    				}else if(currentPiece.getType() == Piece.Type.MINE) {
    					g.setColor(hex2Rgb("#AA4465")); //red
    				}else {
    					g.setColor(hex2Rgb("#F2C14E")); //yellow
    				}
    				g.fillOval(getAverage(columnSpacing[currentPiece.getColumn()]) - 20, getAverage(rowSpacing[currentPiece.getRow()]) - 20, 40, 40);
    			}
    		}
    		if(color) {
    			playMove();
    		}

    }
    private void drawOuterGrid(Graphics g, int startX, int length) {
		//Sets of paralell lines
    		g.fillRect(startX, startX, (length - startX), (length - startX));
	
    }
    int[][] columnSpacing = new int[7][2];
    private void drawColumns(Graphics g, int startX, int length, int number) {
		int newX = startX;
		int spacing = (length - startX) / number;
		int[] coords = new int[2];
		for(int i = 0; i < (number - 1); i++) {
			coords = new int[2];
			coords[0] = newX;
			newX = newX + spacing;
			coords[1] = newX;
			//g.drawLine(newX, startX, newX, length);
			columnSpacing[i] = coords; 
		}
		coords = new int[2];
		coords[0] = newX;
		coords[1] = length;
		columnSpacing[(number - 1)] = coords; 
    }
    int[][] rowSpacing = new int[6][2];
    private void drawRows(Graphics g, int startY, int length, int number) {
    		int newY = startY;
    		int spacing = (length - startY) / number;
    		int[] coords = new int[2];
    		for(int i = 0; i < (number - 1); i++) {
    			coords = new int[2];
    			coords[0] = newY;
    			newY = newY + spacing;
    			coords[1] = newY;
    			//g.drawLine(startY, newY, length, newY);
    			rowSpacing[i] = coords;
    		}
    		coords = new int[2];
    		coords[0] = newY;
    		coords[1] = length;
    		rowSpacing[(number - 1)] = coords; 
    		printBoard();
    }
    private void printBoard() {
		System.out.println();
		for(Piece[] gameBoard : currentGameBoard.getArray()) {
			System.out.println(Arrays.toString(gameBoard));
		}
    }
    private void drawInnerCircles(Graphics g) {
    		for(int columnCount = 0; columnCount < columnSpacing.length; columnCount++) {
    			int x = getAverage(columnSpacing[columnCount]) - 20;
    			for (int rowCount = 0; rowCount < rowSpacing.length; rowCount++) {
    				int y = getAverage(rowSpacing[rowCount]) - 20;
    				g.fillOval((x), (y), 40, 40);
    			}
    		}

    }
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    
} 
