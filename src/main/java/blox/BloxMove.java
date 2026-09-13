package blox;

import astar.Move;
import astar.Node;

class BloxMove extends Move {
    static final int ROLL_COST = 1000000;

    Input input;

    BloxMove(Node node, Input input) {
        super(node, input == Input.nextBlock ? 1 : ROLL_COST);
        this.input = input;
    }

    public String toString() {
        return "Move " + input + "->" + destination;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BloxMove other = (BloxMove) obj;
        if (input == null) {
            if (other.input != null)
                return false;
        } else if (!input.equals(other.input))
            return false;
        return true;
    } 
}
