package piano;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

public class App extends PApplet {
    private GraphicsHandler graphicsHandler;
    private SoundPlayer soundPlayer;
    private boolean playing;

    public App() {
        this.soundPlayer = new SoundPlayer(this);
        this.graphicsHandler = new GraphicsHandler(this);
    }

    /** 
    *Sets the play-state of the App object to the boolean value of the parameter.
    */
    public Consumer<Boolean> setPlaying = (x) -> this.playing = x;

    /** 
    *Returns true if the play-state of the App object is true, otherwise false.
    */
    public Supplier<Boolean> isPlaying = () -> this.playing;

    /** 
    *Returns the App object's SoundPlayer object.
    */
    public Supplier<SoundPlayer> getSoundPlayer = () -> this.soundPlayer;

    /** 
    *Returns the App object's GraphicsHandler object.
    */
    public Supplier<GraphicsHandler> getGraphicsHandler = () -> this.graphicsHandler;

    public void settings() {size(540, 335);}

    public void setup() {
        frameRate(60);
        List<PImage> instrumentIndicatorImages = new ArrayList<>();
        instrumentIndicatorImages.add(this.loadImage("src/main/resources/P.png"));
        instrumentIndicatorImages.add(this.loadImage("src/main/resources/M.png"));
        instrumentIndicatorImages.add(this.loadImage("src/main/resources/B.png"));
        instrumentIndicatorImages.add(this.loadImage("src/main/resources/S.png"));
        graphicsHandler.setGrid(new Grid(this.loadImage("src/main/resources/grid.png"), this.loadImage("src/main/resources/block.png"), 60, 75));
        graphicsHandler.addGraphic(new Graphic(0, 0, this.loadImage("src/main/resources/middleBanner.png")));
        graphicsHandler.addGraphic(new Graphic(0, 0, this.loadImage("src/main/resources/banner.png")));
        graphicsHandler.addGraphic(new Graphic(0, 75, this.loadImage("src/main/resources/keyboard.png")));
        graphicsHandler.addGraphic(new Graphic(5, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(50, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(95, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(140, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(185, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(275, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(320, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new Graphic(365, 5, this.loadImage("src/main/resources/buttonBack.png")));
        graphicsHandler.addGraphic(new UndoBtn(450, 5, this.loadImage("src/main/resources/undo.png"),40,40));
        graphicsHandler.addGraphic(new RedoBtn(495, 5, this.loadImage("src/main/resources/redo.png"),40,40));
        graphicsHandler.addGraphic(new StopBtn(50,5,40,40,this.loadImage("src/main/resources/stop.png")));
        graphicsHandler.addGraphic(new ClearBtn(95,5,40,40,this.loadImage("src/main/resources/reset.png")));
        graphicsHandler.addGraphic(new InstrumentIndicator(320, 5, instrumentIndicatorImages));
        graphicsHandler.addGraphic(new NextBtn(365,5,40,40,this.loadImage("src/main/resources/next.png")));
        graphicsHandler.addGraphic(new PrevBtn(275,5,40,40,this.loadImage("src/main/resources/prev.png")));
        graphicsHandler.addGraphic(new PlayPauseBtn(5, 5, 40, 40, this.loadImage("src/main/resources/play.png"),this.loadImage("src/main/resources/pause.png")));
        graphicsHandler.addGraphic(new SaveBtn(140, 5, 40, 40, this.loadImage("src/main/resources/save.png")));
        graphicsHandler.addGraphic(new LoadBtn(185, 5, 40, 40, this.loadImage("src/main/resources/load.png")));
        graphicsHandler.addGraphic(new Pointer(11, 60, this.loadImage("src/main/resources/pointer.png")));
    }
    public void draw() {graphicsHandler.renderGraphics();}

    public void mousePressed() {graphicsHandler.handleInteractiveActions();}
    
    public static void main(String[] args) {PApplet.main("piano.App");}
}
