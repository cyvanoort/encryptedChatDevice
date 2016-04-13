package nl.hr.EncryptedChat.device.consoleChat;

import java.util.Arrays;
import java.util.List;

import nl.hr.EncryptedChat.device.poller.MessageQueue;

public class MessagePrinterThread implements Runnable {
	
	private MessageQueue<String> queue;
	
	public MessagePrinterThread(MessageQueue<String> queue){
		this.queue = queue;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			while(queue.isEmpty()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String message = queue.get();
			List<String> messageEntry = Arrays.asList(message.split(":"));
			
			System.out.println(messageEntry.get(1) + " has sent: " + messageEntry.get(0));
			
		}
	}
}
