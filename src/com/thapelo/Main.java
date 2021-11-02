package com.thapelo;

import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static Map<String,Integer> leagueTable = new HashMap<String,Integer>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("SPAN Super League");
        while (scanner.hasNext()) {
            // get single scoreLine
            String scoreLine = scanner.nextLine();
            String[] teams = scoreLine.split(",");
            // separate teams into homeTeam and awayTeam
            String[] homeTeam = teamAndScoreSplitter(teams[0]);
            String[] awayTeam = teamAndScoreSplitter(teams[1]);
            //Check if clubs exist in the Map, else createTeam
            addTeamToTable(homeTeam,awayTeam);
            // Update leagueTable based on a win or a tie.
            updateLeagueTable(homeTeam,awayTeam);
        }
        scanner.close();
        System.out.println(returnSolution());
    }

    public static String returnSolution() {
        String solution = "";
        Stream league = leagueTable.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        Integer leaguePosition=1;
        Integer nextPosition = null;
        Integer previousPoints = null;
        int index = 1;
        Object[] leagueStream = league.toArray();
        int leagueLength = leagueStream.length;
        for (Object obj : leagueStream) {
            if (nextPosition!=null){leaguePosition=nextPosition;nextPosition=null;}
            String[] leagueLine = obj.toString().split("=");
            if (previousPoints!=null && previousPoints==Integer.parseInt(leagueLine[1])) {leaguePosition--;nextPosition = leaguePosition + 2;previousPoints=null;}
            previousPoints = Integer.parseInt(leagueLine[1]);
            solution += leaguePosition++ + ". " + leagueLine[0] + ", " + leagueLine[1] + " pts";
            if(index<leagueLength){
                solution += "\n";
            }
            index++;
        }
        return solution;
    }

    public static void addTeamToTable(String[] homeTeam,String[] awayTeam){
        if(!leagueTable.containsKey(homeTeam[0])) { leagueTable.put(homeTeam[0], 0); }
        if(!leagueTable.containsKey(awayTeam[0])) {leagueTable.put(awayTeam[0], 0);}
    }

    public static String[] teamAndScoreSplitter(String teamScore) {
        //length of score
        String[] theSplit = teamScore.trim().split(" ");
        Integer theSplitSize = theSplit.length;
        String score = teamScore.trim().split(" ")[theSplitSize-1];
        // create teamName string by removing score substring using its length
        String teamName = teamScore.substring(0, teamScore.length() - score.length()-1).trim();
        return new String[]{teamName,score};
    }

    public static void updateLeagueTable(String[] homeTeam,String[] awayTeam) {
        if( Integer.parseInt(homeTeam[1]) > Integer.parseInt(awayTeam[1]) ) {
            // homeTeam won
            leagueTable.put(homeTeam[0] , leagueTable.get(homeTeam[0]) + 3);
        } else if( Integer.parseInt(homeTeam[1]) < Integer.parseInt(awayTeam[1]) ) {
            // awayTeam won
            leagueTable.put(awayTeam[0] , leagueTable.get(awayTeam[0]) + 3);
        } else {
            // Both teams tie
            leagueTable.put(homeTeam[0] , leagueTable.get(homeTeam[0]) + 1);
            leagueTable.put(awayTeam[0] , leagueTable.get(awayTeam[0]) + 1);
        }
        //return leagueTable;
    }

}