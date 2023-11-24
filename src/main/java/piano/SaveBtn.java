package piano;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import processing.core.PImage;

public class SaveBtn extends Button {
    
    public SaveBtn(int x, int y, int width, int height, PImage image) {
        super(x, y, image, height, width);
    }

    /**
     * Saves the current state of the grid and the current instrument.
     */
    @Override
    public void click(App app) {
        try {
            FileWriter writer = new FileWriter(new File("src/save.txt"));
            writer.write(String.valueOf(app.getSoundPlayer.get().getCurInstrument.get()));
            writer.write(app.getGraphicsHandler.get().getGrid.get().generateStateInfo());
            writer.close();
        } catch (IOException e) {};
    }
    
}