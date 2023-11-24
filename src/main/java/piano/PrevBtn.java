package piano;
import processing.core.PImage;

public class PrevBtn extends Button {
    
    public PrevBtn(int x, int y, int width, int height, PImage image) {
        super(x, y, image, height, width);
    }

    /**
     * Sets the current instrument to the previous instrument.
     */
    @Override
    public void click(App app) {
        app.getSoundPlayer.get().changeInstrument.accept(-1);
    }
    
}