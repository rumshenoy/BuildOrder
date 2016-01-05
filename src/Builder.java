import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

        //simple iterative algorithm implementation
        Builder builder = new Builder();
        List order = builder.findBuildOrder(projects, dependencies);
        System.out.println(order);

        //DFS implementation
        Stack<Project> buildOrderDFS = builder.findBuildOrderDFS(projects, dependencies);
        System.out.println(buildOrderDFS);

    }

    Stack<Project> findBuildOrderDFS(String[] projects, String[][] dependencies){
        Graph graph = buildGraph(projects, dependencies);
        return orderProjectsDFS(graph.getNodes());
    }

    private Stack<Project> orderProjectsDFS(ArrayList<Project> projects) {
        Stack<Project> stack = new Stack<Project>();
        for(Project project: projects){
            if(project.getState() == Project.State.BLANK){
                if(!doDFS(project, stack)){
                    return null;
                }
            }
        }
        return stack;
    }

    private boolean doDFS(Project project, Stack<Project> stack) {
        if(project.getState() == Project.State.PARTIAL){
            return false; //cycle
        }

        if(project.getState() == Project.State.BLANK){
            project.setState(Project.State.PARTIAL);
            ArrayList<Project> children = project.getChildren();
            for(Project child: children){
                if(!doDFS(child, stack)){
                    return false;
                }
            }

            project.setState(Project.State.COMPLETED);
            stack.push(project);


        }
        return true;
    }

}
