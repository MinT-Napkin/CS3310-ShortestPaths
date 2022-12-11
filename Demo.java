import java.util.LinkedList;
import java.util.Random;

public class Demo {
    
    public static void main(String[] args) {
        
        // DENSE GRAPH
        System.out.println("\n-----Sparse GRAPH-----");
        Graph dGraph = new Graph(5);
        dGraph.addEdge(0, 1, 4);
        dGraph.addEdge(1, 2, 2);
        dGraph.addEdge(2, 3, 7);
        dGraph.addEdge(3, 4, 2);
        dGraph.addEdge(4, 0, 1);
        
        int[] dijkstra0 = dijkstra(dGraph, 0);
        int[] dijkstra1 = dijkstra(dGraph, 1);
        int[] dijkstra2 = dijkstra(dGraph, 2);
        int[] dijkstra3 = dijkstra(dGraph, 3);
        int[] dijkstra4 = dijkstra(dGraph, 4);
        
        dGraph.printAdjList();
        dGraph.printMatrix();
        
        System.out.println("\nSource: 0");
        printArray(dijkstra0);
        
        System.out.println("\nSource: 1");
        printArray(dijkstra1);
        
        System.out.println("\nSource: 2");
        printArray(dijkstra2);
        
        System.out.println("\nSource: 3");
        printArray(dijkstra3);
        
        System.out.println("\nSource: 4");
        printArray(dijkstra4);


        // SPARSE GRAPH
        System.out.println("\n-----Dense GRAPH-----");
        Graph sGraph = new Graph(5);
        sGraph.addEdge(0, 1, 4);
        sGraph.addEdge(0, 2, 3);
        sGraph.addEdge(1, 3, 2);
        sGraph.addEdge(1, 2, 5);
        sGraph.addEdge(2, 3, 7);
        sGraph.addEdge(3, 4, 2);
        sGraph.addEdge(4, 0, 4);
        sGraph.addEdge(4, 1, 4);

        int[] sdijkstra0 = dijkstra(sGraph, 0);
        int[] sdijkstra1 = dijkstra(sGraph, 1);
        int[] sdijkstra2 = dijkstra(sGraph, 2);
        int[] sdijkstra3 = dijkstra(sGraph, 3);
        int[] sdijkstra4 = dijkstra(sGraph, 4);

        sGraph.printAdjList();
        sGraph.printMatrix();

        System.out.println("\nSource: 0");
        printArray(sdijkstra0);
        
        System.out.println("\nSource: 1");
        printArray(sdijkstra1);
        
        System.out.println("\nSource: 2");
        printArray(sdijkstra2);
        
        System.out.println("\nSource: 3");
        printArray(sdijkstra3);
        
        System.out.println("\nSource: 4");
        printArray(sdijkstra4);


        /* ------------TESTING------------ */

        final int sizeN = 3;
        Graph sparseGraphTest = populateSparseGraph(sizeN);
        Graph denseGraphTest = populateDenseGraph(sizeN);

        int[] sparseGraphShortestPaths;
        int[] denseGraphShortestPaths;
        
        System.out.println("\nTESTING RUNTIME : Random Graphs with " + sizeN + " Vertices\n");

        long startTime;
        long endTime;
        long elapsedTime; //endTime - startTime;

        final long initialAttempts = 10;
        long attempts = initialAttempts;

        long dAvgSparse = 0;
        long fwAvgSparse = 0;
        long dAvgDense = 0;
        long fwAvgDense = 0;
        
        System.out.println("-----BEGINNING TEST-----\n");
        while(attempts > 0)
        {
            // Dijkstra ~ Sparse
            System.out.print(attempts + " ");
            attempts--;
            startTime = System.nanoTime();
            sparseGraphShortestPaths = dijkstra(sparseGraphTest, sizeN);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            dAvgSparse += elapsedTime;
            System.out.print("Dijkstra . . . ");

            // Floyd-Warshall ~ Sparse
            startTime = System.nanoTime();
            // naiveMultiplication(randomMatrix1, randomMatrix2);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            fwAvgSparse += elapsedTime;
            System.out.print("Floyd Warshall . . . \n");

            // Dijkstra ~ Dense
            System.out.print(attempts + " ");
            attempts--;
            startTime = System.nanoTime();
            denseGraphShortestPaths = dijkstra(sparseGraphTest, sizeN);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            dAvgDense += elapsedTime;
            System.out.print("Dijkstra . . . ");

            // Floyd-Warshall ~ Dense
            startTime = System.nanoTime();
            // naiveMultiplication(randomMatrix1, randomMatrix2);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            fwAvgDense += elapsedTime;
            System.out.print("Floyd Warshall . . . \n");

            System.out.print("Dense Complete . . . ");
        }
        System.out.println("\n-----END TEST-----\n");
        
        dAvgSparse /= initialAttempts;
        fwAvgSparse /= initialAttempts;
        dAvgDense /= initialAttempts;
        fwAvgDense /= initialAttempts;

        System.out.printf("Sparse Graph ~ Dijkstra Average Time (Size %d): %d nanosecs\n", sizeN, dAvgSparse);
        System.out.printf("Sparse Graph ~ Floyd Warshall Average Time (Size %d): %d nanosecs\n", sizeN, fwAvgSparse);
        System.out.printf("Sparse Graph ~ Dijkstra Average Time (Size %d): %d nanosecs\n", sizeN, dAvgDense);
        System.out.printf("Sparse Graph ~ Floyd Warshall Average Time (Size %d): %d nanosecs\n", sizeN, fwAvgDense);
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
                if (!mark[v] && graph[u][v] > 0 && (dist[v] == -1 || dist[v] > dist[u] + graph[u][v]))
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
            if (!mark[v] && dist[v] > 0 && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
    public static Graph populateSparseGraph(int n)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(100) + 1;

        Graph result = new Graph(n);

        for (int i = 0; i < n - 1; i++) 
        {
            result.addEdge(i, i+1, randNum);
            randNum = rand.nextInt(100) + 1;
        }

        return result;
    }

    public static Graph populateDenseGraph(int n)
    {
        Random rand = new Random();
        int randNum = rand.nextInt(100) + 1;

        Graph result = new Graph(n);

        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                if(i != j)
                {
                    result.addEdge(i, j, randNum);
                    randNum = rand.nextInt(100) + 1;
                }
            }
        }

        return result;
    }

    public static void printArray(int[] arr)
    {
        int vertices = arr.length;

        for (int i = 0; i < vertices; i++) 
        {
            System.out.printf("%-2d ", i);
        }        

        System.out.println();

        for (int i = 0; i < vertices; i++) 
        {
            System.out.printf("%-2d ", arr[i]);
        }

        System.out.println();
    }
}

