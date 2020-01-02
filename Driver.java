import java.io.File;
import java.util.Scanner;

public class Driver
{
    public static void main(String[] args) throws Exception
    {
        File file1 = new File("C:\\Users\\Eric\\Downloads\\graph1.txt");
        Scanner sc1 = new Scanner(file1);

        int vertices1 = Integer.parseInt(sc1.next().replace(",", ""));
        int edges1 = Integer.parseInt(sc1.next().replace(",", ""));

        FinalGraph.Graph graph1 = new FinalGraph.Graph(vertices1);

        System.out.println("GRAPH 1");

        for(int i = 0; i < edges1; i++)
        {

            int source1 = Integer.parseInt(sc1.next().replace(",", ""));
            int destination1 = Integer.parseInt(sc1.next().replace(",", ""));
            double weight1 = Double.parseDouble(sc1.next().replace(",", ""));

            graph1.insertEdge(source1, destination1, weight1);

        }

        graph1.readGraph();
        graph1.printAdjMatrix();
        System.out.println();
        graph1.connectedComponents();
        System.out.println();
        graph1.primAlgorithm();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println();
        System.out.println("GRAPH 2");

        File file2 = new File("C:\\Users\\Eric\\Downloads\\graph2.txt");
        Scanner sc2 = new Scanner(file2);

        int vertices2 = Integer.parseInt(sc2.next().replace(",", ""));
        int edges2 = Integer.parseInt(sc2.next().replace(",", ""));

        FinalGraph.Graph graph2 = new FinalGraph.Graph(vertices2);

        for(int i = 0; i < edges2; i++)
        {
            int source2 = Integer.parseInt(sc2.next().replace(",", ""));
            int destination2 = Integer.parseInt(sc2.next().replace(",", ""));
            double weight2 = Double.parseDouble(sc2.next().replace(",", ""));

            graph2.insertEdge(source2, destination2, weight2);
        }

        graph2.readGraph();
        graph2.printAdjMatrix();
        System.out.println();
        graph2.connectedComponents();
        System.out.println();
        graph2.primAlgorithm();


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println();
        System.out.println("GRAPH 3");

        File file3 = new File("C:\\Users\\Eric\\Downloads\\graph3.txt");
        Scanner sc3 = new Scanner(file3);

        int vertices3 = Integer.parseInt(sc3.next().replace(",", ""));
        int edges3 = Integer.parseInt(sc3.next().replace(",", ""));

        FinalGraph.Graph graph3 = new FinalGraph.Graph(vertices3);

        for(int i = 0; i < edges3; i++)
        {

            int source3 = Integer.parseInt(sc3.next().replace(",", ""));
            int destination3 = Integer.parseInt(sc3.next().replace(",", ""));
            double weight3 = Double.parseDouble(sc3.next().replace(",", ""));

            graph3.insertEdge(source3, destination3, weight3);

        }

        graph3.readGraph();
        graph3.printAdjMatrix();
        System.out.println();
        graph3.connectedComponents();
        System.out.println();
        graph3.primAlgorithm();

    }
}
