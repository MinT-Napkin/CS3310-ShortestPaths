import java.util.LinkedList;

public class Graph {

    private int vertices;
    private LinkedList<Edge> [] adjacencyList;

    public Graph(int vertices) 
    {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];

        for (int i = 0; i <vertices ; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) 
    {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList[source].addFirst(edge); //for directed graph
    }

    public void printEdgesWithWeights(){
        for (int i = 0; i < vertices ; i++) 
        {
            LinkedList<Edge> list = adjacencyList[i];
            for (int j = 0; j <list.size() ; j++) 
            {
                System.out.println(i + " -> " +
                list.get(j).getDestination() + " __ Weight: " + list.get(j).getWeight());
            }
        }
    }
}
