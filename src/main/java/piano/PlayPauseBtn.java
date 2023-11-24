package piano;
import processing.core.PImage;

public class PlayPauseBtn extends Button {    
    private PlayPauseState curState;
    private String curStateName;
    private PImage playImage;
    private PImage pauseImage;

    public PlayPauseBtn(int x, int y, int width, int height, PImage playImage, PImage pauseImage) {
        super(x, y, playImage, height, width);
        this.playImage = playImage;
        this.pauseImage = pauseImage;
        this.curState = new NotPlaying();
        this.curStateName = "NOTPLAYING";
    }

    /**
     * Switches the state of the playPauseBtn and
     * executes the actions corresponding to the 
     * state switched to.
     * @param app The relevant App object.
     */
    @Override
    public void click(App app) {
        this.switchState();
        this.curState.execute(app);
    }

    /**
     * @param app The app on which the play state is to be altered.
     * Sets the play-state of app to false.
     * Draw the playPauseBtn
     */
    @Override
    public void draw(App app) {
        refresh(app);
        super.draw(app);
    }

    /**
     * Switches the state of the playPauseBtn.
     */
    public void switchState() {
        if (this.curStateName.equals("PLAYING")) {
            this.curState = new NotPlaying();
            this.curStateName = "NOTPLAYING";
            this.image = playImage;
        } else {
            this.curState = new Playing();
            this.curStateName = "PLAYING";
            this.image = pauseImage;
        }
    }

    /**
     * @param app The relevant App object.
     * updates the state of the App object based on it's play-state.
     */
    public void refresh(App app) {
        if (app.isPlaying.get()) {

            if (!curStateName.equals("PLAYING")) {
                this.switchState();
            }

            if (app.getSoundPlayer.get().getCurQuaver.get() != (app.getGraphicsHandler.get().getPointerX.get() - 60)/15 || app.getGraphicsHandler.get().getPointerX.get() == 60) {
                app.getSoundPlayer.get().setCurQuaver.accept((app.getGraphicsHandler.get().getPointerX.get() - 60)/15);
                app.getSoundPlayer.get().playcurBeatTrack();
            }
            
            app.getGraphicsHandler.get().updatePointerX.apply();
        } else if (!app.isPlaying.get()) {

            if (curStateName.equals("PLAYING")) {
                this.switchState();
            }
        }
    }
}