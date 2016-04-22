/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onetimepad;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import nl.hr.EncryptedChat.device.poller.MessageQueue;
import nl.hr.EncryptedChat.device.poller.RecieveMessages;
import nl.hr.EncryptedChat.device.publisher.Publisher;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author cyvan
 */
public class frame extends JPanel implements ActionListener{
    private static SecureRandom random = new SecureRandom();
    static private final String newline = "\n";
    JButton newchat, loadchat, sendmessage, start;
    static private String source_file, destination_file;
    JTextArea name, user, message;
    JFileChooser fc;
    public JTextArea log;
    List<Integer> keys;
    
    public frame(){
        //create a layout
        JFrame app = new JFrame("secure chat");
        fc = new JFileChooser();
        app.setLayout(new BorderLayout());
        log = new JTextArea(15,30);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        //Create the open button
        newchat = new JButton("Start new chat");
        newchat.addActionListener(this);

        //Create the save button.
        loadchat = new JButton("Load key");
        loadchat.addActionListener(this);
        
        sendmessage = new JButton("send");
        sendmessage.addActionListener(this);
        
        start = new JButton("start");
        start.addActionListener(this);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newchat);
        buttonPanel.add(loadchat);
        buttonPanel.add(sendmessage);
        buttonPanel.add(start);
        
        JPanel you = new JPanel();
        JTextArea me = new JTextArea("Username: ");
        me.setEnabled(false);
        name = new JTextArea();
        name.setColumns(20);
        you.add(me);
        you.add(name);        
        
        JPanel rec = new JPanel();
        JTextArea to = new JTextArea("To: ");
        to.setEnabled(false);
        user = new JTextArea();
        user.setColumns(20);
        rec.add(to);
        rec.add(user);
        
        JPanel deelnemers = new JPanel();
        deelnemers.setLayout(new BorderLayout());
        deelnemers.add(you, BorderLayout.PAGE_START);
        deelnemers.add(rec, BorderLayout.CENTER);
        
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add(buttonPanel, BorderLayout.PAGE_START);
        top.add(deelnemers, BorderLayout.CENTER);
        
        JPanel send = new JPanel();
        send.setLayout(new BorderLayout());
        JTextArea label = new JTextArea("Message: ");
        label.setEnabled(false);
        message = new JTextArea();
        message.setColumns(30);
        message.setRows(3);
        
        send.add(label, BorderLayout.PAGE_START);
        send.add(message, BorderLayout.CENTER);
        
        
        super.add(top, BorderLayout.PAGE_START);
        super.add(send, BorderLayout.CENTER);
        super.add(logScrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newchat) {
            int returnVal = fc.showSaveDialog(frame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //Set the destination directory
                destination_file = file.getPath();
                
                log.append("Destination file set to: " + file.getPath() + "." + newline);
                log.append("Generate key...");
                
                keyWriter kw = new keyWriter();
                
                randomGenerator rand = new randomGenerator();
                keys = rand.randomNumber(1000000);
                
                kw.Write(keys.toString(), destination_file);
                
                log.append("Key created and saved!" + newline);
                
                } else {
                log.append("Setting destination file canceled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } else if (e.getSource() == loadchat){
            int returnVal = fc.showOpenDialog(frame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //Set the source file
                source_file = file.getPath();
                log.append("Source file set to: " + file.getPath() + "." + newline);
                keyReader kr = new keyReader();
                try {
                    keys = kr.Read(source_file);
                } catch (IOException ex) {
                    log.append("Something went wrong" + ex);
                }
            } else {
                log.append("Getting source file canceled by user" + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } else if (e.getSource() == start){
            if("".equals(name.getText())){
                log.append("you must set your username");
            } else {
                try {
			startup(name.getText());
		} catch (Exception ex) {
			// TODO Auto-generated catch block
                        ex.printStackTrace();
		}
            }
        } else if (e.getSource() == sendmessage){
            if("".equals(user.getText()) || "".equals(message.getText())){
                log.append("You must set the to and a message" + newline);
            } else if(!keys.isEmpty()){
                
                log.append(name.getText() +": " + message.getText() + newline);
                caesarCipher cipher = new caesarCipher();
                String recip = user.getText();
                String enMessage = cipher.encrypt(message.getText(), keys);
                
                Publisher publisher;
                try {
                    publisher = new Publisher("188.166.37.53", "admin", "admin");
                    publisher.sendMessage(name.getText(), enMessage, recip);
                } catch (Exception ex) {
                    Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                log.append("keys are empty" + newline);
            }
        }
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
    
    public void startup(String username) throws Exception{
                Publisher publisher = new Publisher("188.166.37.53", "admin", "admin");
		RecieveMessages messageRetriever = new RecieveMessages("188.166.37.53", "admin", "admin");
		MessageQueue<String> queue = new MessageQueue<String>();
                Thread messagePrinter = new Thread(new MessagePrinterThread(queue));
                messagePrinter.start();
		
		String channel = init(username, publisher, messageRetriever, queue);
		System.out.println(channel);
		messageRetriever.startup(channel, username, queue);
	
		
	}
    
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
                        
                        caesarCipher cc = new caesarCipher();
                        
                        log.append(messageEntry.get(1)+": "+cc.decrypt(messageEntry.get(0), keys));
			
		}
	}
}
    
}
