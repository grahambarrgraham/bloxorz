package blox;
import astar.Node;



public class Site implements Node {

    Orientation orientation;
    Coord coord;
    
    public Site(Orientation o, Coord coord) {
        this.orientation = o;
        this.coord = coord;
    }

    public Site(Site site) {
        this.orientation = site.orientation;
        this.coord = new Coord(site.coord);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coord == null) ? 0 : coord.hashCode());
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
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
        final Site other = (Site) obj;
        if (coord == null) {
            if (other.coord != null)
                return false;
        } else if (!coord.equals(other.coord))
            return false;
        if (orientation == null) {
            if (other.orientation != null)
                return false;
        } else if (!orientation.equals(other.orientation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)-%s", coord.x,coord.y, orientation);
    }

    public Node copy() {
        return new Site(this);
    }

    public boolean isAtTarget(Node target) {
        return equals(target);
    }
    
}
