import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Find shortest path between two weighted graph vertices using Dijkstra
 *
 * @author Ayub Atif
 */
public class Q3 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ3\n--------------------");

        GraphX<String> grX = new UnDiGraph<>();
        String src; String dst;
        try(Scanner sc = new Scanner(new File("contiguous-usa.txt"))) {
            while(sc.hasNext()){
                src = sc.next();
                dst = sc.next();
                sc.nextLine();

                grX.addVertex(src);
                grX.addVertex(dst);
                grX.addEdge(src, dst);
            }
        }
        catch(InputMismatchException e) {
            e.printStackTrace();
        }

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        grX.disjkstra(); // find shortest paths from each vertex to each vertex
        //grX.setShortest("NY");
        //Symmetry
        System.out.println(grX.getShortestPath("NY","OH").UnDiPath());
        System.out.println(grX.getShortestPath("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.getShortestPath("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.getShortestPath("NY","FL").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.getShortestPath("NY","DXB").UnDiPath());
    }
    /*
    --------------------
    Q3
    --------------------
    N: 49
    E: 107

    Multi source Dijkstra activated
    |NY->OH|
    Shortest Distance: 196
    Shortest Path: [NY]-[PA]-[OH]
    |OH->NY|
    Shortest Distance: 196
    Shortest Path: [OH]-[PA]-[NY]
    |NY->NY|
    Shortest Distance: 0
    Shortest Path: [NY]
    |NY->FL|
    Shortest Distance: 366
    Shortest Path: [NY]-[NJ]-[DE]-[MD]-[DC]-[VA]-[NC]-[GA]-[FL]
    |NY->DXB|
    Disconnected src & dst
     */
}
