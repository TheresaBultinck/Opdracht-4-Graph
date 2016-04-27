package graphstream;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;

public class App 
{
    public static void main( String[] args )
    {
    	Traffic t = new Traffic();
    	t.Display();
    	while (true) {
    		t.addTraffic();
    		t.addAccidents();
    		
    		
    		t.visualize("fill-color: black;" , "fill-color: black;");
    		
    		t.doDijkstra("Luik", "Gent");
    		
    		t.visualize("fill-color: red;" , "fill-color: blue;");
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
