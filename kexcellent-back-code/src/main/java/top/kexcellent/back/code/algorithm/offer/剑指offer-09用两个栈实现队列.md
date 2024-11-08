## [09.1 用两个栈实现队列](https://leetcode.cn/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/)

### 题目描述

请用栈实现一个队列，支持如下四种操作：

- push(x) – 将元素 x 插到队尾。
- pop(x) – 将队首的元素弹出，并返回该元素。
- peek() – 返回队首元素。
- empty() – 返回队列是否为空。

**注意：**

- 你只能使用栈的标准操作：`push to top`，`peek/pop from top`, `size` 和 `is empty`；
- 如果你选择的编程语言没有栈的标准库，你可以使用 list 或者 deque 等模拟栈的操作；
- 输入数据保证合法，例如，在队列为空时，不会进行 `pop` 或者 `peek` 等操作；

**样例**

```java
MyQueue queue = new MyQueue();

queue.push(1);
queue.push(2);
queue.peek();  // returns 1
queue.pop();   // returns 1
queue.empty(); // returns false
```

### 解法

`push` 操作，每次都存入 `s1`；
`pop` 操作，每次从 `s2` 取：

- `s2` 栈不为空时，不能将 `s1` 元素倒入；
- `s2` 栈为空时，需要一次将 `s1` 元素全部倒入。

```java
class MyQueue {

    private Stack<Integer> s1;
    private Stack<Integer> s2;

    /** Initialize your data structure here. */
    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        s1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        int t = peek();//交换放到s2中后，将s2反过来的数弹出
        s2.pop();
        return t;
    }

    /** Get the front element. */
    public int peek() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {//s1 s2交换
                s2.push(s1.pop());
            }
        }
        return s2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
```

## 9.2 用两个队列实现栈

来源：[LeetCode](https://leetcode.cn/problems/implement-stack-using-queues/)

### 题目描述

使用队列实现栈的下列操作：

- push(x) -- 元素 x 入栈
- pop() -- 移除栈顶元素
- top() -- 获取栈顶元素
- empty() -- 返回栈是否为空

**注意:**

- 你只能使用队列的基本操作-- 也就是 `push to back`, `peek/pop from front`, `size`, 和 `is empty` 这些操作是合法的。
- 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
- 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。

### 解法

- 出栈时，先将队列的元素依次移入另一个队列中，直到队列剩下一个元素。将该元素出队即可。
- 进栈时，将元素压入不为空的那一个队列即可。如果两队列都为空，随便压入其中一个队列。

```java
class MyStack {

    private Queue<Integer> q1;
    private Queue<Integer> q2;

    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        if (empty() || q2.isEmpty()) {//q2不为空放q2,q2为空放q1
            q1.offer(x);
        } else {
            q2.offer(x);
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if (q1.isEmpty()) {
            while (q2.size() > 1) {//q2放q1，大于1停止可以取q2最后一个
                q1.offer(q2.poll());
            }
            return q2.poll();
        }

        while (q1.size() > 1) {//q1放q2，大于1停止可以取q1最后一个
            q2.offer(q1.poll());
        }
        return q1.poll();
    }

    /** Get the top element. */
    public int top() {
        int val = pop();//取出最后一个并删除
        push(val);//最后一个重新放入
        return val;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.isEmpty() && q2.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
```