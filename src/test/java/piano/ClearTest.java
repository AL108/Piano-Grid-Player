package piano;

import org.junit.Test;

import processing.core.PImage;

public class ClearTest {
    @Test
    public void clearTest1() {
        //clearing the grid with blocks active
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 0, 0));
        app.getGraphicsHandler.get().getGrid.get().activateGridBlocks();
        app.mouseX = 4;
        app.mouseY = 4;
        ClearBtn clearBtn = new ClearBtn(0, 0, 5, 5, new PImage());
        app.getGraphicsHandler.get().addGraphic(clearBtn);
        clearBtn.click(app);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert(!app.getGraphicsHandler.get().getGrid.get().getBlocks()[i][j].isActive());
            }
        }
    }
    
    @Test
    public void clearTest2() {
        //clearing the grid with all blocks inactive
        //clearing the grid with blocks active
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 0, 0));
        app.mouseX = 4;
        app.mouseY = 4;
        ClearBtn clearBtn = new ClearBtn(0, 0, 5, 5, new PImage());
        app.getGraphicsHandler.get().addGraphic(clearBtn);
        clearBtn.click(app);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert(!app.getGraphicsHandler.get().getGrid.get().getBlocks()[i][j].isActive());
            }
        }
    }
}