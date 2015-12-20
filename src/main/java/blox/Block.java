package blox;



/**
 * 
 */

class Block {

    int width;
    int height;
    Site site;
    
    Block(Coord c, Orientation o, int width, int height) {
        this.width = width;
        this.height = height;
        this.site = new Site(o,c);
    }

    public Block(Block block) {
        this.height= block.height;
        this.width = block.width;
        this.site = new Site(block.site);
    }

    public Block(Site s, int width, int height) {
        this.width = width;
        this.height = height;
        this.site = new Site(s);
    }

    Tile[] move(Input input, Scape scape) {
        nextCoord(input);
        Tile[] result = getAffectedTiles(scape);
        return result;
    }

    public void nextCoord(Input input) {

        Orientation nextOrientation = site.orientation;

        switch (input) {
        case up:
            switch (site.orientation) {
            case x:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y + width;
                nextOrientation = Orientation.x;
                break;
            case y:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y + height;
                nextOrientation = Orientation.z;
                break;
            case z:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y + width;
                nextOrientation = Orientation.y;
                break;
            }
            break;
        case down:
            switch (site.orientation) {
            case x:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y - width;
                nextOrientation = Orientation.x;
                break;
            case y:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y - width;
                nextOrientation = Orientation.z;
                break;
            case z:
                this.site.coord.x =site.coord.x;
                this.site.coord.y =site.coord.y - height;
                nextOrientation = Orientation.y;
                break;
            }
            break;
        case left:
            switch (site.orientation) {
            case x:
                this.site.coord.x =site.coord.x - width;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.z;
                break;
            case y:
                this.site.coord.x =site.coord.x - width;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.y;
                break;
            case z:
                this.site.coord.x =site.coord.x - height;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.x;
                break;
            }
            break;
        case right:
            switch (site.orientation) {
            case x:
                this.site.coord.x =site.coord.x + height;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.z;
                break;
            case y:
                this.site.coord.x =site.coord.x + width;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.y;
                break;
            case z:
                this.site.coord.x =site.coord.x + width;
                this.site.coord.y =site.coord.y;
                nextOrientation = Orientation.x;
                break;
            }
            break;
        }

        site.orientation = nextOrientation;
    }

    public Tile[] getAffectedTiles(Scape scape) {
        switch (site.orientation) {
        case x:
            return getAffectedTiles(scape, new Tile[height], 1, 0);
        case y:
            return getAffectedTiles(scape, new Tile[height], 0, 1);
        case z:
            return getAffectedTiles(scape, new Tile[width], 0, 0);
        }
        return null;
    }

    private Tile[] getAffectedTiles(Scape scape, Tile[] tiles, int x,
            int y) {
        for (int i = 0; i < tiles.length; i++) {
            int xCoord =site.coord.x + (i * x);
            int yCoord =site.coord.y + (i * y);
            if ( xCoord < 0 || xCoord > scape.getXSize()-1 ) {
                tiles[i] = new Tile(Tile.Type.missing);
            } else if ( yCoord < 0 || yCoord > scape.getYSize()-1 ) {
                tiles[i] = new Tile(Tile.Type.missing);
            } else {
                tiles[i] = scape.get(xCoord, yCoord);
            }
        }
        return tiles;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)@%s", width, height,site);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + ((site == null) ? 0 : site.hashCode());
        result = prime * result + width;
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
        final Block other = (Block) obj;
        if (height != other.height)
            return false;
        if (site == null) {
            if (other.site != null)
                return false;
        } else if (!site.equals(other.site))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    public boolean joinWith(Block block) {
            
        if (this.width > 1 || this.height > 1 || block.width > 1 || block.height > 1) {
            return false;
        }
        
        int xDiff = site.coord.x - block.site.coord.x;
        int yDiff = site.coord.y - block.site.coord.y;
        
        if( xDiff==1 && yDiff==0) {
            this.height += 1;
            this.site.orientation = Orientation.x;
            this.site.coord.x = block.site.coord.x; 
        } else if( xDiff==0 && yDiff==1) {
            this.height += 1;
            this.site.orientation = Orientation.y;
            this.site.coord.y = block.site.coord.y; 
        } else if( xDiff==-1 && yDiff==0) {
            this.height += 1;
            this.site.orientation = Orientation.x;
        } else if( xDiff==0 && yDiff==-1) {
            this.height += 1;
            this.site.orientation = Orientation.y;
        } else {
            return false;
        }
        
        return true;
    }
   
    
}