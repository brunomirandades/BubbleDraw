import javax.swing.JFrame;

public class BubbleDraw extends JFrame {

	public static void main(String[] args) {
		//setting up the frame for the bubble draw app
		JFrame frame = new JFrame("Bubble Draw GUI App"); //create and name the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //action when closed
		
		frame.getContentPane().add(new BubbleDrawPanel()); //gets the panel from the other class
		
		frame.pack(); //packs to make it visible
		frame.setVisible(true);
		
		

	}

}
