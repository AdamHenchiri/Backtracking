package NQueen;
import Utile.AlgosUtiles;

import java.util.*;

public class AlgosNQueen {
    //Définition sur les solutions partielles : voir PartialSolNQueen

    //Définition sur les domaines :
    //Un domaine D est une Map<Integer,ArrayList<Integer>> tq pour tout i, D.get(i) contient le domaine (les colonnes possibles) de la variable l_i

    //Définitions de "D est non trivial", "solution partielle valide", et "s* étend s : voir cours"


    //compteurs d'appels pour les différentes versions
    public static int c0 = 0;
    public static int c1 = 0;
    public static int c2 = 0;
    public static int c3 = 0;




    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static PartialSolNQueen backTrackQueenV0(PartialSolNQueen s){
        //v0 : equivalent à tester tous les tuples
        //prérequis : s solution partielle (pas forcément valide)
        //action : si une solution s* qui étend s elle existe alors en retourne une (indépendante de s : n'oubliez pas d'utiliser le contructeur par recopie)
        // sinon retourne null et ne modifie pas s

        c0++;
        if (s.isFullSolution()){
            return s.isValid()? s : null;
        }
        int i=s.getUnAffectedVar();
        for (int c=0; c<s.getN(); c++){
            PartialSolNQueen var_s = new PartialSolNQueen(s);
            var_s.add(i,c);
            PartialSolNQueen res = backTrackQueenV0(var_s);
            if (res != null){
                return res;
            }
            var_s.remove(i);
        }
        return null;
    }



    public static PartialSolNQueen mainBackTrackQueenVO(int n) {
        //n >= 1
        //retourne une solution des n-queens en utilisant backTrackQueenVO
        PartialSolNQueen s = new PartialSolNQueen(n);
        return backTrackQueenV0(s);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static PartialSolNQueen backTrackQueenV1(PartialSolNQueen s){
        //prérequis : s solution partielle valide
        //action : si une solution s* qui étend s existe alors en retourne une (indépendante de s),
        // sinon retourne null
        // ne modifie pas s

        //stratégie : ne teste que les valeurs qui ne violent pas de contrainte (qui redonnent donc une solution partielle valide)

        //ici, on sait en plus que s contient (0,c0), (1,c1), etc, car on a affecté dans l'ordre

        c1++;
        if (s.isFullSolution()){
            return s;
        }
        int i=s.getUnAffectedVar();
        for (int c=0; c<s.getN(); c++){
            if (s.checkNewVal(i,c)) {
                PartialSolNQueen s2 = new PartialSolNQueen(s);
                s2.add(i, c);
                PartialSolNQueen res = backTrackQueenV1(s2);
                if (res != null) {
                    return res;
                }
                s2.remove(i);
            }
        }
        return null;

    }


    public static PartialSolNQueen mainBackTrackQueenV1(int n){
        //n >= 1
        //retourne une solution des n-queens en utilisant backTrackQueenV1
        PartialSolNQueen s = new PartialSolNQueen(n);
        return backTrackQueenV1(s);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static PartialSolNQueen backTrackQueenV2(PartialSolNQueen s, Map<Integer,ArrayList<Integer>> D){

        //prérequis : (s,D) est FCC et non trivial
        //action : si il existe une solution s* qui étend s et qui respecte D, en retourne une (indépendante de s)
        //sinon retourne null
        //
        // ne modifie pas s

        //stratégie : branche dans l'ordre des lignes 0, 1, .., et pour chaque nouveau l_i, teste toutes les valeurs prévues dans D, et fait du fwd checking
        //pour calculer un nouveau domaine qui restera bien FCC

        c2++;
        if (s.isFullSolution()){
            return s ;
        }
        int i=s.getUnAffectedVar();
        ArrayList<Integer> D_i = D.get(i);
        for (int c : D_i){
            if (s.checkNewVal(i,c)) {
                PartialSolNQueen s2 = new PartialSolNQueen(s);
                s2.add(i, c);
                Map<Integer,ArrayList<Integer>> D2 = new HashMap<>();
                for (int j : D.keySet()){
                    if (j>i){
                        ArrayList<Integer> D_j = new ArrayList<>();
                        for (int c2 : D.get(j)){
                            if (s2.checkNewVal(j,c2)){
                                D_j.add(c2);
                            }
                        }
                        D2.put(j,D_j);
                    }
                }
                PartialSolNQueen res = backTrackQueenV2(s2,D2);
                if (res != null) {
                    return res;
                }
                s2.remove(i);
            }
        }
        return null;
    }

    private static Map<Integer,ArrayList<Integer>> prepareDomain(int n){
        //retourne un domaine contenant pour tout i dans [0,n-1] l'arraylist [0,..,n-1]
        Map<Integer,ArrayList<Integer>> D = new HashMap<>();
        for(int i=0;i<n;i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int x = 0; x < n; x++) {
                list.add(x);
            }
            D.put(i,list);
        }
        return D;
    }

    public static PartialSolNQueen mainBackTrackQueenV2(int n) {
        //n >= 1
        //retourne une solution des n-queens en utilisant backTrackQueenV2
        PartialSolNQueen s = new PartialSolNQueen(n);
        Map<Integer,ArrayList<Integer>> D = prepareDomain(n);
        return backTrackQueenV2(s,D);

    }




    public static PartialSolNQueen backTrackQueenV3(PartialSolNQueen s, Map<Integer,ArrayList<Integer>> D){
        //prérequis : (s,D) est FCC et non trivial
        //action : si il existe une solution s* qui étend s et qui respecte D, en retourne une (indépendante de s)
        //sinon retourne null
        //
        // ne modifie pas s

        //stratégie : fait du FC + MRV pour choisir la prochaine variable

        c3++;
        if (s.isFullSolution()){
            return s ;
        }
        int i=AlgosUtiles.getUnaffectedVariableMRV(D);
        ArrayList<Integer> D_i = D.get(i);
        for (int c : D_i){
            if (s.checkNewVal(i,c)) {
                PartialSolNQueen s2 = new PartialSolNQueen(s);
                s2.add(i, c);
                Map<Integer,ArrayList<Integer>> D2 = new HashMap<>();
                for (int j : D.keySet()){
                    if (j>i){
                        ArrayList<Integer> D_j = new ArrayList<>();
                        for (int c2 : D.get(j)){
                            if (s2.checkNewVal(j,c2)){
                                D_j.add(c2);
                            }
                        }
                        D2.put(j,D_j);
                    }
                }
                PartialSolNQueen res = backTrackQueenV2(s2,D2);
                if (res != null) {
                    return res;
                }
                s2.remove(i);
            }
        }
        return null;
    }


    public static PartialSolNQueen mainBackTrackQueenV3(int n) {
        //n >= 1
        //retourne une solution des n-queens en utilisant backTrackQueenV3
        PartialSolNQueen s = new PartialSolNQueen(n);
        Map<Integer,ArrayList<Integer>> D = prepareDomain(n);
        return backTrackQueenV3(s,D);
    }

    public static void main(String[] args) {
        //exemple d'utilisation d'une Map
        //vous devriez pouvoir faire le TP avec les méthodes ci-dessous, mais en cas de besoin voir la javadoc.
        //une Map<K,V> permet d'enregistrer des associations (clef, valeur) avec clef de type K et valeur de type V.
        //Les Map vont nous servir pour stocker les domaines, et les solutions partielles.

        Map<Integer,String> m = new HashMap<>(); //Map est l'interface, on ne fait donc pas new Map<>(), mais on utilise
        //la classe HashMap qui implémente l'interface Map.

        //m.put(k,v) permet ajouter l'association (k,v) à m
        m.put(10,"bach"); //j'associe 10 -> "bach"
        m.put(22,"chopin"); //j'associe 22 -> "chopin"
        m.put(22,"ravel"); //j'associe 22 -> "ravel" (une clef ne peut être associée qu'à une valeur)
        m.put(30,"beethoven");

        if(m.containsKey(12))
            System.out.println("la map contient un objet de clef 12");
        if(m.containsKey(30))
            System.out.println("la map contient un objet de clef 30");

        //m.get(k) retourne null si !m.contains(k), et la valeur correspondante sinon
        String r = m.get(22);; // b = "ravel"
        String n = m.get(12);; // n = null

        m.remove(22);  //on enlève la clef 22, et donc l'association 22->"ravel"

        //parcours de map, V1
        for(Integer i : m.keySet()) { //i va valoir 10, 30 (ordre pas garanti)
            System.out.println("clef : " + i + " valeur : " + m.get(i));
        }
        for(Map.Entry<Integer,String> e : m.entrySet()){
            System.out.println("clef : " + e.getKey() + " valeur : " + e.getValue());
        }




        //////////////////////////////////////////////
        //pacours d'une arrayList avec modifications
        ///////////////////////////////////////////////
        ArrayList<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        //attention ne jamais écrire (car il ne faut pas modifier une arraylist pendant un parcours ..
        /*for(Integer x : l){
            if(x%2==0)
                l.remove(x);
        }*/
        //utiliser un itérateur pour cela
        Iterator<Integer> it = l.iterator();
        while(it.hasNext()){
            Integer x = it.next();
            if(x%2==0)
                it.remove();
        }
        System.out.println(l);

    }


}
