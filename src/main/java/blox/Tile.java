package blox;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

class Tile {
    
    private String id;
    private Type type;
    int distanceToTarget = Integer.MAX_VALUE;
    
    static Map<String, Type> tileSymbolMap = new HashMap<String, Type>();
    
    static {
        Tile.tileSymbolMap.put("p", Tile.Type.plain);
        Tile.tileSymbolMap.put("s", Tile.Type.start);
        Tile.tileSymbolMap.put("w", Tile.Type.weak);
        Tile.tileSymbolMap.put("x", Tile.Type.missing);
        Tile.tileSymbolMap.put("t", Tile.Type.teleport);
        Tile.tileSymbolMap.put("S", Tile.Type.strongSwitch);
        Tile.tileSymbolMap.put("W", Tile.Type.weakSwitch);
        Tile.tileSymbolMap.put("e", Tile.Type.end);
        Tile.tileSymbolMap.put("l", Tile.Type.teleportLanding);
    }
    
    enum Type {

        start('s'), plain('p'), weak('w'), missing('x'), teleport('t'), strongSwitch('S'), weakSwitch('W'), end('e'), teleportLanding('l');
        
        private char tag;
        
        Type(char c) {
            tag = c;
        }
        
        public char getTag() {
            return tag;
        }
    }

    public Tile(Type type, String ruleId) {
        super();
        this.id = ruleId;
        this.type = type;
    }

    public Tile(Type type) {
        super();
        this.type = type;
    }

    public Tile(Tile tile) {
        this.id = tile.id;
        this.type = tile.type;
        this.distanceToTarget = tile.distanceToTarget;
    }

    public Tile(Tile tile, Type newType) {
        this.id = tile.id;
        this.type = newType;
        this.distanceToTarget = tile.distanceToTarget;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        final Tile other = (Tile) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return type + (id == null ? "" : ","+id);
    }
    
    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}