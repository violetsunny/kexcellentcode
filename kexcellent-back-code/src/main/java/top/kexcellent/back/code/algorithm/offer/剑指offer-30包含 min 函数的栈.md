## 30 包含 min 函数的栈

来源：[AcWing](https://www.acwing.com/problem/content/90/)

### 题目描述

设计一个支持 push，pop，top 等操作并且可以在 O(1) 时间内检索出最小元素的堆栈。

- push(x)–将元素 x 插入栈中
- pop()–移除栈顶元素
- top()–得到栈顶元素
- getMin()–得到栈中最小元素

**样例**

```
MinStack minStack = new MinStack();
minStack.push(-1);
minStack.push(3);
minStack.push(-4);
minStack.getMin();   --> Returns -4.
minStack.pop();
minStack.top();      --> Returns 3.
minStack.getMin();   --> Returns -1.
```

### 解法

定义两个`stack`。

压栈时，先将元素 `x` 压入 `stack1`。然后判断 `stack2` 的情况：

- `stack2` 栈为空或者栈顶元素大于 `x`，则将 `x` 压入 `stack2` 中。
- `stack2` 栈不为空且栈定元素小于 `x`，则重复压入栈顶元素。

获取最小元素时，从 `stack2` 中获取栈顶元素即可。

```java
class MinStack {

    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    /** initialize your data structure here. */
    public MinStack() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
        //stack2总是压入当前最小值
        if (stack2.isEmpty() || stack2.peek() > x) {
            stack2.push(x);
        } else {
            stack2.push(stack2.peek());
        }
    }

    public void pop() {
        stack1.pop();
        stack2.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int getMin() {
        return stack2.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
```