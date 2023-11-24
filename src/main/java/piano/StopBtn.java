package piano;
import processing.core.PImage;

public class StopBtn extends Button {
    
    public StopBtn(int x, int y, int width, int height, PImage image) {
        super(x, y, image, height, width);
    }

    /**
     * @param app The relevant app.
     * Sets the app play-state to false and resets the pointer position.
     */
    @Override
    public void click(App app) {
        app.setPlaying.accept(false);
        app.getGraphicsHandler.get().resetPointerX.apply();
    }
}