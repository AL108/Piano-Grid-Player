package piano;
import processing.core.PImage;

public abstract class Button extends Graphic {
    
    public Button(int x, int y, PImage image, int height, int width) {
        super(x, y, image);
        this.height = height;
        this.width = width;
    }

    public abstract void click(App app);
}