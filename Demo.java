import java.util.LinkedList;

public class Demo {

    public static void main(String[] args) {
        int vertices = 5;
        Graph graph = new Graph(vertices);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 0, 4);
        graph.addEdge(4, 1, 4);

        int[] dijkstra0 = dijkstra(graph, 0);

        graph.printAdjList();
        graph.printMatrix();

        printArray(dijkstra0);
    }

    public static int[] dijkstra(Graph a_graph, int src)
    {
        int[][] graph = a_graph.getMatrix();
        int V = a_graph.getVertices();

        int dist[] = new int[V];
        // int prev[] = new int[V];
        Boolean mark[] = new Boolean[V];
    
        // Initialize dist, prev, mark
        for (int i = 0; i < V; i++) {
            dist[i] = graph[src][i];
            // prev[i] = src;
            mark[i] = false;
        }
    
        mark[src] = true;
    
        for (int count = 0; count < V - 1; count++) {

            int u = findMinimumDistance(dist, mark);
    
            mark[u] = true;
    
            for (int v = 0; v < V; v++)
            {
                // !mark[v] ~ if the following vertex is not marked yet...
                // graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE


                // System.out.print(dist[v]);
                // System.out.println();
                // System.out.print(dist[u] + graph[u][v]);

                // if (!sptSet[v] && graph[u][v] != 0
                //     && dist[u] != Integer.MAX_VALUE
                //     && dist[u] + graph[u][v] < dist[v])
                //     dist[v] = dist[u] + graph[u][v];

                if(u == 2)
                {
                    System.out.println(v);
                    System.out.println("1: " + !mark[v]);
                    System.out.println("2: " + (graph[u][v] != 0 && graph[u][v] != -1));
                    System.out.println("3: " + (dist[v] > dist[u] + graph[u][v]));
                    System.out.println(dist[v]);
                    System.out.println(dist[u] + graph[u][v]);
                    System.out.println();
                }

                if (!mark[v] && graph[u][v] != 0 && graph[u][v] != -1 && (dist[v] == -1 || dist[v] > dist[u] + graph[u][v]))
                {
                    dist[v] = dist[u] + graph[u][v];
                    // prev[v] = u;
                }
            }
        }

        return dist;
    }

    public static int findMinimumDistance(int dist[], Boolean mark[])
    {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
    
        for (int v = 0; v < dist.length; v++)
        {
            // not marked
            // not infinity / -1
            // is it less than the minimum?
            if (!mark[v] && dist[v] != -1 && dist[v] != 0 && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        // printArray(dist);

        // System.out.println();
        
        // System.out.println(dist.length);

        System.out.println(minIndex);

        return minIndex;
    }

    public static void printArray(int[] arr)
    {
        int vertices = arr.length;

        System.out.println();

        for (int i = 0; i < vertices; i++) 
        {
            System.out.printf("%2d ", i);
        }        

        System.out.println();

        for (int i = 0; i < vertices; i++) 
        {
            System.out.printf("%2d ", arr[i]);
        }
    }
}

