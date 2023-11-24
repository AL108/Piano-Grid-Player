package piano;
import org.junit.Test;

import piano.App;

public class AppTest {
    @Test
    public void appOpensWithoutException() {
        App app = new App();
        app.runSketch(new String[] {""} , app);
    }
}