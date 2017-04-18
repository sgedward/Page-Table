package project2;

public class TerminationMessage implements IMessage {

	 public TerminationMessage() {
		
	}
	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
		
	}

}
