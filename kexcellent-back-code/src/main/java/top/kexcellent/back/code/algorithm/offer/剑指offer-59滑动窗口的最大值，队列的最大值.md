## [59 - I. 滑动窗口的最大值](https://leetcode.cn/problems/hua-dong-chuang-kou-de-zui-da-zhi-lcof/)


### 题目描述

给定一个数组和滑动窗口的大小，请找出所有滑动窗口里的最大值。

例如，如果输入数组 `[2, 3, 4, 2, 6, 2, 5, 1]` 及滑动窗口的大小 3,那么一共存在 6 个滑动窗口，它们的最大值分别为 `[4, 4, 6, 6, 6, 5]`。

**注意：**

- 数据保证 k 大于 0，且 k 小于等于数组长度。

**样例**

```
输入：[2, 3, 4, 2, 6, 2, 5, 1] , k=3

输出: [4, 4, 6, 6, 6, 5]
```

### 解法

利用双向队列，保证队列头部存放的是最大值的下标，当队列头部下标过期时弹出。

细节：

- 当数组元素小于队列头部下标对应的元素时，在队列尾部中插入数组元素下标。（如果队列尾部有比该元素小的元素，先弹出，再插入。）
- 当数组元素大于或等于队列头部下标构成的元素时，弹出元素直至队列为空，再插入数组元素下标。

```java
import java.util.Deque;
import java.util.LinkedList;

class Solution {
    /**
     * 求滑动窗口的最大值
     *
     * @param nums 数组
     * @param k 滑动窗口的大小
     * @return 最大值构成的数组
     */
    public int[] maxInWindows(int[] nums, int k) {
        if (nums == null || k < 1 || k > nums.length) {
            return null;
        }
        Deque<Integer> queue = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < k; ++i) {
            if (queue.isEmpty()) {
                queue.addLast(i);
            } else {
                if (nums[queue.getFirst()] < nums[i]) {
                    while (!queue.isEmpty()) {
                        queue.removeFirst();
                    }
                } else {
                    while (nums[queue.getLast()] < nums[i]) {
                        queue.removeLast();
                    }
                }
                queue.addLast(i);
            }
        }

        for (int i = k; i < nums.length; ++i) {
            res[i - k] = nums[queue.getFirst()];
            if (nums[i] < nums[queue.getFirst()]) {
                while (nums[queue.getLast()] < nums[i]) {
                    queue.removeLast();
                }
            } else {
                while (!queue.isEmpty()) {
                    queue.removeFirst();
                }
            }
            queue.addLast(i);
            if (i - queue.getFirst() == k) {
                queue.removeFirst();
            }
        }
        res[nums.length - k] = nums[queue.getFirst()];
        return res;
    }
}
```

# [59 - II. 队列的最大值](https://leetcode.cn/problems/dui-lie-de-zui-da-zhi-lcof/)

## 题目描述

<!-- description:start -->

<p>请定义一个队列并实现函数 <code>max_value</code> 得到队列里的最大值，要求函数<code>max_value</code>、<code>push_back</code> 和 <code>pop_front</code> 的<strong>均摊</strong>时间复杂度都是O(1)。</p>

<p>若队列为空，<code>pop_front</code> 和 <code>max_value</code>&nbsp;需要返回 -1</p>

<p><strong>示例 1：</strong></p>

<pre><strong>输入:</strong> 
[&quot;MaxQueue&quot;,&quot;push_back&quot;,&quot;push_back&quot;,&quot;max_value&quot;,&quot;pop_front&quot;,&quot;max_value&quot;]
[[],[1],[2],[],[],[]]
<strong>输出:&nbsp;</strong>[null,null,null,2,1,2]
</pre>

<p><strong>示例 2：</strong></p>

<pre><strong>输入:</strong> 
[&quot;MaxQueue&quot;,&quot;pop_front&quot;,&quot;max_value&quot;]
[[],[],[]]
<strong>输出:&nbsp;</strong>[null,-1,-1]
</pre>

<p>&nbsp;</p>

<p><strong>限制：</strong></p>

<ul>
	<li><code>1 &lt;= push_back,pop_front,max_value的总操作数&nbsp;&lt;= 10000</code></li>
	<li><code>1 &lt;= value &lt;= 10^5</code></li>
</ul>

<!-- description:end -->

## 解法

<!-- solution:start -->

### 方法一：双队列

我们维护两个队列 $q_1$ 和 $q_2$，其中 $q_1$ 用于存储所有元素，而 $q_2$ 用于存储当前队列中的最大值。

当获取队列中的最大值时，如果队列 $q_2$ 不为空，则队列中的最大值即为 $q_2$ 的队首元素；否则队列为空，返回 $-1$。

当向队列中添加元素时，我们需要将 $q_2$ 弹出所有队尾元素小于当前元素的元素，然后将当前元素添加到 $q_2$ 的队尾，最后将当前元素添加到 $q_1$ 的队尾。

当从队列中弹出元素时，如果 $q_1$ 为空，则返回 $-1$；否则，如果 $q_1$ 的队首元素等于 $q_2$ 的队首元素，则将 $q_2$ 的队首元素弹出，然后将 $q_1$ 的队首元素弹出；否则，只将 $q_1$ 的队首元素弹出。

以上操作的时间复杂度均为 $O(1)$，空间复杂度为 $O(n)$。其中 $n$ 为队列中的元素个数。

<!-- tabs:start -->

```java
class MaxQueue {
    private Deque<Integer> q1 = new ArrayDeque<>();
    private Deque<Integer> q2 = new ArrayDeque<>();

    public MaxQueue() {
    }

    public int max_value() {
        return q2.isEmpty() ? -1 : q2.peek();
    }

    public void push_back(int value) {
        while (!q2.isEmpty() && q2.peekLast() < value) {
            q2.pollLast();
        }
        q1.offer(value);
        q2.offer(value);
    }

    public int pop_front() {
        if (q1.isEmpty()) {
            return -1;
        }
        int ans = q1.poll();
        if (q2.peek() == ans) {
            q2.poll();
        }
        return ans;
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */
```