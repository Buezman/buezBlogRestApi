package com.buezman.buezBlogRestApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> arr1 = List.of(123,543);
        List<Integer> arr2 = List.of(321,279);
        List<Integer> teamA = new ArrayList<>();
        teamA.add(1);
        teamA.add(4);
        teamA.add(2);
        teamA.add(4);
        List<Integer> teamB = new ArrayList<>();
        teamB.add(3);
        teamB.add(5);

        System.out.println(counts(teamA, teamB));

        System.out.println(minimumMoves(arr1, arr2));
    }

    public static int minimumMoves(List<Integer> arr1, List<Integer> arr2) {
        int count = 0;
        for (int i = 0; i < arr1.size(); i++) {
            String a = "" + arr1.get(i);
            String b = "" + arr2.get(i);
            for (int j = 0; j < a.length(); j++) {
                int a1 = Integer.parseInt("" + a.charAt(j));
                int b1 = Integer.parseInt("" + b.charAt(j));
                count+= Math.abs(a1 - b1);
            }
        }
        return count;

    }

    public static List<Integer> counts(List<Integer> teamA, List<Integer> teamB) {
        ArrayList<Integer> res = new ArrayList<>();
        Collections.sort(teamA);
        //1,2,4,4    //3,5
//        for (Integer integer : teamB) {
//            int a = 0;
//            int b = teamA.size() - 1;
//            while (a <= b) {
//                int mid = (a + b) / 2;
//                if (teamA.get(mid) > integer)
//                    b = mid - 1;
//                else
//                    a = mid + 1;
//            }
//            res.add(a);
//        }
        for (Integer integer : teamB) {
            res.add(findIndex(teamA, integer));
        }
        return res;
    }

    public static int findIndex(List<Integer> list, int n) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > n) return i;
        }
        return list.size();
    }


}
