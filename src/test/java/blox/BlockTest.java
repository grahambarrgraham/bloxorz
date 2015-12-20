package blox;
import java.util.Arrays;
import java.util.List;

import blox.Block;
import blox.Coord;
import blox.Input;
import blox.Orientation;
import blox.Scape;
import blox.Site;
import blox.Tile;

import junit.framework.TestCase;

public class BlockTest extends TestCase {

    Scape scape = null;

    protected void setUp() throws Exception {
        super.setUp();
        scape = new Scape();
        scape.load("test1.txt");
    }
    public void testStartZ() {
        Block block = new Block(scape.start, Orientation.z, 1, 2);
        assertEquals(11, scape.start.x);
        assertEquals(5, scape.start.y);
        assertEquals(5, block.site.coord.y);
        assertEquals(11, block.site.coord.x);
        assertEquals(Orientation.z, block.site.orientation);
        assertEquals(1, block.width);
        assertEquals(2, block.height);
    }

    public void testStartY() {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        assertEquals(11, scape.start.x);
        assertEquals(5, scape.start.y);
        assertEquals(5, block.site.coord.y);
        assertEquals(11, block.site.coord.x);
        assertEquals(Orientation.y, block.site.orientation);
        assertEquals(1, block.width);
        assertEquals(2, block.height);
    }

    public void testStartX() {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        assertEquals(11, scape.start.x);
        assertEquals(5, scape.start.y);
        assertEquals(5, block.site.coord.y);
        assertEquals(11, block.site.coord.x);
        assertEquals(Orientation.x, block.site.orientation);
        assertEquals(1, block.width);
        assertEquals(2, block.height);
    }

    
    public void testUpFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.up, scape);
        assertEquals(2, result.length);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
        assertEquals(new Tile(Tile.Type.plain), result[1]);
        assertEquals(11, block.site.coord.x);
        assertEquals(6, block.site.coord.y);
    }

    public void testDownFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.down, scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(3, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W2"), result[1]);
        assertEquals(new Tile(Tile.Type.plain), result[0]);
    }

    public void testLeftFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.left, scape);
        assertEquals(2, result.length);
        assertEquals(9, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.end), result[0]);
        assertEquals(new Tile(Tile.Type.strongSwitch, "S1"), result[1]);
    }

    public void testRightFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.right, scape);
        assertEquals(2, result.length);
        assertEquals(12, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.plain), result[0]);
        assertEquals(new Tile(Tile.Type.teleport,"t1"), result[1]);
    }

    public void testUpFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.up, scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(6, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
    }

    public void testDownFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.down,scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(4, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W3"), result[1]);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W2"), result[0]);
    }

    public void testLeftFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.left,scape);
        assertEquals(1, result.length);
        assertEquals(10, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.strongSwitch, "S1"), result[0]);
    }

    public void testRightFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.teleport, "t1"));
        blockMoveTest(new Coord(13, 5), block, affectedTiles,
                Input.right, Orientation.z);
    }

    public void testUpFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(11, 7), block, affectedTiles, Input.up,
                Orientation.z);
    }

    public void testUpFromY_2() throws Exception {
        Site start = new Site(Orientation.y, new Coord(14,3));
        Block block = new Block(start, 1, 2);
        blockMoveTest(new Coord(14, 5), block, null, Input.up,
                Orientation.z);
    }
    
    public void testDownFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.weakSwitch, "W2"));
        blockMoveTest(new Coord(11, 4), block, affectedTiles, Input.down,
                Orientation.z);
    }

    public void testLeftFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.strongSwitch, "S1"), new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(10, 5), block, affectedTiles, Input.left,
                Orientation.y);
    }

    public void testRightFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain), new Tile(Tile.Type.weak));
        blockMoveTest(new Coord(12, 5), block, affectedTiles,
                Input.right, Orientation.y);
    }

    public void testMoveOffGridToOffGrid() {
        Site start = new Site(Orientation.z, new Coord(-1,-1));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.missing));
        blockMoveTest(new Coord(0, -1), block, affectedTiles,
                Input.right, Orientation.x);
    }

    public void testOffToOnGrid() {
        Site start = new Site(Orientation.z, new Coord(-1,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain), new Tile(Tile.Type.teleport, "t4"));
        blockMoveTest(new Coord(0, 0), block, affectedTiles,
                Input.right, Orientation.x);
    }

    public void testOnToOffGridFull() {
        Site start = new Site(Orientation.z, new Coord(0,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.missing));
        blockMoveTest(new Coord(-2, 0), block, affectedTiles,
                Input.left, Orientation.x);
    }

    public void testOnToOffGridPart() {
        Site start = new Site(Orientation.z, new Coord(1,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(-1, 0), block, affectedTiles,
                Input.left, Orientation.x);
    }
    
    public void testJoinWith(Block block) {
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
        
        assertFalse(block1.joinWith(block4));
        assertFalse(block1.joinWith(block5));
        assertFalse(block2.joinWith(block3));
        assertTrue(block1.joinWith(block2));
        assertEquals(new Site(Orientation.x, new Coord(0,0)), block1.site.orientation);

        assertTrue(block4.joinWith(block2));
        assertEquals(new Site(Orientation.y, new Coord(1,0)), block1.site.orientation);

    }

    public void testJoinWith2(Block block) {
        Site site1 = new Site(Orientation.z, new Coord(10,1));
        Site site2 = new Site(Orientation.z, new Coord(10,3));
        
        Block block1 = new Block(site1, 1, 1);
        Block block2 = new Block(site2, 1, 1);
        
        assertTrue(block1.joinWith(block2));
        assertEquals(new Site(Orientation.y, new Coord(10,1)), block1.site.orientation);
    }

    public void testJoinWith3(Block block) {
        Site site1 = new Site(Orientation.z, new Coord(10,3));
        Site site2 = new Site(Orientation.z, new Coord(10,1));
        
        Block block1 = new Block(site1, 1, 1);
        Block block2 = new Block(site2, 1, 1);
        
        assertTrue(block1.joinWith(block2));
        assertEquals(new Site(Orientation.y, new Coord(10,1)), block1.site.orientation);
    }

    
    private void blockMoveTest(Coord coord, Block block,
            List<Tile> affectedTiles, Input input,
            Orientation orientation) {
        Tile[] result = block.move(input,scape);
        assertEquals(coord.x, block.site.coord.x);
        assertEquals(coord.y, block.site.coord.y);
        assertEquals(orientation, block.site.orientation);
        if(affectedTiles != null) {
            assertEquals(affectedTiles.size(), result.length);
            int i = 0;
            for (Tile tile : affectedTiles) {
                assertEquals(tile, result[i++]);
            }
        }
    }

}
