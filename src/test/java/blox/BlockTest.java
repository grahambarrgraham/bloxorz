package blox;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockTest {

    Scape scape = null;

    @BeforeEach
    void setUp() throws Exception {
        scape = new Scape();
        scape.load("test1.txt");
    }

    @Test
    void testStartZ() {
        Block block = new Block(scape.start, Orientation.z, 1, 2);
        assertEquals(11, scape.start.x);
        assertEquals(5, scape.start.y);
        assertEquals(5, block.site.coord.y);
        assertEquals(11, block.site.coord.x);
        assertEquals(Orientation.z, block.site.orientation);
        assertEquals(1, block.width);
        assertEquals(2, block.height);
    }

    @Test
    void testStartY() {
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

    @Test
    void testStartX() {
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


    @Test
    void testUpFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.up, scape);
        assertEquals(2, result.length);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
        assertEquals(new Tile(Tile.Type.plain), result[1]);
        assertEquals(11, block.site.coord.x);
        assertEquals(6, block.site.coord.y);
    }

    @Test
    void testDownFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.down, scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(3, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W2"), result[1]);
        assertEquals(new Tile(Tile.Type.plain), result[0]);
    }

    @Test
    void testLeftFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.left, scape);
        assertEquals(2, result.length);
        assertEquals(9, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.end), result[0]);
        assertEquals(new Tile(Tile.Type.strongSwitch, "S1"), result[1]);
    }

    @Test
    void testRightFromZ() throws Exception {
        Site start = new Site(Orientation.z, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.right, scape);
        assertEquals(2, result.length);
        assertEquals(12, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.plain), result[0]);
        assertEquals(new Tile(Tile.Type.teleport,"t1"), result[1]);
    }

    @Test
    void testUpFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.up, scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(6, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
        assertEquals(new Tile(Tile.Type.weak), result[0]);
    }

    @Test
    void testDownFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.down,scape);
        assertEquals(2, result.length);
        assertEquals(11, block.site.coord.x);
        assertEquals(4, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W3"), result[1]);
        assertEquals(new Tile(Tile.Type.weakSwitch, "W2"), result[0]);
    }

    @Test
    void testLeftFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        Tile[] result = block.move(Input.left,scape);
        assertEquals(1, result.length);
        assertEquals(10, block.site.coord.x);
        assertEquals(5, block.site.coord.y);
        assertEquals(new Tile(Tile.Type.strongSwitch, "S1"), result[0]);
    }

    @Test
    void testRightFromX() throws Exception {
        Site start = new Site(Orientation.x, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.teleport, "t1"));
        blockMoveTest(new Coord(13, 5), block, affectedTiles,
                Input.right, Orientation.z);
    }

    @Test
    void testUpFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(11, 7), block, affectedTiles, Input.up,
                Orientation.z);
    }

    @Test
    void testUpFromY_2() throws Exception {
        Site start = new Site(Orientation.y, new Coord(14,3));
        Block block = new Block(start, 1, 2);
        blockMoveTest(new Coord(14, 5), block, null, Input.up,
                Orientation.z);
    }

    @Test
    void testDownFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.weakSwitch, "W2"));
        blockMoveTest(new Coord(11, 4), block, affectedTiles, Input.down,
                Orientation.z);
    }

    @Test
    void testLeftFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.strongSwitch, "S1"), new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(10, 5), block, affectedTiles, Input.left,
                Orientation.y);
    }

    @Test
    void testRightFromY() throws Exception {
        Site start = new Site(Orientation.y, scape.start);
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain), new Tile(Tile.Type.weak));
        blockMoveTest(new Coord(12, 5), block, affectedTiles,
                Input.right, Orientation.y);
    }

    @Test
    void testMoveOffGridToOffGrid() {
        Site start = new Site(Orientation.z, new Coord(-1,-1));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.missing));
        blockMoveTest(new Coord(0, -1), block, affectedTiles,
                Input.right, Orientation.x);
    }

    @Test
    void testOffToOnGrid() {
        Site start = new Site(Orientation.z, new Coord(-1,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.plain), new Tile(Tile.Type.teleport, "t4"));
        blockMoveTest(new Coord(0, 0), block, affectedTiles,
                Input.right, Orientation.x);
    }

    @Test
    void testOnToOffGridFull() {
        Site start = new Site(Orientation.z, new Coord(0,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.missing));
        blockMoveTest(new Coord(-2, 0), block, affectedTiles,
                Input.left, Orientation.x);
    }

    @Test
    void testOnToOffGridPart() {
        Site start = new Site(Orientation.z, new Coord(1,0));
        Block block = new Block(start, 1, 2);
        List<Tile> affectedTiles = Arrays.asList(new Tile(Tile.Type.missing), new Tile(Tile.Type.plain));
        blockMoveTest(new Coord(-1, 0), block, affectedTiles,
                Input.left, Orientation.x);
    }

    @Test
    void testJoinWithAdjacent() {
        Site site1 = new Site(Orientation.z, new Coord(0,0));
        Site site2 = new Site(Orientation.z, new Coord(1,0));
        Site site3 = new Site(Orientation.z, new Coord(0,1));
        Site site5 = new Site(Orientation.z, new Coord(5,5));

        Block block1 = new Block(site1, 1, 1);
        Block block2 = new Block(site2, 1, 1);
        Block block3 = new Block(site3, 1, 1);
        Block block5 = new Block(site5, 1, 1);

        assertFalse(block1.joinWith(block5));
        assertFalse(block2.joinWith(block3));
        assertTrue(block1.joinWith(block2));
        assertEquals(new Site(Orientation.x, new Coord(0,0)), block1.site);
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
