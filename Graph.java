import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;

    public Graph(Map<String, List<String>> dependencies) {
        this.adjacencyList = new HashMap<>(dependencies);
    }
}