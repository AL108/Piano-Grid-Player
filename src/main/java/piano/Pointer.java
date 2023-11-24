package piano;
import processing.core.PImage;

public class Pointer extends Graphic {

    public Pointer(int x, int y, PImage image) {
        super(x, y, image);
    }

    /**
     * @param app The relevant App object.
     * Draws the pointer
     */
    @Override
    public void draw(App app) {
        this.coordX = app.getGraphicsHandler.get().getPointerX.get() - 11;
        app.stroke(app.color(255, 0, 0));
        app.strokeWeight(2);
        app.line(app.getGraphicsHandler.get().getPointerX.get(), 75, app.getGraphicsHandler.get().getPointerX.get(), 335);
        super.draw(app);
    }
}