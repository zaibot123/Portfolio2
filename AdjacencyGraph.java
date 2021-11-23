import java.util.ArrayList;

public class AdjacencyGraph {
    int StartVertex=0;
    int inf=999999;
    private ArrayList<Vertex> vertices;

    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>(); //constructor arraylist med navn vertices
    }
    public void addVertex(Vertex v) {
        vertices.add(v);
    }
    public void addEdge(Vertex from, Vertex to, Integer dist){
        if (!(vertices.contains(from) && vertices.contains(to))){
            System.out.println("Vertex not found");
            return;
        }
        Edge newEdge = new Edge(from,to,dist);
    }

    public void MSTPrims(){

        MinHeap<Vertex> Q  =new MinHeap<>();                     // min heap navn er Q (en arraylist)

        for (int i = 0; i<vertices.size();i++) {                 //put all vertices in heap distance vertices to vertices = inf
            vertices.get(i).distance = inf;
            vertices.get(i).prev = null;
            Q.Insert(vertices.get(i));                         //insert values from adjacency list to min heap (Q)
            vertices.get(i).visited = false;                      // mark visited
        }
        if (vertices.size()>0){
            vertices.get(0).distance=0;                         //set root start vertex to 0 if adjecnecy list exist
            int pos = Q.getPosition(vertices.get(0));
            Q.decreasekey(pos);
        }
        int MST=0;
        while(!Q.isEmpty()){
            Vertex u=Q.extractMin();                            //take minimum ( get edge with least weight)
            for (int v = 0; v  < u.getOutEdges().size();v ++) {
                if (u.getOutEdges().get(v).getWeight() < u.getOutEdges().get(v).getToVertex().distance) {
                    u.getOutEdges().get(v).getToVertex().distance = u.getOutEdges().get(v).getWeight();
                    u.getOutEdges().get(v).getToVertex().prev = u;

                    int pos = Q.getPosition(u.getOutEdges().get(v).getToVertex());
                    Q.decreasekey(pos);
                }
            }
            MST+=u.getDistance();

        }

    }
    public void printMST(){
        System.out.println("Minnimum spanning tree distance");
        for (int k =1; k< vertices.size();k++){
            System.out.println("Parent " + vertices.get(k).prev.getName()+ " to " +vertices.get(k).getName()+ " weight: "+vertices.get(k).distance);
        }

    }


    public void printGraph(){
        Vertex currentv;
        for (int i = 0; i < vertices.size(); i++) {
            currentv=vertices.get(i);
            System.out.println("Edges from Vertex " + currentv.getName());
            for (int j = 0; j < currentv.getOutEdges().size(); j++)
            {
                Edge currente=currentv.getOutEdges().get(j);
                System.out.println("To " + currente.getToVertex().getName()+ "  weight " +currente.getWeight());
            }
            System.out.println(" ");

        }
    }
}

class Vertex implements Comparable<Vertex> {
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public ArrayList<Edge> getOutEdges() {
        return outEdges;
    }
    public void setOutEdges(ArrayList<Edge> outEdges) {
        this.outEdges = outEdges;
    }
    public Integer getDistance() {
        return distance;
    }
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    private String Name;
    private ArrayList<Edge> outEdges;
    Integer distance = Integer.MAX_VALUE;
    boolean visited=false;

    Vertex prev = null;
    public Vertex(String ID) {
        this.Name = ID;               //constructor
        outEdges = new ArrayList<>();
    }
    public int compareTo(Vertex o) {
        if (this.distance < o.distance)
            return -1;
        if (this.distance > o.distance)
            return 1;
        return 0;
    }

    public void addOutEdge(Edge outEdge) {
        outEdges.add(outEdge);
    }

}

class Edge {
    public Vertex getFromVertex() {
        return fromVertex;
    }
    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }
    public Vertex getToVertex() {
        return toVertex;
    }
    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    private Vertex fromVertex;
    private Vertex toVertex;
    private Integer weight;

    public Edge(Vertex from, Vertex to, Integer Cost) {
        fromVertex = from;
        toVertex = to;
        weight = Cost;
        from.addOutEdge(this);

    }

}
