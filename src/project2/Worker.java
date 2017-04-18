package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends ThreadComponent {
	protected Scanner s;
	protected ThreadComponent receiver;
	protected int number;
	private volatile boolean terminated;
	
	public Worker(int number,File f,ThreadComponent receiver) throws IOException{
		s=new Scanner(f);
		this.receiver=receiver;
		this.number=number;
		this.terminated=false;
	}
	
	@Override
	public void start(){
		ResultMessage msg=new ResultMessage();
		super.q.offer(msg);
		super.start();
	}

	
	
	
	public void handle(ResultMessage msg){
		if(terminated == true){
			return;
		}
		if(s.hasNextInt()){
			int logicalValue=s.nextInt();
			//request message create here;
			RequestMessage request=new RequestMessage(number, logicalValue,Worker.this);
			receiver.send(request);
		//	System.out.println("proccess" +number+": Message send");
		}
		else{
		System.out.println("[Proccess: "+number+"] end");
		terminated=true;
		s.close();
		}
	}
	
	
	public void handle(TerminationMessage msg){
		terminated=true;
		s.close();
	}
 
	
	

}
