## [31. 栈的压入、弹出序列](https://leetcode.cn/problems/zhan-de-ya-ru-dan-chu-xu-lie-lcof/)


### 题目描述

输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。

假设压入栈的所有数字均不相等。

例如序列 `1,2,3,4,5` 是某栈的压入顺序，序列 `4,5,3,2,1` 是该压栈序列对应的一个弹出序列，但 `4,3,5,1,2` 就不可能是该压栈序列的弹出序列。

注意：若两个序列为空或长度不等则视为并不是一个栈的压入、弹出序列。

**样例**

```
输入：[1,2,3,4,5]
      [4,5,3,2,1]

输出：true
```

### 解法

判断下一个要弹出的元素：

- 如果刚好是栈顶元素，直接弹出。
- 如果不在栈顶，则把压栈序列中还没有入栈的数字压入栈，直到待弹出的数字压入栈顶。
- 如果所有数字都压入栈顶后依然没有后找到下一个弹出的数字，则不可能是弹出序列。

```java
import java.util.Stack;

public class Solution {
    /**
     * 判断是否是弹出序列
     * @param pushA 压栈序列
     * @param popA 弹栈序列
     * @return 是否是弹出序列
     */
    public boolean IsPopOrder(int[] pushA,int[] popA) {
        if (pushA == null || popA == null || pushA.length != popA.length) {
            return false;
        }

        Stack<Integer> stack = new Stack<>();
        int i = 0;
        int n = pushA.length;
        boolean flag = false;
        for (int val : popA) {
            while (stack.isEmpty() || stack.peek() != val) {
                if (i >= n) {
                    flag = true;
                    break;
                }
                stack.push(pushA[i++]);
            }
            if (flag) {
                break;
            }
            stack.pop();
        }

        return stack.isEmpty();
    }
}
```
如果压入和弹出相等，那按照push压入就能按照pop弹出。
```java
import java.util.Deque;
import java.util.ArrayDeque;
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stk = new ArrayDeque<>();
        int j = 0;
        for (int v : pushed) {
            //压入栈中，按照相等就弹出，就是弹出序列
            stk.push(v);
            while (!stk.isEmpty() && stk.peek() == popped[j]) {
                stk.pop();
                ++j;
            }
        }
        return stk.isEmpty();
    }
}
````