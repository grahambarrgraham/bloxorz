package blox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import astar.AbstractAStarSearcher;
import astar.Move;
import astar.Node;

public class BloxAStarSearcher extends AbstractAStarSearcher {

    BloxAStarSearcher(BloxNode start, Site target) {
        super(start, target, new BloxPathComparator(target));
    }

    private BloxNode applySwitchRules(BloxNode x) {
        Block block = x.getActiveBlock();
        Tile[] affectedTiles = block.getAffectedTiles(x.scape);
        Scape scape = null;
        for (Tile tile : affectedTiles) {
            switch (tile.getType()) {
            case weakSwitch:
                scape = applySwitch(x, scape, tile);
                break;
            case strongSwitch:
                if (block.site.orientation == Orientation.z && block.height > 1) {
                    scape = applySwitch(x, scape, tile);
                }
                break;
            }
        }
        return scape != null ? new BloxNode(x, scape) : x;
    }

    private Scape applySwitch(BloxNode x, Scape scape, Tile tile) {
        if (scape == null) {
            scape = new Scape(x.scape);
        }
        List<Rule> rules = scape.subjectIdToRuleMap.get(tile.getId());
        for (Rule rule : rules) {
            if (scape.invokeSwitchRule(rule)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Applied rule " + rule + " as a result of searching node " + x);
                }
            }
        }
        return scape;
    }

    @Override
    protected Set<Move> successors(Node node) {

        BloxNode start = (BloxNode) node;
        Set<Move> options = new HashSet<Move>();

        INPUTS: for (Input input : Input.values()) {

            if (input == Input.start)
                continue;

            if (input == Input.nextBlock) {
                BloxNode next = start.nextBlock();
                if (next != null) {
                    options.add(new BloxMove(next, Input.nextBlock));
                }
                continue;
            }

            Block block = new Block(start.getActiveBlock());
            Tile[] tiles = block.move(input, start.scape);

            for (Tile tile : tiles) {
                switch (tile.getType()) {
                case missing:
                    if (logger.isDebugEnabled())
                        logger.debug("Ignoring option " + block.site + " has missing tile.");
                    continue INPUTS;
                case weak:
                    if (block.site.orientation == Orientation.z && block.height > 1) {
                        if (logger.isDebugEnabled())
                            logger.debug("Ignoring option " + block.site + " has a weak tile.");
                        continue INPUTS;
                    }
                default:
                }
            }

            if (block.height > 1 && block.site.orientation == Orientation.z && tiles.length == 1
                    && tiles[0].getType() == Tile.Type.teleport) {
                Rule rule = start.scape.getRules(tiles[0].getId()).get(0);
                BloxNode teleportDestination = start.scape.invokeTeleportRule(start, rule);
                options.add(new BloxMove(teleportDestination, input));
            } else {
                options.add(new BloxMove(new BloxNode(start, block), input));
            }

        }
        return options;
    }

    @Override
    protected Node transformCurrentNode(Move lastMove) {

        BloxMove lastBloxMove = (BloxMove) lastMove;

        if (lastBloxMove.input != Input.nextBlock) {
            return applySwitchRules((BloxNode) lastBloxMove.destination);
        }

        return super.transformCurrentNode(lastMove);
    }

}
