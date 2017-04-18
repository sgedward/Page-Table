package project2;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class main {
	public static void main(String agcs[]) throws IOException{
		Scanner s=new Scanner(System.in);
		int pageSize=s.nextInt();
		int page=s.nextInt();
		int frame=s.nextInt();
		int numThread=s.nextInt();
		PageFaultHandler handler=new PageFaultHandler(frame);
		ThreadManager manager=new ThreadManager(page, numThread, handler, pageSize);
		Worker[] worker=new Worker[numThread];
		for(int i=0; i<worker.length; ++i){
			File f=new File("trace_"+(i+1)+".txt");
			worker[i]=new Worker(i, f,manager);
		}
		handler.start();
		manager.start();
		for(int i=0; i<worker.length; ++i){
			worker[i].start();
		}
		s.close();
		
	}
}
