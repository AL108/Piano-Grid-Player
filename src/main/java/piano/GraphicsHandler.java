package piano;
import java.util.List;
import java.util.function.Supplier;
import java.util.ArrayList;

public class GraphicsHandler {
    private int pointerX;
    private List<Graphic> graphics;
    private Grid grid;
    private App app;

    public GraphicsHandler(App app) {
        this.graphics = new ArrayList<>();
        this.app = app;
        this.pointerX = 60;
    }

    /**
     * Updates the pointer x-coordinate.
     */
    public Modify updatePointerX = () -> this.pointerX = 60 + ((this.pointerX - 60) + 1) % 480;

    /**
     * Resets the pointer to its original position.
     */
    public Modify resetPointerX = () -> this.pointerX = 60;

    /**
     * Returns the Pointer objects x-position.
     */
    public Supplier<Integer> getPointerX = () -> this.pointerX;

    /**
     * Returns the Grid object associated with the GraphicsHandler
     */
    public Supplier<Grid> getGrid = () -> this.grid;

    /**
     * Returns a list of Graphic objects
     */
    public Supplier<List<Graphic>> getGraphics = () -> this.graphics;

    /**
     * @param grid The Grid object to be associated with the GraphicsHandler.
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Renders all Graphic objects stored in the graphics handler.
     */
    public void renderGraphics() {
        if (this.grid != null) {
            this.grid.draw(app);
        }
        for (Graphic i : graphics) {
            i.draw(app);
        }
    }

    /**
     * @param a The graphic to add.
     * Add a graphic to the GraphicsHandler
     */
    public void addGraphic(Graphic a) {
        graphics.add(a);
    }
        
    /**
     * If a 'clickable' graphic has been clicked, it's corresponding actions are executed.
     */
    public void handleInteractiveActions() {
        if (this.grid != null && clickedOnGraphic(this.grid)) {
            grid.click(this.app);
        }
        for (Graphic i : graphics) {
            if (clickedOnGraphic(i)) {
                i.click(this.app);
            }
        }
    }
 
    /**
     * @return True if the graphic has been clicked on, otherwise false.
     * @param graphic The graphic object which may have been clicked on.
     */
    public boolean clickedOnGraphic(Graphic graphic) {
        if ((app.mouseX >= graphic.getX() && app.mouseY >= graphic.getY()) && 
        this.app.mouseX < graphic.getX() + graphic.getWidth() && app.mouseY < graphic.getY() + graphic.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}