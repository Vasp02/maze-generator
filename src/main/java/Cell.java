import java.awt.*;
import java.awt.geom.Line2D;

public class Cell extends Panel {

    private int cellID;
    private Cell parent;
    private int rank;

    private CellGraphics cellGraphics;

    public Cell() {
    }

    public Cell(int cellID){
        this.cellID = cellID;
        this.parent = this;
        this.rank = 0;
        this.cellGraphics = new CellGraphics(this);
    }

    public int getCellID() {
        return cellID;
    }

    public void setCellID(int cellID) {
        this.cellID = cellID;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public CellGraphics getCellGraphics(){
        return this.cellGraphics;
    }
}
