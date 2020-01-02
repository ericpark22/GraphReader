import java.util.LinkedList;

public class FinalGraph
{
    static class Graph
    {
        LinkedList<Edge>[] adjList; // linked list to store edges with source, destination, and weight
        int vertices; // total amount of vertices

        int[] value; // used to mark where next component of graph starts

        int source; //used to find different components


        Graph(int vertices)
        {
            this.vertices = vertices;
            value = new int[vertices];
            source = 0;

            adjList = new LinkedList[vertices + 1];

            for (int i = 0; i <= vertices; i++)
            {
                adjList[i] = new LinkedList<>(); // linked list with the capacity of vertices
            }
        }

        public void insertEdge(int source, int destination, double weight)
        {
            Edge edge = new Edge(source, destination, weight);
            adjList[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjList[destination].addFirst(edge); // adds edges twice because it is undirected graph

        }

        public void readGraph()
        {
            for (int i = 0; i < vertices; i++)
            {
                LinkedList<Edge> getInd = adjList[i];

                for (int j = 0; j < getInd.size(); j++)
                {
                    System.out.println("u:" + i + " v:" + getInd.get(j).destination + " w:" + getInd.get(j).weight);
                }
            }
        }

        public void printAdjMatrix()
        {
            System.out.println();
            System.out.println("Adjacency Matrix Representation:");
            String[][] adjMatrix = new String[vertices + 1][vertices + 1];

            adjMatrix[0][0] = "";

            // sets up first row and first column of vertices
            for (int i = 1; i <= vertices; i++)
            {
                adjMatrix[0][i] = i + "";
                adjMatrix[i][0] = i + "";
            }

            // fills board with *
            for (int r = 1; r <= vertices; r++)
            {
                for (int c = 1; c <= vertices; c++)
                {
                    adjMatrix[r][c] = "0.00";
                }
            }

            // replaces * with weighted values when applicable
            for (int i = 0; i < vertices; i++)
            {
                LinkedList<Edge> getInd = adjList[i];

                for (int j = 0; j < getInd.size(); j++)
                {
                    if ((getInd.get(j).weight + "").length() > 3)
                        adjMatrix[i][getInd.get(j).destination] = getInd.get(j).weight + "";
                    else
                        // adds 0 if only 2 numbers (2.5 vs 2.50) to make matrix more readable
                        adjMatrix[i][getInd.get(j).destination] = getInd.get(j).weight + "0";
                }
            }

            // prints out board
            for (int c = 0; c <= vertices; c++)
            {
                System.out.print(adjMatrix[0][c] + "     ");
            }

            System.out.println();

            for (int r = 1; r < adjMatrix.length; r++)
            {
                // align 1 digit numbers with 2 digit numbers on matrix
                if (r <= 9)
                    System.out.print(" ");

                for (int c = 0; c < adjMatrix[0].length; c++)
                {
                    System.out.print(adjMatrix[r][c] + "  ");
                }

                System.out.println();
            }
        }


        void FindConnectedDFS(int v, boolean[] visited)
        {
            visited[v] = true; // node has been visited
            LinkedList<Edge> getInd = adjList[v];

            System.out.print(v + " ");

            // go through all edges at each source of the adjacency list
            for (int j = 0; j < getInd.size(); j++)
            {

                if (!visited[getInd.get(j).destination])// if destination has not been visited yet, visit it
                {
                    if (getInd.get(j).destination >= adjList.length) // if number is out of bounds in the adjList
                    {
                        visited[getInd.get(j).destination] = true;
                        System.out.print(getInd.get(j).destination + " ");
                        source = getInd.get(j).source;
                        continue;
                    }

                    else
                        FindConnectedDFS(getInd.get(j).destination, visited); // keep on recursing until everything has been visited
                }
            }
        }

        void connectedComponents()
        {
            boolean[] visited = new boolean[vertices + 1];

            int countComponents = 1; // how many components

            for (int v = 1; v < vertices; v++)
            {
                if (!visited[v]) // if it has not been visited
                {
                    if (adjList[v].size() < 1) // if there is nothing at the current index in adjList
                    {
                        visited[v] = true;
                        value[0] = v - 1;
                        break;
                    }


                    System.out.println("Component " + countComponents);
                    countComponents++;

                    value[0] = v; // where the separation in the graph is

                    FindConnectedDFS(v, visited);
                    System.out.println();
                }
            }

            System.out.println();

            if (value[0] != 1) // if there is a change in starting value (another graph component exists)
            {
                System.out.println("Adjacency List Representation:");
                System.out.println("Component 1 ");
                for (int i = 1; i < value[0]; i++)
                {
                    LinkedList<Edge> getInd2 = adjList[i];
                    System.out.print(i + ": ");

                    for (int j = 0; j < getInd2.size(); j++)
                    {
                        System.out.print(getInd2.get(j).destination + "|" + getInd2.get(j).weight + " ---> ");
                    }

                    System.out.println();
                }

                System.out.println();
                System.out.println("Component 2 ");

                for (int i = value[0]; i < vertices; i++)
                {
                    LinkedList<Edge> getInd3 = adjList[i];
                    System.out.print(i + ": ");

                    for (int j = 0; j < getInd3.size(); j++)
                    {
                        System.out.print(getInd3.get(j).destination + "|" + getInd3.get(j).weight + " ---> ");
                    }

                    System.out.println();
                }
            }

            else // there is only one component of the graph
            {
                System.out.println("Adjacency List Representation:");
                System.out.println("Component 1");
                for (int i = 1; i < vertices; i++)
                {
                    LinkedList<Edge> getInd4 = adjList[i];
                    System.out.print(i + ": ");

                    for (int j = 0; j < getInd4.size(); j++)
                    {
                        System.out.print(getInd4.get(j).destination + "|" + getInd4.get(j).weight + " ---> ");
                    }

                    System.out.println();
                }
            }
        }

        public void primAlgorithm()
        {
            double[] key = new double[vertices + 1]; // stores keys to see if min heap is needed
            boolean[] heapContain = new boolean[vertices + 1];
            EndValues[] endResult = new EndValues[vertices + 1];
            HeapNode[] heapNodes = new HeapNode[vertices + 1]; // contains all the vertices

            for (int i = 0; i <= vertices ; i++)
            {
                heapNodes[i] = new HeapNode();

                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;

                endResult[i] = new EndValues();
                endResult[i].parent = -1;

                heapContain[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            heapNodes[0].key = 0;

            HeapMinimum minHeap = new HeapMinimum(vertices + 1); // all vertices are inserted into the minHeap

            for (int i = 0; i <= vertices; i++)
            {
                minHeap.insert(heapNodes[i]); // adds to prio queue
            }

            while (!minHeap.isEmpty()) // there is something in the minheap
            {
                HeapNode extractedNode = minHeap.obtainMinimum();
                int extractedVertex = extractedNode.vertex;
                heapContain[extractedVertex] = false;
                LinkedList<Edge> list = adjList[extractedVertex];

                for (int i = 0; i < list.size(); i++) // goes through all possible adjacent vertices
                {
                    Edge edge = list.get(i);

                    if (heapContain[edge.destination]) // if the destination is in the heap
                    {
                        int destination = edge.destination;
                        double newKey = edge.weight;

                        if (key[destination] > newKey) // compares to see if key is less than
                        {
                            subtractCurrentKey(minHeap, newKey, destination);
                            endResult[destination].parent = extractedVertex; // new parent node for the destination
                            endResult[destination].weight = newKey;
                            key[destination] = newKey;
                        }
                    }
                }
            }

            printPrimMatrix(endResult);
        }

        public void subtractCurrentKey(HeapMinimum minHeap, double newKey, int vertex)
        {
            int index = minHeap.indexes[vertex]; // index of key that needs to decrease
            HeapNode node = minHeap.minHeapArray[index]; // obtain node and update the value

            node.key = newKey;
            minHeap.flowUpwards(index);
        }

        public void printPrimMatrix(EndValues[] endingMatrix)
        {
            System.out.println("Prim Algorithm Matrix: ");
            String[][] adjMatrix = new String[vertices + 1][vertices + 1];
            double totalWeight = 0;
            adjMatrix[0][0] = "";

            // sets up first row and first column of vertices
            for (int i = 1; i <= vertices; i++)
            {
                adjMatrix[0][i] = i + "";
                adjMatrix[i][0] = i + "";
            }

            // fills board with *
            for (int r = 1; r <= vertices; r++)
            {
                for (int c = 1; c <= vertices; c++)
                {
                    adjMatrix[r][c] = "0.00";
                }
            }

            for (int i = 1; i <= vertices; i++)
            {
                if(endingMatrix[i].parent != -1)
                {
                    if((endingMatrix[i].weight + "").length() > 3)
                        adjMatrix[i][endingMatrix[i].parent] = endingMatrix[i].weight + "";
                    else
                        adjMatrix[i][endingMatrix[i].parent] = endingMatrix[i].weight + "0";

                }

                totalWeight += endingMatrix[i].weight;
            }


            // prints out board
            for (int c = 0; c <= vertices; c++)
            {
                System.out.print(adjMatrix[0][c] + "     ");
            }

            System.out.println();

            for (int r = 1; r < adjMatrix.length; r++)
            {
                // align 1 digit numbers with 2 digit numbers on matrix
                if (r <= 9)
                    System.out.print(" ");

                for (int c = 0; c < adjMatrix[0].length; c++)
                {
                    System.out.print(adjMatrix[r][c] + "  ");
                }

                System.out.println();
            }

            System.out.println();
            System.out.println("Final minimum key: " + totalWeight);
            System.out.println();
        }
    }

    static class HeapMinimum
    {
        int capacity;
        int currentSize;
        HeapNode[] minHeapArray;
        int[] indexes;


        public HeapMinimum(int capacity)
        {
            this.capacity = capacity;
            minHeapArray = new HeapNode[capacity + 1];
            indexes = new int[capacity];

            minHeapArray[0] = new HeapNode();
            minHeapArray[0].key = Integer.MIN_VALUE;
            minHeapArray[0].vertex = -1;

            currentSize = 0;
        }


        public void insert(HeapNode x)
        {
            currentSize++;

            int index = currentSize;

            minHeapArray[index] = x;
            indexes[x.vertex] = index;
            flowUpwards(index);
        }

        public void flowUpwards(int pos)
        {
            int parentIdx = pos / 2; // similar to heap array in getting position of parent
            int currentIdx = pos;

            while (currentIdx > 0 && minHeapArray[parentIdx].key > minHeapArray[currentIdx].key)
            {
                HeapNode currentNode = minHeapArray[currentIdx];
                HeapNode parentNode = minHeapArray[parentIdx];

                //swap these positions
                indexes[currentNode.vertex] = parentIdx;
                indexes[parentNode.vertex] = currentIdx;
                swap(currentIdx, parentIdx);

                currentIdx = parentIdx;
                parentIdx = parentIdx / 2;
            }
        }

        public HeapNode obtainMinimum()
        {
            HeapNode min = minHeapArray[1];
            HeapNode lastNode = minHeapArray[currentSize];

            indexes[lastNode.vertex] = 1; // updates indexes and make last node the top

            minHeapArray[1] = lastNode;
            minHeapArray[currentSize] = null;

            flowDownwards(1);

            currentSize--;

            return min;
        }

        public void flowDownwards(int k)
        {
            int smallest = k;
            int rightChild = 2 * k + 1; // index of right child
            int leftChild = 2 * k; // index of left child

            if (leftChild < heapSize() && minHeapArray[smallest].key > minHeapArray[leftChild].key) // compares the two children
                smallest = leftChild;

            if (rightChild < heapSize() && minHeapArray[smallest].key > minHeapArray[rightChild].key) // compares the two children
                smallest = rightChild;

            if (smallest != k)
            {

                HeapNode smallestNode = minHeapArray[smallest];
                HeapNode kNode = minHeapArray[k];

                // swaps
                indexes[smallestNode.vertex] = k;
                indexes[kNode.vertex] = smallest;
                swap(k, smallest);
                flowDownwards(smallest);
            }
        }

        public void swap(int a, int b)
        {
            HeapNode temp = minHeapArray[a];
            minHeapArray[a] = minHeapArray[b];
            minHeapArray[b] = temp;
        }

        public boolean isEmpty()
        {
            return currentSize == 0;
        }

        public int heapSize()
        {
            return currentSize;
        }
    }


    static class HeapNode
    {
        int vertex;
        double key;
    }

    static class EndValues
    {
        int parent;
        double weight;
    }


    static class Edge
    {
        int source;
        int destination;
        double weight;

        public Edge(int source, int destination, double weight)
        {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}
