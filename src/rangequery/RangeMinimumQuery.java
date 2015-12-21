package rangequery;

import java.util.*;


/**
    range minimum query implemented with sparse table
    construction cost O(nlogn) time and space
    query cost O(1) time

    current version only support integers
    TODO: make it support any type implemented Comparable
*/
public class RangeMinimumQuery {

    private int[][] sparse_table;

    public RangeMinimumQuery(int[] nums) {
        int n = nums.length;
        int k = 0;
        while ((1 << k) < n) ++k;

        sparse_table = new int[n][k+1];
        for (int i = 0; i < n; ++i)
            sparse_table[i][0] = nums[i];

        for (int j = 1; j <= k; ++j) {
            for (int i = 0; (i + (1 << j)) <= n; ++i) {
                int mi = Math.min(sparse_table[i][j-1], sparse_table[i+(1<<(j-1))][j-1]);
                sparse_table[i][j] = mi;
            }
        }
    }

    // the two index is 0-based index
    public int query(int i, int j) {
        int diff = j - i + 1;
        int k = 0;
        while((1 << (k+1)) <= diff) ++k;
        return Math.min(sparse_table[i][k], sparse_table[j-(1<<k)+1][k]);
    }

    public void print() {
        int n = sparse_table.length;
        int m = sparse_table[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j)
                System.out.print(sparse_table[i][j] + " ");
            System.out.println("");
        }
        System.out.println("");
    }
}
