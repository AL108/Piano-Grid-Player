package piano;
import processing.core.PImage;

public class RedoBtn extends Button {
    public RedoBtn(int x, int y, PImage image, int height, int width) {
        super(x, y, image, height, width);
    }

    /**
     * Redos the last undo
     */
    @Override
    public void click(App app) {
        app.getGraphicsHandler.get().getGrid.get().redo();
    }
}