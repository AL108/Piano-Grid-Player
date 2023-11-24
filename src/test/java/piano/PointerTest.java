package piano;

import org.junit.Test;

public class PointerTest {
    @Test
    public void operationsOnPointerX() {
        App app = new App();
        assert(app.getGraphicsHandler.get().getPointerX.get() == 60); //checks that px starts at x=60px.
        app.getGraphicsHandler.get().updatePointerX.apply();
        assert(app.getGraphicsHandler.get().getPointerX.get() == 61); 
        app.getGraphicsHandler.get().resetPointerX.apply();
        assert(app.getGraphicsHandler.get().getPointerX.get() == 60);
        for (int i = 0; i < 500; i++) {
            app.getGraphicsHandler.get().updatePointerX.apply();
        }
        assert(app.getGraphicsHandler.get().getPointerX.get() == 80);
    }
}