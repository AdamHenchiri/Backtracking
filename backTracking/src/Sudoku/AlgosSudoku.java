package Sudoku;

import Utile.AlgosUtiles;

import java.util.*;

public class AlgosSudoku {

    //Définition sur les solutions partielles : voir PartialSolSudoku

    //Définition sur les domaines :
    //Un domaine D est une Map<Integer,ArrayList<Integer>> tq pour tout numero de case numCase (avec 0 <= numCase < n*n),
    // D.get(numCase) contient le domaine (les valeurs possibles) de cette case

    //Définitions de "D est non trivial", "solution partielle valide", et "s* étend s : voir cours"


    public static PartialSolSudoku backTrackSudoku(PartialSolSudoku s, Map<Integer,ArrayList<Integer>> D){
        //prérequis : (s,D) est FCC et non trivial
        //action : si il existe une solution s* qui étend s et qui respecte D, en retourne une (indépendante de s)
        //sinon retourne null
        //utilise MRV pour le choix de la prochaine variable
        //
        // ne modifie pas s


        if (s.isFullSolution()) {
            return new PartialSolSudoku(s);
        }
        int numcase = AlgosUtiles.getUnaffectedVariableMRV(D);

        for (Integer val : D.get(numcase)) {
            Map<Integer, ArrayList<Integer>> Dmodif = AlgosUtiles.<Integer>deepCopyMap(D);
            boolean ok = s.propagateConstraints(Dmodif, numcase, val);
            if (ok) {
                s.add(numcase, val);
                PartialSolSudoku res = backTrackSudoku(s, Dmodif);
                s.remove(numcase);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;

    }

    public static Map<Integer,ArrayList<Integer>> createDomain(int[][]g){
        //prerequis : g de taille n tq sqrt(n) entier
        //retourne un domaine tq, pour les cases affectées, le domaine correspondant est de taille 1 et ne contient que la valeur,
        //pour les autres cases, le domaine vaut [1..n]
        Map<Integer,ArrayList<Integer>> res = new HashMap<>();
        int n = g.length;
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                int numCase = PartialSolSudoku.coordToInt(n,i,j);
                res.put(numCase,new ArrayList<>());
                if(g[i][j]!=0)
                    res.get(numCase).add(g[i][j]);
                else{
                    for(int v = 1;v <= n;v++){
                        res.get(numCase).add(v);
                    }
                }
            }
        }
        return res;


    }

    public static Queue<Integer> createQueue(Map<Integer, ArrayList<Integer>> D){
        //create a priority queue to sort the domain by size (MRV)
        Queue<Integer> q = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return D.get(o1).size() - D.get(o2).size();
            }
        });
        for (Map.Entry<Integer, ArrayList<Integer>> e : D.entrySet()) {
            q.add(e.getKey());
        }
        return q;
    }

    public static PartialSolSudoku mainBackTrackSudoku(int[][]g){
        //retourne une sol de s si existe, null sinon

        return backTrackSudoku(new PartialSolSudoku(g.length), createDomain(g));
    }

    public static int backTrackSudokuCount(PartialSolSudoku s, Map<Integer,ArrayList<Integer>> D, Queue<Integer> q){

        //prérequis : (s,D) est FCC et non trivial
        //action : retourne le nombre de solutions s* qui étendent s et qui respectent D
        //utilise MRV pour le choix de la prochaine variable
        //
        // ne modifie pas s

        if (s.isFullSolution()) {
            return 1;
        }
        int numcase = q.poll();
        int res = 0;
        for (Integer val : D.get(numcase)) {
            Map<Integer, ArrayList<Integer>> Dmodif = AlgosUtiles.<Integer>deepCopyMap(D);
            boolean ok = s.propagateConstraints(Dmodif, numcase, val);
            if (ok) {
                s.add(numcase, val);
                res += backTrackSudokuCount(s, Dmodif, q);
                s.remove(numcase);
            }
        }
        return res;

    }

    public static int mainBackTrackSudokuCount(int [][]g) {
        Map<Integer, ArrayList<Integer>> D = createDomain(g);
        Queue<Integer> q = createQueue(D);
        return backTrackSudokuCount(new PartialSolSudoku(g.length), D, q);
    }

}
