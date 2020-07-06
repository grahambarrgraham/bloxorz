package astar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;

import blox.Site;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAStarSearcher {

    protected Logger logger = LogManager.getLogger(AbstractAStarSearcher.class);

    private Set<Node> closed = new HashSet<Node>();
    private SortedSet<LinkedList<Move>> paths = null;

    Node target;
    Node start;

    private BasePathComparator pathComparator;

    private int nodeExpansionCounter = 0;

    protected AbstractAStarSearcher(Node start, Node target, BasePathComparator pathComparator) {
        this.target = target;
        this.start = start.copy();
        this.pathComparator = pathComparator;
        resetPaths();
    }

    // idempotent method
    public LinkedList<Move> search() {

        long now = System.currentTimeMillis();
        logger.info("Starting A* search set for start " + start
                + " and target " + target);

        resetPaths();
        try {
            LinkedList<Move> path = search1();
            if (path != null) {
                path.removeFirst();
                logger.info("A* path found - length " + path.size() + " - "
                        + path);
            } else {
                logger.info("No path was found!");
            }
            return path;

        } finally {
            long diff = (System.currentTimeMillis() - now);
            long rate = diff > 0 ? nodeExpansionCounter / diff : 0;
            logger.info( String.format("Completed in %s ms with %s expansions at rate %s expansion/ms", diff, nodeExpansionCounter, rate ));
        }
    }

    // non idempotent method, subsequent calls will find next best route
    public LinkedList<Move> search1() {

        while (!paths.isEmpty()) {

            if (logger.isDebugEnabled())
                logger.debug(printPathCosts());

            LinkedList<Move> path = paths.first();
            if (logger.isDebugEnabled())
                logger.debug("First path is " + path);

            if (!paths.remove(path)) {
                throw new RuntimeException("Failed to remove path");
            }

            Node currentNode = path.getLast().destination;
            if (logger.isDebugEnabled())
                logger.debug("Last site on path is " + currentNode);

            if (closed.contains(currentNode)) {
                if (logger.isDebugEnabled())
                    logger.debug("Site " + currentNode + " was closed.");
                continue;
            }
            if (currentNode.isAtTarget(target)) {
                return path;
            }

            closed.add(currentNode);

            Move lastMove = path.getLast();
            if ( !(lastMove instanceof InitialMove) ) {
                currentNode = transformCurrentNode(lastMove);
            }

            Set<Move> successors = successors(currentNode);
            for (Move y : successors) {
                LinkedList<Move> successor = new LinkedList<Move>(path);
                y.pathCost = successor.getLast().pathCost + y.edgeCost;
                successor.add(y);
                paths.add(successor);
                if (logger.isDebugEnabled())
                    logger.debug("Added successor " + y);
                nodeExpansionCounter ++;
            }
        }

        return null;
    }

    protected Node transformCurrentNode(Move lastMove) {
        return lastMove.destination;
    }

    protected abstract Set<Move> successors(Node start);
    
    private void resetPaths() {
        closed.clear();
        paths = new TreeSet<LinkedList<Move>>(pathComparator);
        LinkedList<Move> path = new LinkedList<Move>();
        path.add(new InitialMove(start));
        paths.add(path);
        nodeExpansionCounter = 0;
    }
    
    public static class InitialMove extends Move {

        protected InitialMove(Node site) {
            super(site, 1);
        }
    }
    
    private String printPathCosts() {
        StringBuffer buf = new StringBuffer();
        buf.append("\n***Path costs***\n");
        for (List<Move> path : paths) {
            printCost(path, buf).append('\n');
            break;
        }
        buf.append("****************\n");
        return buf.toString();
    }

    private StringBuffer printCost(List<Move> path, StringBuffer buf) {
        int cost = pathComparator.heuristic(path);
        buf.append("Length=" + path.size() + ", cost=" + cost
                + ", score=" + (path.size() + cost));
        return buf;
    }

    public List<Move> search(Node from, Site to) {
        this.target = to;
        this.start = from;
        return search();
    }
}
