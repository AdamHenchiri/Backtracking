package VC;

import java.util.HashSet;
import java.util.Objects;

public class Edge {
    private int i,j;

    public Edge(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge arete = (Edge) o;
        return (i == arete.i && j == arete.j) || ((i == arete.j && j == arete.i));
    }

    @Override
    public int hashCode() {
        HashSet<Integer> e= new HashSet<>();
        e.add(i);
        e.add(j);
        return Objects.hash(e);
    }

    public boolean contains(int v){
        return i==v || j==v;
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public int getMin(){
        return Math.min(i,j);
    }

    public int getMax(){
        return Math.max(i,j);
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "Arete{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
