## [21. 调整数组顺序使奇数位于偶数前面](https://leetcode.cn/problems/diao-zheng-shu-zu-shun-xu-shi-qi-shu-wei-yu-ou-shu-qian-mian-lcof/)

### 题目描述

输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

### 解法
最优写法<br>
我们定义两个指针 $i$ 和 $j$，其中指针 $i$ 指向当前元素，指针 $j$ 指向当前最后一个奇数的下一个位置。

接下来，我们从左到右遍历数组，当 $nums[i]$ 是奇数时，我们将其与 $nums[j]$ 交换，然后指针 $j$ 向右移动一位。指针 $i$ 每次向右移动一位，直到遍历完整个数组。

时间复杂度 $O(n)$，空间复杂度 $O(1)$。其中 $n$ 是数组的长度。
```java
class Solution {
    public int[] exchange(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; ++i) {
            //如果是偶数，i移动j不动
            if ((nums[i] & 1) == 1) {
                //如果是奇数，i和j都移动并交换
                int t = nums[i];
                nums[i] = nums[j];
                nums[j++] = t;
            }
        }
        return nums;
    }
}
```

#### 解法一

计算出奇数的个数，就很容易写出来了。

```java
import java.util.Arrays;

public class Solution {
    /**
     * 调整数组元素顺序，使得奇数元素位于偶数元素前面，且保证奇数和奇数，偶数和偶数之间的相对位置不变。
     * @param array 数组
     */
    public void reOrderArray(int [] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int numsOfOdd = 0;
        for (int val : array) {
            if (val % 2 == 1) {
                ++numsOfOdd;//计算出个数
            }
        }
        int[] bak = Arrays.copyOf(array, array.length);
        int i = 0, j = numsOfOdd;
        for (int val : bak) {
            if (val % 2 == 1) {
                array[i++] = val;//奇数从0开始放
            } else {
                array[j++] = val;//偶数从奇数个数后开始放
            }
        }
    }

}
```

第二种写法，利用Arrays的方法

```java
import java.util.Arrays;

public class Solution {
    public void reOrderArray(int [] array) {
        if (array == null || array.length < 2) {
            return;
        }
        Integer[] bak = new Integer[array.length];
        Arrays.setAll(bak, i -> array[i]);//bak[i] = array[i]
        //排序，如果y是奇数且x是偶数，结果为 1，意味着y应该排在x前面。
        //如果y和x都是奇数或者都是偶数，结果为 0，保持它们的相对顺序不变。
        //如果y是偶数且x是奇数，结果为 -1，意味着x应该排在y前面。
        Arrays.sort(bak, (x, y) -> (y & 1) - (x & 1));
        Arrays.setAll(array, i -> bak[i]);
    }

}
```

#### 解法三
双指针交换
```java
import java.util.Arrays;

public class Solution {
    
    public void reOrderArray(int[] array) {
        if(array==null||array.length==0){
            return;
        }
        int left = 0;
        int right = array.length-1;
        while(left<right){
            while(left<right&&array[left]%2!=0){
                left++;
            }
            while(left<right&&array[right]%2!=1){
                right--;
            }
            int temp = array[right];
            array[right] = array[left];
            array[left] = temp;
        }
    }
}
```
