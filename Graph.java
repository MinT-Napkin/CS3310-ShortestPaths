import java.util.LinkedList;

public class Graph {

    
    private final static int INF = Integer.MAX_VALUE;

    private int vertices;
    private int[][] matrix;
    private LinkedList<Edge>[] adjacencyList;

    public Graph(int vertices) 
    {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];

        matrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) 
        {
            for (int j = 0; j < vertices; j++) 
            {
                // indicates no edge
                matrix[i][j] = INF;

                if(i == j)
                {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < vertices ; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int destination, int weight) 
    {
        Edge edge = new Edge(source, destination, weight);

        // Matrix
        matrix[source][destination] = weight;

        // Adj List
        adjacencyList[source].addFirst(edge); //for directed graph
    }

    public void printAdjList(){
        for (int i = 0; i < vertices ; i++) 
        {
            LinkedList<Edge> list = adjacencyList[i];
            for (int j = 0; j < list.size() ; j++) 
            {
                System.out.println((i) + " -> " +
                (list.get(j).getDestination()) + " __ Weight: " + list.get(j).getWeight());
            }
        }
    }

    public void printMatrix(){

        System.out.print("\n   ");

        for (int i = 0; i < vertices; i++) 
        {
            System.out.printf(" %2d ", i);
        }

        System.out.println("\n");

        for (int i = 0; i < vertices ; i++) 
        {
            System.out.print(i + "  ");

            for (int j = 0; j < vertices ; j++) 
            {
                if(matrix[i][j] == INF)
                {
                    System.out.printf(" %2s ", "∞");
                }
                else
                {
                    System.out.printf(" %2d ", matrix[i][j]);
                }
            }

            System.out.println();
        }
    }

    public int getVertices() {
        return vertices;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public LinkedList<Edge>[] getAdjacencyList() {
        return adjacencyList;
    }
}
