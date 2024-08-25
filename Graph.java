import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;
    private List<String> nodes;

    public Graph(Map<String, List<String>> dependencies) {
        this.adjacencyList = new HashMap<>(dependencies);
        this.nodes = adjacencyList.keySet().stream().sorted().toList();
        adjacencyList.values().stream().toList().forEach(Collections::sort);
    }

    public void print() {
        for (var node : nodes) {
            System.out.println(node + ":");
            adjacencyList.get(node).forEach(System.out::println);
        }
    }

    public List<String> sort() {
        return nodes;
    }
}