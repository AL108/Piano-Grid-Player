package piano;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import processing.core.PImage;

public class GridTest {

    @Test
    public void loadInactiveGrid() {
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 1, 1));
        SaveBtn saveBtn = new SaveBtn(0, 0, 1, 1, new PImage());
        saveBtn.click(app);
        app.getGraphicsHandler.get().getGrid.get().activateGridBlocks();
        app.getGraphicsHandler.get().getGrid.get().loadGrid("src/save.txt");
        Block[][] loadedBlocks = app.getGraphicsHandler.get().getGrid.get().getBlocks();

        for (Block[] i : loadedBlocks) {
            for (Block j : i) {
                assert (j.isActive() == false);
            }
        }
    }

    @Test
    public void loadActiveGrid() {
        App app = new App();
        app.getGraphicsHandler.get().setGrid(new Grid(new PImage(), new PImage(), 1, 1));
        SaveBtn saveBtn = new SaveBtn(0, 0, 1, 1, new PImage());
        app.getGraphicsHandler.get().getGrid.get().activateGridBlocks();
        saveBtn.click(app);
        app.getGraphicsHandler.get().getGrid.get().deactivateGridBlocks();
        app.getGraphicsHandler.get().getGrid.get().loadGrid("src/save.txt");
        Block[][] loadedBlocks = app.getGraphicsHandler.get().getGrid.get().getBlocks();

        for (Block[] i : loadedBlocks) {
            for (Block j : i) {
                assert (j.isActive() == true);
            }
        }
    }

    @Test
    public void undoToggleTest() {
        App app = new App();
        app.mouseX = 65;
        app.mouseY = 65;
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.click(app);
        assert (grid.getBlocks()[0][0].isActive());
        UndoBtn undoBtn = new UndoBtn(1, 1, new PImage(), 10, 10);
        undoBtn.click(app);
        assert (!grid.getBlocks()[0][0].isActive());
    }

    @Test
    public void redoToggleTest() {
        App app = new App();
        app.mouseX = 66;
        app.mouseY = 76;
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.click(app);
        assert (grid.getBlocks()[0][0].isActive());
        UndoBtn undoBtn = new UndoBtn(1, 1, new PImage(), 10, 10);
        undoBtn.click(app);
        assert (!grid.getBlocks()[0][0].isActive());
        grid.redo();
        assert (grid.getBlocks()[0][0].isActive());
    }

    @Test
    public void loadFromLine() {
        // testing loading from first entry and non-first entry
        // also testing 'active and non-active' block data
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.activateGridBlocks();
        String stateInfo = grid.generateStateInfo();

        try {
            FileWriter writer = new FileWriter(new File("src/UndoRedoSaveData.txt"));
            writer.write(stateInfo);
            writer.flush();
            grid.deactivateGridBlocks();
            grid.loadGridFromLine("src/UndoRedoSaveData.txt", 2);
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    assert (grid.getBlocks()[i][j].isActive());
                }
            }
            grid.deactivateGridBlocks();
            stateInfo = grid.generateStateInfo();
            writer.write(stateInfo);
            writer.flush();
            grid.activateGridBlocks();
            grid.loadGridFromLine("src/UndoRedoSaveData.txt", 34);
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    assert (!grid.getBlocks()[i][j].isActive());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recordLoad() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);

        FileWriter writer;
        try {
            writer = new FileWriter(new File("src/save.txt"));
            writer.write(grid.generateStateInfo());
            writer.flush();
            grid.activateGridBlocks();
            grid.recordLoad();
            Scanner scan = new Scanner(new File("src/UndoRedoSaveData.txt"));
            assert(scan.nextLine().equals("LOADFROM"));
            grid.loadGridFromLine("src/UndoRedoSaveData.txt", 2);    
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    assert (grid.getBlocks()[i][j].isActive());
                }
            }
            for (int i = 0; i < 32; i++) {
                scan.nextLine();
            }
            assert(scan.nextLine().equals("LOADTO"));
            grid.loadGridFromLine("src/UndoRedoSaveData.txt", 35);
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    assert (!grid.getBlocks()[i][j].isActive());
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }   
    }

    @Test 
    public void undoClear() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.activateGridBlocks();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert (grid.getBlocks()[i][j].isActive());
            }
        } 
        app.mouseX = 4;
        app.mouseY = 4;
        ClearBtn clearBtn = new ClearBtn(0, 0, 5, 5, new PImage());
        app.getGraphicsHandler.get().addGraphic(clearBtn);
        clearBtn.click(app);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert (!grid.getBlocks()[i][j].isActive());
            }
        } 
        grid.undo();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert (grid.getBlocks()[i][j].isActive());
            }
        } 
    }

    @Test 
    public void redoClear() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.activateGridBlocks();
        app.mouseX = 4;
        app.mouseY = 4;
        ClearBtn clearBtn = new ClearBtn(0, 0, 5, 5, new PImage());
        app.getGraphicsHandler.get().addGraphic(clearBtn);
        clearBtn.click(app);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert (!grid.getBlocks()[i][j].isActive());
            }
        } 
        grid.undo();
        grid.redo();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                assert (!grid.getBlocks()[i][j].isActive());
            }
        }
    }

    @Test 
    public void undoLoad() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);

        try {
            FileWriter writer = new FileWriter(new File("src/save.txt"));
            grid.getBlocks()[0][1].toggleState();
            writer.write(grid.generateStateInfo());
            writer.flush();
            grid.activateGridBlocks();
            grid.recordLoad();
            grid.loadGrid("src/save.txt");
            grid.undo();
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    assert(grid.getBlocks()[i][j].isActive());
                }
            }
            

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    @Test 
    public void redoLoad() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);

        try {
            FileWriter writer = new FileWriter(new File("src/save.txt"));
            grid.getBlocks()[0][1].toggleState();
            writer.write(grid.generateStateInfo());
            writer.flush();
            grid.activateGridBlocks();
            grid.recordLoad();
            grid.loadGrid("src/save.txt");
            grid.undo();
            grid.redo();
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    if (i == 0 && j == 1) {
                        assert(grid.getBlocks()[i][j].isActive());
                    } else {
                        assert(!grid.getBlocks()[i][j].isActive());
                    }
                }
            }
            

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Test 
    public void mixedGridTest() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);

        try {
            FileWriter writer = new FileWriter(new File("src/save.txt"));
            grid.getBlocks()[0][1].toggleState();
            writer.write(grid.generateStateInfo());
            writer.flush();
            grid.activateGridBlocks();
            grid.recordLoad();
            grid.loadGrid("src/save.txt");
            grid.undo();
            grid.redo();
            ClearBtn clearBtn = new ClearBtn(0, 0, 5, 5, new PImage());
            app.getGraphicsHandler.get().addGraphic(clearBtn);
            clearBtn.click(app);
            grid.undo();
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 13; j++) {
                    if (i == 0 && j == 1) {
                        assert(grid.getBlocks()[i][j].isActive());
                    } else {
                        assert(!grid.getBlocks()[i][j].isActive());
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Test 
    public void loadBtnClick() {
        App app = new App();
        Grid grid = new Grid(new PImage(), new PImage(), 60, 75);
        app.getGraphicsHandler.get().setGrid(grid);
        grid.getBlocks()[0][1].toggleState();
        SaveBtn saveBtn = new SaveBtn(0, 0, 5, 5, new PImage());
        saveBtn.click(app);
        grid.activateGridBlocks();
        LoadBtn loadBtn = new LoadBtn(0, 0, 5, 5, new PImage());
        app.getGraphicsHandler.get().addGraphic(grid);
        loadBtn.click(app);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                if (i == 0 && j == 1) {
                    assert(grid.getBlocks()[i][j].isActive());
                } else {
                    assert(!grid.getBlocks()[i][j].isActive());
                }
            }
        }
    }
}