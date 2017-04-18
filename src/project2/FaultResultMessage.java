package project2;

public class FaultResultMessage implements IMessage {
	
		public int process,page,offset,frame,oldProcess,oldPage;
		public Worker worker;

	 public FaultResultMessage(int process,int page,int offset,int frame,Worker worker) {
		this.worker=worker;
		this.process=process;
		this.page=page;
		this.frame=frame;
		this.offset=offset;
		this.oldPage=-1;
		this.oldProcess=-1;
	}
	 
	 public FaultResultMessage(int process,int page,int offset,int frame,Worker worker,int oldProcess, int oldPage) {
		 this.worker=worker;
			this.process=process;
			this.page=page;
			this.frame=frame;
			this.offset=offset;
			this.oldPage=oldPage;
			this.oldProcess=oldProcess;
	}
	@Override
	public void dispatch(ThreadComponent receiver) {
		receiver.handle(this);
		
	}

}
