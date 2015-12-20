package blox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import astar.Node;

/**
 * 
 */

class BloxNode implements Node {

    Scape scape;
    Set<Block> blocks;
    Block activeBlock;

    public BloxNode(Scape scape, Block... blocks) {
        this.scape = scape;
        this.blocks = new HashSet<Block>(Arrays.asList(blocks));
        this.activeBlock = blocks[0];
    }

    public BloxNode(Scape scape, Set<Block> blocks, Block active) {
        this.scape = scape;
        this.blocks = blocks;
        if (!blocks.contains(active)) {
            throw new RuntimeException("active block wasn't in set");
        }
        this.activeBlock = active;
        joinContiguousBlocks();
    }

    public BloxNode(BloxNode site) {
        this.blocks = new HashSet<Block>();
        for (Block block : site.blocks) {
            this.blocks.add(new Block(block));
        }
        this.activeBlock = site.activeBlock;
        this.scape = site.scape;
    }

    public BloxNode(BloxNode site, Scape scape) {
        this(site);
        this.scape = scape;
    }

    public BloxNode(BloxNode start, Block block) {
        this(start);
        replaceActiveBlock(block);
        joinContiguousBlocks();
    }

    void joinContiguousBlocks() {
        Set<Block> dead = new HashSet<Block>();
        for (Iterator<Block> it = blocks.iterator(); it.hasNext();) {

            Block block = it.next();
            if (dead.contains(block)) {
                continue;
            }

            for (Iterator<Block> it2 = blocks.iterator(); it2.hasNext();) {
                Block block2 = it2.next();
                if (block.equals(block2)) {
                    continue;
                }
                if (block.joinWith(block2)) {
                    dead.add(block2);
                    activeBlock = block;
                }
            }
        }
        blocks.removeAll(dead);
    }

    void replaceActiveBlock(Block block) {
        blocks.remove(this.activeBlock);
        this.activeBlock = block;
        blocks.add(this.activeBlock);
    }

    public String toString() {
        //Object distString = (distance == Integer.MAX_VALUE ? "?" : distance);
        return String.format("node:%s-%s", blocks, scape.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
        result = prime * result + ((scape == null) ? 0 : scape.hashCode());
        result = prime * result + ((activeBlock == null) ? 0 : activeBlock.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BloxNode other = (BloxNode) obj;
        if(activeBlock == null) {
            if(other.activeBlock != null)
                return false;
        } else if (!activeBlock.equals(other.activeBlock))
            return false;
        if (blocks == null) {
            if (other.blocks != null)
                return false;
        } else if (!blocks.equals(other.blocks))
            return false;
        if (scape == null) {
            if (other.scape != null)
                return false;
        } else if (!scape.equals(other.scape))
            return false;
        return true;
    }


    public Block getActiveBlock() {
        return activeBlock;

    }

    public BloxNode nextBlock() {
        BloxNode ret = new BloxNode(this);

        for (Block block : blocks) {
            if (!block.equals(activeBlock)) {
                ret.activeBlock = block;
                return ret;
            }
        }
        return null;
    }

    public Node copy() {
        return new BloxNode(this);
    }

    public boolean isAtTarget(Node target) {
        if(target instanceof Site) {
            return isAtTarget((Site) target);
        }
        return false;
    }

    public boolean isAtTarget(Site obj) {
        return blocks.size() == 1 && activeBlock.height > 1 && activeBlock.site.equals(obj);
    }

}