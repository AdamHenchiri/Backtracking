package NQueen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestNQueen {
    public static void printTest(String msg, boolean b){
        System.out.println("test : "+(b?" ok":" KO") + " (" + msg + ")");
    }

    public static void testConstraintOK_0(){
        printTest("testConstraintOK_0",PartialSolNQueen.constraintOk(0, 0, 3, 1));
        printTest("testConstraintOK_0",!PartialSolNQueen.constraintOk(0, 0, 1, 1));
    }

    public static void testV0(){
        PartialSolNQueen s;

        int n;

        n = 4;
        s = AlgosNQueen.mainBackTrackQueenVO(n);
        printTest("testV0", s!=null && s.isFullSolution() && s.isValid());

        n = 5;
        s = AlgosNQueen.mainBackTrackQueenVO(n);
        printTest("testV0", s!=null && s.isFullSolution() && s.isValid());

    }

    public static void testV1(){
        PartialSolNQueen s;

        int n = 3;
        s = AlgosNQueen.mainBackTrackQueenV1(n);
        printTest("testV1", s==null);

        n = 4;
        s = AlgosNQueen.mainBackTrackQueenV1(n);
        printTest("testV1", s!=null && s.isFullSolution() && s.isValid());

        n = 5;
        s = AlgosNQueen.mainBackTrackQueenV1(n);
        printTest("testV1", s!=null && s.isFullSolution() && s.isValid());

    }

    public static void testComparaisonV0V1(){
        int n = 4;
        AlgosNQueen.mainBackTrackQueenVO(n);
        System.out.println("nb appels à V0 : " + AlgosNQueen.c0);
        AlgosNQueen.mainBackTrackQueenV1(n);
        System.out.println("nb appels à V1 : " + AlgosNQueen.c1);



    }
    public static void testV2(){
        PartialSolNQueen s;

        int n = 3;
        s = AlgosNQueen.mainBackTrackQueenV2(n);
        printTest("testV2", s==null);

        n = 4;
        s = AlgosNQueen.mainBackTrackQueenV2(n);
        printTest("testV2", s!=null && s.isFullSolution() && s.isValid());

        n = 5;
        s = AlgosNQueen.mainBackTrackQueenV2(n);
        printTest("testV2", s!=null && s.isFullSolution() && s.isValid());

    }





    public static void testpropageContraintes_1(){
        PartialSolNQueen s = new PartialSolNQueen(4);

        Map<Integer, ArrayList<Integer>> D = new HashMap<>();
        ArrayList<Integer> posQueen0 = new ArrayList<>();
        posQueen0.add(0);
        posQueen0.add(1);
        posQueen0.add(2);
        posQueen0.add(3);
        ArrayList<Integer> posQueen1 = new ArrayList<>();
        posQueen1.add(0);
        posQueen1.add(1);
        posQueen1.add(2);
        posQueen1.add(3);
        ArrayList<Integer> posQueen2 = new ArrayList<>();
        posQueen2.add(0);
        posQueen2.add(1);
        posQueen2.add(2);
        posQueen2.add(3);
        ArrayList<Integer> posQueen3 = new ArrayList<>();
        posQueen3.add(0);
        posQueen3.add(1);
        posQueen3.add(2);
        posQueen3.add(3);

        D.put(0,posQueen0);
        D.put(1,posQueen1);
        D.put(2,posQueen2);
        D.put(3,posQueen3);

        System.out.println(D);

        s.propagateConstraints(D,0,0);
        System.out.println(D);

        s.add(0,0);
        s.propagateConstraints(D,1,3);
        System.out.println(D);


    }

    public static void testsLongs(){
        PartialSolNQueen s;
        int n = 28;
        long startTime = System.nanoTime();
        System.out.println(AlgosNQueen.mainBackTrackQueenV1(n)); //avec juste solutions valides sans domaines
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000000.0 + " secondes passé sur V1");

        startTime = System.nanoTime();
        System.out.println(AlgosNQueen.mainBackTrackQueenV2(n)); //avec domaines et FCC
        endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000000.0 + " secondes passé sur V2");

        startTime = System.nanoTime();
        System.out.println(AlgosNQueen.mainBackTrackQueenV3(n)); //avec domaines et FCC + MRV
        endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000000.0 + " secondes passé sur V3");

    }


    public static void main(String[] args) {
        testConstraintOK_0();
        testComparaisonV0V1();
        testpropageContraintes_1();
        testV0();
        testV1();
        testV2();
       // testsLongs();
    }
}
