package Utile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgosUtiles {

    public static <T> Map<Integer,ArrayList<T>> deepCopyMap(Map<Integer,ArrayList<T>> D){
        Map<Integer,ArrayList<T>> res = new HashMap<>();
        for(Map.Entry<Integer,ArrayList<T>> e : D.entrySet()){
            res.put(e.getKey(),new ArrayList<T>());
            res.get(e.getKey()).addAll(D.get(e.getKey()));
        }
        return res;
    }

    public static <T> int getUnaffectedVariableMRV(Map<Integer,ArrayList<T>> D) {
        //prérequis : !D.isEmpty()
        //retourne une variable de taille de domaine minimal
        throw new RuntimeException("methode non implémentée");

    }
}
