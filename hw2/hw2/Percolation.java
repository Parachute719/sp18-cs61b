package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Set;
import java.util.HashSet;


public class Percolation {

    private int N;
    private boolean[] openGridIn1D;
    private WeightedQuickUnionUF connectedGrids;
    private int nOpen = 0;
    private boolean percolated = false;
    private Set<Integer> topFullIds = new HashSet<>();
    private Set<Integer> bottomFullIds = new HashSet<>();

    private int xyTo1D(int row, int col) {
        int indexIn1D = row * N + col;
        return indexIn1D;
    }

    public Percolation(int N) throws IllegalArgumentException {
        if (N <= 0) {
            throw new IllegalArgumentException(
                    "N must greater than 0, but given N is " + N + " .");
        }
        this.N = N;
        openGridIn1D = new boolean[N * N];
        for (int i = 0; i < N * N; i++) {
            openGridIn1D[i] = false;
        }
        connectedGrids = new WeightedQuickUnionUF(N * N);
    }

    private boolean isInvalidIndex(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return true;
        }
        return false;
    }

    public void open(int row, int col) {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given row = " + row + ", col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        if (openGridIn1D[indexIn1D]) {
            return;
        }
        openGridIn1D[indexIn1D] = true;
        nOpen += 1;

        int id = connectedGrids.find(indexIn1D);
        if (indexIn1D < N) {
            topFullIds.add(id);
        }
        if (N * N - indexIn1D <= N) {
            bottomFullIds.add(id);
        }

        //update connection and full conditions both from top and bottom with neighbors around
        updateConnection(row, col, row, col + 1);
        updateConnection(row, col, row, col - 1);
        updateConnection(row, col, row + 1, col);
        updateConnection(row, col, row - 1, col);

        //update percolation condition
        int newId = connectedGrids.find(indexIn1D);
        if (topFullIds.contains(newId) && bottomFullIds.contains(newId)) {
            percolated = true;
        }
    }

    private void updateConnection(int row, int col, int neighborRow, int neighborCol) {
        if (!isInvalidIndex(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
            int indexIn1D = xyTo1D(row, col);
            int id = connectedGrids.find(indexIn1D);
            int neighborIndexIn1D = xyTo1D(neighborRow, neighborCol);
            int oldId = connectedGrids.find(neighborIndexIn1D);
            connectedGrids.union(indexIn1D, neighborIndexIn1D); //id may change
            int newId = connectedGrids.find(neighborIndexIn1D);

            //update full conditions from top
            if (topFullIds.contains(id)) {
                topFullIds.remove(id);
                topFullIds.add(newId);
            }

            if (topFullIds.contains(oldId)) {
                topFullIds.remove(oldId);
                topFullIds.add(newId);
            }

            //update full conditions from bottom
            if (bottomFullIds.contains(id)) {
                bottomFullIds.remove(id);
                bottomFullIds.add(newId);
            }

            if (bottomFullIds.contains(oldId)) {
                bottomFullIds.remove(oldId);
                bottomFullIds.add(newId);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given row = " + row + ", col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        return openGridIn1D[indexIn1D];
    }

    public boolean isFull(int row, int col) {
        if (isInvalidIndex(row, col)) {
            throw new IndexOutOfBoundsException(
                    "Invalid arguments given row = " + row + ", col = " + col + ".");
        }
        int indexIn1D = xyTo1D(row, col);
        int id = connectedGrids.find(indexIn1D);
        return topFullIds.contains(id);

    }

    public int numberOfOpenSites() {
        return nOpen;
    }

    public boolean percolates() {
        return percolated;
    }

    public static void main(String[] args) {
    }
}
