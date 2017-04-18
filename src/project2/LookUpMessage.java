package project2;

public class LookUpMessage implements IMessage {
	
	public int process,page,offset;
	public Worker worker;
	public ThreadComponent sender;
	
	public LookUpMessage(int process,int page,int offset, ThreadComponent sender, Worker worker) {
		this.process=process;
		this.page=page;
		this.offset=offset;
		this.worker=worker;
		this.sender=sender;
	}
	
	

	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
		
	}

}
