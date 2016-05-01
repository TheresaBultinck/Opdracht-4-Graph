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
		printNodeNames();
		removeArrowVisualisation();
	}
	
	private void initializeAccidents(){
		for (Edge e: graph.getEachEdge()){
			e.setAttribute("accident", false);
		}
	}
	
	private void printNodeNames(){
        for(Node n: graph.getEachNode()){
            n.setAttribute("ui.style", "text-mode: normal;");
        }
    }
    
    private void removeArrowVisualisation(){
        for (Edge e: graph.getEachEdge()){
            e.setAttribute("ui.style", "arrow-shape: none;");
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
	
	public boolean isValidLocation(String location){
		for(Node n: graph.getEachNode()){
			if(n.getId().equals(location))
				return true;
		} 
		return false;
	}
	
	private  void initialize(Graph graph){
		graph.addNode("Brugge").setAttribute("ui.label", "Brugge");
        graph.addNode("Kortrijk").setAttribute("ui.label", "Kortrijk");
        graph.addNode("Gent").setAttribute("ui.label", "Gent");
        graph.addNode("Bergen").setAttribute("ui.label", "Bergen");
        graph.addNode("Brussel").setAttribute("ui.label", "Brussel");
        graph.addNode("Antwerpen").setAttribute("ui.label", "Antwerpen");
        graph.addNode("Hasselt").setAttribute("ui.label", "Hasselt");
        graph.addNode("Leuven").setAttribute("ui.label", "Leuven");
        graph.addNode("Luik").setAttribute("ui.label", "Luik");
        graph.addNode("Namen").setAttribute("ui.label", "Namen");
        graph.addNode("Waver").setAttribute("ui.label", "Waver");
        graph.addNode("Neufchateau").setAttribute("ui.label", "Neufchateau");
        graph.addNode("Aarlen").setAttribute("ui.label", "Aarlen");

        graph.addEdge("Brugge-Kortrijk", "Brugge", "Kortrijk",true).setAttribute("weight", 56);
        graph.addEdge("Kortrijk-Brugge", "Kortrijk", "Brugge",true).setAttribute("weight", 56);

        graph.addEdge("Brugge-Antwerpen", "Brugge", "Antwerpen",true).setAttribute("weight", 95);
        graph.addEdge("Antwerpen-Brugge", "Antwerpen", "Brugge",true).setAttribute("weight", 95);

        graph.addEdge("Brugge-Gent", "Brugge", "Gent",true).setAttribute("weight", 50);
        graph.addEdge("Gent-Brugge", "Gent", "Brugge",true).setAttribute("weight", 50);

        graph.addEdge("Kortrijk-Bergen", "Kortrijk", "Bergen",true).setAttribute("weight", 83);
        graph.addEdge("Bergen-Kortrijk", "Bergen", "Kortrijk",true).setAttribute("weight", 83);

        graph.addEdge("Gent-Antwerpen", "Gent", "Antwerpen",true).setAttribute("weight", 60);
        graph.addEdge("Antwerpen-Gent", "Antwerpen", "Gent",true).setAttribute("weight",60);

        graph.addEdge("Gent-Brussel", "Gent", "Brussel",true).setAttribute("weight", 50);
        graph.addEdge("Brussel-Gent", "Brussel", "Gent",true).setAttribute("weight", 50);
        
        graph.addEdge("Antwerpen-Hasselt", "Antwerpen", "Hasselt",true).setAttribute("weight", 80);
        graph.addEdge("Hasselt-Antwerpen", "Hasselt", "Antwerpen",true).setAttribute("weight", 80);
        
        graph.addEdge("Brussel-Hasselt", "Brussel", "Hasselt",true).setAttribute("weight",89);
        graph.addEdge("Hasselt-Brussel", "Hasselt", "Brussel",true).setAttribute("weight", 89);
        
        graph.addEdge("Brussel-Waver", "Brussel", "Waver",true).setAttribute("weight",30);
        graph.addEdge("Waver-Brussel", "Waver", "Brussel",true).setAttribute("weight",30);
        
        graph.addEdge("Brussel-Bergen", "Brussel", "Bergen",true).setAttribute("weight", 78);
        graph.addEdge("Bergen-Brussel","Bergen","Brussel",true).setAttribute("weight", 78);
        
        graph.addEdge("Brussel-Leuven", "Brussel", "Leuven",true).setAttribute("weight", 30);
        graph.addEdge("Leuven-Brussel", "Leuven", "Brussel",true).setAttribute("weight",30);
        
        graph.addEdge("Leuven-Hasselt", "Leuven", "Hasselt",true).setAttribute("weight", 59);
        graph.addEdge("Hasselt-Leuven", "Hasselt", "Leuven",true).setAttribute("weight", 59);
        
        graph.addEdge("Leuven-Luik", "Leuven", "Luik",true).setAttribute("weight", 82);
        graph.addEdge("Luik-Leuven", "Luik", "Leuven",true).setAttribute("weight", 82);
       
        graph.addEdge("Waver-Namen", "Waver", "Namen",true).setAttribute("weight", 40);
        graph.addEdge("Namen-Waver", "Namen", "Waver",true).setAttribute("weight", 40);
        
        graph.addEdge("Bergen-Namen", "Bergen", "Namen",true).setAttribute("weight", 75);
        graph.addEdge("Namen-Bergen", "Namen", "Bergen",true).setAttribute("weight",75);
        
        graph.addEdge("Namen-Luik", "Namen", "Luik",true).setAttribute("weight",65);
        graph.addEdge("Luik-Namen", "Luik", "Namen",true).setAttribute("weight", 65);
        
        graph.addEdge("Namen-Neufchateau", "Namen", "Neufchateau",true).setAttribute("weight", 90);
        graph.addEdge("Neufchateau-Namen", "Neufchateau", "Namen",true).setAttribute("weight", 90);
        
        graph.addEdge("Luik-Neufchateau", "Luik", "Neufchateau",true).setAttribute("weight",110);
        graph.addEdge("Neufchateau-Luik", "Neufchateau", "Luik",true).setAttribute("weight", 110);
        
        graph.addEdge("Neufchateau-Aarlen", "Neufchateau", "Aarlen",true).setAttribute("weight", 37);
        graph.addEdge("Aarlen-Neufchateau", "Aarlen", "Neufchateau",true).setAttribute("weight",37);
    }
}
