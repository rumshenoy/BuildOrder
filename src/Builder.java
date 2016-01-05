import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramyashenoy on 1/4/16.
 */
public class Builder {
    List findBuildOrder(String[] projects, String[][] dependencies) {
        Graph graph = buildGraph(projects, dependencies);
        return orderProjects(graph.getNodes());
    }

    private List orderProjects(ArrayList<Project> projects) {

        List order = new ArrayList<Project>();

        int endOfList = addNonDependent(order, projects, 0);
        int toBeProcessed = 0;

        while (toBeProcessed < projects.size()) {
            Project current = (Project) order.get(toBeProcessed);

            //We have detected a circular dependecy since there are no remaining projects with zero dependencies
            if (current == null) {
                return null;
            }

            //remove myself as a dependency
            ArrayList<Project> children = current.getChildren();
            for (Project child : children) {
                child.decrementDependencies();
            }

            endOfList = addNonDependent(order, projects, endOfList);
            toBeProcessed++;
        }

        return order;
    }

    private int addNonDependent(List order, ArrayList<Project> projects, int offset) {
        for (Project project : projects) {
            if (project.getDependencies() == 0 && !order.contains(project)) {
                order.add(offset, project);
                offset++;
            }
        }
        return offset;
    }

    private Graph buildGraph(String[] projects, String[][] dependencies) {
        Graph graph = new Graph();
        for (String project : projects) {
            graph.getOrCreateNode(project);
        }

        for (String[] dependency : dependencies) {
            String first = dependency[0];
            String second = dependency[1];

            graph.addEgde(second, first);
        }

        return graph;
    }

    public static void main(String[] args) {
        String[] projects = {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies = {{"d", "a"}, {"b", "f"}, {"d", "b"}, {"a", "f"}, {"c", "d"}};

        Builder builder = new Builder();
        List order = builder.findBuildOrder(projects, dependencies);
        System.out.println(order);

    }

}
