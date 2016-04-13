package nl.hr.EncryptedChat.device.poller;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class DeviceConsumer extends DefaultConsumer {
	
	private MessageQueue<String> queue;

	public DeviceConsumer(Channel channel, MessageQueue<String> queue) {
		super(channel);
		this.queue = queue;
	}
	
	@Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		String message = new String(body, "UTF-8");
		queue.put(message);
    }

}
