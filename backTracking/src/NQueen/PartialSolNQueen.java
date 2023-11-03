package NQueen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PartialSolNQueen {

    //définitions pour n queens:
    //une PartialSolNqueen est une solution partielle : un ensemble {(l_i,c_i)} avec l_i et c_i dans {0,n-1} signifiant que reine ligne l_i affectée en colonne c_i
    //la ligne 0 est celle du haut, et la colonne 0 est celle de gauche.

    //une solution partielle est *valide* si pour tout couple (l_1,c_1), (l_2,c_2) dans s, reine en (l_1,c_1) ne peut pas prendre celle en (l_2,c_2)
    //s est une *solution* si c'est une solution partielle valide de taille n

    int n;
    private Map<Integer, Integer> m; //stocke les couples de this (m.get(l)==c ssi (l,c) dans this)
    public PartialSolNQueen(int n){
        this.n=n;
        m = new HashMap<>();
    }

    public PartialSolNQueen(PartialSolNQueen s){
        n = s.n;
        m = new HashMap<>();
        for(Map.Entry<Integer,Integer> e : s.m.entrySet()){
            m.put(e.getKey(),e.getValue());
        }
    }
    public void add(int l, int c){
        //prerequis couple = (l,c) entiers  dans [0,n-1] et !m.containsKey(l)
        //action : ajoute le couple (l,c)
        m.put(l,c);
    }
    public void remove(int l){
        //aucun prérequis
        //action : retire le couple (l,*) si il existe (sinon ne fait rien)
        m.remove(l);
    }
    public int getN(){
        return n;
    }

    public String toString(){
        return m.toString();
    }

    public boolean isFullSolution(){
        return getN()==m.size();
    }

    public int getUnAffectedVar(){
        //prerequis !s.isFullSolution()
        //action : retourne un indice de variable non affectée (ici une ligne sans reine)
        throw new RuntimeException("methode non implémentée");

    }



    public static boolean constraintOk(int l1, int l2, int c1, int c2){
        //prérequis li et ci >= 0
        //retourne vrai ssi reine en (l_1,c_1) ne peut pas prendre celle en (l_2,c_2) (et qu'elles ne sont pas sur la même case)
        throw new RuntimeException("methode non implémentée");

    }
    public boolean checkNewVal(int l0, int c0){
        //retourne vrai ssi une nouvelle reine placée en position "(l0,c0)" ne pourrait manger aucune reine déjà dans this
        throw new RuntimeException("methode non implémentée");

    }

    public boolean isValid(){
        //retourne vrai ssi this est valide
        for(Integer l : m.keySet()){
            for(Integer l2 : m.keySet()){
                if((l!=l2) && !constraintOk(l,l2,m.get(l),m.get(l2)))
                    return false;
            }
        }
        return true;
    }


    public boolean propagateConstraints(Map<Integer,ArrayList<Integer>> D, int l0, int c0) {
        // On souhaite ajouter (l0,c0) à this, et on veut faire du fwd checking.

        //prérequis : (this,D) est FCC, l0 pas dans this (!m.containsKey(l0)), 0 <= c0 < n
        //action :
        //  -enlève l0 de D (car D ne doit contenir que les domaines des variables restantes)
        //  -fait du fwd checking pour enlever de D les valeurs devenant illégales si l'on ajoutait (l0,c0) à this
        //  -si aucun domaine ne devient vide, retourne true
        //  -sinon, sinon retourne faux (et aucune garantie à fournir sur D)
        //  -ne modifie pas this
        throw new RuntimeException("methode non implémentée");

    }

}
