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
        /*
        Dijkstra algo = new Dijkstra(Dijkstra.Element.EDGE, null, "weight");
        algo.init(graph);
        algo.setSource(graph.getNode("Brugge"));
        algo.compute();
        Path path = algo.getPath(graph.getNode("Luik"));
        algo.clear();
        
     		for (Node node : path.getEachNode())
     			node.addAttribute("ui.style", "fill-color: blue;");
     		for (Edge edge : path.getEachEdge())
     			edge.addAttribute("ui.style", "fill-color: red;");

        */
       
    }

    
}
