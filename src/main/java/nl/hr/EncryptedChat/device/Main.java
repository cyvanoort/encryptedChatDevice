/**
 * 
 */
package nl.hr.EncryptedChat.device;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import nl.hr.EncryptedChat.device.consoleChat.ConsoleChat;
import nl.hr.EncryptedChat.device.poller.MessageQueue;
import nl.hr.EncryptedChat.device.poller.RecieveMessages;
import nl.hr.EncryptedChat.device.publisher.Publisher;

/**
 * @author MrDemnoc
 *
 */
public class Main {
	
	private static SecureRandom random = new SecureRandom();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			startup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startup() throws Exception{
		Publisher publisher = new Publisher("188.166.37.53", "admin", "admin");
		RecieveMessages messageRetriever = new RecieveMessages("188.166.37.53", "admin", "admin");
		MessageQueue<String> queue = new MessageQueue<String>();
		Scanner userInput = new Scanner(System.in);
		
		System.out.print("Enter username: ");
		String username =  userInput.next();
		
		String channel = init(username, publisher, messageRetriever, queue);
		System.out.println(channel);
		messageRetriever.startup(channel, username, queue);
		
		ConsoleChat chat = new ConsoleChat(publisher, queue, channel, username);
		
	}
	
	public static String init(String username, Publisher publisher, RecieveMessages messageRetriever, MessageQueue<String> queue){
		String routingKey = StringUtils.substring(new BigInteger(100, random).toString(32), 0, 10);
		String channel;
		try {
			messageRetriever.startup(routingKey, username, queue);
			publisher.sendMessage(username, routingKey, username);
			while(queue.isEmpty()){
				//wait till queue is not empty
				Thread.sleep(10);
			}
			String message = queue.get();
			publisher.sendMessage(username, "update;" + routingKey, username);
			while(queue.isEmpty()){
				//wait till queue is not empty
				Thread.sleep(10);
			}
			message = queue.get();
			List<String> messageEntry = Arrays.asList(message.split(":"));
			channel = messageEntry.get(0);
			messageRetriever.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			channel = null;
		}
		
		return channel;
		
	}

}
