public class Point {
	
	final static int UNKNOWN_CLUSTER = -1;

	public double x;
	public double y;

	public int clusterIndex;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.clusterIndex = UNKNOWN_CLUSTER;
	}

	public Point(double x, double y, int clusterIndex) {
		this.x = x;
		this.y = y;
		this.clusterIndex = clusterIndex;
	}

	public Point clone() {
		return new Point(x, y, clusterIndex);
	}

	// calculate distance in space from Point to other Point
	public double distanceTo(Point other) {
		double dx = x - other.x;
		double dy = y - other.y;

		return Math.sqrt(dx * dx + dy * dy);
	}
}
