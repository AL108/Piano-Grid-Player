package piano;
import processing.core.PImage;

public class Graphic {
    protected int coordX;
    protected int coordY;
    protected PImage image;
    protected int height;
    protected int width;
    
    public Graphic(int x, int y, PImage image) {
        this.coordX = x;
        this.coordY = y;
        this.image = image;
    }

    /** 
     * @return Height of graphic.
    */
    public int getHeight() {
        return this.height;
    }

    /** 
     * @return width of graphic
    */
    public int getWidth() {
        return this.width;
    }

    /** 
     * @return X coordinate of graphic. 
    */
    public int getX() {
        return this.coordX;
    }

    /** 
     * @return Y coordinate of graphic
    */
    public int getY() {
        return this.coordY;
    }

    /** 
     * Some subclasses of Graphic may need to vary their characteristics on a regular basis,
     * this method is for those classes to override. This method is invoked each time the draw() method 
     * for the Graphic is invoked. 
     * @param app The App object which has the GraphicsHandler object which stores the Graphic.
    */
    public void update(App app) {};

    
    /** 
     * Graphic Subclass-objects will implement this method if they represent 'clickable' graphics.
     *  @param app The App object which has the GraphicsHandler object which stores the Graphic.
    */
    public void click(App app) {}

    /** 
     * Updates and draws the graphic.
     * @param app The App object which has the GraphicsHandler object which stores the Graphic.
    */
    public void draw(App app) {
        this.update(app);
        app.image(this.image, this.coordX, this.coordY);
    }
}