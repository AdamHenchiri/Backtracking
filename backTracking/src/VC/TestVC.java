package VC;

import java.util.ArrayList;
import java.util.Map;

public class TestVC {
    public static void printTest(String msg, boolean b){
        System.out.println("test : "+(b?" ok":" KO") + " (" + msg + ")");
    }

    public static void testPropagate_1(){
        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        Map<Integer, ArrayList<Boolean>> d = AlgosVC.createDomain(3);
        PartialSolVC s = new PartialSolVC(g,3);
        s.propageContraintes(d,1,false);
        printTest("testPropagate_1",d.get(0).size()==1 && d.get(2).size()==1 && d.get(0).get(0) && d.get(0).get(0) && !d.containsKey(1));

    }

    public static void testPropagate_2(){
        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        Map<Integer, ArrayList<Boolean>> d = AlgosVC.createDomain(3);
        PartialSolVC s = new PartialSolVC(g,3);
        s.propageContraintes(d,1,true);
        printTest("testPropagate_2",d.get(0).size()==2 && d.get(2).size()==2 && !d.containsKey(1));

    }


    public static void testPropagate_3(){
        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(0,2);


        Map<Integer, ArrayList<Boolean>> d = AlgosVC.createDomain(3);
        PartialSolVC s = new PartialSolVC(g,1);
        boolean b1 = s.propageContraintes(d,0,true);
        s.add(0,true);
        boolean b2 = s.propageContraintes(d,1,true);
        printTest("testPropagate_3",b1 && !b2);

    }


    public static void testPetitG_YES_V0(){

        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);


        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,1);
        printTest("testPetitG_YES_V0", s2!=null && s2.isValid());
    }


    public static void testPetitG2_YES_V0(){

        Graph g = new Graph(4);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(2,3);


        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,2);
        printTest("testPetitG2_YES_V0", s2!=null && s2.isValid());
    }

    public static void testPetitG2_YES_V1(){

        Graph g = new Graph(4);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(2,3);

        PartialSolVC s2 = AlgosVC.mainBackTrackVCV1(g,2);
        printTest("testPetitG2_YES_V1", s2!=null && s2.isValid());
    }

    public static void testPetitG3_NO_V0(){

        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(0,2);


        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,1);
        printTest("testPetitG3_NO_V0", s2==null);
    }

    public static void testPetitG3_NO_V1(){

        Graph g = new Graph(3);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(0,2);



        PartialSolVC s2 = AlgosVC.mainBackTrackVCV1(g,1);
        printTest("testPetitG3_NO_V1", s2==null);
    }

    public static void testPetitG4_NO_V0(){

        Graph g = new Graph(6);
        g.ajoutArete(0,1);
        g.ajoutArete(1,2);
        g.ajoutArete(2,3);
        g.ajoutArete(3,4);
        g.ajoutArete(4,5);


        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,2);
        printTest("testPetitG4_NO_V0", s2==null);
    }


    public static void testClique_YES_V0(){

        int n = 10;
        Graph g = new Graph(n);
        for(int i = 0;i<n;i++){
            for(int j=i+1;j<n;j++){
                g.ajoutArete(i,j);
            }
        }


        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,9);
        printTest("testClique_YES_V0", s2!=null && s2.isValid());
    }


  /*  public static void testRandomG() {

        Graph g = new Graph(9, true, 0.2);
        System.out.println("g = \n" + g);
        PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(g,4);
        System.out.println("s2 \n" + s2);

    }
*/
    public static void testHighestDegVsArbit() {

        int n = 80;
        int k = 55;
        double proba = 0.1;
        int nbgraphs = 50;
        double totalV0 = 0;
        double totalV1 = 0;
        for(int i = 1; i<= nbgraphs ; i++) {
            Graph g = new Graph(n, true, proba);
            System.out.println("nb edges of g " + g.m()+" matching "+g.matching().size());
            double t1 = System.currentTimeMillis();
            PartialSolVC s = new PartialSolVC(g, k);
         //   PartialSolVC s2 = AlgosVC.mainBackTrackVCV0(s);
            double t2 = System.currentTimeMillis();
            System.out.println("solving time V0 : " + (t2 - t1));
            double t1hd = System.currentTimeMillis();
            PartialSolVC s2hd = AlgosVC.mainBackTrackVCV1(g,k);
            double t2hd = System.currentTimeMillis();
            System.out.println("solving time V1: " + (t2hd - t1hd));

            totalV0+=t2-t1;
            totalV1+=t2hd-t1hd;
        }
        System.out.println("total v0 " + totalV0 + " total v1 " + totalV1);
    }

        public static void main(String[] args) {

        testPropagate_1();
            testPropagate_2();
            testPropagate_3();
        testPetitG_YES_V0();
        testPetitG2_YES_V0();
        testPetitG3_NO_V0();
        testPetitG4_NO_V0();
        testClique_YES_V0();
        //testRandomG();
          //  testHighestDegVsArbit();
           /* testPetitG2_YES_V1();
            testPetitG3_NO_V1();
            testRandomG();*/

    }
}
