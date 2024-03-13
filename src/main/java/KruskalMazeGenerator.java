import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KruskalMazeGenerator {

    private List<List<Cell>> cellSets;
    private Cell[][] cellMatrix;
    private int mazeHeight;
    private int mazeWidth;

    public KruskalMazeGenerator(){

    }
    public KruskalMazeGenerator(Cell[][] cells, int mazeHeight, int mazeWidth){
        cellMatrix = cells;
        cellSets = new ArrayList<List<Cell>>();
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;

        for(Cell[] cellColumn : cells){
            for(Cell cell : cellColumn){
                List<Cell> newCellSet = new ArrayList<>();
                newCellSet.add(cell);
                cellSets.add(newCellSet);
            }
        }
    }

    private void updateCellSets() {
        List<List<Cell>> updatedCellSets = new ArrayList<>();

        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                Cell currentCell = cellMatrix[i][j];
                Cell root = findSet(currentCell);

                boolean found = false;
                for (List<Cell> set : updatedCellSets) {
                    if (set.contains(root)) {
                        set.add(currentCell);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    List<Cell> newSet = new ArrayList<>();
                    newSet.add(currentCell);
                    updatedCellSets.add(newSet);
                }
            }
        }

        cellSets = updatedCellSets;
    }

    private void union(Cell cell1, Cell cell2) {
        Cell root1 = findSet(cell1);
        Cell root2 = findSet(cell2);

        if (root1 == root2) {
            return;
        }

        int root1Index = -1, root2Index = -1;
        for (int i = 0; i < cellSets.size(); i++) {
            if (cellSets.get(i).contains(root1)) {
                root1Index = i;
            }
            if (cellSets.get(i).contains(root2)) {
                root2Index = i;
            }
        }

        if (root1.getRank() < root2.getRank()) {
            cellSets.get(root2Index).addAll(cellSets.get(root1Index));
            cellSets.remove(root1Index);
            root1.setParent(root2);
        } else {
            cellSets.get(root1Index).addAll(cellSets.get(root2Index));
            cellSets.remove(root2Index);
            if (root1.getRank() == root2.getRank()) {
                root1.setRank(root1.getRank() + 1);
            }
            root2.setParent(root1);
        }
    }




    private Cell findSet(Cell cell) {
        if (cell != cell.getParent()) {
            cell.setParent(findSet(cell.getParent()));
        }
        return cell.getParent();
    }

    public void generateMaze(int animDelayInMs){

        while (!allCellsInSameSet()){
            generateMazeStep(animDelayInMs);
        }
    }

    public void generateMazeStep(int animDelayInMs){

        System.out.println("Running one step");

        if(!allCellsInSameSet()){
            Cell[] cellpair = getValidCellPair();
            Cell cell1 = cellpair[0];
            Cell cell2 = cellpair[1];
            cell1.getCellGraphics().setSelected();
            cell2.getCellGraphics().setSelected();
            union(cell1,cell2);
            breakCellWalls(cell1,cell2);
            System.out.println("Joined cell " + cell1.getCellID() + " --- " + cell2.getCellID());
            updateCellSets();
            try {
                Thread.sleep(animDelayInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cell1.getCellGraphics().setUnselected();
            cell2.getCellGraphics().setUnselected();
        }


    }

    public List<List<Cell>> getCellSets(){
        return cellSets;
    }

    private boolean allCellsInSameSet(){
        return cellSets.size() == 1;
    }

    public String cellSetToString(){
        String cellSetString = "";
        for(List<Cell> cellSet : cellSets){
            cellSetString += "{";
            for(Cell cell : cellSet){
                cellSetString += cell.getCellID() + " ";
            }
            cellSetString += "}";
        }
        return cellSetString;
    }

    public void printCellMatrix(){
        if (cellMatrix == null) {
            System.out.println("Cell matrix is not initialized.");
            return;
        }

        for (Cell[] cellColumn : cellMatrix) {
            for (Cell cell : cellColumn) {
                System.out.print(cell.getCellID() + " ");
            }
            System.out.println();
        }
    }

    private Cell[] getValidCellPair(){
        Random random = new Random();
        boolean isValidPair = false;
        Cell cell1 = null;
        Cell cell2 = null;

        while (!isValidPair){
            int yOfCell = random.nextInt(mazeHeight);
            int xOfCell = random.nextInt(mazeWidth);

            int[] xOfNeighbourArray = {-1 , 0 , 1 , 0};
            int[] yOfNeighbourArray = {0 , 1 , 0 , -1};

            int neighbourSeed = random.nextInt(4);

            int xOfNeighbourCell = xOfCell + xOfNeighbourArray[neighbourSeed];
            int yOfNeighbourCell = yOfCell + yOfNeighbourArray[neighbourSeed];

            if (xOfNeighbourCell >= 0 && yOfNeighbourCell >= 0 && xOfNeighbourCell < mazeWidth && yOfNeighbourCell < mazeHeight) {
                if (cellMatrix[yOfCell][xOfCell].getParent() != cellMatrix[yOfNeighbourCell][xOfNeighbourCell].getParent()) {
                    isValidPair = true;
                    cell1 = cellMatrix[yOfCell][xOfCell];
                    cell2 = cellMatrix[yOfNeighbourCell][xOfNeighbourCell];
                }
            }
        }
        return new Cell[] { cell1, cell2 };
    }


    private void breakCellWalls(Cell cell1, Cell cell2){

        int xCell1 = cell1.getCellID() / mazeWidth;
        int yCell1 = cell1.getCellID() % mazeWidth;

        int xCell2 = cell2.getCellID() / mazeWidth;
        int yCell2 = cell2.getCellID() % mazeWidth;

        int deltaX = xCell1 - xCell2;
        int deltaY = yCell1 - yCell2;

        if(deltaX == -1){
            System.out.println("breaking " + cell1.getCellID() + " bottom and " + cell2.getCellID() + " top");
            cell1.getCellGraphics().removeBottomWall();
            cell2.getCellGraphics().removeTopWall();
        }
        else if(deltaX == 1){
            System.out.println("breaking " + cell1.getCellID() + " top and " + cell2.getCellID() + " bottom");
            cell1.getCellGraphics().removeTopWall();
            cell2.getCellGraphics().removeBottomWall();
        }
        else if(deltaY == -1){
            System.out.println("breaking " + cell1.getCellID() + " right and " + cell2.getCellID() + " left");
            cell1.getCellGraphics().removeRightWall();
            cell2.getCellGraphics().removeLeftWall();
        }
        else if(deltaY == 1){
            System.out.println("breaking " + cell1.getCellID() + " left and " + cell2.getCellID() + " right");
            cell1.getCellGraphics().removeLeftWall();
            cell2.getCellGraphics().removeRightWall();
        }

        System.out.println("x: " + xCell1 + " y: " + yCell1);
        System.out.println("x: " + xCell2 + " y: " + yCell2);
    }

}
