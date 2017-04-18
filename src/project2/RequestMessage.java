package project2;

public class RequestMessage implements IMessage{

	int proccess,logicalValue;
	Worker worker;
	
	public RequestMessage(int proccess,int logicalValue,Worker worker) {
		this.proccess=proccess;
		this.logicalValue=logicalValue;
		this.worker=worker;
	}
	
	
	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
	}
	
}
