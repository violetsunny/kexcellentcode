## [40. 最小的 k 个数](https://leetcode.cn/problems/zui-xiao-de-kge-shu-lcof/)


### 题目描述

输入 n 个整数，找出其中最小的 K 个数。例如输入 `4,5,1,6,2,7,3,8` 这 8 个数字，则最小的 4 个数字是 `1,2,3,4`。

### 解法

#### 解法一

利用快排中的 partition 思想。

数组中有一个数字出现次数超过了数组长度的一半，那么排序后，数组中间的数字一定就是我们要找的数字。我们随机选一个数字，利用 partition() 函数，使得比选中数字小的数字都排在它左边，比选中数字大的数字都排在它的右边。

判断选中数字的下标 `index`：

- 如果 `index = k-1`，结束循环，返回前 k 个数。
- 如果 `index > k-1`，那么接着在 index 的左边进行 partition。
- 如果 `index < k-1`，则在 index 的右边继续进行 partition。

**注意**，这种方法会修改输入的数组。时间复杂度为 `O(n)`。

```java
import java.util.ArrayList;

public class Solution {

    /**
     * 获取数组中最小的k个数
     *
     * @param input 输入的数组
     * @param k 元素个数
     * @return 最小的k的数列表
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (input == null || input.length == 0 || input.length < k || k < 1) {
            return res;
        }
        int n = input.length;
        int start = 0, end = n - 1;
        int index = partition(input, start, end);
        while (index != k - 1) {
            if (index > k - 1) {
                end = index - 1;
            } else {
                start = index + 1;
            }
            index = partition(input, start, end);
        }
        for (int i = 0; i < k; ++i) {
            res.add(input[i]);
        }
        return res;
    }

    private int partition(int[] input, int start, int end) {
        int index = start - 1;
        for (int i = start; i < end; ++i) {
            if (input[i] < input[end]) {
                swap(input, i, ++index);
            }
        }
        ++index;
        swap(input, index, end);
        return index;
    }

    private void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
```

````java
class Solution {
    private int[] arr;
    private int k;

    public int[] getLeastNumbers(int[] arr, int k) {
        int n = arr.length;
        this.arr = arr;
        this.k = k;
        return k == n ? arr : quickSort(0, n - 1);
    }

    private int[] quickSort(int l, int r) {
        int i = l, j = r;
        while (i < j) {
            while (i < j && arr[j] >= arr[l]) {
                --j;
            }
            while (i < j && arr[i] <= arr[l]) {
                ++i;
            }
            swap(i, j);
        }
        swap(i, l);
        if (k < i) {
            return quickSort(l, i - 1);
        }
        if (k > i) {
            return quickSort(i + 1, r);
        }
        return Arrays.copyOf(arr, k);
    }

    private void swap(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
````

#### 解法二

利用大根堆，存储最小的 k 个数，最后返回即可。

此方法时间复杂度为 `O(nlogk)`。虽然慢一点，但是它不会改变输入的数组，并且它**适合海量数据的输入**。

假设题目要求从海量的数据中找出最小的 k 个数，由于内存的大小是有限的，有可能不能把这些海量的数据一次性全部载入内存。这个时候，用这种方法是最合适的。就是说它适合 n 很大并且 k 较小的问题。

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Solution {

    /**
     * 获取数组中最小的k个数
     *
     * @param input 输入的数组
     * @param k 元素个数
     * @return 最小的k的数列表
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (input == null || input.length == 0 || input.length < k || k < 1) {
            return res;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Comparator.reverseOrder());
        System.out.println(maxHeap.size());
        for (int e : input) {
            if (maxHeap.size() < k) {
                maxHeap.add(e);
            } else {
                if (maxHeap.peek() > e) {
                    maxHeap.poll();
                    maxHeap.add(e);
                }

            }
        }
        res.addAll(maxHeap);
        return res;
    }
}
```

## [数组中的第K个最大元素](https://leetcode.cn/problems/kth-largest-element-in-an-array/description/)
给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。

请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。


````
示例 1:
输入: [3,2,1,5,6,4], k = 2
输出: 5
示例 2:
输入: [3,2,3,1,2,4,5,5,6], k = 4
输出: 4
````
````
提示：
1 <= k <= nums.length <= 105
-104 <= nums[i] <= 104
````

````java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Queue<Integer> max = new PriorityQueue<Integer>((x,y)->y-x);
        for(int num:nums){
            max.add(num);
        }
        int res=0;
        for(int i=0;i<k;i++){
            res = max.poll();
        }
        return res;
    }
}
````

自己实现堆排序
````java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    public void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2 - 1; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        } 
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        } 
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    public void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
````
## [有序矩阵中第 K 小的元素](https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/description/)
给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。

你必须找到一个内存复杂度优于 O(n2) 的解决方案。

````
示例 1：
输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
输出：13
解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
示例 2：
输入：matrix = [[-5]], k = 1
输出：-5
````

提示：
- n == matrix.length
- n == matrix[i].length
- 1 <= n <= 300
- -109 <= matrix[i][j] <= 109
- 题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
- 1 <= k <= n2


进阶：
- 你能否用一个恒定的内存(即 O(1) 内存复杂度)来解决这个问题?
- 你能在 O(n) 的时间复杂度下解决这个问题吗?这个方法对于面试来说可能太超前了，但是你会发现阅读这篇文章（ this paper ）很有趣。

### 解法：归并排序+大根堆
这个矩阵的每一行均为一个有序数组。问题即转化为从这 n 个有序数组中找第 k 大的数，可以想到利用归并排序的做法，归并到第 k 个数即可停止。
一般归并排序是两个数组归并，而本题是 n 个数组归并，所以需要用小根堆维护，以优化时间复杂度。
````java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{matrix[i][0], i, 0});
        }
        for (int i = 0; i < k - 1; i++) {
            int[] now = pq.poll();
            if (now[2] != n - 1) {
                pq.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
            }
        }
        return pq.poll()[0];
    }
}
````

### 解法：二分查找
检查min=left+(right-left)>>1位置的数肯定左斜角肯定都是小于matrix[mid]
- 时间复杂度：O(nlog(r−l))，二分查找进行次数为 O(log(r−l))，每次操作时间复杂度为 O(n)。
- 空间复杂度：O(1)。
````java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = left + ((right - left) >> 1);//中点
            if (check(matrix, mid, k, n)) {
                right = mid;
            } else {
                left = mid + 1;//两边挪动
            }
        }
        return left;
    }

    public boolean check(int[][] matrix, int mid, int k, int n) {
        int i = n - 1;//从左下角开始查找
        int j = 0;
        int num = 0;//统计范围内满足的num
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num = num + i + 1;//往右代表前面的I行都满足
                j++;//往右
            } else {
                i--;//往上
            }
        }
        return num >= k;
    }
}
````