package piano;
import processing.core.PImage;

public class ClearBtn extends Button {
    
    public ClearBtn(int x, int y, int width, int height, PImage image) {
        super(x, y, image, height, width);
    }

    /** 
     * Deactivates all blocks and resets the cursor.
    */
    @Override
    public void click(App app) {
        app.setPlaying.accept(false);
        app.getGraphicsHandler.get().resetPointerX.apply();
        app.getGraphicsHandler.get().getGrid.get().recordReset();
        app.getGraphicsHandler.get().getGrid.get().deactivateGridBlocks();
    }
}