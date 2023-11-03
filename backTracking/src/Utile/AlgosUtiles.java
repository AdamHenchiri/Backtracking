package Utile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgosUtiles {
    public static <T> boolean pasEnsembleVide(ArrayList<ArrayList<T>> D){
        boolean ok = true;
        int i = 0;
        while(ok && i < D.size()){
            ok = !D.get(i).isEmpty();
            i++;
        }
        return ok;
    }

    public static <T> ArrayList<ArrayList<T>> deepCopy(ArrayList<ArrayList<T>> D){
        ArrayList<ArrayList<T>> res = new ArrayList<>();
        for(ArrayList<T> liste : D){
            res.add(new ArrayList<T>());
            res.get(res.size()-1).addAll(liste);
        }
        return res;
    }

    public static <T> Map<Integer,ArrayList<T>> deepCopyMap(Map<Integer,ArrayList<T>> D){
        Map<Integer,ArrayList<T>> res = new HashMap<>();
        for(Map.Entry<Integer,ArrayList<T>> e : D.entrySet()){
            res.put(e.getKey(),new ArrayList<T>());
            res.get(e.getKey()).addAll(D.get(e.getKey()));
        }
        return res;
    }

    public static <T> int getUnaffectedVariableMRV(Map<Integer,ArrayList<T>> D){
        //prérequis : !D.isEmpty()
        //retourne une variable de taille de domaine minimal
        int nc = -1;
        int tailleMin = Integer.MAX_VALUE;
        for(Map.Entry<Integer,ArrayList<T>> e : D.entrySet()){
            int taille = e.getValue().size();
            if(taille<tailleMin){
                tailleMin = taille;
                nc = e.getKey();
            }
        }
        return nc;
    }

}
