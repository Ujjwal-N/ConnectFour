import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.*;

public class GraphicsTest extends JFrame {

    public GraphicsTest() {

        setTitle("My Gui");
        setSize(500, 500);

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

    MouseListener listener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        		int[] halfX = getHalfRow(columnSpacing, e.getX());
        		int[] halfY = getHalfRow(rowSpacing, e.getY());
        		System.out.println(halfX[0]);
        		System.out.println(halfY[0]);
        		circleCoords[0] = halfX[1] - 20;
        		circleCoords[1] = halfY[1] - 20;
        		repaint();
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
				System.out.println("CLICKEDY CLICKEDY CLICK " + i);
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
    private Boolean color = true;
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
        		drawInnerCircles(g);
        		gridDrawn = true;
    		}
    		if(circleCoords[0] != 0) {
    			if(color) {
    				g.setColor(hex2Rgb("#AA4465")); //red
    				color = false;
    			}else {
    				g.setColor(hex2Rgb("#F2C14E")); //yellow
    				color = true;
    			}
    			g.drawOval(circleCoords[0], circleCoords[1], 40, 40);
			g.fillOval(circleCoords[0], circleCoords[1], 40, 40);
    			
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
    		for(int[] columnSpace : columnSpacing) {
    			System.out.println(Arrays.toString(columnSpace));
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
