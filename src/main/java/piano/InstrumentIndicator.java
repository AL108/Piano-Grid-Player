package piano;
import java.util.ArrayList;
import java.util.List;
import processing.core.PImage;

public class InstrumentIndicator extends Graphic{
    private List<PImage> instrumentIndicatorImages;

    public InstrumentIndicator(int x, int y, List<PImage> instrumentIndicatorImages) {
        super(x, y, instrumentIndicatorImages.get(0));
        this.instrumentIndicatorImages = new ArrayList<>();
        this.instrumentIndicatorImages.addAll(instrumentIndicatorImages);
    }

    /**
     * Updates the instrument indicator image to be displayed.
     */
    @Override
    public void update(App app) {
        this.image = instrumentIndicatorImages.get(app.getSoundPlayer.get().getCurInstrument.get());
    }
}