package nl.hr.EncryptedChat.device.publisher;

import java.util.Random;

import nl.hr.EncryptedChat.device.poller.RecieveMessages;

import com.rabbitmq.client.*;

public class Send {
	
	  private static final String TASK_QUEUE_NAME = "input";
	  private static String COMMAND = "";
	  private static String CHANNEL = "channelTest";
	  private static String USERNAME = "lolz";

	  public static void main(String[] argv) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("178.62.254.16");
		  factory.setUsername("admin");
		  factory.setPassword("admin");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
	    
	    String message = "bla:" + COMMAND + CHANNEL + ":" + USERNAME;

	    channel.basicPublish("", TASK_QUEUE_NAME,
	    		MessageProperties.PERSISTENT_TEXT_PLAIN,
	    		message.getBytes("UTF-8"));
	    System.out.println(" [x] Sent '" + message + "'");
	    
	    RecieveMessages messageRetriver = new RecieveMessages("178.62.254.16", "admin", "admin");
	    //messageRetriver.startup(CHANNEL, USERNAME);
	  }

}
