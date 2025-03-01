package com.troopybinary.path;

import java.util.Objects;

public class Node {
	
    public int x, y;
    public double gCost;
    public double hCost;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getFCost() {
        return gCost + hCost;
    }

    public double getGCost() {
        return gCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}