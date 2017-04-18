package project2;

public abstract class Component {
	
	protected Component logger;
	
	protected void log(String logString){
		logString=this.getClass()+":"+logString;
		if(logger != null){
			
		}
		else{
			System.out.println(logString);
		}
	}
	
	public abstract void send(IMessage message);
	
	public abstract void start();
	
	public void handle(IMessage message){
		log("Unhandles message"+message.toString());
	}
	
	public void handle(RequestMessage msg){
		handle((IMessage) msg);
	}
	
	public void handle(LookUpMessage msg){
		handle((IMessage) msg);
	}
	
	public void handle(ResultMessage msg){
		handle((IMessage) msg);
	}
	public void handle(FaultResultMessage msg){
		handle((IMessage) msg);
	}
	
	public void handle(TerminationMessage msg){
		handle((IMessage) msg);
	}
	
	public void handle(UpdateMessage msg){
		handle((IMessage) msg);
	}
	
}
