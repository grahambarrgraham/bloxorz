package blox;
import java.util.Arrays;

import blox.Action;
import blox.Block;
import blox.BloxNode;
import blox.Coord;
import blox.Orientation;
import blox.Rule;
import blox.Scape;
import blox.Tile;

import junit.framework.TestCase;

public class ScapeTest extends TestCase {

    Scape scape1 = null;
    Scape scape2 = null;
    
    protected void setUp() throws Exception {
        super.setUp();
        scape1 = new Scape();
        scape1.load("test1.txt");
    }

    public void testLoad1() throws Exception {
        assertEquals(Tile.Type.teleport, scape1.get(13, 5).getType());
    }

    public void testLoad2() throws Exception {
        assertEquals(Tile.Type.plain, scape1.get(0, 0).getType());
    }

    public void testLoad3() throws Exception {
        assertEquals(Tile.Type.missing, scape1.get(25, 0).getType());
    }

    public void testLoad4() throws Exception {
        assertEquals(Tile.Type.missing, scape1.get(10, 4).getType());
    }

    public void testLoad5() throws Exception {
        assertEquals(Tile.Type.weakSwitch, scape1.get(11, 4).getType());
    }

    public void testLoad6() throws Exception {
        assertEquals(Tile.Type.plain, scape1.get(21, 3).getType());
    }

    public void testLoad7() throws Exception {
        assertEquals(Tile.Type.end, scape1.get(9, 5).getType());
        assertEquals(new Coord(9,5), scape1.end);
    }
    
    public void testLoad8() throws Exception {

        scape2 = new Scape();
        scape2.load("level2.txt");
        
        assertEquals(Tile.Type.end, scape2.get(13, 4).getType());
        assertEquals(new Coord(13,4), scape2.end);
    }

    public void testLoad9() throws Exception {
        
        scape2 = new Scape();
        scape2.load("level2.txt");
        
        assertEquals(Tile.Type.weakSwitch, scape2.get(2, 3).getType());//1
        assertEquals(Tile.Type.missing, scape2.get(4, 1).getType());//1
        assertEquals(Tile.Type.missing, scape2.get(10, 1).getType());//2
        assertEquals(Tile.Type.strongSwitch, scape2.get(8, 4).getType());//2
    }

    public void testLoad10() throws Exception {
        
        scape2 = new Scape();
        scape2.load("level2.txt");
         
        Rule switchRule1 = new Rule("W1", Action.toggles, Arrays.asList("x1"));
        Rule switchRule = new Rule("S2", Action.toggles, Arrays.asList("x2"));
        
        assertEquals(switchRule1, scape2.getRules("W1").get(0));
        assertEquals(switchRule, scape2.getRules("S2").get(0));
    }

    public void testTeleport() throws Exception {
        
        scape2 = new Scape();
        scape2.load("test1.txt");
        
        Rule teleportRule1 = new Rule("t1", Action.teleports, Arrays.asList("l1"));
        Rule teleportRule = new Rule("t2", Action.teleports, Arrays.asList("l3"));
        
        assertEquals(teleportRule1, scape2.getRules("t1").get(0));
        assertEquals(teleportRule, scape2.getRules("t2").get(0));
        
        Block inputBlock = new Block(new Coord(1,2), Orientation.z, 1, 2);
        
        Block expectedBlock1 = new Block(new Coord(11,1), Orientation.z, 1, 1);
        Block expectedBlock2 = new Block(new Coord(13,1), Orientation.z, 1, 1);
        Block expectedBlock3 = new Block(new Coord(16,1), Orientation.z, 1, 1);
        
        BloxNode inputNode = new BloxNode(scape2, inputBlock);
        BloxNode resultNode = scape2.invokeTeleportRule(inputNode, scape2.getRules("t2").get(0));
        BloxNode expectedNode = new BloxNode(scape2, expectedBlock1, expectedBlock2, expectedBlock3);  
        
        assertEquals(expectedNode.blocks, resultNode.blocks);
        assertEquals(expectedNode.scape, resultNode.scape);

    }

    
}
