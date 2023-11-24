package piano;
import processing.core.PImage;

public class UndoBtn extends Button {


    public UndoBtn(int x, int y, PImage image, int height, int width) {
        super(x, y, image, height, width);
    }

    /**
     * Undos the last operation that can be undone. Operations that can be undone include 
     * clear, load and toggle (i.e. clicking on a block).
     */
    @Override
    public void click(App app) {
        app.getGraphicsHandler.get().getGrid.get().undo();
    }
}