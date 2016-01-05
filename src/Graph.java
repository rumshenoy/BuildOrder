import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ramyashenoy on 1/4/16.
 */
public class Graph {
    private ArrayList<Project> nodes = new ArrayList<Project>();
    private HashMap<String, Project> map = new HashMap<String, Project>();


    public Project getOrCreateNode(String name){
        if(!map.containsKey(name)){
            Project node = new Project(name);
            nodes.add(node);
            map.put(name, node);
        }
        return map.get(name);
    }

    public void addEgde(String startName, String endName){
        Project start = getOrCreateNode(startName);
        Project end = getOrCreateNode(endName);
        start.addNeighbour(end);
    }

    public ArrayList<Project> getNodes() {
        return nodes;
    }

}
