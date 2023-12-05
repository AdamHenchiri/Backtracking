package VC;

import Utile.AlgosUtiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgosVC {

    public static PartialSolVC backTrackVCV0(PartialSolVC s, Map<Integer,ArrayList<Boolean>> D){
        //prérequis : (s,D) est FCC+ et non trivial
        //action : si il existe une solution s* qui étend s et qui respecte D, en retourne une (indépendante de s)
        //sinon retourne null
        //utilise MRV pour le choix de la prochaine variable
        //
        // ne modifie pas s

        throw new RuntimeException("methode non implementee");
    }

    public static PartialSolVC mainBackTrackVCV0(Graph g, int k){
        return backTrackVCV0(new PartialSolVC(g,k),createDomain(g.n()));
    }


    public static PartialSolVC backTrackVCV1(PartialSolVC s, Map<Integer,ArrayList<Boolean>> D){
        //utilise MRV puis highestdegree
        throw new RuntimeException("methode non implementee");
    }

    public static Map<Integer,ArrayList<Boolean>> createDomain(int n){
        Map<Integer,ArrayList<Boolean>> D = new HashMap<>();
        for(int i=0;i<n;i++){
            D.put(i,new ArrayList<>());
            D.get(i).add(true);
            D.get(i).add(false);
        }
        return D;
    }
    public static PartialSolVC mainBackTrackVCV1(Graph g, int k){
        return backTrackVCV1(new PartialSolVC(g,k),createDomain(g.n()));
    }


}
