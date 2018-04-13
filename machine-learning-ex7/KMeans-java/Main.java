import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args) throws Exception {
		// N = number of points
		// k = number of clusters
		// minRadius = minimum radius of a generated cluster
		// maxRadius = maximum radius of a generated cluster
		// feel free to vary these numbers in order to observe the behaviour
		// of the program  
		final int N = 5000;
		final int k = 5;
		final double minRadius = 0.0;
		final double maxRadius = 0.25;
		final int maxIterations = 100;

		// points = the set of points to be clustered
		// k defined a few rows above
		// iterations = number of iterations before the algorithm stops
		ArrayList<Point> points = Generator.generate(N, k, minRadius, maxRadius);
		kmeans(points, k, maxIterations);
	}

	public static int getClosest(ArrayList<Point> centroids, Point current) {
		int i = 0;
		double dist = Double.MAX_VALUE;
		for (Point p : centroids) {
			if (current.distanceTo(p) < dist) {
				dist = current.distanceTo(p);
				i = centroids.indexOf(p);
			}
		}
		return i;
	}
	
	public static void centroidPosition(ArrayList<Point> centroids, ArrayList<Point> points, int clusterInd) {
		double xsum = 0;
		double ysum = 0;
		double count = 0;
		
		for (Point p : points) {
			if (p.clusterIndex == clusterInd) {
				count++;
				xsum += p.x;
				ysum += p.y;
			}
		}
		centroids.get(clusterInd).x = xsum / count;
		centroids.get(clusterInd).y = ysum / count;
	}
	
	public static void kmeans(ArrayList<Point> points, int k, int iterations) {
		// firstly, the points are asigned to no cluster
		for (Point point : points) {
			point.clusterIndex = Point.UNKNOWN_CLUSTER;
		}

		Painter picasso = new Painter(points);
		ArrayList<Point> centroids = randomCentroids(points, k);
		ArrayList<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < iterations; i++) {

			for (Point p : points) {
				p.clusterIndex = getClosest(centroids, p);
			}
			for (Point p : points) {
				if (!indexes.contains(p.clusterIndex)) {
					indexes.add(p.clusterIndex);
				}
			}
			for (int j = 0; j < indexes.size(); j++) {
				centroidPosition(centroids, points, indexes.get(j));
			}

			picasso.setPoints(points);
			picasso.setCentroids(centroids);

			// sleep 2s to give us a chance to take a look at the image.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static ArrayList<Point> randomCentroids(ArrayList<Point> points, int k) {
		ArrayList<Point> centroids = new ArrayList<Point>();
		ArrayList<Integer> selected = new ArrayList<>();
		int ind;
		
		for (int i = 0; i < k; i++) {
			ind = Math.abs(new Random().nextInt()%points.size());
			if (!selected.contains(ind)) {
				centroids.add(points.get(ind));
				selected.add(ind);
			}
		}
		return centroids;
	}

	static ArrayList<Point> kmeansppCentroids(ArrayList<Point> points, int k) {
		// TODO - improved initialization algorithm
		return randomCentroids(points, k);
	}
}
