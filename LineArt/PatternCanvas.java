/******************************************************************************
filename    PatternCanvas.java
author      Max Wright
completion  10/16/2017

Brief Description:
  This program extends JComponent which allows the function to display a 
  graphic of lines on a JFrame. Quite honestly I wrote this code when playing
  around with making my own JCompnents several months ago. At the time I wrote
  zero comments for the code. Now going back and having fixed some errors, I 
  am not 100% sure why this class works correctly.
  
  © 2017 Max Wright. All rights reserved.
******************************************************************************/

package lineart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class PatternCanvas extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private int density = 200;
	private int canvasSize = 1;
	private int numberOfOccurances;
		/* Holds the color of the top left color of the gradient the lines drawn
		   in. */
	private Color topLeft = Color.RED;  
		/* Holds the color of the bottom right color of the gradient the lines 
		   drawn in. */
	private Color bottomRight = Color.BLUE;
		/* Holds the color of the background color of the graphic. */
	private Color background = Color.BLACK;

	public PatternCanvas() {

	}

/******************************************************************************
   Function: paintComponent

Description: This function draws a fancy graphic on the screen in a JFrame.

     Inputs: Graphics object.

    Outputs: N/A
******************************************************************************/
	public void paintComponent(Graphics g) {

		image = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
			/* So in order to make the logic of the code handle odd numbers when 
			   drawing the lines the mathematics of the code treats the 
			   entered density as half the value it is. So, here in the only case 
			   where the density variable is used we multiply it by two. Later 
			   on in the program we will divide by two, meaning it is best to work
			   in even numbers. */
		int tempDensity = density * 2;

			/* Divide by tempDensity then times by tempDensity to make size the 
			   graphic that is being drawn evenly divisible by the density. To 
			   note if the density was odd instead of even we could not divide
			   the canvas by two easily. */
		canvasSize = ((getWidth() < getHeight()) ? getWidth() - 10
				: getHeight() - 10) / tempDensity * tempDensity;
			/* This is where we are treating the density as half the size it is.
               This is why tempDensity exists. No truncation should occur because
               tempDensity is a factor of canvasSize due to when canvasSize is set
               in this function. */		
		numberOfOccurances = canvasSize / tempDensity;
		if (numberOfOccurances == 0) {
			numberOfOccurances = 1;
		}

		int xBuffer = (getWidth() - canvasSize) / 2;
		int yBuffer = (getHeight() - canvasSize) / 2;

		g2.setColor(background);
		g2.fillRect(0, 0, getWidth(), getHeight());

		GradientPaint p;
		p = new GradientPaint(0 + xBuffer, 0 + yBuffer, topLeft, canvasSize,
				canvasSize, bottomRight);
		g2.setPaint(p);

			// Create box around geometric object
		g2.drawLine(0 + xBuffer, 0 + yBuffer, 0 + xBuffer, canvasSize + yBuffer);
		g2.drawLine(0 + xBuffer, canvasSize + yBuffer, canvasSize + xBuffer,
				canvasSize + yBuffer);
		g2.drawLine(canvasSize + xBuffer, canvasSize + yBuffer, canvasSize
				+ xBuffer, 0 + yBuffer);
		g2.drawLine(canvasSize + xBuffer, 0 + yBuffer, 0 + xBuffer, 0 + yBuffer);

			// Create cross
		g2.drawLine(0 + xBuffer, canvasSize / 2 + yBuffer,
				canvasSize + xBuffer, canvasSize / 2 + yBuffer);
		g2.drawLine(canvasSize / 2 + xBuffer, 0 + yBuffer, canvasSize / 2
				+ xBuffer, canvasSize + yBuffer);

			// Create center
		for (int i = 1; i < 5; i++) {
			drawQuadrant(g2, canvasSize / 2 + xBuffer,
					canvasSize / 2 + yBuffer, i);
		}

			// Create outer edge
		drawQuadrant(g2, 0 + xBuffer, 0 + yBuffer, 4);
		drawQuadrant(g2, canvasSize + xBuffer, 0 + yBuffer, 3);
		drawQuadrant(g2, canvasSize + xBuffer, canvasSize + yBuffer, 2);
		drawQuadrant(g2, 0 + xBuffer, canvasSize + yBuffer, 1);

		g.drawImage(image, 0, 0, null);
	}

/******************************************************************************
   Function: drawQuadrant

Description: This function draws a half a quadrant of the fancy graphic.

     Inputs: Graphics 2D g2 - Graphics2D object of an image we are drawing on.
             int x          - The starting x coordinate of the quadrant.
             int y          - The starting y coordinate of the quadrant.
             int quadrant   - The half quadrant being drawn.

    Outputs: N/A
******************************************************************************/
	public void drawQuadrant(Graphics2D g, int x, int y, int quadrant) {
		int startXShift, endYShift, endY;

		switch (quadrant) {
			// Case 1: Quadrant 1 of the main center piece.
		case 1:
				/* Here we divide the canvas size by two. Sense we have
			       set the canvas size to always be an even number, even
			       when an odd number has been entered as the density, no
			       mathematical errors occur. Sense numberOfOccurances is
			       calculated by using canvasSize and setDensity, and 
			       canvasSize is so massive, numberOfOccurance will be
			       a factor of canvasSize, even after canvasSize has
			       been divided by two. */
			startXShift = canvasSize / 2 / numberOfOccurances;
			endYShift = canvasSize / 2 / numberOfOccurances;
			endY = y - canvasSize / 2;
			break;
			// Case 2: Quadrant 2 of the main center piece.
		case 2:
			startXShift = -canvasSize / 2 / numberOfOccurances;
			endYShift = canvasSize / 2 / numberOfOccurances;
			endY = y - canvasSize / 2;
			break;
			// Case 3: Quadrant 1 of the main center piece.
		case 3:
			startXShift = -canvasSize / 2 / numberOfOccurances;
			endYShift = -canvasSize / 2 / numberOfOccurances;
			endY = y + canvasSize / 2;
			break;
			// Case 4: Quadrant 1 of the main center piece.
		case 4:
			startXShift = canvasSize / 2 / numberOfOccurances;
			endYShift = -canvasSize / 2 / numberOfOccurances;
			endY = y + canvasSize / 2;
			break;
		default:
			startXShift = 0;
			endYShift = 0;
			endY = 0;
			break;
		}
		int startX = x + startXShift;

		 for (int i = 0; i < numberOfOccurances; i++) {
			 g.drawLine(startX, y, x, endY);
		 	startX += startXShift;
		 	endY += endYShift;
		 }
	}
	
	//////////////////
	// Generic Setters
	//////////////////
	public void setDensity(int x) {
		/* If the density is zero, it will cause a division by zero in the 
		   paintComponent function. This statement makes sure the density
		   cannot be zero. */
		if (x != 0) {
			density = x;
			repaint();
		}
	}

	
	public void setGradientAndBackground(Color a, Color b, Color c) {
		topLeft = a;
		bottomRight = b;
		background = c;
		repaint();
	}
	
	//////////////////
	// Generic Getters
	//////////////////
	public int getDensity() {
		return density;
	}

	public Color getTopLeft() {
		return topLeft;
	}

	public Color getBottomRight() {
		return bottomRight;
	}

	public Color getBackground() {
		return background;
	}

	public BufferedImage getPattern() {
		return image;
	}

}
