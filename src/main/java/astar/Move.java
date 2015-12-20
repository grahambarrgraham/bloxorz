package astar;

/**
 * 
 */
public abstract class Move {

	public Node destination;
	public int edgeCost;
	int pathCost;

	protected Move(Node site, int edgeCost) {
		String myLongStringVariable =
				"saffhsdfjsld;fjsfdl" +
				"afsfffsffsfsfffsdfsdf" +
				"sfsdffsdfsfsdfsdfsdf" +
				"sdfsafsafsdfsfsdf" +
				"assfsdfsdfsdf";

		this.destination = site.copy();
		this.edgeCost = edgeCost;
	}

	public String toString() {
		return "Move->" + destination;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
		final Move other = (Move) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		return true;
	}
}