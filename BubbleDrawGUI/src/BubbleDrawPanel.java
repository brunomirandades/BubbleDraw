import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox; //for graphical interface we use the javax.swing.Timer

public class BubbleDrawPanel extends JPanel {

	private ArrayList<Bubble> bubbleList; //a variable that is an array, it is needed to import the ArrayList util
	private int bubbleSize = 30;
	private Timer timer; //declaring the variable that will store the timer function
	private final int DELAY = 33; //declaring and assigning the delay in which the bubbles will rise in ms (1000ms/30)
	private JTextField txtSize;
	private JTextField txtSpeed;
	private JCheckBox chkGroup;
	private JCheckBox chkPause;
	
	public BubbleDrawPanel() { //public because this is the panel that will be used by the other classes
		//this is the main method that will execute all the other methods
		
		bubbleList = new ArrayList<Bubble>(); //assigning bubbleList as an ArrayList of the type Bubble
		
		addMouseListener(new BubbleListener()); //with the click of the mouse the BubbleListener will perform an action
		addMouseMotionListener(new BubbleListener()); //the listener is needed to be set for the MotionListener as well
		addMouseWheelListener(new BubbleListener()); //Listener for the mouse wheel
		
		timer = new Timer(DELAY, new BubbleListener()); //setting a Timer to the variable timer that will play the method BubbleListener() each time the DELAY time is reached
		
		setBackground(Color.BLACK); //sets the background color of the panel to black. It is needed to import the Color util
		setPreferredSize(new Dimension(720,400)); //sets the dimension
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblNewLabel = new JLabel("Dot Size: ");
		panel.add(lblNewLabel);
		
		txtSize = new JTextField();
		txtSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtSize.setText("30");
		panel.add(txtSize);
		txtSize.setColumns(3);
		
		JLabel lblNewLabel_1 = new JLabel("Animation Speed (fps): ");
		panel.add(lblNewLabel_1);
		
		txtSpeed = new JTextField();
		txtSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeed.setText("30");
		panel.add(txtSpeed);
		txtSpeed.setColumns(3);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get the bubble size
				int newSize = Integer.parseInt(txtSize.getText());
				//get the animation speed
				int newSpeed = Integer.parseInt(txtSpeed.getText());
				//set the bubble size
				bubbleSize = newSize; //refers to the bubbleSize argument inside the new Bubble method in the mouse action
				//set the animation speed
				timer.setDelay(1000/newSpeed); //will get the newSpeed variable and set it as the number of frames inside 1000ms
				
				repaint();
			}
		});
		panel.add(btnUpdate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() { //action listener for the "Clear" button
			public void actionPerformed(ActionEvent arg0) {
				
				//Cleaning the screen from all the bubble already drawn
				bubbleList = new ArrayList<Bubble>(); //just run the command for a new ArrayList with the same name (variable) to clear it;
				repaint();
			}
		});
		
		chkGroup = new JCheckBox("Group Bubbles");
		panel.add(chkGroup);
		
		chkPause = new JCheckBox("Pause");
		chkPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//event listener for when the check box is clicked
				if(chkPause.isSelected())//when the check box is clicked, it checks itself to see whether it is checked or not
					timer.stop();
				else
					timer.start();
			}
		});
		panel.add(chkPause);
		panel.add(btnClear);
		
		timer.start(); //initializes the timer via the program, different from the other action listeners that are triggered by the user
		//keep the timers at the very end for good practice
		
	}
	
	public void paintComponent(Graphics page) { //method available in eclipse to paint components into the panel. We need to name it right after "Graphics"
		super.paintComponent(page); //initializes/link the paint component to our paintComponent "page"
		
		//draw all the bubbles from the bubbleList
		for(Bubble bubble:bubbleList) { //for each bubble inside the bubbleList
			page.setColor(bubble.color); //sets the color of the bubbles that will be drawn
			page.fillOval(bubble.x - bubble.size/2, //center the mouse in the x axis
					bubble.y - bubble.size/2, //center the mouse in the y axis. Otherwise, the circle will be drawn behind and below the cursor
					bubble.size, bubble.size); //these args will be taken from the arrayList, they are the size to be set in x and y
		}
		
		//write the number of bubbles on screen
		//page.setColor(Color.GREEN); //set the text color
		//page.drawString("Count: " + bubbleList.size(), 5, 15); //sets the text and its position on-screen
		
	}
	
	private class BubbleListener implements MouseListener,
											MouseMotionListener,
											MouseWheelListener,
											ActionListener{ //adds the mouse listener which is an anonymous class, but by implementing it to the BubbleListener class, now we have a name for it

		//click our class to add the events automatically by eclipse
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {//this perform an action as the mouse is pressed, doesn't matter how long
			// arg0 is the name of the variable that carries the action
			
			//timer.stop(); //stops the timer while drawing the bubbles
			//add to the bubbleList my mouse location
			bubbleList.add(new Bubble(arg0.getX(), arg0.getY(), bubbleSize)); //adds a bubble to the list based on the position of the mouse and a already determined size
			
			repaint(); //function of the method paintComponent() that paints the dot
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
			//timer.start(); //starts the timer as soon as the mouse is released
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) { //as the mouse is dragged, new bubbles will be drawn
			//add to the bubbleList my mouse location
			bubbleList.add(new Bubble(arg0.getX(), arg0.getY(), bubbleSize)); //adds a bubble to the list based on the position of the mouse and a already determined size
			
			//check to see if the "Group Bubbles" check box is set to true
			if(chkGroup.isSelected()) {
				
				//set the xSpeed and ySpeed of the last bubble to this new bubble added as the mouse is dragged
				bubbleList.get(bubbleList.size() - 1).xSpeed = 
						bubbleList.get(bubbleList.size() - 2).xSpeed;
				
				bubbleList.get(bubbleList.size() - 1).ySpeed = 
						bubbleList.get(bubbleList.size() - 2).ySpeed;
			}

			repaint(); //function of the method paintComponent() that paints the dot
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) { //arg0 is the variable that receives the value of the action
			//change the size of the bubble as the mouse wheel is moved
			bubbleSize -= arg0.getWheelRotation();
			
			txtSize.setText("" + bubbleSize); //gets the size, transforms it from int to string and set it to the Size text field
			
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//actions for when the timer DELAY is reached
			//update the location of each bubble for the animation
			for(Bubble bubble:bubbleList)//for each bubble of the bubble list, do the below
				bubble.update(); //we use bubble here because that is the name given to the elements inside the bubbleList inside the for conditions
				
			repaint(); //since it is as a new frame is drawn each time the timer is triggered we have to use the paintComponent() to paint this new frame
		}
		
	}
	
	private class Bubble{ //necessary a private class to declare the position and size variables locally
		
		//this class sets the values for the bubbles, but they need to be turned visible in the paintComponent method
		
		//a bubble needs a x,y position and size
		public int x;
		public int y;
		public int size;
		public Color color; //Color is a class of the swing tool kit, it has all the RGB color components
		public int xSpeed; //Variables for the rgn
		public int ySpeed; //generate and store the speed of each bubble in the x and y axis
		private final int MAX_SPEED = 5; //maximum speed a bubble can be set. It updates 30 times every second which means 150px per second in one direction
		
		private float transparentColor;
		
		public Bubble(int newX, int newY, int newSize){
			x = newX;
			y = newY;
			size = newSize;
			transparentColor = (float)Math.random(); //variable for the transparency argument
			
			//wont let any color to be solid or completely transparent
			if(transparentColor == 0) 
				transparentColor = (float)0.01;
			
			if(transparentColor == 1)
				transparentColor = (float)0.99;
			
			color = new Color((float)Math.random(), //setting a new method of the Color class to generate randomly the color of each new bubble
					(float)Math.random(),
					(float)Math.random(),
					transparentColor); //this fourth element adds transparency which 1=totally opaque 0=totally transparent 
			
			//adding the for functions below prevents the generation of static bubbles
			//however this solution prevents the bubbles to navigate in only one axis
			for(xSpeed = 0; xSpeed == 0; ) //if the number for the xSpeed generated by the rgn equals zero, it runs again, otherwise it leaves the loop
			xSpeed = (int)(Math.random() * MAX_SPEED * 2 - MAX_SPEED); //the max speed subtracting will allow bubbles to go to the - direction of the axis
			
			for(ySpeed = 0; ySpeed == 0; )
			ySpeed = (int)(Math.random() * MAX_SPEED * 2 - MAX_SPEED); //the max speed subtracting will allow bubbles to go to the - direction of the axis
		}
		
		public void update() { //method to update the bubbles position by adding pixels to the Y positioning
			
			x += xSpeed; //for each bubble a speed will be generated once and will keep adding to its position for each timer's action
			y += ySpeed;
			
			//y -= 5; //"-" because it considers as if the window is moved below every time
			
			//collision detection with the edges of the panel
			//every time the bubble hits the edge of the panel, it turns to the opposite direction
			if((x - size/2) < 0 || (x + size/2) > getWidth()) //getWitdth() can be used directly because we are inside the panel class, there is no need point it 
				xSpeed = -1 * xSpeed;
			if((y - size/2) < 0 || (y + size/2) > getHeight())
				ySpeed = -ySpeed; //the same as -1 * ySpeed
			//since we are inside the class that generates a new bubble, we can use "size/2" to get the radius of the bubble instead of "bubbleList.size()"
			//doing so would create a new bubble to have restraint related to the one before it in the ArrayList
			
		}
		
	}
	
	
}
