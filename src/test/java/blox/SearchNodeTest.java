package blox;
import java.io.IOException;

import blox.Block;
import blox.BloxNode;
import blox.Coord;
import blox.Orientation;
import blox.Scape;
import blox.Site;

import junit.framework.TestCase;



public class SearchNodeTest extends TestCase {

    Scape scape;
    
    protected void setUp() throws Exception {
        super.setUp();
        scape = new Scape();
    }

    public void testJoinContiguousBlocks() throws IOException {
        
        Site site1 = new Site(Orientation.z, new Coord(0,0));
        Site site2 = new Site(Orientation.z, new Coord(1,0));
        Site site3 = new Site(Orientation.z, new Coord(0,1));
        Site site4 = new Site(Orientation.z, new Coord(1,1));
        Site site5 = new Site(Orientation.z, new Coord(5,5));
        
        Block block1 = new Block(site1, 1, 1);
        Block block2 = new Block(site2, 1, 1);
        Block block3 = new Block(site3, 1, 1);
        Block block4= new Block(site4, 1, 1);
        Block block5 = new Block(site5, 1, 1);
        
        Scape scape = new Scape();
        scape.load("level1.txt");
        
        BloxNode node = new BloxNode(scape, block1, block2);
        node.joinContiguousBlocks();
        assertEquals(1, node.blocks.size());
        assertEquals(new Block(new Site(Orientation.x, new Coord(0,0)), 1, 2),node.activeBlock);
        
        node = new BloxNode(scape, block3, block4, block5);
        node.activeBlock = block5;
        node.joinContiguousBlocks();
        assertEquals(2, node.blocks.size());
        assertEquals(new Block(new Site(Orientation.x, new Coord(0,1)), 1, 2),node.activeBlock);
    }
}
