package project2;

public class UpdateMessage implements IMessage{
	public int frame;

	public UpdateMessage(int frame) {
		this.frame=frame;
	}
	
	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
		
	}

}
