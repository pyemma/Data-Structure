import rangequery.RangeMinimumQuery;
import java.util.*;

public class RangeMinimumQueryTest {
    public static void main(String[] args) {
        Random rn = new Random();
        int n = 10 + rn.nextInt(10);
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i)
            nums[i] = -50 + rn.nextInt(100);

        RangeMinimumQuery rmq = new RangeMinimumQuery(nums);
        System.out.println("The length of the array is: " + n);
        for (int i = 0; i < n; ++i)
            System.out.print(nums[i] + " ");
        System.out.println("");
        // rmq.print();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            int i = scan.nextInt();
            int j = scan.nextInt();
            int result1 = rmq.query(i, j);
            int result2 = Integer.MAX_VALUE;
            for (int k = i; k <= j; ++k)
                result2 = Math.min(nums[k], result2);
            System.out.println("The actual result is: " + result2);
            System.out.println("My implemented result is: " + result1);
        }
    }
}
