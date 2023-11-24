package piano;

public class NotPlaying implements PlayPauseState {

    /**
     * @param app The app on which the play state is to be altered.
     * Sets the play-state of app to false.
     */
    @Override
    public void execute(App app) {
        app.setPlaying.accept(false);
    }
}