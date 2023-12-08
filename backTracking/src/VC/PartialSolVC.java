package VC;

import Utile.AlgosUtiles;

import java.util.*;

public class PartialSolVC {
    private Graph g; //graphe initial (pas modifié)
    int k;
    private HashMap<Integer,Boolean> s;
    //contient les décisions pour les sommets déjà traités
    //s.keySet() contient l'ensemble des sommets pour lesquels on a fait un choix,
    //et pour tout i dans s.ketSet(), s.get(i) vrai si la solution a pris sommet i, faux sinon

    //attributs ci-dessous redondants mais sont maintenus à jour pour accélerer les calculs
    private HashSet<Integer> sIn; // = {i dans s.keySet()/ s.get(i) est vrai}
    private Graph gs; //représente "le graphe restant", c'est à dire  g - s.getKey()
    //ainsi, les sommets de gs sont les variables pas encore affectées par s

    public PartialSolVC(Graph g, int k){
        //prérequis k > 0
        if(k==0)
            throw new RuntimeException("k==0 interdit");
        this.k=k;
        this.g=g;
        gs = new Graph(g);
        s = new HashMap<Integer, Boolean>();
        sIn = new HashSet<>();
    }

    public PartialSolVC(PartialSolVC sol){
        this(sol.g,sol.k);
        for(Map.Entry<Integer,Boolean> e : sol.s.entrySet())
            s.put(e.getKey(),e.getValue());
        for(Integer i : sol.sIn)
            sIn.add(i);
        gs = new Graph(sol.gs);

    }

    public boolean isFullSolution(){

        throw new RuntimeException("methode non implementee");
    }
    public int nbChosenVertices(){
        return sIn.size();
    }

    public void add(int i, boolean v){
        //prérequis : i sommet de g pas dans s.keySet()
        //action : ajoute i à la solution avec valeur b met à jour s, gs et sIn
      throw new RuntimeException("methode non implementee");
    }

    public void remove(int i){
        //prérequis : i sommet de g  et s.containsKey(i)
        //action : supprime i à la solution et met à jour s, gs et sIn
        //attention, pour gs il ne faut pas rajouter tous les voisins de i dans g, mais seulement ceux dans gs
        throw new RuntimeException("methode non implementee");
    }


    public int getUnaffectedVariableMRVHighestDeg(Map<Integer,ArrayList<Boolean>> D){
        //pre : !s.isFullSolution()
        //action : retourne variable pas affectée par s, de taille de domaine 1 si cela existe, et sinon de degré maximum dans gs

        int i = AlgosUtiles.getUnaffectedVariableMRV(D);
        if(D.get(i).size()==1) //les domaines sont de taille 2, donc si 1 de taille 1 on est sûr de le choisir
                return i;

        return gs.getMaxDegVertex();
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
        throw new RuntimeException("methode non implementee");

  }

    public Graph getGs(){
        return gs;
    }

    public String toString(){
        return s.toString();
    }

    /////////// pour les tests
    public boolean isValid(){
        //retourne vrai ssi sIn est bien un VC cover de g de taille <= k
        return sIn.size()<=k && g.isVertexCover(sIn);
    }
}
