package nl.hr.EncryptedChat.device.poller;

import java.util.LinkedList;

public class MessageQueue<E> {
	
	private LinkedList<E> list = new LinkedList<E>();
	
	public void put(E o){
		list.addLast(o);
	}
	
	public E get(){
		if(list.isEmpty()){
			return null;
		}
		return list.removeFirst();
	}
	
	public E peek(){
		return list.getFirst();
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public int size(){
		return list.size();
	}

}
