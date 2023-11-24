package piano;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.sound.midi.*;

public class SoundPlayer {
    private Sequencer sequencer;
    private Sequence sequence;
    private App app;
    private int curInstrumentNumber; //represented in terms of order within INSTRUMENT_NUMBERS array.
    private final int[] INSTRUMENT_NUMBERS = {0,12,105,65};
    private int curQuaver; 

    public SoundPlayer(App app) {
        this.app = app;
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current quaver.
     */
    public Supplier<Integer> getCurQuaver = () -> this.curQuaver;

    /**
     * Sets the current quaver to the Integer specified in the parameter.
     */
    public Consumer<Integer> setCurQuaver = (x) -> this.curQuaver = x;//k

    /**
     * Returns an integer representing the current instrument.
     */
    public Supplier<Integer> getCurInstrument = () -> this.curInstrumentNumber;
    
    /**
     * Changes the instrument according the the integer specified in the parameter.
     * If the integer specified in the parameter is 1, then the current instrument is switched to the next, 
     * otherwise if it is -1, it is switched to the previous. This lambda does nothing if it tries to in-effect 
     * switch the current instrument to the next instrument but the current instrument is already the last and vice-versa.
     */
    public Consumer<Integer> changeInstrument = (x) -> {if (!((curInstrumentNumber + x > 3) || (curInstrumentNumber + x < 0))) {curInstrumentNumber += x;}};

    /**
     * @param fileName The file which contains the save data.
     * Sets the current instrument the one saved in the save file.
     */
    public void loadInstrument(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));

            if (!scan.hasNextLine()) {
                return;
            }

            this.curInstrumentNumber = Integer.valueOf(scan.nextLine());
            scan.close();
        } catch (IOException e) {}
    }

    /**
     * Plays the track of the current quaver based on the active blocks in the current column.
     */
    public void playcurBeatTrack() {
        try {           
            this.sequence = new Sequence(Sequence.PPQ, 2);
            this.sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {}

        sequencer.setTempoInBPM(120);

        try {
            Track quaverTrack = sequence.createTrack();
            // midi note number = cur key + 60
           
            for (int i = 0; i < app.getGraphicsHandler.get().getGrid.get().getBlocks()[0].length; i++) {
                if (app.getGraphicsHandler.get().getGrid.get().getBlocks()[this.curQuaver][i].isActive()) {
                    quaverTrack.add(new MidiEvent(new ShortMessage(192, 5, INSTRUMENT_NUMBERS[curInstrumentNumber], 0), 0));   
                    quaverTrack.add(new MidiEvent(new ShortMessage(144, 5, 72 - i, 100), 0));
                    quaverTrack.add(new MidiEvent(new ShortMessage(128, 5, 72 - i, 100), 10));
                }
            }
            sequencer.setSequence(sequence);
            sequencer.start();

        } catch (InvalidMidiDataException e) {}
    }
}