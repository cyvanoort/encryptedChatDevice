package nl.hr.EncryptedChat.device.consoleChat;

import java.util.Scanner;

import nl.hr.EncryptedChat.device.poller.MessageQueue;
import nl.hr.EncryptedChat.device.poller.RecieveMessages;
import nl.hr.EncryptedChat.device.publisher.Publisher;

public class ConsoleChat {

	public ConsoleChat(Publisher publisher, MessageQueue<String> queue, String channel, String username) {
		// TODO Auto-generated constructor stub
		Thread messagePrinter = new Thread(new MessagePrinterThread(queue));
		messagePrinter.start();
		
		Scanner userInput = new Scanner(System.in);
		
		while(true){
			String message = userInput.nextLine();
			try {
				publisher.sendMessage(message, username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
