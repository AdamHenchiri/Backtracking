package Sudoku;

import Utile.AlgosUtiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


        throw new RuntimeException("methode non implémentée");

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



    public static PartialSolSudoku mainBackTrackSudoku(int[][]g){
        //retourne une sol de s si existe, null sinon

        return backTrackSudoku(new PartialSolSudoku(g.length), createDomain(g));
    }

    public static int backTrackSudokuCount(PartialSolSudoku s, Map<Integer,ArrayList<Integer>> D){

        //prérequis : (s,D) est FCC et non trivial
        //action : retourne le nombre de solutions s* qui étendent s et qui respectent D
        //utilise MRV pour le choix de la prochaine variable
        //
        // ne modifie pas s

        throw new RuntimeException("methode non implémentée");

    }

    public static int mainBackTrackSudokuCount(int [][]g) {

        return backTrackSudokuCount(new PartialSolSudoku(g.length), createDomain(g));
    }

}
