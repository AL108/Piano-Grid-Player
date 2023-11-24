package piano;
import processing.core.PImage;

public class Block {
    private int x;
    private int y;
    private boolean active;

    private PImage block;

    public Block(int x, int y, PImage block) {
        this.x = x;
        this.y = y;
        this.block = block;
    }

    /** 
    *@return True if the block is active, otherwise false.
    */
    public boolean isActive() {
        return this.active;
    }

    /** 
    *Deactivates the block.
    */
    public void deactivate() {
        this.active = false;
    }

    /** 
    *Activates the block.
    */
    public void activate() {
        this.active = true;
    }

    /** 
    *Draws the block if it is active.
    *@param app The App object which stores the GraphicsHandler object that stores the grid on which the block resides.
    */
    public void draw(App app) { 
        if (this.isActive()) {
            app.image(this.block,this.x,this.y);
        }
    }

    /** 
    *If the block is active, this method makes it inactive and vice versa.
    */
    public void toggleState() {
        this.active = !this.active;
    }
}