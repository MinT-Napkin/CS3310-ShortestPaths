import java.util.LinkedList;
import java.util.Random;

public class Demo {

    private final static int INF = Integer.MAX_VALUE;
    
    public static void main(String[] args) {
        
        // SPARSE GRAPH
        System.out.println("\n-----Sparse GRAPH-----");
        Graph sGraph = new Graph(5);
        sGraph.addEdge(0, 1, 4);
        sGraph.addEdge(1, 2, 2);
        sGraph.addEdge(2, 3, 7);
        sGraph.addEdge(3, 4, 2);
        sGraph.addEdge(4, 0, 1);
        
        int[][] sDijkstra = dijkstraAll(sGraph);
        int[][] sFloyd = sGraph.getMatrix();
        
        // System.out.println("\n***ADJACENCY LIST***");
        // sGraph.printAdjList();
        System.out.println("\n***MATRIX***");
        sGraph.printMatrix();

        System.out.println("\n\n***EXTRA CREDIT RESULTS***");
        shortestPath(sGraph, 0, 3);
        
        System.out.println("\n\n***DIJKSTRA RESULTS***");
        printSolution(sDijkstra, "Dijkstra");

        System.out.println("\n\n***FLOYD WARSHALL RESULTS***\n");
        floydWarshall(sFloyd);
        printSolution(sFloyd, "Floyd-Warshall");


        // DENSE GRAPH
        System.out.println("\n-----Dense GRAPH-----");
        Graph dGraph = new Graph(5);
        dGraph.addEdge(0, 1, 4);
        dGraph.addEdge(0, 2, 3);
        dGraph.addEdge(1, 3, 2);
        dGraph.addEdge(1, 2, 5);
        dGraph.addEdge(2, 3, 7);
        dGraph.addEdge(3, 4, 2);
        dGraph.addEdge(4, 0, 4);
        dGraph.addEdge(4, 1, 4);

        int[][] dDijkstra = dijkstraAll(dGraph);
        int[][] dFloyd = dGraph.getMatrix();

        // System.out.println("\n***ADJACENCY LIST***");
        // dGraph.printAdjList();
        System.out.println("\n***MATRIX***");
        dGraph.printMatrix();

        System.out.println("\n\n***EXTRA CREDIT RESULTS***");
        shortestPath(dGraph, 0, 4);

        System.out.println("\n\n***DIJKSTRA RESULTS***");
        printSolution(dDijkstra, "Dijkstra");

        System.out.println("\n\n***FLOYD WARSHALL RESULTS***\n");
        floydWarshall(dFloyd);
        printSolution(dFloyd, "Floyd-Warshall");

        /* ------------TESTING------------ */

        final int sizeN = 3;
        Graph sparseGraphTest = populateSparseGraph(sizeN);
        Graph denseGraphTest = populateDenseGraph(sizeN);

        int[][] tempS = sparseGraphTest.getMatrix();
        int[][] tempD = denseGraphTest.getMatrix();

        System.out.println("\n\n***TESTING***");
        
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
            dijkstra(sparseGraphTest, 0);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            dAvgSparse += elapsedTime;
            System.out.print("Dijkstra . . . ");

            // Floyd-Warshall ~ Sparse
            startTime = System.nanoTime();
            floydWarshall(tempS);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            fwAvgSparse += elapsedTime;
            tempS = sparseGraphTest.getMatrix();
            System.out.print("Floyd Warshall . . . ");

            System.out.print("Sparse Complete . . . ");

            // Dijkstra ~ Dense
            startTime = System.nanoTime();
            dijkstra(denseGraphTest, 0);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            dAvgDense += elapsedTime;
            System.out.print("Dijkstra . . . ");

            // Floyd-Warshall ~ Dense
            startTime = System.nanoTime();
            floydWarshall(tempD);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            fwAvgDense += elapsedTime;
            tempD = denseGraphTest.getMatrix();
            System.out.print("Floyd Warshall . . . ");

            System.out.print("Dense Complete . . . \n");
        }
        System.out.println("\n-----END TEST-----\n");
        
        dAvgSparse /= initialAttempts;
        fwAvgSparse /= initialAttempts;
        dAvgDense /= initialAttempts;
        fwAvgDense /= initialAttempts;

        System.out.printf("Sparse Graph ~ Dijkstra Average Time (Size %d): %d nanosecs\n", sizeN, dAvgSparse);
        System.out.printf("Sparse Graph ~ Floyd Warshall Average Time (Size %d): %d nanosecs\n", sizeN, fwAvgSparse);
        System.out.printf("Dense Graph ~ Dijkstra Average Time (Size %d): %d nanosecs\n", sizeN, dAvgDense);
        System.out.printf("Dense Graph ~ Floyd Warshall Average Time (Size %d): %d nanosecs\n", sizeN, fwAvgDense);
    }

    // Dijkstra Algorithm (Minh Tran)
    public static int[][] dijkstraAll(Graph a_graph)
    {
        int vertices = a_graph.getVertices();
        int[][] result = new int[vertices][vertices];

        for (int i = 0; i < vertices; i++) 
        {
            result[i] = dijkstra(a_graph, i);    
        }

        return result;
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

            if(u != INF)
            {
                mark[u] = true;
        
                for (int v = 0; v < V; v++)
                {
                    if (!mark[v] && graph[u][v] > 0 && graph[u][v] != INF && (dist[v] == INF || dist[v] > dist[u] + graph[u][v]))
                    {
                        dist[v] = dist[u] + graph[u][v];
                        // prev[v] = u;
                    }
                }
            }
        }

        return dist;
    }

    public static int findMinimumDistance(int dist[], Boolean mark[])
    {
        int min = INF;
        int minIndex = INF;
    
        for (int v = 0; v < dist.length; v++)
        {
            if (!mark[v] && dist[v] != INF && dist[v] > 0 && dist[v] <= min) {
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

        // for (int i = 0; i < vertices; i++) 
        // {
        //     System.out.printf("%-2d ", i);
        // }        

        // System.out.println();

        for (int i = 0; i < vertices; i++) 
        {   
            if(arr[i] == INF)
            {
                System.out.printf("%-4s ", "∞");
            }
            else
            {
                System.out.printf("%-4d ", arr[i]);
            }
        }

        // System.out.println();
    }


    /* Floyd Warshall's Algorithm (Priyanshu) */
    public static void floydWarshall(int dist[][])
	{		
		int i, j, k;
        int V = dist.length;

		for (k = 0; k < V; k++) {
			// Pick all vertices as source one by one
			for (i = 0; i < V; i++) {
				// Pick all vertices as destination for the above picked source
				for (j = 0; j < V; j++) {
					// If vertex k is on the shortest path from i to j, then update the value of dist[i][j]
					if (dist[i][k] + dist[k][j] < dist[i][j] && dist[i][k] != INF && dist[k][j] != INF)
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
				}
			}
		}

		// Print the shortest distance matrix
		// printSolution(dist);
	}

	public static void printSolution(int dist[][], String algo)
	{
        int V = dist.length;

		System.out.println(
			"The following matrix shows the shortest "
			+ "distances between every pair of vertices based on the " + algo + " algorithm.");
		for (int i = 0; i < V; ++i) {
			for (int j = 0; j < V; ++j) {
				if (dist[i][j] == INF)
					System.out.printf("%-4s", "∞");
				else
					System.out.printf("%-4d", dist[i][j]);
			}
			System.out.println();
		}
	}

    //EXTRA CREDIT METHODS

    public static int[] shortestPath(Graph a_graph, int src, int destination)
    {

        int[][] graph = a_graph.getMatrix();
        int V = a_graph.getVertices();

        int dist[] = new int[V];
        int prev[] = new int[V];
        int pathToDestination[][] = new int[V][V];
        int selectVertex = -1;
        Boolean mark[] = new Boolean[V];
    
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < mark.length; j++) {
                pathToDestination[i][j] = -1;
            }
        }
    
        // Initialize dist, prev, mark
        for (int i = 0; i < V; i++) {
            dist[i] = graph[src][i];
            prev[i] = src;
            pathToDestination[i][0] = i;
            // printPath(pathToDestination[i]);
            // System.out.println();
            mark[i] = false;
        }
    
        mark[src] = true;
    
        int counter = 0;
        for (int count = 0; count < V - 1; count++) {

            int u = findMinimumDistance(dist, mark);

            if(u != INF)
            {
                mark[u] = true;
        
                for (int v = 0; v < V; v++)
                {
                    if (!mark[v] && graph[u][v] > 0 && graph[u][v] != INF && (dist[v] == INF || dist[v] > dist[u] + graph[u][v]))
                    {
                        
                        dist[v] = dist[u] + graph[u][v];

                        count = 0;
                        for (int i = 0; i < V; i++) {
                            if(pathToDestination[prev[v]][i] == -1)
                            {
                                // System.out.println(pathToDestination[i]);
                                pathToDestination[prev[v]][i] = u;
                                // System.out.println(pathToDestination[i]);
                                i = V;

                                counter++;
                                // System.out.print("V: " + v + " ");
                                // printPath(pathToDestination[prev[v]]);
                                // System.out.println();
                            }
                        }

                        int temp = prev[v];
                        prev[v] = u;

                        if(v == destination)
                        {
                            count = 0;
                            for (int i = 0; i < V; i++) {
                                if(pathToDestination[temp][i] == -1)
                                {
                                    // System.out.println(destination);
                                    // System.out.println(pathToDestination[i]);
                                    pathToDestination[temp][i] = destination;
                                    // System.out.println(pathToDestination[i]);
                                    i = V;
    
                                    counter++;
                                    // System.out.print("V: " + v + " ");
                                    // printPath(pathToDestination[temp]);
                                    // System.out.println();
                                }
                            }
                            selectVertex = temp;
                            break;
                        }

    
                    }
                }
            }
        }

        if(selectVertex == -1)
        {
            // no path to destination
            return null;
        }

        int[] result = new int[counter+1];
        for (int i = 0; i < counter+1; i++) {
            result[i] = pathToDestination[selectVertex][i];
        }

        System.out.println();
        printPath(result);

        return result;
    }

    public static void printPath(int[] arr)
    {
        int vertices = arr.length;

        System.out.println("Source Vertex: " + arr[0] + " / To Destination Vertex: " + arr[arr.length-1]);
        for (int i = 0; i < vertices; i++) 
        {   
            System.out.printf("%d", arr[i]);

            if(i != vertices-1)
            {
                System.out.print(" -> ");
            }
        }
    }
}