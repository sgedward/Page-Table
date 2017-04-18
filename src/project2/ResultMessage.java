package project2;

public class ResultMessage implements IMessage {
	
	 public ResultMessage() {
		
	}

	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
		
	}
	

}
