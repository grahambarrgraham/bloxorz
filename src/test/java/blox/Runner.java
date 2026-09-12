package blox;

import astar.Move;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 * Solves all 33 levels with BloxAStarSearcher and writes the result as a moves
 * file in the format Validator understands, plus a per-level move-count report.
 *
 * Usage: java blox.Runner [outputDir]
 *   outputDir defaults to "generated" (gitignored).
 *   Writes outputDir/solution.txt and prints the report to stdout.
 */
public class Runner {

    public static void main(String[] args) throws Exception {
        String outputDir = args.length > 0 ? args[0] : "generated";
        new java.io.File(outputDir).mkdirs();

        Writer writer = new FileWriter(outputDir + "/solution.txt");
        int grandTotal = 0;

        try {
            for (int levelNum = 1; levelNum <= 33; levelNum++) {
                String levelFile = "level" + levelNum + ".txt";

                Scape scape = new Scape();
                scape.load(levelFile);

                Block block = new Block(scape.start, Orientation.z, 1, 2);
                BloxNode start = new BloxNode(scape, block);
                BloxAStarSearcher searcher = new BloxAStarSearcher(start, new Site(Orientation.z, scape.end));

                LinkedList<Move> path = searcher.search();
                if (path == null) {
                    System.out.println(levelFile + " : NO PATH FOUND");
                    continue;
                }

                int rollCount = 0;
                StringBuilder moveLine = new StringBuilder();
                for (Move m : path) {
                    if (!(m instanceof BloxMove)) continue;
                    Input input = ((BloxMove) m).input;
                    if (moveLine.length() > 0) moveLine.append(",");
                    moveLine.append(toChar(input));
                    if (input != Input.nextBlock) rollCount++;
                }

                grandTotal += rollCount;

                writer.write("level " + levelNum + "\n");
                writer.write(moveLine.toString() + "\n\n");

                System.out.println(levelFile + " : rolls=" + rollCount + ", runningTotal=" + grandTotal);
            }
        } finally {
            writer.close();
        }

        System.out.println();
        System.out.println("Grand total rolls (all 33 levels): " + grandTotal);
        System.out.println("Moves written to " + outputDir + "/solution.txt");
    }

    private static char toChar(Input input) {
        switch (input) {
        case up: return 'U';
        case down: return 'D';
        case left: return 'L';
        case right: return 'R';
        case nextBlock: return 'S';
        default: throw new RuntimeException("unexpected input " + input);
        }
    }
}
