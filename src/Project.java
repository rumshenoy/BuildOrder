import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ramyashenoy on 1/4/16.
 */
public class Project {
    //Every project has children that represents the projects that depend on it
    //That is if A has an edge B, (A->B). it means B has a dependency on A and therefore
    //A must be built before B. Each node also tracks the number of incoming edges
    private ArrayList<Project> children = new ArrayList<>();
    private HashMap<String, Project> map = new HashMap<String, Project>();

    public String getName() {
        return name;
    }

    public int getDependencies() {
        return dependencies;
    }


    private String name;
    private int dependencies = 0;

    public Project(String n){
        name = n;
    }

    public void addNeighbour(Project node){
            children.add(node);
            node.incrementDependencies();
    }

    public void incrementDependencies() {
        dependencies++;
    }

    public void decrementDependencies(){
        dependencies--;
    }

    public ArrayList<Project> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return name;
    }
}
