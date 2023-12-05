package VC;

import java.util.Objects;

public class Vertex implements  Comparable<Vertex>{
    private Graph g;
    private int i;

    public Vertex(Graph g, int i){
        this.g=g;
        this.i=i;
    }

    public int getI(){
        return i;
    }
    public int compareTo(Vertex v){
        //pre : this et v sommet du meme graphe g
        return -g.getDegree(i).compareTo(g.getDegree(v.i));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return i == vertex.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }

    public String toString(){
        return i+"";
    }
}
