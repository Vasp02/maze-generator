import java.util.Vector;

public class MazeManager {

    private Cell[][] cells;

    public MazeManager(){

    }
    public MazeManager(Cell[][] cells){
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public int[][] getMazeMatrix() {
        int height = cells.length;
        int width = cells[0].length;

        int[][] mazeMatrix = new int[height * 2 + 1][width * 2 + 1];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int row = i * 2;
                int col = j * 2;


                mazeMatrix[row + 1][col + 1] = 0;


                mazeMatrix[row + 1][col + 2] = cells[i][j].getCellGraphics().isRightWall() ? 1 : 0;


                mazeMatrix[row + 2][col + 1] = cells[i][j].getCellGraphics().isBottomWall() ? 1 : 0;


                mazeMatrix[row][col] = mazeMatrix[row][col + 1] = mazeMatrix[row + 1][col] = 1;
            }
        }


        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix[i].length; j++) {
                System.out.print(mazeMatrix[i][j] + " ");
            }
            System.out.println();
        }

        return mazeMatrix;
    }
}
