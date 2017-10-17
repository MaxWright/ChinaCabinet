/******************************************************************************
filename    LineArt.java
author      Max Wright
completion  10/16/2017

Brief Description:
  This object extends JFrame and acts as a display for the PatternCanvas
  object.
  
  © 2017 Max Wright. All rights reserved.
******************************************************************************/

package lineart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LineArt extends JFrame {

	private static final long serialVersionUID = 1L;
	
		// This text field is where the user enters the density of the line art.
	private JTextField densityField;
		/* This patternCanvas creates the line art and also contains the code
	       of how it prints on the screen. */
	private PatternCanvas patternCanvas;

	public LineArt() {
		setTitle("Line Art");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(700, 700));
			// Assures that the program will launch in full screen.
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		patternCanvas = new PatternCanvas();
		
		setLayout(new BorderLayout());
		add(saveComponents(), BorderLayout.NORTH);
		add(patternCanvas, BorderLayout.CENTER);
		add(setDensityComponents(), BorderLayout.SOUTH);
		add(setGradientComponents(), BorderLayout.WEST);
		setVisible(true);
	}

/******************************************************************************
   Function: saveComponents

Description: This function contains a text field and a button. When the button
             is pressed it save the line art with its background as a .png 
             file. The file will be named based on its components automatically
             or it will be named what ever is currently typed into the text 
             field, if the text field is not empty. It will save over any 
             existing .png file with the same name without warning.

     Inputs: N/A

    Outputs: A JPanel with a button and a text field.
******************************************************************************/
	public JPanel saveComponents() {
		JPanel tile = new JPanel();
		JButton save = new JButton("Save");
		JTextField saveName = new JTextField("", 15);
		saveName.setEditable(true);

			// Defines what the button will do when pressed.
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String temp = saveName.getText();
				if (temp.equals("")) {
						// Save the top left of the gradient.
					Color topLeft = patternCanvas.getTopLeft();
						// Save the bottom right of the gradient.
					Color bottomRight = patternCanvas.getBottomRight();
						// Save the background of the gradient.
					Color background = patternCanvas.getBackground();
						/* Example of final temp string:
	    	"density(800)topLeft(255,0,0)bottomRight(0,0,255)background(0,0,0)"
					      */
					temp += "density(" + densityField.getText()
							+ ")topLeft(" + topLeft.getRed() + ","
							+ topLeft.getGreen() + "," + topLeft.getBlue()
							+ ")bottomRight(" + bottomRight.getRed() + ","
							+ bottomRight.getGreen() + ","
							+ bottomRight.getBlue() + ")background("
							+ background.getRed() + ","
							+ background.getGreen() + ","
							+ background.getBlue() + ")";
				}
				try {
					File myNewLineArt = new File(temp + ".png");
					ImageIO.write(patternCanvas.getPattern(), "png",
							myNewLineArt);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		tile.add(saveName);
		tile.add(save);
		
		return tile;
	}

/******************************************************************************
   Function: setDensityComponents

Description: This function contains a text field and a button. When the button
             is pressed it will take the number in the text field and set that
             value as the distance between the lines on the x and y axis on
             the graphic. If something other than a number is in the field,
             the distance between lines will be set to 50.
             
     Inputs: N/A

    Outputs: A JPanel with a button and a text field.
******************************************************************************/
	public JPanel setDensityComponents() {
		JPanel tile = new JPanel();
		densityField = new JTextField("" + patternCanvas.getDensity(), 5);
		JButton densityFieldButton = new JButton("Apply");
		
			// Functionality of the button
		densityFieldButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
				patternCanvas.setDensity(Integer.parseInt(densityField
						.getText()));
				densityField.setText("" + patternCanvas.getDensity());
				} catch (NumberFormatException e){
					patternCanvas.setDensity(50);
					densityField.setText("" + patternCanvas.getDensity());
				}
				
			}
		});
		
		tile.add(densityField);
		tile.add(densityFieldButton);
		
		return tile;
	}
	
/******************************************************************************
   Function: setDensityComponents

Description: This function contains a series of labels, text fields and a 
             button. The labels act as headers describing what each column is.
             When the button is pressed the values in the text fields will be 
             used as RGB values for the gradient and background of the graphic.
             If a non number is entered it the text fields, nothing happens
             when the button is pressed.
             
	             
     Inputs: N/A

    Outputs: A JPanel with a button and a text field.
******************************************************************************/

	public JPanel setGradientComponents() {
		JPanel tile = new JPanel();
		tile.setLayout(new GridLayout(5, 3));
		
		JTextField[] rgbValues = new JTextField[9];
		JButton set = new JButton("Set");

			// Row 1 of the grid layout
		tile.add(new JLabel("Red", SwingConstants.CENTER));
		tile.add(new JLabel("Green", SwingConstants.CENTER));
		tile.add(new JLabel("Blue", SwingConstants.CENTER));

			// Row 2, 3 and 4 of the grid layout
		for (int i = 0; i < 9; i++) {
			rgbValues[i] = new JTextField("", 3);
			tile.add(rgbValues[i]);
		}

			/* Set all the text fields to the correct number according to what the
		       patternCanvas object has stored. */
		Color topLeft = patternCanvas.getTopLeft();
		Color bottomRight = patternCanvas.getBottomRight();
		Color background = patternCanvas.getBackground();
		int i = 0;
		rgbValues[i++].setText("" + topLeft.getRed());
		rgbValues[i++].setText("" + topLeft.getGreen());
		rgbValues[i++].setText("" + topLeft.getBlue());
		rgbValues[i++].setText("" + bottomRight.getRed());
		rgbValues[i++].setText("" + bottomRight.getGreen());
		rgbValues[i++].setText("" + bottomRight.getBlue());
		rgbValues[i++].setText("" + background.getRed());
		rgbValues[i++].setText("" + background.getGreen());
		rgbValues[i++].setText("" + background.getBlue());

			// Define the functionality of the button.
		set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Color a = new Color(
							Integer.parseInt(rgbValues[0].getText()),
							Integer.parseInt(rgbValues[1].getText()), 
							Integer.parseInt(rgbValues[2].getText()));
					Color b = new Color(
							Integer.parseInt(rgbValues[3].getText()),
							Integer.parseInt(rgbValues[4].getText()), 
							Integer.parseInt(rgbValues[5].getText()));
					Color c = new Color(
							Integer.parseInt(rgbValues[6].getText()),
							Integer.parseInt(rgbValues[7].getText()), 
							Integer.parseInt(rgbValues[8].getText()));
					patternCanvas.setGradientAndBackground(a, b, c);
				
				} catch (NumberFormatException e) {
					
				}
			}
		});
		
			// Only item of row 5 of the grid layout
		tile.add(set);
		
		return tile;
	}

	public static void main(String args[]) {
		Runnable doRun = new Runnable() {
			public void run() {
				new LineArt();
			}
		};
		SwingUtilities.invokeLater(doRun);
	}
}
