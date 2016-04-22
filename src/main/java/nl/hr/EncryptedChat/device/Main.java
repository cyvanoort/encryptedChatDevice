/**
 * 
 */
package nl.hr.EncryptedChat.device;

import javax.swing.JFrame;
import onetimepad.frame;

/**
 * @author MrDemnoc
 *
 */
public class Main {
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                JFrame frame = new JFrame("Secure chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new frame());
        //Display the window.
        frame.setSize(500, 500);
        frame.setVisible(true);
		
	}
}
