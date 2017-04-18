package project2;

public class PageFaultHandler extends ThreadComponent {
	
	class Frame{
		public long enterTime;
		public int page;
		public int process;
		public boolean empty;
		
		
		public Frame(){
			enterTime=0;
			page=-1;
			process=-1;
			empty=true;
		}
	}
	
	Frame[] frameSize;
	int size;
	private long counter;
	public PageFaultHandler(int frame){
		frameSize=new Frame[frame];
		size=frame;
		counter=0;
		for(int i=0; i<frameSize.length; ++i){
			frameSize[i]=new Frame();
		}
	}
	
	@Override
	public void handle(LookUpMessage msg){
	//	System.out.println("handler receive");
		int number=msg.process;
		ThreadComponent sender=msg.sender;
		++counter;
		
		for( int i=0; i<frameSize.length; i++){
			if(frameSize[i].empty == true){
				
				System.out.println("[Process "+number+"] finds a free frame in main memory (frame number = "+i+").");
				FaultResultMessage result=new FaultResultMessage(number, msg.page,msg.offset,i,msg.worker);
				frameSize[i].enterTime=counter;
				frameSize[i].page=msg.page;
				frameSize[i].process=number;
				frameSize[i].empty=false;
				sender.send(result);
				System.out.println("[Process "+number+"] issues an I/O operation to swap in demanded page (page number = "+msg.page+").");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return;
				
			}
		}
		
		long max=0;
		int index=-1;
		for( int i=0; i<frameSize.length; i++){
			if(counter-frameSize[i].enterTime > max){
				max=counter-frameSize[i].enterTime;
				index=i;
			}
		}
		System.out.println("[Process "+number+"] replaces a frame (frame number = "+index+") from the main memory.");
		FaultResultMessage result=new FaultResultMessage(number,msg.page, msg.offset,index, msg.worker, frameSize[index].process, frameSize[index].page);
		frameSize[index].enterTime=counter;
		frameSize[index].page=msg.page;
		frameSize[index].process=number;
		System.out.println("[Process "+number+"] issues an I/O operation to swap in demanded page (page number = "+msg.page+").");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sender.send(result);
		
	}
	
	@Override
	public void handle(UpdateMessage msg){
		++counter;
		frameSize[msg.frame].enterTime=counter;
	}
	
	
}
