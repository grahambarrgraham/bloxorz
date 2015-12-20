package blox;
/**
 * 
 */

class Coord implements Cloneable {
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coord(Coord coord) {
        this.x = coord.x;
        this.y = coord.y;
    }
    int x;
    int y;
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        final Coord other = (Coord) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    
    public Coord clone() {
        return new Coord(this.x, this.y);
    }
}