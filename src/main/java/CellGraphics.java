import java.awt.*;
import java.awt.geom.Line2D;

public class CellGraphics extends Panel {

    private Cell cell;
    private boolean topWall, bottomWall, leftWall, rightWall;

    private boolean showCellID;

    public CellGraphics(){

    }
    public CellGraphics(Cell cell){
        this.cell = cell;
        setTopWall(true);
        setBottomWall(true);
        setLeftWall(true);
        setRightWall(true);
        showCellID = true;
        initializeCellGraphics();
    }

    private void initializeCellGraphics(){
        this.setPreferredSize(new Dimension(60,60));

    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(this.getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.BLACK);
        float thickness = 10.0f;
        g2d.setStroke(new BasicStroke(thickness));

        int w = getWidth();
        int h = getHeight();

        if (topWall) {
            g2d.draw(new Line2D.Float(0, 0, w, 0));
        }
        if (bottomWall) {
            g2d.draw(new Line2D.Float(0, h, w, h));
        }
        if (leftWall) {
            g2d.draw(new Line2D.Float(0, 0, 0, h));
        }
        if (rightWall) {
            g2d.draw(new Line2D.Float(w, 0, w, h));
        }

        if(showCellID){
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));

            FontMetrics metrics = g2d.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(String.valueOf(cell.getCellID()))) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

            g2d.drawString(String.valueOf(cell.getCellID()), x, y);
        }


    }

    public boolean isTopWall() {
        return topWall;
    }

    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    public boolean isBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public void removeTopWall() {
        topWall = false;
        repaint();
    }

    public void removeBottomWall() {
        bottomWall = false;
        repaint();
    }

    public void removeLeftWall() {
        leftWall = false;
        repaint();
    }

    public void removeRightWall() {
        rightWall = false;
        repaint();
    }

    public void setSelected(){
        this.setBackground(Color.BLUE);
        this.repaint();
    }

    public void setUnselected(){
        this.setBackground(Color.WHITE);
        this.repaint();
    }

    public void toggleShowCellID(){
        showCellID = !showCellID;
        repaint();
    }

}
