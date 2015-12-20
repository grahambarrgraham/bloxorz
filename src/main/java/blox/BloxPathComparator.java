package blox;

import java.util.List;

import astar.BasePathComparator;
import astar.Move;
import astar.Node;

class BloxPathComparator extends BasePathComparator {

    public BloxPathComparator(Site target) {
        super(target);
    }
        

    private int getFirstXCoord(List<Move> o) {
        return getLastBloxNode(o).activeBlock.site.coord.x;
    }

    private int getFirstYCoord(List<Move> o1) {
        return getLastBloxNode(o1).activeBlock.site.coord.y;
    }

    private BloxNode getLastBloxNode(List<Move> moves) {
        return (BloxNode) getLastMove(moves).destination;
    }
         
    public int distance(List<Move> moves, Coord b) {
        return distance(getLastBloxNode(moves), b);
    }

    public int distance(BloxNode node, Coord b) {

        int distance = Integer.MAX_VALUE;
        
        for (Block block : node.blocks) {
            distance += Math.min(distance, distance(node, block));
        }
        return distance;
    }

    private static int distance(BloxNode node, Block block) {
        return node.scape.getDistanceToTarget(block.site.coord);
    }

    public static int distance(Block block, Coord b) {
        return distance(block.site.coord, b);
    }

    public static int distance(Coord a, Coord b) {
        int xDiff = Math.abs(a.x - b.x);
        int yDiff = Math.abs(a.y - b.y);
        // return xDiff + yDiff;
        return (int) Math.round(Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));
    }

    public int compare(Coord o1, Coord o2) {
        if (o1 == o2)
            return 0;
        if (o1.equals(o2))
            return 0;
        return distance(o2, getTarget().coord) - distance(o1, getTarget().coord);
    }
    
    private Site getTarget() {
        return (Site) super.target;
    }

    @Override
    public int cost(Node node) {
        return distance((BloxNode) node, getTarget().coord);
    }

    @Override
    protected int refineCompare(List<Move> o1, List<Move> o2) {
        int firstXCoord = getFirstXCoord(o1);
        int firstXCoord2 = getFirstXCoord(o2);
        if (firstXCoord > firstXCoord2) {
            return 1;
        } else if (firstXCoord == firstXCoord2) {
            if (getFirstYCoord(o1) > getFirstYCoord(o2)) {
                return 1;
            }
        }
        return -1;
    }
}