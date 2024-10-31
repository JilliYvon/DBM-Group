import java.util.ArrayList;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

public class PerfComp {
    public static void main(String[] args) {
        // Create tuple generation objects that are used for specified test cases
        var select_1 = new TupleGeneratorImpl();
        // Create relational schemas for tuple generators.
        // All schemas will have the same dimensions and attribute types.
        // This will ensure consistency across results.
        select_1.addRelSchema("Student",
                "id name address",
                "Integer String String",
                "id",
                null);
        select_1.addRelSchema("Professor",
                "id name deptId",
                "Integer String String",
                "id",
                null);
        select_1.addRelSchema("Class",
                "id name department",
                "Integer String String",
                "id",
                null);
        select_1.addRelSchema("Building",
                "id street department",
                "Integer String String",
                "number",
                null);
        select_1.addRelSchema("Permit",
                "id permit_holder permit_location",
                "Integer String String",
                "number",
                null);
        // Create a tables array with the names and another array with the quantity of tuples we want for this problem
        var tables = new String[]{"Student", "Professor", "Class", "Building", "Permit"};
        var tups = new int[]{10000, 35000, 50000, 75000, 100000};
        var resultTest = select_1.generate(tups); // Generate the tuples for the relation. It automatically splits them
        // Now, we have to make tables that can take the generated tuples
        var student = new Table ("student", "id name address",
                "Integer String String", "id");
        var professor = new Table ("Professor",
                "id name deptId",
                "Integer String String",
                "id");
        var classes = new Table("Class",
                "id name department",
                "Integer String String",
                "id");
        var building = new Table("Building",
                "id street department",
                "Integer String String",
                "number");
        var permit = new Table("Permit",
                "id permit_holder permit_location",
                "Integer String String",
                "number");
        var tables_new = new Table[]{student, professor, classes, building, permit};
        // Fill the tables with the tuple-generator tuples
        for (int i = 0; i < tables_new.length; i++){
            for (var j = 0; j < resultTest[i].length; j++){
                tables_new[i].insert(resultTest[i][j]);
            } // for
        } // for

        ///////////////
        // Select Case 1: Selecting based on a string.
        // The following code will select based on a condition (all tuples will satisfy this condition) and the time will be recorded.
        ///////////////
       var sum   = 0;
//        ArrayList<Integer> select1_averages = new ArrayList<>();
//        for (int i = 0; i < tables_new.length; i++){
//            for (var it = 0; it < 6; it++) {
//                var t0 = nanoTime ();
//                tables_new[i].select("id > 0");
//                var et = (nanoTime () - t0) / 1000;
//                //out.println ("for it = " + it + " Table.select (String) time = " + et + " mu-sec");
//                if (it > 0) sum += et;
//            } // for
//            select1_averages.add(sum/5);
//            //out.println ("Average for Table.select (String) time = " + sum / 5 + " mu-sec");
//            sum = 0;
//        }
//        out.print("Averages for first Select Test Case: ");
//        out.println(select1_averages);
//
//        ///////////////
//        // Select Case 2: Selecting based on a predicate.
//        // The following code will select the row with ID 100 and the time will be recorded.
//        ///////////////
//        sum   = 0;
//        ArrayList<Integer> select2_averages = new ArrayList<>();
//        for (int i = 0; i < tables_new.length; i++){
//            for (var it = 0; it < 6; it++) {
//                var t0 = nanoTime ();
//                int finalI = i;
//                tables_new[i].select (t -> t[tables_new[finalI].col("id")].equals ("100"));
//                var et = (nanoTime () - t0) / 1000;
//               // out.println ("for it = " + it + " Table.select (Predicate) time = " + et + " mu-sec");
//                if (it > 0) sum += et;
//            } // for
//            select2_averages.add(sum/5);
//            //out.println ("Average for Table.select (Predicate) time = " + sum / 5 + " mu-sec");
//            sum = 0;
//        }
//        out.print("Averages for second Select Test Case: ");
//        out.println(select2_averages);
//
        // Select Case 3: Indexed Join.
        // The following code will select the row with ID 100 and the time will be recorded.
        ///////////////
        sum   = 0;
        ArrayList<Integer> select3_averages = new ArrayList<>();
        for (int i = 0; i < tables_new.length; i++){
            for (var it = 0; it < 6; it++) {
                var t0 = nanoTime ();
                int finalI = i;
                tables_new[i].select (new KeyType (100));
                var et = (nanoTime () - t0) / 1000;
                //out.println ("for it = " + it + " Table.select (Indexed) time = " + et + " mu-sec");
                if (it > 0) sum += et;
            } // for
            select3_averages.add(sum/5);
            //out.println ("Average for Table.select (Indexed) time = " + sum / 5 + " mu-sec");
            sum = 0;
        }
        out.print("Averages for third Select Test Case: ");
        out.println(select3_averages);

//
//        // ONLY RUN IF NEEDED, TAKES A VERY LONG TIME
//        ///////////////
//        // Join Case 1: Natural Join.
//        // The following code will naturally join the tables, with each iteration having bigger tables.
//        ///////////////
//        sum   = 0;
//        ArrayList<Integer> join1_averages = new ArrayList<>();
//        for (int i = 0; i < tables_new.length; i++){
//            for (var it = 0; it < 6; it++) {
//                if (i != tables_new.length - 1){
//                    var t0 = nanoTime ();
//                    tables_new[i].join(tables_new[i+1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Natural Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } else {
//                    var t0 = nanoTime ();
//                    tables_new[i].join(tables_new[i-1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Natural Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } // if-else
//            } // for
//            join1_averages.add(sum/5);
//            //out.println ("Average for Table.join (Natural Join) time = " + sum / 5 + " mu-sec");
//            sum = 0;
//        } // for
//        out.print("Averages for first Join Test Case: ");
//        out.println(join1_averages);


        ///////////////
         //Join Case 2: Equi-Join.
         //The following code will equi-join the table on the ID attribute, with each iteration having larger tables.
        /////////////
//        sum   = 0;
//        ArrayList<Integer> join2_averages = new ArrayList<>();
//        for (int i = 0; i < tables_new.length; i++){
//            for (var it = 0; it < 6; it++) {
//                if (i != tables_new.length - 1){
//                    var t0 = nanoTime ();
//                    tables_new[i].join("id", "id", tables_new[i+1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Equi Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } else {
//                    var t0 = nanoTime ();
//                    tables_new[i].join("id", "id", tables_new[i-1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Equi Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } // if-else
//            } // for
//            join2_averages.add(sum/5);
//            //out.println ("Average for Table.join (Equi Join) time = " + sum / 5 + " mu-sec");
//            sum = 0;
//        } // for
//        out.print("Averages for second Join Test Case: ");
//        out.println(join2_averages);
//        //Join Case 3: Indexed-Join.
//        //The following code will Indexed-join the table on the ID attribute, with each iteration having larger tables.
//        /////////////
//        sum   = 0;
//        ArrayList<Integer> join3_averages = new ArrayList<>();
//        for (int i = 0; i < tables_new.length; i++){
//            for (var it = 0; it < 6; it++) {
//                if (i != tables_new.length - 1){
//                    var t0 = nanoTime ();
//                    tables_new[i].i_join ("id", "id", tables_new[i+1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Indexed Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } else {
//                    var t0 = nanoTime ();
//                    tables_new[i].i_join ("id", "id", tables_new[i-1]);
//                    var et = (nanoTime () - t0) / 1000;
//                    //out.println ("for it = " + it + " Table.join (Indexed Join) time = " + et + " mu-sec");
//                    if (it > 0) sum += et;
//                } // if-else
//            } // for
//            join3_averages.add(sum/5);
//           // out.println ("Average for Table.join (Indexed Join) time = " + sum / 5 + " mu-sec");
//            sum = 0;
//        } // for
//        out.print("Averages for third Join Test Case: ");
//        out.println(join3_averages);
    } // main
} // PerfComp
