package com.thapelo;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void isResultCorrect() throws IOException {
        final InputStream original = System.in;
        final FileInputStream fileInputStream = new FileInputStream("src/com/thapelo/TestData.txt");
        System.setIn(fileInputStream);
        Main.main(null);
        // Assertion of the correct response
        String expected = "1. Tarantulas, 6 pts\n" +
                "2. Lions, 5 pts\n" +
                "3. Snakes, 1 pts\n" +
                "3. FC Awesome, 1 pts\n" +
                "5. Grouches, 0 pts";
        assertEquals(expected,Main.returnSolution(),"Solution is Correct");
        System.setIn(original);
    }


    @Test
    void teamExistOrCreateTeam(){
        // Tigers exists
        Main.leagueTable.put("Tigers",2);
        Main.addTeamToTable(new String[]{"Tigers","0"}, new String[]{"Cows","0"});
        // Is Table empty or Null
        assertNotNull(Main.leagueTable,"League table is not null");
        assertFalse(Main.leagueTable.isEmpty(),"League table is not Empty");
        // Tigers must have existing value of 2, not changed to 0
        assertEquals(2, Main.leagueTable.get("Tigers"),"Correct value exists in the table");
        // Cows must be Added and have value 0
        assertEquals(2, Main.leagueTable.get("Tigers"),"New entry exists in the table");
    }

    @Test
    void teamsInLeagueUpdated(){
        // create Teams
        Main.addTeamToTable(new String[]{"Pirates","0"},new String[]{"Chiefs","0"});
        Main.addTeamToTable(new String[]{"Sundowns","0"},new String[]{"Swallows","0"});
        // Update table with points
        Main.updateLeagueTable(new String[]{"Pirates","5"},new String[]{"Chiefs","0"});
        // Win/Lose test
        assertEquals(3,Main.leagueTable.get("Pirates"), "Winning team has 3 points");
        assertEquals(0,Main.leagueTable.get("Chiefs"), "Losing team has 0 points");
        // Draw test
        Main.updateLeagueTable(new String[]{"Sundowns","2"},new String[]{"Swallows","2"});
        // They have equal points for draw and That is 1 points
        assertTrue(Objects.equals(Main.leagueTable.get("Sundowns"), Main.leagueTable.get("Swallows")) && Main.leagueTable.get("Sundowns")==1 && Main.leagueTable.get("Swallows")==1,"Entry recorded as a 1 point draw for both teams");
        assertEquals(3,Main.leagueTable.get("Pirates"), "Winning team has 3 points");
    }
}
