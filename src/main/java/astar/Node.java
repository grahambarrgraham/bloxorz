package astar;


public interface Node {

    Node copy();

    boolean isAtTarget(Node target);

}
