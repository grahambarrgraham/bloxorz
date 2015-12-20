package blox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Scape {

    Logger logger = Logger.getLogger(Scape.class);

    private Tile[][] land = null;
    Coord start = null;
    Coord end = null;
    Map<String, List<Rule>> subjectIdToRuleMap = new HashMap<String, List<Rule>>();
    Map<String, Set<Coord>> objectIdToCoordsMap = new HashMap<String, Set<Coord>>();
    String id = "1";

    public String getId() {
        return id;
    }

    // expensive to produce, so produce only once for immutable object
    private Integer cachedHashcode = null;

    public Scape() {
    }

    public Scape(Scape scape) {
        this.start = new Coord(scape.start);
        this.end = new Coord(scape.end);
        subjectIdToRuleMap = scape.subjectIdToRuleMap;
        objectIdToCoordsMap = scape.objectIdToCoordsMap;
        land = copyLand(scape.land);
        this.id = scape.id + 1;
    }

    boolean invokeSwitchRule(Rule switchRule) {

        if (switchRule.action == Action.teleports) {
            return false;
        }

        Set<Coord> coords = new HashSet<Coord>();
        for (String bridgeId : switchRule.objectIds) {
            Set<Coord> bridgeCoords = objectIdToCoordsMap.get(bridgeId);

            if (bridgeCoords == null) {
                throw new RuntimeException("Unknown bridge " + bridgeId
                        + " in switch rule " + switchRule);
            }

            coords.addAll(bridgeCoords);
        }

        for (Coord coord : coords) {
            Tile tile = land[coord.x][coord.y];
            switch (switchRule.action) {
            case toggles:

                if (tile.getType() == Tile.Type.missing) {
                    land[coord.x][coord.y] = new Tile(tile, Tile.Type.plain);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Scape " + getId() + " applying rule "
                                + switchRule + " opening " + coord);
                    }
                } else {
                    land[coord.x][coord.y] = new Tile(tile, Tile.Type.missing);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Scape " + getId() + " applying rule "
                                + switchRule + " closing " + coord);
                    }
                }
                break;
            case closes:

                if (logger.isDebugEnabled()) {
                    logger.debug("Scape " + getId() + " applying rule "
                            + switchRule + " on " + coord);
                }

                land[coord.x][coord.y] = new Tile(tile, Tile.Type.missing);
                break;
            case opens:

                if (logger.isDebugEnabled()) {
                    logger.debug("Scape " + getId() + " applying rule "
                            + switchRule + " on " + coord);
                }

                land[coord.x][coord.y] = new Tile(tile, Tile.Type.plain);
                break;
            }
        }
        return true;
    }

    BloxNode invokeTeleportRule(BloxNode input, Rule teleportRule) {

        if (teleportRule.action != Action.teleports) {
            return null;
        }

        Block active = null;
        Set<Block> blocks = new HashSet<Block>();
        for (String objectId : teleportRule.objectIds) {
            Set<Coord> coords = objectIdToCoordsMap.get(objectId);
            if (coords != null) {
                for (Coord coord : coords) {
                    Block block = new Block(coord, Orientation.z, 1, 1);
                    blocks.add(block);
                    if (active == null)
                        active = block;
                }
            }
        }

        BloxNode output = new BloxNode(this, blocks, active);

        if (logger.isDebugEnabled()) {
            logger.debug("Scape " + getId() + " applying rule " + teleportRule
                    + " on " + input + " to get " + output);
        }

        return output;
    }

    private Tile[][] copyLand(Tile[][] land2) {

        Tile[][] copy = new Tile[land2.length][land2[0].length];

        for (int x = 0; x < land2.length; x++) {
            for (int y = 0; y < land2[x].length; y++) {
                copy[x][y] = new Tile(land2[x][y]);
            }
        }
        return copy;
    }

    public void load(String file) throws IOException {
        
        logger.info("Loading scape " + file);
        
        InputStream is = Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream(file);
        
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
        try {
            ArrayList<String> lines = new ArrayList<String>();
            for (String line = bReader.readLine(); line != null; line = bReader
                    .readLine()) {
                if (line.startsWith("-")) {
                    readRules(bReader);
                } else {
                    lines.add(line);
                }
            }

            Tile[][] temp = new Tile[lines.size()][];

            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                String[] tileIds = line.split(" +");
                Tile[] tiles = getTiles(tileIds);
                temp[y] = tiles;
            }

            land = new Tile[temp[0].length][temp.length];

            // transpose
            for (int y = 0; y < temp.length; y++) {
                Tile[] inner = temp[y];
                for (int x = 0; x < inner.length; x++) {
                    land[x][y] = temp[temp.length - y - 1][x];
                }
            }

            for (int x = 0; x < land.length; x++) {
                for (int y = 0; y < land[x].length; y++) {
                    String id = land[x][y].getId();
                    Coord coord = new Coord(x, y);
                    switch (land[x][y].getType()) {
                    case start:
                        start = new Coord(x, y);
                        if (id != null)
                            addRuleObject(id, coord);
                        break;
                    case end:
                        end = new Coord(x, y);
                        break;
                    case strongSwitch:
                    case weakSwitch:
                    case teleport:
                        addRuleCoord(id, coord);
                        addRuleObject(id, coord);
                        break;
                    case missing:
                    case plain:
                    case teleportLanding:
                        addRuleObject(id, coord);
                        break;
                    }
                }
            }

            List<Rule> teleports = scoreAllTeleports();

            for (int x = 0; x < land.length; x++) {
                for (int y = 0; y < land[x].length; y++) {
                    land[x][y].distanceToTarget = BloxPathComparator.distance(
                            new Coord(x, y), end);
                    for (Rule rule : teleports) {
                        land[x][y].distanceToTarget = Math.min(
                                land[x][y].distanceToTarget, rule.score);
                    }
                }
            }

        } finally {
            bReader.close();
        }

        if (start == null) {
            throw new RuntimeException("Couldn't load scape, 'start' not found");
        }

        if (end == null) {
            throw new RuntimeException("Couldn't load scape, 'end' not found");
        }
    }

    private Stack<Rule> teleportScoreStack = new Stack<Rule>();

    private void scoreTeleports1(List<Rule> teleports, Rule from) {

        teleportScoreStack.push(from);

        for (Rule to : teleports) {
            if (teleportScoreStack.contains(to))
                continue;
            scoreTeleports1(teleports, to);
            from.setScore(teleportScore(from, to.coord) + to.score);
        }

        teleportScoreStack.pop();
    }

    private List<Rule> scoreAllTeleports() {
        List<Rule> teleports = findTeleports();

        if (!teleports.isEmpty()) {

            for (Rule rule : teleports) {
                rule.setScore(teleportScore(rule, end));
            }
            //scoreTeleports1(teleports, teleports.get(0));
        }
        return teleports;
    }

    private int teleportScore(Rule rule, Coord target) {
        int score = 0;
        for (String objectId : rule.objectIds) {
            Set<Coord> coordSet = objectIdToCoordsMap.get(objectId);
            for (Coord coord : coordSet) {
                score += BloxPathComparator.distance(coord, target);
            }
        }
        return score;
    }

    private List<Rule> findTeleports() {
        List<Rule> teleports = new ArrayList<Rule>();
        for (List<Rule> list : subjectIdToRuleMap.values()) {
            for (Rule rule : list) {
                if (rule.action == Action.teleports) {
                    teleports.add(rule);
                }
            }
        }
        return teleports;
    }

    private void addRuleCoord(String id, Coord coord) {
        if (id == null) {
            throw new RuntimeException("Attempt to add rule at " + coord
                    + " without an id");
        }

        List<Rule> rules = subjectIdToRuleMap.get(id);
        if (rules == null) {
            logger.warn("Couldn't find rule at " + coord + " with id '" + id
                    + "'");
        } else {
            for (Rule rule : rules) {
                rule.setCoord(coord);
            }
        }

    }

    Pattern rulePattern = Pattern.compile("(\\w+)\\s+(\\w+)\\s+(.+)");

    private void readRules(BufferedReader reader) throws IOException {
        for (String line = reader.readLine(); line != null; line = reader
                .readLine()) {

            line = line.trim();
            if (line.length() == 0)
                continue;

            Matcher matcher = rulePattern.matcher(line);
            if (matcher.matches()) {
                String subjectId = matcher.group(1).trim();
                String verb = matcher.group(2).trim().toLowerCase();
                String[] objectIds = matcher.group(3).split(",\\s+");
                List<String> objectIdsList = new ArrayList<String>();
                for (String s : objectIds) {
                    objectIdsList.add(s.trim());
                }
                Rule rule = new Rule(subjectId, Action.valueOf(verb),
                        objectIdsList);
                List<Rule> rules = subjectIdToRuleMap.get(subjectId);
                if (rules == null) {
                    rules = new ArrayList<Rule>();
                    subjectIdToRuleMap.put(subjectId, rules);
                }
                rules.add(rule);
            }
        }
    }

    private void addRuleObject(String objectId, Coord coord) {

        if (objectId == null)
            return;

        Set<Coord> coords = objectIdToCoordsMap.get(objectId);
        if (coords == null) {
            coords = new HashSet<Coord>();
            objectIdToCoordsMap.put(objectId, coords);
        }
        coords.add(coord);
    }

    private Tile[] getTiles(String[] tileIds) {
        Tile[] tiles = new Tile[tileIds.length];
        for (int i = 0; i < tileIds.length; i++) {
            String nodeDef = tileIds[i].trim();
            String tileType = nodeDef.substring(0, 1);
            Tile.Type type = Tile.tileSymbolMap.get(tileType);
            if (type == null) {
                throw new RuntimeException("unrecognized symbol: " + tileType);
            }
            tiles[i] = new Tile(type, nodeDef.length() > 1 ? nodeDef : null);
        }
        return tiles;
    }

    public List<Rule> getRules(String id) {
        return subjectIdToRuleMap.get(id);
    }

    @Override
    public int hashCode() {
        if (cachedHashcode == null) {
            cachedHashcode = Arrays.deepHashCode(land);
        }
        return cachedHashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Scape other = (Scape) obj;
        if (!Arrays.deepEquals(land, other.land))
            return false;
        return true;
    }

    public Tile get(int x, int y) {
        return land[x][y];
    }

    public int getXSize() {
        return land.length;
    }

    public int getYSize() {
        return land[0].length;
    }

    public int getDistanceToTarget(Coord coord) {
        return 0;
        //return land[coord.x][coord.y].distanceToTarget;
    }

}