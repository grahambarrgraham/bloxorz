package blox;

import java.util.List;


public class Rule {

    String subjectId;
    Action action;
    Coord coord;
    List<String> objectIds;
    int score = Integer.MAX_VALUE;

    public Rule(String switchId) {
        this.subjectId = switchId;
    }

    public Rule(String switchId, Action action,
            List<String> bridgeIdsList) {
        this.subjectId = switchId;
        this.action = action;
        this.objectIds = bridgeIdsList;
    }

    @Override
    public String toString() {
        String s = subjectId + ',' + action.name() + '(';
        for (String ss : objectIds) {
            s += ss + ',';
        }
        return s + ')';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((subjectId == null) ? 0 : subjectId.hashCode());
        result = prime * result + ((action == null) ? 0 : action.hashCode());
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
        final Rule other = (Rule) obj;
        if (subjectId == null) {
            if (other.subjectId != null)
                return false;
        } else if (!subjectId.equals(other.subjectId))
            return false;
        if (action == null) {
            if (other.action != null)
                return false;
        } else if (!action.equals(other.action))
            return false;
        return true;
    }

    void setCoord(Coord coord) {
        this.coord = coord;
        
    }

    public void setScore(int s) {
        if (score >= s)
            score = s;
    }
}
