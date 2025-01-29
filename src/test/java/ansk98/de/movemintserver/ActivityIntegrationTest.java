package ansk98.de.movemintserver;

import org.junit.jupiter.api.Test;

public class ActivityIntegrationTest {

    @Test
    public void findActivityIntegrationTest() {
        // create activities for 2 different users
        // ensure that each user sees their own activity
    }

    @Test
    public void acceptActivityTest() {
        // create activity
        // ensure that a user-whom the activity does not belong cannot accept it
        // ensure that activity can be accepted only by the person who created activity
    }

    @Test
    public void rejectActivityTest() {
        // create activity
        // ensure that a user-whom the activity does not belong cannot reject it
        // ensure that activity can be rejected only by the person who created activity
    }

    @Test
    public void findLatestActivityTest() {

    }
}
