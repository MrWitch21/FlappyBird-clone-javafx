package mszzsm.floppybird.controllers.share;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class usersTest {
    @Test
    public void testGetUsername() {
        String expectedUsername = "JohnDoe";
        int score = 100;
        users user = new users(expectedUsername, score);
        String actualUsername = user.getUsername();
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testGetScore() {
        String username = "AliceSmith";
        int expectedScore = 150;
        users user = new users(username, expectedScore);
        
        int actualScore = user.getScore();
        assertEquals(expectedScore, actualScore);
    }

}