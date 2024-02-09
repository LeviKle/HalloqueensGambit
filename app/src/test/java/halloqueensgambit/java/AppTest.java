/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package halloqueensgambit.java;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }

    @Test void testUnMakeMove(){
        String fileName = "/bestmove/a.txt";
        Game game = IO.readGameFromFile(fileName);

        ArrayList<Move> moves = game.getLegalMoves();
        
        for(Move m: moves){
            Game tmp = game.copy();
            tmp.makeMove(m);
            tmp.unMakeMove(m);
            assertEquals(game, tmp);
        }
    }
}
