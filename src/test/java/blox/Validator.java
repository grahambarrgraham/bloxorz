package blox;

import astar.Move;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replays a move file against the real rules (successors()/transformCurrentNode())
 * instead of trusting whatever produced the moves. Rejects a level if any move is
 * illegal on the board, or if the final state isn't at the target.
 *
 * After a multi-target teleport a node can have more than one block, and the game
 * lets the player freely pick which one is active (that's what nextBlock does) --
 * there's no rule-mandated "first" choice. Scape.invokeTeleportRule commits to one
 * via HashSet iteration order, which is an implementation accident, not a game rule.
 * So this validator fans out one candidate per block-as-active after any such split
 * and accepts a move if ANY candidate branch supports it.
 *
 * Usage: java blox.Validator <movesFile>
 *   Format: "level <n>" line followed by a comma-separated move line, e.g.
 *     level 1
 *     R2,D,R3,D
 */
public class Validator extends BloxAStarSearcher {

    Validator(BloxNode start, Site target) {
        super(start, target);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java blox.Validator <movesFile>");
            System.exit(1);
        }

        BufferedReader bReader = new BufferedReader(new FileReader(args[0]));

        String levelName = null;
        int grandTotal = 0;
        int failures = 0;

        for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("level")) {
                levelName = line.replace("level", "").trim();
                continue;
            }

            List<Input> moves = parse(line);
            String levelFile = "level" + levelName + ".txt";

            Scape scape = new Scape();
            scape.load(levelFile);

            Block block = new Block(scape.start, Orientation.z, 1, 2);
            BloxNode start = new BloxNode(scape, block);

            Validator validator = new Validator(start, new Site(Orientation.z, scape.end));

            Set<BloxNode> candidates = new HashSet<BloxNode>();
            candidates.add(start);

            int rollCount = 0;
            boolean valid = true;
            String failReason = null;
            Set<BloxNode> expanded = candidates;

            for (int i = 0; i < moves.size() && valid; i++) {
                Input desired = moves.get(i);

                expanded = new HashSet<BloxNode>();
                for (BloxNode candidate : candidates) {
                    expanded.addAll(expandActiveBlockChoices(candidate));
                }

                Set<BloxNode> next = new HashSet<BloxNode>();
                for (BloxNode candidate : expanded) {
                    Set<Move> options = validator.successors(candidate);
                    for (Move m : options) {
                        if (m instanceof BloxMove && ((BloxMove) m).input == desired) {
                            next.add((BloxNode) validator.transformCurrentNode(m));
                        }
                    }
                }

                if (next.isEmpty()) {
                    valid = false;
                    failReason = "move #" + (i + 1) + " (" + desired
                            + ") is illegal from all " + expanded.size()
                            + " candidate state(s) (including alternate active-block choices): " + expanded;
                    break;
                }

                candidates = next;
                if (desired != Input.nextBlock) {
                    rollCount++;
                }
            }

            boolean atTarget = false;
            if (valid) {
                Site targetSite = new Site(Orientation.z, scape.end);
                for (BloxNode candidate : candidates) {
                    if (candidate.isAtTarget(targetSite)) {
                        atTarget = true;
                        break;
                    }
                }
            }

            if (!valid) {
                failures++;
                System.out.println(levelFile + " : INVALID -- " + failReason);
            } else if (!atTarget) {
                failures++;
                System.out.println(levelFile + " : all moves legal but FINAL STATE NOT AT TARGET -- ended at "
                        + candidates);
            } else {
                grandTotal += rollCount;
                System.out.println(levelFile + " : VALID, rolls=" + rollCount + ", runningTotal=" + grandTotal);
            }
        }

        bReader.close();

        System.out.println();
        System.out.println("Levels with problems: " + failures);
        System.out.println("Grand total rolls (valid levels only): " + grandTotal);

        if (failures > 0) {
            System.exit(1);
        }
    }

    /**
     * See class javadoc: fans out one candidate per block-as-active instead of
     * trusting the arbitrary pick baked into Scape.invokeTeleportRule.
     */
    private static Set<BloxNode> expandActiveBlockChoices(BloxNode node) {
        Set<BloxNode> variants = new HashSet<BloxNode>();
        if (node.blocks.size() <= 1) {
            variants.add(node);
            return variants;
        }
        for (Block block : node.blocks) {
            // BloxNode(Scape, Set<Block>, Block) stores the set by reference and
            // mutates it in joinContiguousBlocks(), so each variant needs its own copy.
            variants.add(new BloxNode(node.scape, new HashSet<Block>(node.blocks), block));
        }
        return variants;
    }

    private static List<Input> parse(String line) {
        List<Input> inputs = new ArrayList<Input>();
        line = line.replace(" ", "");
        String[] bits = line.split(",");
        Pattern p = Pattern.compile("([UDLRS])(\\d*)");
        for (String bit : bits) {
            Matcher matcher = p.matcher(bit);
            if (!matcher.matches()) {
                throw new RuntimeException("failed to match '" + bit + "'");
            }
            Input input = getInput(matcher.group(1).charAt(0));
            String countString = matcher.group(2);
            int count = countString.isEmpty() ? 1 : Integer.parseInt(countString);
            for (int i = 0; i < count; i++) {
                inputs.add(input);
            }
        }
        return inputs;
    }

    private static Input getInput(char c) {
        switch (c) {
        case 'U': return Input.up;
        case 'D': return Input.down;
        case 'L': return Input.left;
        case 'R': return Input.right;
        case 'S': return Input.nextBlock;
        }
        throw new RuntimeException("unknown input char " + c);
    }
}
