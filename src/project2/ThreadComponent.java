package project2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ThreadComponent extends Component{
	protected BlockingQueue<IMessage> q;
	protected Thread reader;
	
	protected ThreadComponent(){
		q=new LinkedBlockingQueue<>();
		reader=new Thread(new Reader());
	}
	public void send(IMessage message){
	q.offer(message);
	}
	
	public void start(){
		reader.start();
	}
	
	protected class Reader implements Runnable{

		@Override
		public void run() {
			while(true){
				IMessage message;
				try {
					message = q.take();
					message.dispatch(ThreadComponent.this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Throwable t){
					log(t.toString());
				}
			
			}
			
		}
		
	}
	
	
	
	
}
