package project2;




public class ThreadManager extends ThreadComponent{
	

	private Table Table;
	private ThreadComponent handler;
	private int pageSize;
	
	 class Page{
		public int offset;
		public int f;
		public boolean check;
		
		public Page(){
			check=false;
			f=-1;
			offset=-1;
		}
	}
	class Pages{
		
		public Page[] p;
		
		public Pages(int pages){
			p=new Page[pages];
			for(int i=0; i<p.length; ++i){
				p[i]=new Page();
			}
			
		}
		
	}
	
	 class Table{
		public Pages[] table;
		
		public Table(int pages, int numthread){
			table=new Pages[numthread];
			for(int i=0; i<table.length; ++i){
				table[i]=new Pages(pages);
			}
		}
	}
	
	
	public ThreadManager(int pages, int numthread,ThreadComponent handler,int pageSize) {
		Table=new Table(pages,numthread);
		this.handler=handler;
		this.pageSize=pageSize;
	}
	
	
	private void insert(int logicalValue, int process,Worker receiver){
		int page=logicalValue / pageSize;
		int offset=logicalValue % pageSize;
		
		if(page >= Table.table[process].p.length || logicalValue < 0){
			System.out.println("[Process "+process+"]" +" address "+logicalValue+" is invalid and so the user process terminates");
			TerminationMessage result=new TerminationMessage();
			receiver.send(result);
			return;
		}
		
			if(Table.table[process].p[page].check){
				Table.table[process].p[page].offset=offset;
				System.out.println("[Process "+process+"] accesses address "+logicalValue+"(page number = "+page+", page offset="+offset+" ) in main memory (frame "
						+ "number ="+Table.table[process].p[page].f+").");
				UpdateMessage update=new UpdateMessage(Table.table[process].p[page].f);
				handler.send(update);
				ResultMessage result=new ResultMessage();
				receiver.send(result);
			}
			
			else{
				System.out.println("[Process "+process+"] accesses address "+logicalValue+" (page number = "+page+","
						+ "page offset ="+offset+") not in main memory.");
				LookUpMessage msg=new LookUpMessage(process,page,offset,this,receiver);
				handler.send(msg);
				
			}
		
		
		
		
	}
	
	@Override
	public void handle(RequestMessage msg){
	//	System.out.println("Manager receive from"+"process"+msg.proccess);
		this.insert(msg.logicalValue, msg.proccess,msg.worker);
		
	}
	
	@Override
	public void handle(FaultResultMessage msg){
		System.out.println("[Process "+msg.process+"] demanded page (page number ="+msg.page+") has been swapped in main memory"+
"(frame number = "+msg.frame+").");
		if(msg.oldProcess > -1){
			Table.table[msg.oldProcess].p[msg.oldPage].check=false;
		}
		
		
		Table.table[msg.process].p[msg.page].check=true;
		Table.table[msg.process].p[msg.page].f=msg.frame;
		System.out.println("[Process "+msg.process+"]"+
				"accesses address "+(msg.page*pageSize +msg.offset)+" (page number = "+msg.page+", page offset ="+msg.offset+
				") in main memory (frame number"+
						"= "+msg.frame+").");
		
		ResultMessage result=new ResultMessage();
		msg.worker.send(result);
		
	}
	
	
	
	
	
}
