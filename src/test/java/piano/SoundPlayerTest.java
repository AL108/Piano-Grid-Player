package piano;
import processing.core.PImage;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import processing.core.PApplet;

public class SoundPlayerTest {

    @Test
    public void soundPlayerCreation() {
        App app = new App();
        assert (app.getSoundPlayer.get() != null);
    }

    @Test
    public void loadInstrumentSavedInstrumentPresentOrAbsent() {
       App app = new App();
        try {
            FileWriter writer = new FileWriter(new File("src/save.txt"));
            writer.write("2");
            writer.close();
            app.getSoundPlayer.get().loadInstrument("src/save.txt");
            assert(app.getSoundPlayer.get().getCurInstrument.get() == 2);
            FileWriter erasor = new FileWriter(new File("src/save.txt"));
            erasor.close();
            app.getSoundPlayer.get().loadInstrument("src/save.txt");
            assert(app.getSoundPlayer.get().getCurInstrument.get() == 2);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test 
    public void getAndSetCurQuaver() {    
        App app = new App();
        assert(app.getSoundPlayer.get().getCurQuaver.get() == 0);
        app.getSoundPlayer.get().setCurQuaver.accept(1);
        assert(app.getSoundPlayer.get().getCurQuaver.get() == 1);
    }  

    @Test 
    public void getAndChangeInstrument() {
        App app = new App();
        assert(app.getSoundPlayer.get().getCurInstrument.get() == 0);
        app.getSoundPlayer.get().changeInstrument.accept(1);
        assert(app.getSoundPlayer.get().getCurInstrument.get() == 1);
        app.getSoundPlayer.get().changeInstrument.accept(-1);
        assert(app.getSoundPlayer.get().getCurInstrument.get() == 0);
        app.getSoundPlayer.get().changeInstrument.accept(-1);
        assert(app.getSoundPlayer.get().getCurInstrument.get() == 0);
        app.getSoundPlayer.get().changeInstrument.accept(1);
        app.getSoundPlayer.get().changeInstrument.accept(1);
        app.getSoundPlayer.get().changeInstrument.accept(1);
        app.getSoundPlayer.get().changeInstrument.accept(1);
        assert(app.getSoundPlayer.get().getCurInstrument.get() == 3);
    }

    @Test 
    public void saveInstrument() {
        App app = new App(); 
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 0, 0));
        SaveBtn saveBtn = new SaveBtn(0, 0, 1, 1, new PImage());
        app.getSoundPlayer.get().changeInstrument.accept(1);
        saveBtn.click(app);
        try {
            Scanner scan = new Scanner(new File("src/save.txt"));
            assert(scan.nextLine().equals("1"));
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void playCurBeatTest1() {
        //playing current beat with no blocks selected
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 0, 0));
        app.setPlaying.accept(true);
        app.getSoundPlayer.get().playcurBeatTrack();
        PApplet.runSketch(new String[] {"HW"}, app);
    }

    @Test
    public void playCurBeatTest2() {
        //playing current beat with blocks selected
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 0, 0));
        app.getGraphicsHandler.get().getGrid.get().activateGridBlocks();
        app.setPlaying.accept(true);
        app.getSoundPlayer.get().playcurBeatTrack();
        PApplet.runSketch(new String[] {"HW"}, app);
    }

}