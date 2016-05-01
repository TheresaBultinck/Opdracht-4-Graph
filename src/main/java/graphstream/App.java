package graphstream;

import java.util.Scanner;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;

public class App 
{
    public static void main( String[] args )
    {
    	Traffic t = new Traffic();
    	
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Where do you want to go?");
    	String to = scanner.nextLine();
    	if (!t.isValidLocation(to)){
    		System.out.println("Invalid location");
    		System.exit(5);
    	}
    	System.out.println("Where are you now?");
    	String from = scanner.nextLine();
    	if (!t.isValidLocation(from)){
    		System.out.println("Invalid location");
    		System.exit(5);
    	}
	
    	
    	t.Display();
    	while (true) {
    		t.addTraffic();
    		t.addAccidents();
    		
    		
    		t.visualize("fill-color: black; z-index: 0;" , "fill-color: black; z-index: 0;");
    		
    		t.doDijkstra(from,to);
    		
    		t.visualize("fill-color: red; z-index: 11;" , "fill-color: blue; z-index: 10;");
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
 
    }
}
