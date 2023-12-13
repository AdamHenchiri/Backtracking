package VC;

import Utile.AlgosUtiles;

import java.util.*;

public class PartialSolVC {
    private Graph grapheInitial; //graphe initial (pas modifié)
    int k;
    private HashMap<Integer,Boolean> solution;
    //contient les décisions pour les sommets déjà traités
    //s.keySet() contient l'ensemble des sommets pour lesquels on a fait un choix,
    //et pour tout i dans s.ketSet(), s.get(i) vrai si la solution a pris sommet i, faux sinon

    //attributs ci-dessous redondants mais sont maintenus à jour pour accélerer les calculs
    private HashSet<Integer> sommetAjouté; // = {i dans s.keySet()/ s.get(i) est vrai}
    private Graph grapheRestant; //représente "le graphe restant", c'est à dire  g - s.getKey()
    //ainsi, les sommets de gs sont les variables pas encore affectées par s

    public PartialSolVC(Graph grapheInitial, int k){
        //prérequis k > 0
        if(k==0)
            throw new RuntimeException("k==0 interdit");
        this.k=k;
        this.grapheInitial = grapheInitial;
        grapheRestant = new Graph(grapheInitial);
        solution = new HashMap<Integer, Boolean>();
        sommetAjouté = new HashSet<>();
    }

    public PartialSolVC(PartialSolVC sol){
        this(sol.grapheInitial,sol.k);
        for(Map.Entry<Integer,Boolean> e : sol.solution.entrySet())
            solution.put(e.getKey(),e.getValue());
        for(Integer i : sol.sommetAjouté)
            sommetAjouté.add(i);
        grapheRestant = new Graph(sol.grapheRestant);

    }

    public boolean isFullSolution(){
        return solution.size()==grapheInitial.n();
    }
    public int nbChosenVertices(){
        return sommetAjouté.size();
    }

    public void add(int i, boolean v){
        //prérequis : i sommet de g pas dans s.keySet()
        //action : ajoute i à la solution avec valeur b met à jour s, gs et sIn
        solution.put(i,v);
        if (v)
            sommetAjouté.add(i);
        grapheRestant.supprimeSommet(i);
    }

    public void remove(int i){
        //prérequis : i sommet de g  et s.containsKey(i)
        //action : supprime i à la solution et met à jour s, gs et sIn
        //attention, pour gs il ne faut pas rajouter d'artes vers tous les voisins de i dans g, mais seulement ceux dans gs

       grapheRestant.ajoutSommet(i);
       HashSet<Integer> voisinsDuSommetSupprimeDansLeGrapheRestant = new HashSet<>();
       for(Integer j : grapheInitial.getVoisins(i))
           if(grapheRestant.getVertexSet().contains(j))
               voisinsDuSommetSupprimeDansLeGrapheRestant.add(j);
       grapheRestant.ajoutAretes(i,voisinsDuSommetSupprimeDansLeGrapheRestant);
       sommetAjouté.remove(i);
       solution.remove(i);

    }


    public int getUnaffectedVariableMRVHighestDeg(Map<Integer,ArrayList<Boolean>> D){
        //pre : !s.isFullSolution()
        //action : retourne variable pas affectée par s, de taille de domaine 1 si cela existe, et sinon de degré maximum dans gs

        int i = AlgosUtiles.getUnaffectedVariableMRV(D);
        if(D.get(i).size()==1) //les domaines sont de taille 2, donc si 1 de taille 1 on est sûr de le choisir
                return i;

        return grapheRestant.getMaxDegVertex();
    }

    public boolean propageContraintes(Map<Integer,ArrayList<Boolean>> D, int i, Boolean val) {
        // idée : On souhaite ajouter (i,val) à this pour obtenir une nouvelle solution s'.
        // et on veut garantir que la nouvelle solution s' et son nouveau domaine D' sont FCC+
        //(cf cours : cela signifie que s' contient au plus k sommets,
        // et que pour toutes les arêtes {u,v} avec u déjà décidé (et pas pris), et v pas encore décide, alors dom(v)={1} (on force à prendre v)

        //spécification formelle :
        //prérequis : this est valide, i n'est pas dans s.getKeySet()
        //action :
        //  -enlève i de D (car D ne doit contenir que les domaines des variables restantes)
        //  -si (val==true) (signifie qu'on veut prendre le sommet i), retourne vrai si s' contient bien encore au plus k sommets (et rien à faire sur les domaines)
        //  -sinon
        //     fait du fwd checking pour enlever de D les valeurs devenant illégales si l'on ajoutait (i,val) à this
        //     return true (aucun domaine ne peut devenir vide)

        //  -ne modifie pas this
        D.remove(i);
        if (val){
            return nbChosenVertices()< k;
        }
        else{
            Set<Integer> voisins = grapheRestant.getVoisins(i);
            for(Integer j : voisins) {
                if (D.containsKey(j)) {
                    //pour supprimer le false du domaine de j (car on doit prendre j)
                    D.get(j).remove(false);
                }
            }
            return true;
        }
  }

    public Graph getGrapheRestant(){
        return grapheRestant;
    }

    public String toString(){
        return solution.toString();
    }

    /////////// pour les tests
    public boolean isValid(){
        //retourne vrai ssi sIn est bien un VC cover de g de taille <= k
        return sommetAjouté.size()<=k && grapheInitial.isVertexCover(sommetAjouté);
    }
}
