import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int grid;
    private int trials;
    private double[] prop;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        grid = n;
        this.trials = trials;
        prop = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(grid);
            while (!per.percolates()) {
                int row = StdRandom.uniform(1, grid + 1);
                int col = StdRandom.uniform(1, grid + 1);
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                }
            }
            prop[i] = (double) per.numberOfOpenSites() / (double) (grid * grid);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.prop);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.prop);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double LowC;
        LowC = mean() - 1.96 * stddev() / Math.sqrt(trials);
        return LowC;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double HighC;
        HighC = mean() + 1.96 * stddev() / Math.sqrt(trials);
        return HighC;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(200, 10000);
        System.out.println("mean =" + test.mean());
        System.out.println("stddev =" + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLow() + "," + test.confidenceHigh() + "]");
    }
}
