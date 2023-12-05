package Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PartialSolSudoku {
    //définitions pour sudoku:
    //une PartialSolSudoku est une solution partielle codée ainsi
    private int n; //sudokup dimension n x n, avec racine(n) entier
    private int[][] grille; //avec valeur entre 0 et n, 0 signifiant pas rempli
    //grille[i][j] dénote ligne i colonne j, avec lignes numérotées de haut en bas, colonnes de gauche à droite

    //pour coller à l'algorithme général de backtrack qui essaye toute valeur (int) v pour sa prochaine variable (case) x,
    //il nous faut une correspondance "coordonnées <-> int" :
    //on numérote les variables ligne par ligne de gauche à droite :
    //variable x_0 signifie case (0,0) en haut à gauche, x_1 case à droite de celle de x_0, etc ..


    //une solution partielle est *valide* si il n'y a aucune répétition dans une ligne, colonne, ou sous carré (même partiellement remplis)


    //attributs pour faciliter propagation et éviter de tout recalculer à chaque fois
    private int nVal; //nb valeurs non nulles dans la grille

    public PartialSolSudoku(int n){
        this.n=n;
        grille = new int[n][n];
        nVal = 0;
    }



    public PartialSolSudoku(int[][]g){
        //utiliser add (qui met à jour d'autres infos)
        this(g.length);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(g[i][j]!=0){
                    add(coordToInt(g.length,i,j), g[i][j]);
                }
            }
        }
    }

    public PartialSolSudoku(PartialSolSudoku s){
        this(s.grille);
        this.nVal=s.nVal;
    }

    public static int[][] readFile(String fileName){
        //fichier input à placer dans le dossier au dessus de src (et donc pas dans src!)
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        try {
            File f = new File(fileName);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                res.add(new ArrayList<Integer>());
                String line = myReader.nextLine();
                Scanner sc = new Scanner(line);
                while(sc.hasNext()){
                    String digit = sc.next();
                    res.get(res.size()-1).add(Integer.parseInt(digit));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int [][]resTab = new int[res.size()][res.size()];
        for(int i=0;i<res.size();i++){
            for(int j=0;j<res.size();j++){
                resTab[i][j]= res.get(i).get(j);
            }
        }
        return resTab;
    }


    public int getN(){return n;}
    public static int coordToInt(int n, int i, int j){
        //pré : (i,j) indique un numéro de case dans grille
        return i*n+j;
    }

    public static int intToLine(int n, int c){
        return c/n;
    }
    public static int intToCol(int n, int c){
        return c%n;
    }

    public static ArrayList<Integer> intToSS(int n, int numCase){
        //pré 0 <= c < n^2-1
        //retourne (i,j) tq le sous-carré (i,j) contienne la  case num numCase

        ArrayList<Integer> res = new ArrayList<>();
        int sizeSS = (int)Math.sqrt(n);
        res.add(intToLine(n, numCase)/sizeSS);
        res.add(intToCol(n, numCase)/sizeSS);
        return res;
    }


    public void add(int numCase, int v){
        //prérequis 0 <= numCase < n*n , 1 <= v <= n, et case numCase vide
        //action : ajoute v dans case numCase de grille (et met à jour nVal)
        throw new RuntimeException("methode non implémentée");

    }

    public void remove(int numCase){
        //prérequis : 0 <= numCase < n*n, case numCase pleine
        //action : remet à 0 la case numCase de grille (et met  jour nVal)
        throw new RuntimeException("methode non implémentée");

    }

    public boolean isFullSolution(){

        throw new RuntimeException("methode non implémentée");

    }








    public static boolean reviseL(Map<Integer,ArrayList<Integer>> D, int n, int numCase, int val){
        //prerequis :
        //  0<= numcase <n*n, et la case numCase est vide
        //  !D.contains(numCase)

        //soit l la ligne de numcase
        //action :
        // pour toutes les variables de D dont la case est sur la ligne l, retire de leur domaine la valeur val :
        //si un domaine est devenu vide on peut s'arrêter et retourner false (et rien de garanti sur domaines)
        //sinon retourne true et enlève bien la valeur de tous les domaines


        int line = intToLine(n, numCase);
        for(int colonne = 0; colonne < n; colonne++){
            int nc = coordToInt(n, line, colonne);
            if(D.containsKey(nc)) {
                D.get(nc).remove((Integer) val);
                if (D.get(nc).isEmpty())
                    return false;
            }
        }
        return true;
    }

    public static boolean reviseC(Map<Integer,ArrayList<Integer>> D, int  n, int numcase, int val){
        //prerequis :
        //  0<= numcase <n*n, et la case numCase est vide
        //  !D.contains(numCase)
        //
        //soit c la colonne de numcase
        //action :
        // pour toutes les variables de D dont la case est sur la colonne c, retire de leur domaine la valeur val :
        //si un domaine est devenu vide on peut s'arrêter et retourner false (et rien de garanti sur domaines)
        //sinon retourne true et enlève bien la valeur de tous les domaines

        throw new RuntimeException("methode non implémentée");

    }


    public static boolean reviseSS(Map<Integer,ArrayList<Integer>> D, int n, int numcase, int val){
        //prerequis :
        //  0<= numcase <n*n, et la case numCase est vide
        //  !D.contains(numCase)
        //
        //soit sc les n cases "du sous carré" de numcase
        //action :
        // pour toutes les variables de D dont la case est dans sc, retire de leur domaine la valeur val :
        //si un domaine est devenu vide on peut s'arrêter et retourner false (et rien de garanti sur domaines)
        //sinon retourne true et enlève bien la valeur de tous les domaines


        int sizeSS = (int)Math.sqrt(n);
        ArrayList<Integer> coord = intToSS(n,numcase);
        //coord du coin haut gauche du sous carré de numcase
        int l_c0 = coord.get(0)*sizeSS;
        int c_c0 = coord.get(1)*sizeSS;
        for(int i = 0;i < sizeSS;i++){
            for(int j = 0;j < sizeSS;j++){
                int nc = coordToInt(n,l_c0+i,c_c0+j);
                if(D.containsKey(nc)) {
                    D.get(nc).remove((Integer) val);
                    if (D.get(nc).isEmpty())
                        return false;
                }
            }
        }
        return true;
    }

    public boolean propagateConstraints(Map<Integer,ArrayList<Integer>> D, int numCase, int val) {
        // On souhaite ajouter (numcase,val) à this, et on veut faire du fwd checking.


        //prérequis : this est valide, case numCase pas remplie  0 <= val < n
        //action :
        //  -enlève numCase de D (car D ne doit contenir que les domaines des variables restantes)
        //  -fait du fwd checking pour enlever de D les valeurs devenant illégales si l'on ajoutait (numCase,val) à this
        //  -si aucun domaine ne devient vide, retourne true
        //  -sinon, sinon retourne faux (et aucune garantie à fournir sur D)
        //  -ne modifie pas this

        throw new RuntimeException("methode non implémentée");

    }


    public String toString(){
        String res = "";
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                res+=grille[i][j]+"\t";
            }
            res+="\n";
        }
        return res;
    }


}
