package piano;
import processing.core.PImage;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import processing.core.PApplet;

public class GraphicsHandlerTest {
    
    @Test
    public void GraphicsHandlerInitialise() {
        App app = new App();
        assert(app.getGraphicsHandler.get() != null);
    }

    @Test
    public void clickedOnGraphic() {
        App app = new App();
        app.mouseX = 5;
        app.mouseY = 5;
        Graphic a = new Graphic(1, 1, new PImage());
        Graphic b = new Graphic(20, 20, new PImage());
        a.height = 5;
        a.width = 5;
        assert(app.getGraphicsHandler.get().clickedOnGraphic(a));
        assertFalse(app.getGraphicsHandler.get().clickedOnGraphic(b));
    }

    @Test 
    public void handleInteractiveActionsTest() {
        // clicking a grid block testing if it toggles state
        // clicking a button, checking if it works
        App app = new App();
        app.mouseX = 65;
        app.mouseY = 85;
        Grid grid = new Grid(new PImage(),new PImage(),60,75);
        app.getGraphicsHandler.get().setGrid(grid);
        app.getGraphicsHandler.get().handleInteractiveActions();
        assert(grid.getBlocks()[0][0].isActive());
        app.setPlaying.accept(true);
        assert(app.isPlaying.get());
        app.getGraphicsHandler.get().addGraphic(new StopBtn(1, 1, 10, 10, new PImage()));
        app.mouseX = 5;
        app.mouseY = 5;
        app.getGraphicsHandler.get().handleInteractiveActions();
        assert(!app.isPlaying.get());
    } 
}