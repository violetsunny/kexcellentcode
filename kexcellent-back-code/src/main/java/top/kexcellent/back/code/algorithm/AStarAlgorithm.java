/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A*搜索算法，A* 算法结合了最好优先搜索和迪杰斯特拉算法的特点，通过使用启发式函数来减少搜索空间，从而提高效率。
 * 通过优先队列来管理待探索的节点，根据节点的总代价fCost进行排序。算法在搜索过程中不断更新节点的代价，并记录节点的父节点以便最后重构路径。
 * @author kanglele
 * @version $Id: AStarAlgorithm, v 0.1 2024/11/6 14:16 kanglele Exp $
 */
public class AStarAlgorithm {
    static class Node {
        int x;
        int y;
        int gCost;// 从起点到当前节点的实际代价
        int hCost;// 从当前节点到终点的估计代价
        Node parent;// 父节点，用于回溯路径

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            gCost = Integer.MAX_VALUE;// 初始化为最大值
            hCost = 0;
            parent = null;
        }

        // 计算总代价，即实际代价加上估计代价
        public int fCost() {
            return gCost + hCost;
        }
    }


    // 定义四个方向（上、下、左、右）
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static List<Node> findPath(int[][] grid, Node start, Node end) {
        // 创建优先队列，用于存储待探索的节点，根据节点的总代价进行排序
        PriorityQueue<Node> openSet = new PriorityQueue<>((n1, n2) -> n1.fCost() - n2.fCost());
        // 创建已探索节点的列表
        List<Node> closedSet = new ArrayList<>();

        //初始
        start.gCost = 0;// 起点到自身的实际代价为 0
        start.hCost = calculateHeuristic(start, end);// 计算起点到终点的估计代价
        openSet.add(start);// 将起点加入待探索队列

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.x == end.x && current.y == end.y) {
                // 如果当前节点是终点，回溯路径并返回
                return reconstructPath(current);
            }

            closedSet.add(current);// 将当前节点加入已探索列表

            for (int[] direction : DIRECTIONS) {
                // 遍历四个方向，得到新的坐标
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValidPosition(grid, newX, newY) &&!isInClosedSet(closedSet, newX, newY)) {
                    // 创建新的邻居节点
                    Node neighbor = new Node(newX, newY);
                    // 计算从起点到邻居节点的临时实际代价（假设每个移动的代价为 1）
                    int tentativeGCost = current.gCost + 1;

                    if (tentativeGCost < neighbor.gCost) {
                        // 更新邻居节点的父节点为当前节点
                        neighbor.parent = current;
                        // 更新邻居节点的实际代价
                        neighbor.gCost = tentativeGCost;
                        // 计算邻居节点到终点的估计代价
                        neighbor.hCost = calculateHeuristic(neighbor, end);

                        if (!openSet.contains(neighbor)) {
                            // 如果邻居节点不在待探索队列中，加入队列
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }

        return null;// 如果没有找到路径，返回 null
    }

    private static boolean isValidPosition(int[][] grid, int x, int y) {
        // 判断给定坐标是否在网格内且不是障碍物（这里假设值为 1 表示障碍物）
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y]!= 1;
    }

    private static boolean isInClosedSet(List<Node> closedSet, int x, int y) {
        for (Node node : closedSet) {
            // 检查给定坐标的节点是否在已探索列表中
            if (node.x == x && node.y == y) {
                return true;
            }
        }
        return false;
    }

    //启发式函数，使用的是曼哈顿距离用于计算 hcost 值
    private static int calculateHeuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current!= null) {
            path.add(0, current);
            // 从终点回溯，将节点依次加入路径列表的开头
            current = current.parent;
        }
        return path;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };
        Node start = new Node(0, 0);
        Node end = new Node(3, 4);
        List<Node> path = AStarAlgorithm.findPath(grid, start, end);
        if (path!= null) {
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}


