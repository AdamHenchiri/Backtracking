package VC;

import java.util.*;

public class Graph {

    //todo traduire noms méthodes en anglais
    //graphe non orienté, indice des sommets peut etre quelconque


    private HashMap<Integer,HashSet<Integer>> adj; //voisins du sommet i :  adj.get(i)
    private int m;

    private HashMap<Integer,Integer> degres; //degres.get(i) est le sommet du degré i (on maintient cette information à jour pour éviter de la recalculer)

    private PriorityQueue<Vertex> vertexOrderedByDegre;

    public Graph(int n){
        this(n,false,0);
    }
    public Graph(int n, boolean random, double p) {
        //construit à graphe à n sommets 0..n-1
        //si random faux, construit graphe vide, sinon, construit graphe avec proba p que chaque arête existe
        vertexOrderedByDegre = new PriorityQueue<>();
        adj = new HashMap<Integer, HashSet<Integer>>();
        degres = new HashMap<>();
        for(int i=0;i<n;i++){
            ajoutSommet(i);
        }
        m=0;


        if(random){
            Random r = new Random();
            for(int i=0;i<n;i++){
                for(int j=i+1;j<n;j++){

                        if(r.nextDouble()<p){
                            ajoutArete(i,j);
                        }


                }
            }
        }
    }

    public Graph(Graph g){
        //deepcopy
        m=g.m;
        adj = new HashMap<>();
        for(Map.Entry<Integer,HashSet<Integer>> e : g.adj.entrySet()){
            HashSet<Integer> voisins = e.getValue();
            HashSet<Integer> copie = new HashSet<>();
            for(int v : voisins)
                copie.add(v);
            adj.put(e.getKey(),copie);
        }
        degres = new HashMap<>();
        for(Map.Entry<Integer,Integer> e : g.degres.entrySet()){
            degres.put(e.getKey(),e.getValue());
        }
        vertexOrderedByDegre = new PriorityQueue<>(g.vertexOrderedByDegre);

    }

    public int n() {
        return adj.size();
    }

    public Set<Integer> sommets(){
        return adj.keySet();
    }
    public int m(){return m;}

    public Integer getDegree(int i){
        //pre : i sommet du graphe
        return degres.get(i);
    }

    public Integer getVertex(){
        //prérequis graphe non vide
        //retourne un sommet arbitraire
        return adj.keySet().iterator().next();
    }


    public Integer getMaxDegVertex(){
        return vertexOrderedByDegre.peek().getI();
    }
    public Edge getArete(){
        //prérequis graphe non vide
        // retourne une arête arbitraire
        for(Map.Entry<Integer,HashSet<Integer>> e : adj.entrySet()){
            if(!e.getValue().isEmpty()){
                for(int v : e.getValue()){
                    return new Edge(e.getKey(),v);
                }
            }
        }
        return null;
    }

    public void changeDegre(int i, int d){
        //sommet i à un nouveau degré d
        //"modifier priorité" n'existe pas dans priorityqueue, donc on enlève et rajoute
        vertexOrderedByDegre.remove(new Vertex(this,i));
        degres.put(i,d);
        vertexOrderedByDegre.add(new Vertex(this, i));
    }
    public void supprimeArete(int i, int j) {
        //prérequis 0 <= i,j < nbSommets()

        adj.get(i).remove(j);
        adj.get(j).remove(i);
        m--;
        changeDegre(i,degres.get(i)-1);
        changeDegre(j,degres.get(j)-1);

    }


    public int chercheDegUn(){
        for(Integer i : degres.keySet()){
            if(degres.get(i)==1){
                return i;
            }
        }
        return -1;
    }

    /*public int chercheDegStrictK(int k){
        for(Map.Entry<Integer,HashSet<Integer>> e : adj.entrySet()){
            if(e.getValue().size()>k){
                return e.getKey();
            }
        }
        return -1;
    }
    */

    public void supprimeSommet(int i){

        HashSet<Integer> voisins = new HashSet<>(adj.get(i));
        if(voisins!=null){
            for(Integer j : voisins){
                supprimeArete(i,j);
            }
            adj.remove(i);
            degres.remove(i);
            vertexOrderedByDegre.remove(new Vertex(this,i));
        }

    }
    public boolean ajoutSommet(int i){
        //si i pas déjà présent, l'ajout et retourne vrai, sinon ne fait rien et retourne faux
        if(adj.containsKey(i))
            return false;
        else{
            adj.put(i,new HashSet<>());
            degres.put(i,0);
            vertexOrderedByDegre.add(new Vertex(this,i));
            return true;
        }
    }

    public Set<Integer> getVoisins(int i){
        //prerequis i present
        return adj.get(i);
    }
    public void ajoutArete(int i, int j) {
        //prérequis
        //i et j existent
        //i != j

        if(!existeArete(i,j)) {
            adj.get(i).add(j);
            adj.get(j).add(i);
            changeDegre(i,degres.get(i)+1);
            changeDegre(j,degres.get(j)+1);
            m++;
        }
    }

    public void ajoutAretes(int i, Set<Integer> X) {
        //prérequis
        //i et tous sommets de X existent
        //i != j

        for(Integer j : X)
            ajoutArete(i,j);
    }

    public boolean existeArete(int i, int j) {
        return adj.containsKey(i) && adj.get(i).contains(j);
    }


    public Set<Integer> getVertexSet(){
        return adj.keySet();
    }

    public ArrayList<Edge> matching(){
        //trouve un matching maximal gloutonnement
        Graph g2 = new Graph(this);
        if(m==0){
            return new ArrayList<>();
        }
        Edge a = getArete();
        g2.supprimeSommet(a.getJ());
        g2.supprimeSommet(a.getI());
        ArrayList<Edge> res = g2.matching();
        res.add(a);
        return res;
    }
    public String toString() {
        return adj.toString()+"\n"+degres.toString()+"\n"+vertexOrderedByDegre;
    }


    public boolean isVertexCover(Set<Integer> X){
        for(Map.Entry<Integer,HashSet<Integer>> e : adj.entrySet()){
            int i = e.getKey();
            if(!X.contains(i)){
                for(Integer j : adj.get(i)){
                    if(!X.contains(j))
                        return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Graph g = new Graph(6,true,0.5);
        System.out.println(g);
        System.out.println(g.getMaxDegVertex()+"\n");
        g.supprimeSommet(g.getMaxDegVertex());

        System.out.println(g);
        System.out.println(g.getMaxDegVertex()+"\n");
        g.supprimeSommet(g.getMaxDegVertex());

        System.out.println(g);
        System.out.println(g.getMaxDegVertex()+"\n");
        g.supprimeSommet(g.getMaxDegVertex());

        System.out.println(g);
        System.out.println(g.getMaxDegVertex()+"\n");
        g.supprimeSommet(g.getMaxDegVertex());
        /*Graph g = new Graph(5);
        System.out.println(g.);
        g.ajoutSommet(7);
        System.out.println(g);
        g.ajoutArete(3,7);
        System.out.println(g);*/


    }

}