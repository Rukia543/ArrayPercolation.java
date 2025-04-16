public class ArrayPercolation implements Percolation {
    private final int n;
    private int openSites;
    private final boolean[][] full;
    private final boolean[][] grid;

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        this.grid = new boolean[n][n];
        this.full = new boolean[n][n];
        this.openSites = 0;
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!isOpen(i, j)) {
            grid[i][j] = true;
            openSites++;

            // Update full[][] for newly opened site
            if (i == 0) {
                full[i][j] = true; // Top row sites are full
            }

            // Update full[][] for neighboring sites
            if (i > 0 && isFull(i - 1, j)) {
                floodFill(i, j);
            }
            if (j > 0 && isFull(i, j - 1)) {
                floodFill(i, j);
            }
            if (i < n - 1 && isFull(i + 1, j)) {
                floodFill(i, j);
            }
            if (j < n - 1 && isFull(i, j + 1)) {
                floodFill(i, j);
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return this.grid[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return this.full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        for (int j = 0; j < n; j++) {
            if (full[n - 1][j]) {
                return true;
            }
        }
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n || !isOpen(i, j) || full[i][j]) {
            return;
        }
        full[i][j] = true;

        floodFill(i - 1, j);
        floodFill(i + 1, j);
        floodFill(i, j - 1);
        floodFill(i, j + 1);
    }

    // Test the implementation
    public static void main(String[] args) {
        ArrayPercolation perc = new ArrayPercolation(5);

        perc.open(0, 0);
        perc.open(1, 0);
        perc.open(2, 0);
        perc.open(3, 0);
        perc.open(4, 0);

        System.out.println("Number of open sites: " + perc.numberOfOpenSites());
        System.out.println("Percolates? " + perc.percolates()); // should print true
    }
}
