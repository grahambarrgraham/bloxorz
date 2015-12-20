package astar;

import java.util.Comparator;
import java.util.List;

import blox.Site;

public abstract class BasePathComparator implements Comparator<List<Move>> {

    protected static boolean dijkstraMode = true;
    protected Node target;

    public BasePathComparator(Site target) {
        this.target = target;
    }

    public int compare(List<Move> o1, List<Move> o2) {
        if (o1 == o2)
            return 0;
        if (o1.equals(o2))
            return 0;

        int score1 = score(o1);
        int score2 = score(o2);

        if (score1 > score2)
            return 1;

        if (score1 == score2) {
            if (getPathCost(o1) > getPathCost(o2)) {
                return 1;
            }

            if (getPathCost(o1) == getPathCost(o2)) {
                if (refineCompare(o1, o2) > 0) {
                    return 1;
                }
            }
        }

        return -1;
    }

    private int score(List<Move> moves) {
        if (moves == null || moves.isEmpty())
            return Integer.MAX_VALUE;

        int score = getPathCost(moves) + (dijkstraMode ? 0 : heuristic(moves));
        return score;
    }

    private int getPathCost(List<Move> moves) {
        return getLastMove(moves).pathCost;
    }

    protected int heuristic(List<Move> moves) {
        return cost(getLastMove(moves).destination);
    }
    
    protected Move getLastMove(List<Move> o) {
        return o.get(o.size() - 1);
    }
    
    public abstract int cost(Node node);
    
    protected abstract int refineCompare(List<Move> o1, List<Move> o2);
    
}