import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JFrame {

    private Cell[][] cells;

    public Display(){
        initializeMenu();
    }

    public Display(Cell[][] cells, int w, int h, KruskalMazeGenerator kmg){
        initializeWindow(cells,w,h,kmg);
    }

    private void initializeMenu(){
        JFrame frame = new JFrame("Labyrinth Generator");
        frame.setTitle("Labyrinth Generator Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Border padding = BorderFactory.createEmptyBorder(10, 0, 0, 0); // 10 pixels padding at the top
        mainPanel.setBorder(padding);

        JPanel firstInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField firstInput = new JTextField(10);
        firstInput.setPreferredSize(new Dimension(100, 20)); // Set preferred size
        JLabel firstInputLabel = new JLabel("Height:");
        firstInputPanel.add(firstInputLabel);
        firstInputPanel.add(firstInput);



        JPanel secondInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField secondInput = new JTextField(10);
        secondInput.setPreferredSize(new Dimension(100, 20)); // Set preferred size
        JLabel secondInputLabel = new JLabel("Width:");
        secondInputPanel.add(secondInputLabel);
        secondInputPanel.add(secondInput);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton openFrameButton = new JButton("Open Labyrinth Generator");
        openFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int height = processInput(firstInput.getText());
                int width = processInput(secondInput.getText());


                Cell[][] cells = new Cell[height][width];

                for(int i = 0; i < height; i++){
                    for (int j = 0; j < width; j++){

                        int cellID = i * width + j;
                        cells[i][j] = new Cell(cellID);
                    }
                }
                KruskalMazeGenerator kmg = new KruskalMazeGenerator(cells, height, width);
                initializeWindow(cells,height,width,kmg);
            }
        });
        buttonPanel.add(openFrameButton);

        mainPanel.add(firstInputPanel);
        mainPanel.add(secondInputPanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }
    private static void initializeWindow(Cell[][]  cells, int w, int h, KruskalMazeGenerator kmg){



        JFrame frame = new JFrame("Labyrinth Generator");
        frame.setSize(1200, 1200);
        frame.getContentPane().setBackground(new Color(20,0,20));


        JPanel gridPanel = new JPanel(new GridLayout(w, h,0,0));
        gridPanel.setBackground(Color.white);

        for (Cell[] cellcolumn : cells){
            for(Cell cell : cellcolumn){
                gridPanel.add(cell.getCellGraphics());
            }
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        wrapperPanel.setBackground(new Color(20,0,20));


        wrapperPanel.add(gridPanel, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(10,10,10,10));
        menuPanel.setBackground(new Color(20,0,20));

        JButton playAnimStepButton = new JButton(">");
        playAnimStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        kmg.generateMazeStep(100);
                    }
                }).start();
            }
        });

        JLabel stepButtonLabel = new JLabel("Run one step");
        stepButtonLabel.setForeground(new Color(200,200,200));

        JLabel playAnimLabel = new JLabel("Play animation");
        playAnimLabel.setForeground(new Color(200,200,200));

        JButton playAnimFullButton = new JButton(">>>");
        playAnimFullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        kmg.generateMaze(100);
                    }
                }).start();

                playAnimFullButton.setEnabled(false);
                playAnimStepButton.setEnabled(false);
            }
        });

        JLabel allStepLabel = new JLabel("Jump to final state");
        allStepLabel.setForeground(new Color(200,200,200));

        JButton playAnimAll = new JButton(">|");
        playAnimAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        kmg.generateMaze(0);
                    }
                }).start();

                playAnimFullButton.setEnabled(false);
                playAnimStepButton.setEnabled(false);
                playAnimAll.setEnabled(false);
            }
        });

        JLabel toggleIDLabel = new JLabel("Toggle ID");
        toggleIDLabel.setForeground(new Color(200,200,200));

        JToggleButton toggleButton = new JToggleButton("Show ID");

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JToggleButton tBtn = (JToggleButton) e.getSource();
                if (tBtn.isSelected()) {
                    toggleCellID(cells);
                } else {
                    toggleCellID(cells);
                }
            }
        });

//        JLabel saveLabel = new JLabel("Save");
//        saveLabel.setForeground(new Color(200,200,200));
//
//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        MazeManager mazeManager = new MazeManager(cells);
//                        mazeManager.getMazeMatrix();
//                    }
//                }).start();
//            }
//        });

        menuPanel.add(playAnimLabel);
        menuPanel.add(playAnimFullButton);
        menuPanel.add(stepButtonLabel);
        menuPanel.add(playAnimStepButton);
        menuPanel.add(allStepLabel);
        menuPanel.add(playAnimAll);
        menuPanel.add(toggleIDLabel);
        menuPanel.add(toggleButton);
//        menuPanel.add(saveLabel);
//        menuPanel.add(saveButton);

        wrapperPanel.add(menuPanel, BorderLayout.EAST);
        frame.add(wrapperPanel);

        frame.pack();

        frame.setVisible(true);
    }

    public static void toggleCellID(Cell [][] cells){
        for(Cell[] cellcolumn : cells){
            for(Cell cell : cellcolumn){
                cell.getCellGraphics().toggleShowCellID();
            }
        }
    }

    public int processInput(String input){
        try {
            int value = Integer.parseInt(input);
            return value;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
