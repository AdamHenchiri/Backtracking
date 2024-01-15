package Sudoku;

import java.util.ArrayList;
import java.util.Map;

public class TestSudoku {
    public static void printTest(String msg, boolean b){
        System.out.println("test : "+(b?" ok":" KO") + " (" + msg + ")");
    }


    public static void testReviseC(){
        int[][]g = new int[4][4];
        Map<Integer, ArrayList<Integer>> d = AlgosSudoku.createDomain(g); //tous les domaines à {1,2,3,4}
        d.remove(5);
        PartialSolSudoku.reviseC(d,4,5,3);
        boolean ok1 = !d.get(1).contains(3) && !d.get(9).contains(3) && !d.get(13).contains(3);
        boolean ok2 = true;
        int i = 0;
        while(i< 16 && ok2){
            if((i!=1)&&(i!=5)&&(i!=9)&&(i!=13)){
                ok2 = d.get(i).size()==4;
            }
            i++;
        }
        printTest("testReviseC1",ok1);
        printTest("testReviseC1",ok2);

    }

    public static void testPropagate1(){
        int[][]g = new int[4][4];
        Map<Integer, ArrayList<Integer>> d = AlgosSudoku.createDomain(g); //tous les domaines à {1,2,3,4}
        PartialSolSudoku s = new PartialSolSudoku(4);
        s.propagateConstraints(d,0,2);
        s.add(0,2);
        s.propagateConstraints(d,5,3);
        s.add(5,3);
        printTest("testPropagate1",d.keySet().size()==14 && d.get(1).size()==2 && d.get(4).size()==2 && d.get(2).size()==3 && d.get(10).size()==4);
        System.out.println("test : testPropagate1 : \n \t On considère une solution partielle qui, partant d'un sudoku 4x4 vide, a choisi de placer 2 en (0,0) et 3 en (1,1).");
        System.out.println("\t Vérifiez, en plus du test, si l'affichage suivant des domaines restants est ok ");
        System.out.println("\t" + d);


    }
    public static void test4x4feasable1(){
        PartialSolSudoku s2 = AlgosSudoku.mainBackTrackSudoku(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku4x4feasible.txt"));
        System.out.println(s2);
        printTest("test4x4feasable1", s2!=null);
    }

    public static void test9x9feasable1(){
        PartialSolSudoku s2 = AlgosSudoku.mainBackTrackSudoku(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku9x9feasible.txt"));
        printTest("test9x9feasable1", s2!=null);
    }


    public static void test9x9unfeasable1(){
        PartialSolSudoku s2 = AlgosSudoku.mainBackTrackSudoku(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku9x9unfeasible.txt"));
        printTest("test9x9unfeasable1", s2==null);
    }


    public static void test9x9counting(){
        int n1 = AlgosSudoku.mainBackTrackSudokuCount(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku1.txt"));
        int n2 = AlgosSudoku.mainBackTrackSudokuCount(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku2.txt"));
        int n3 = AlgosSudoku.mainBackTrackSudokuCount(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku3.txt"));
        printTest("test9x9counting", n1==0 && n2==1 && n3 == 9);

    }

    public static void test2525(){
        //permet de distinguer le MRV qui peut résoudre ce 25x25, alors que la version pas MRV n'y arrive pas en un temps raison nable
        PartialSolSudoku s2 = AlgosSudoku.mainBackTrackSudoku(PartialSolSudoku.readFile("backTracking/src/Sudoku/sudoku2525_1.txt"));
        System.out.println(s2);

    }




    public static void main(String[] args) {
//        testReviseC();
//        testPropagate1();
//        test4x4feasable1();
//        test9x9feasable1();
//        test9x9unfeasable1();
//        test9x9counting();
        test2525();
    }
}
