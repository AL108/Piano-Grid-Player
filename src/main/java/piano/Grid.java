package piano;
import java.util.Scanner;
import processing.core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Grid extends Graphic {
    private int curUndoRedoIndex;
    private FileWriter undoRedoRecorder;
    private String undoRedoSaveFileName = "src/UndoRedoSaveData.txt";
    private Block[][] blocks;

    public Grid(PImage gridImage, PImage blockImage, int x, int y) {
        super(x, y, gridImage);
        this.height = 260;
        this.width = 480;
        try {
            this.undoRedoRecorder = new FileWriter(new File(undoRedoSaveFileName));
        } catch (IOException e) {
        };
        System.out.println(this.undoRedoRecorder);
        this.blocks = new Block[32][13];

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                this.blocks[i][j] = new Block(15 * i + 61, 20 * j + 76, blockImage);
            }
        }
    }

    /**
     * @param fileName The name of the file containing the saved grid-state data.
     * Sets the grid state based on the data in file.
     */
    public void loadGrid(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            if (!scan.hasNextLine()) {
                return;
            }
            scan.nextLine(); // skips instrument line and goes to line where grid state data is stored.
            for (int i = 0; i < 32; i++) {
                String[] QuaverblocksActiveStatus = scan.nextLine().split("", 13);
                for (int j = 0; j < 13; j++) {
                    if ((QuaverblocksActiveStatus[j].equals("1") && !this.blocks[i][j].isActive())
                            || (QuaverblocksActiveStatus[j].equals("0") && this.blocks[i][j].isActive())) {
                        this.blocks[i][j].toggleState();
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
        }
    }

    /**
     * @param fileName The name of the file containing the saved grid-state data.
     * @param line The line from which to consider the data from (indexed from 1).
     * Sets the grid state based on the data in file.
     */
    public void loadGridFromLine(String fileName, int line) {
        //imp: lines in file starting with 1.
        try {
            Scanner scan = new Scanner(new File(fileName));
            for (int i = 0; i < line - 1; i++) {
                scan.nextLine();
            }
            for (int i = 0; i < 32; i++) {
                String[] QuaverblocksActiveStatus = scan.nextLine().split("", 13);
                for (int j = 0; j < 13; j++) {
                    if ((QuaverblocksActiveStatus[j].equals("1") && !this.blocks[i][j].isActive())
                            || (QuaverblocksActiveStatus[j].equals("0") && this.blocks[i][j].isActive())) {
                        this.blocks[i][j].toggleState();
                    }
                }
            }
            scan.close();
        } catch (IOException e) {
        }
    }

    /**
     * Resets save data.
     */
    public void resetUndoRedoSaveFile() {
        this.curUndoRedoIndex = 0;
        try {
            this.undoRedoRecorder = new FileWriter(undoRedoSaveFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Records reset data to a file when a reset of the grid has occured.
     */
    public void recordReset() {
        try {
            undoRedoRecorder.write("RESET");
            undoRedoRecorder.write(this.generateStateInfo() + "\n");
            undoRedoRecorder.flush();
            this.curUndoRedoIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Undos the last operation. Operations that can be undone include 
     * toggling grid blocks, clearing grid blocks and loading in grid-states
     * and instruments.
     */
    public void undo() {
        int lineBeingScanned = 0;
        try {
            Scanner scan = new Scanner(new File(undoRedoSaveFileName));
            String line = "";
            for (int i = 0; i < this.curUndoRedoIndex; i++) {
                if (line.equals("RESET") || line.equals("LOADFROM") || line.equals("LOADTO")) {
                    line = scan.nextLine();
                    lineBeingScanned++;
                    while (line.split("").length > 10) {
                         line = scan.nextLine();
                         lineBeingScanned++;
                     }
                    continue;
                }
                line = scan.nextLine();
                lineBeingScanned++;
            }
            if (!line.equals("")) {
                if (line.equals("RESET")) {
                    this.loadGridFromLine(this.undoRedoSaveFileName, lineBeingScanned + 1);
                    this.curUndoRedoIndex--;
                } else if (line.equals("LOADFROM")) {
                    this.loadGridFromLine(this.undoRedoSaveFileName, lineBeingScanned + 1);
                    this.curUndoRedoIndex--;
                } else if (line.equals("LOADTO")) {
                    this.loadGridFromLine(this.undoRedoSaveFileName, lineBeingScanned + 1);
                    this.curUndoRedoIndex--;
                    undo();
                } else {
                    this.blocks[Integer.valueOf(line.split(",",2)[0])][Integer.valueOf(line.split(",",2)[1])].toggleState(); 
                    this.curUndoRedoIndex--;
                }
                
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves corresponding data when a load operation has occured.
     * This is used for the undo and redo operations.
     */
    public void recordLoad() {
        try {
            this.undoRedoRecorder.write("LOADFROM");
            this.undoRedoRecorder.write(this.generateStateInfo() + "\n");
            Scanner scanner = new Scanner(new File("src/save.txt"));
            scanner.nextLine(); //skipping the line which saves the instrument.
            StringBuilder savedGridData = new StringBuilder();
            while (scanner.hasNextLine()) {
                savedGridData.append("\n");
                savedGridData.append(scanner.nextLine());
            }
            scanner.close();
            this.undoRedoRecorder.write("LOADTO");
            this.undoRedoRecorder.write(savedGridData.toString() + "\n");
            undoRedoRecorder.flush();
            this.curUndoRedoIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redos the last undo operation.
     */
    public void redo() {
        int lineBeingScanned = 0;
        try {
            Scanner scan = new Scanner(new File(undoRedoSaveFileName));
            String line = "";
            for (int i = 0; i < this.curUndoRedoIndex; i++) {
                if (line.equals("RESET") || line.equals("LOADFROM") || line.equals("LOADTO")) {
                    line = scan.nextLine();
                    lineBeingScanned++;
                    while (line.split("").length > 10 && scan.hasNextLine()) {
                         line = scan.nextLine();
                         lineBeingScanned++;
                     }
                    continue;
                }
                line = scan.nextLine();
                lineBeingScanned++;
            }
            if (scan.hasNextLine()) {
                line = scan.nextLine();
                lineBeingScanned++;
                while (line.split("").length > 10) {
                    if (!scan.hasNextLine()) {
                        return;
                    }
                    line = scan.nextLine();
                    lineBeingScanned++;
                }

                if (line.equals("RESET")) {
                    this.deactivateGridBlocks();
                    this.curUndoRedoIndex++;
                } else if (line.equals("LOADTO")) {
                    this.loadGridFromLine(this.undoRedoSaveFileName, lineBeingScanned + 1);
                    this.curUndoRedoIndex++;
                } else if (line.equals("LOADFROM")) {
                    curUndoRedoIndex++;
                    redo();
                } else {
                    this.blocks[Integer.valueOf(line.split(",",2)[0])][Integer.valueOf(line.split(",",2)[1])].toggleState(); 
                    this.curUndoRedoIndex++;
                }
                
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves toggle data.
     * @param gridX The number of blocks to the right.
     * @param gridY The number of blocks down.
     */
    public void recordToggle(int gridX, int gridY) {
        try {
            if (this.curUndoRedoIndex == 0) {
                resetUndoRedoSaveFile();
            }
            this.undoRedoRecorder.write(String.valueOf(gridX) + "," + String.valueOf(gridY) + "\n");
            this.undoRedoRecorder.flush();
            this.curUndoRedoIndex++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return A string containing encoded information that describes the grid-state at the time of invocation.
     */
    public String generateStateInfo() {
        StringBuilder stateInfo = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            stateInfo.append("\n");
            for (int j = 0; j < 13; j++) {
                if (this.blocks[i][j].isActive()) {
                    stateInfo.append("1");
                } else {
                    stateInfo.append("0");
                }
            } 
        }
        return stateInfo.toString();
    }

    /**
     * @return The Block objects stored in the grid.
     */
    public Block[][] getBlocks() {
        return this.blocks;
    }

    /**
     * Draws the static grid and the active blocks on it.
     */
    public void draw(App app) {
        app.image(this.image,60,75);
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j].draw(app);
            }
        }
    }

    /**
     * Toggles the state of the block on which the mouse coordinates are positioned at the time of click.
     */
    @Override
    public void click(App app) {
        this.blocks[(app.mouseX-60)/15][(app.mouseY-75)/20].toggleState();
        this.recordToggle((app.mouseX-60)/15,(app.mouseY-75)/20);
    }

    /**
     * Sets the state of all blocks to inactive.
     */
    public void deactivateGridBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j].deactivate();
            }
        }
    }

    /**
     * Sets the state of all blocks to active.
     */
    public void activateGridBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j].activate();
            }
        }
    }
}