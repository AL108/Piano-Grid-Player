package piano;

    /**
     * Executes the actions pertaining to the current playPauseBtn state.
     * This is a core part of the play-pause 'state design pattern' implementation.
    */
public interface PlayPauseState {
    public abstract void execute(App app); 
}