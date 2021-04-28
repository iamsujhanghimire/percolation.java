import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wqu;
    private int n, virbottom, opencells;
    private boolean[][] open;
    private int virtop = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Enter a valid number");
        }
        int site = n * n;
        this.n = n;
        virbottom = site + 1;

        //creating virtual top and bottom
        wqu = new WeightedQuickUnionUF(site + 2);
        open = new boolean[site][site];

        //blocking all sites
        for (int i = 1; i < site; i++) {
            for (int j = 0; j < site; j++) {
                open[i][j] = false;
            }
        }
    }

    private int assignedindex(int a, int b) {
        return (n * a) + b + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int id = assignedindex(row, col);
        if (!open[row][col]) {
            open[row][col] = true;
            opencells++;
        }
        //connecting the neighboring open sites
        if (row == 0) wqu.union(id, virtop);
        if (row == n - 1) wqu.union(id, virbottom);

        if (row > 0 && isOpen(row - 1, col)) { //north
            wqu.union(id, assignedindex(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) { //south
            wqu.union(id, assignedindex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) { //west
            wqu.union(id, assignedindex(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) { //east
            wqu.union(id, assignedindex(row, col + 1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int id = assignedindex(row, col);
        return wqu.find(id) == wqu.find(virtop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opencells;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.find(virtop) == wqu.find(virbottom);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);

        p.open(1, 2);
        StdOut.println(p.isOpen(1, 2));
        StdOut.println(p.isOpen(0, 2));
        StdOut.println(p.isOpen(2, 0));

        StdOut.println(p.assignedindex(1, 2));
        StdOut.println(p.assignedindex(2, 1));
        StdOut.println(p.assignedindex(0, 0));

        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 2);
        p.open(2, 0);
        p.open(3, 3);

        StdOut.println(p.numberOfOpenSites());

        StdOut.println(p.isFull(1, 2));
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.isFull(3, 3));

        StdOut.println(p.percolates());

        p.open(2, 3);
        StdOut.println(p.percolates());
    }
}
