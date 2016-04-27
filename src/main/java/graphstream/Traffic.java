package graphstream;

import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.algorithm.Dijkstra;


public class Traffic {

	private Graph graph;
	private Random random; 
	private Dijkstra algo;
	private Path path; 
	
	public Traffic(){
		graph = new MultiGraph("Belgium");
		random = new Random();
		algo = new Dijkstra(Dijkstra.Element.EDGE, null, "weight");
		path = null;
		initialize(graph);
		initializeAccidents();
	}
	
	private void initializeAccidents(){
		for (Edge e: graph.getEachEdge()){
			e.setAttribute("accident", false);
		}
	}
	
	/**
	 * Add 1K cars to random edge.
	 */
	public void addTraffic(){		
		int rng = random.nextInt(graph.getEdgeCount());
		int i = 0;
		for(Edge e: graph.getEachEdge()){
			if(i == rng){
				int w = e.getAttribute("weight");
				e.setAttribute("weight", w+1);
				System.out.println("1K cars added to " + e.getId());
				System.out.println("Total weight: " + (w+1));
				break;
			}
		i++;
		}
	}
	
	/**
	 * Check for accidents, add weight if necessary. 
	 */
	public void addAccidents(){
		for(Edge e: graph.getEachEdge()){
			int w = e.getAttribute("weight");
			double accidentPercentage = 0.1*w;
			double rng = random.nextDouble() *100;
			if(accidentPercentage > rng){
				int extraWeight = 0;
				if (e.getAttribute("accident")){
					extraWeight = 50;
				} else {
					extraWeight = 30;
				}
				
				e.setAttribute("weight", w+extraWeight);
				
				System.out.println("Accident on road: " + e.getId());
				System.out.println("Total weight: " + (w+extraWeight));
			} 
		}
	}
	
	public void Display(){
		graph.display();
	}
	
	public void doDijkstra(String from, String to){
        algo.init(graph);
        algo.setSource(graph.getNode(from));
        algo.compute();
        
        this.path = algo.getPath(graph.getNode(to));
        
        algo.clear();
        prettyPrintPath();
	}
	
	public void visualize(String nodeStyle, String edgeStyle){
		if(path != null){
			for (Node node : path.getEachNode())
	 			node.addAttribute("ui.style", nodeStyle);
	 		for (Edge edge : path.getEachEdge())
	 			edge.addAttribute("ui.style", edgeStyle);
		}
	}
	
	private void prettyPrintPath(){
		if (path == null){
			return;
		}
		String prettyNodes = "";
		for (Node n : path.getEachNode()){
			prettyNodes += " => ";
			prettyNodes += n.getId();
		}
		System.out.println(prettyNodes);
		
		String prettyEdges = "";
		for (Edge e : path.getEachEdge()){
			prettyEdges += " => ";
			prettyEdges += e.getId();
		}
		System.out.println(prettyEdges);
	}
	
	private  void initialize(Graph graph){
    	graph.addNode("Brugge");
        graph.addNode("Kortrijk");
        graph.addNode("Gent");
        graph.addNode("Bergen");
        graph.addNode("Brussel");
        graph.addNode("Antwerpen");
        graph.addNode("Hasselt");
        graph.addNode("Leuven");
        graph.addNode("Luik");
        graph.addNode("Namen");
        graph.addNode("Waver");
        graph.addNode("Neufchateau");
        graph.addNode("Aarlen");

        graph.addEdge("Brugge-Kortrijk", "Brugge", "Kortrijk").setAttribute("weight", 56);
        graph.addEdge("Kortrijk-Brugge", "Kortrijk", "Brugge").setAttribute("weight", 56);

        graph.addEdge("Brugge-Antwerpen", "Brugge", "Antwerpen").setAttribute("weight", 95);
        graph.addEdge("Antwerpen-Brugge", "Antwerpen", "Brugge").setAttribute("weight", 95);

        graph.addEdge("Brugge-Gent", "Brugge", "Gent").setAttribute("weight", 50);
        graph.addEdge("Gent-Brugge", "Gent", "Brugge").setAttribute("weight", 50);

        graph.addEdge("Kortrijk-Bergen", "Kortrijk", "Bergen").setAttribute("weight", 83);
        graph.addEdge("Bergen-Kortrijk", "Bergen", "Kortrijk").setAttribute("weight", 83);

        graph.addEdge("Gent-Antwerpen", "Gent", "Antwerpen").setAttribute("weight", 60);
        graph.addEdge("Antwerpen-Gent", "Antwerpen", "Gent").setAttribute("weight",60);

        graph.addEdge("Gent-Brussel", "Gent", "Brussel").setAttribute("weight", 50);
        graph.addEdge("Brussel-Gent", "Brussel", "Gent").setAttribute("weight", 50);
        
        graph.addEdge("Antwerpen-Hasselt", "Antwerpen", "Hasselt").setAttribute("weight", 80);
        graph.addEdge("Hasselt-Antwerpen", "Hasselt", "Antwerpen").setAttribute("weight", 80);
        
        graph.addEdge("Brussel-Hasselt", "Brussel", "Hasselt").setAttribute("weight",89);
        graph.addEdge("Hasselt-Brussel", "Hasselt", "Brussel").setAttribute("weight", 89);
        
        graph.addEdge("Brussel-Waver", "Brussel", "Waver").setAttribute("weight",30);
        graph.addEdge("Waver-Brussel", "Waver", "Brussel").setAttribute("weight",30);
        
        graph.addEdge("Brussel-Bergen", "Brussel", "Bergen").setAttribute("weight", 78);
        graph.addEdge("Bergen-Brussel","Bergen","Brussel").setAttribute("weight", 78);
        
        graph.addEdge("Brussel-Leuven", "Brussel", "Leuven").setAttribute("weight", 30);
        graph.addEdge("Leuven-Brussel", "Leuven", "Brussel").setAttribute("weight",30);
        
        graph.addEdge("Leuven-Hasselt", "Leuven", "Hasselt").setAttribute("weight", 59);
        graph.addEdge("Hasselt-Leuven", "Hasselt", "Leuven").setAttribute("weight", 59);
        
        graph.addEdge("Leuven-Luik", "Leuven", "Luik").setAttribute("weight", 82);
        graph.addEdge("Luik-Leuven", "Luik", "Leuven").setAttribute("weight", 82);
       
        graph.addEdge("Waver-Namen", "Waver", "Namen").setAttribute("weight", 40);
        graph.addEdge("Namen-Waver", "Namen", "Waver").setAttribute("weight", 40);
        
        graph.addEdge("Bergen-Namen", "Bergen", "Namen").setAttribute("weight", 75);
        graph.addEdge("Namen-Bergen", "Namen", "Bergen").setAttribute("weight",75);
        
        graph.addEdge("Namen-Luik", "Namen", "Luik").setAttribute("weight",65);
        graph.addEdge("Luik-Namen", "Luik", "Namen").setAttribute("weight", 65);
        
        graph.addEdge("Namen-Neufchateau", "Namen", "Neufchateau").setAttribute("weight", 90);
        graph.addEdge("Neufchateau-Namen", "Neufchateau", "Namen").setAttribute("weight", 90);
        
        graph.addEdge("Luik-Neufchateau", "Luik", "Neufchateau").setAttribute("weight",110);
        graph.addEdge("Neufchateau-Luik", "Neufchateau", "Luik").setAttribute("weight", 110);
        
        graph.addEdge("Neufchateau-Aarlen", "Neufchateau", "Aarlen").setAttribute("weight", 37);
        graph.addEdge("Aarlen-Neufchateau", "Aarlen", "Neufchateau").setAttribute("weight",37);
    }
}
