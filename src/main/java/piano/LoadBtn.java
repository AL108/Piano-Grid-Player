package piano;
import processing.core.PImage;

public class LoadBtn extends Button {
    
    public LoadBtn(int x, int y, int width, int height, PImage image) {
        super(x, y, image, height, width);
    }

    /**
     * Loads in the saved grid.
     */
    @Override
    public void click(App app) {
        app.getSoundPlayer.get().loadInstrument("src/save.txt");
        app.getGraphicsHandler.get().getGrid.get().recordLoad();
        app.getGraphicsHandler.get().getGrid.get().loadGrid("src/save.txt");
    }
}