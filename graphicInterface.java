/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package java_project;

/**
 *
 * @author mauud
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author maud sananikone s225004
 */
public class graphicInterface
        extends JFrame
        implements ActionListener {

    JFrame f;
    JButton luButton;
    JButton invButton;
    JButton clearButton;
    JButton load;
    JButton save;
    JTextArea matrixInput;
    JTextArea vectorInput;
    JTextArea outputText;
    //  JScrollPane output; 
    JScrollPane matrixInScroll;
    JScrollPane vectorScroll;

    JLabel labelOne;
    JLabel labelTwo;
    JLabel labelError;

    /*
     *contructor by default
     */
    public graphicInterface() {
        //the three button
        luButton = new JButton("LU");
        invButton = new JButton("Inverse");
        clearButton = new JButton("Clear");
        load = new JButton("Load");
        save = new JButton("Save");

        //for upper side of the frame  
        labelOne = new JLabel(" A = ");
        labelTwo = new JLabel(" b = ");
        matrixInput = new JTextArea("input your matrix");
        vectorInput = new JTextArea("input your vector");

        matrixInScroll = new JScrollPane(matrixInput);

        vectorScroll = new JScrollPane(vectorInput);

        //for the botton side of the frame
        outputText = new JTextArea("here the result");

    }

    public JPanel contentPane(int lengthFrame, int widthFrame, int middleWidth, int upWidth) {

        int widthLabel = 30;
        labelOne.setPreferredSize(new Dimension(widthLabel, widthLabel));
        labelTwo.setPreferredSize(new Dimension(widthLabel, widthLabel));
        labelError = new JLabel("Error field");
        labelError.setPreferredSize(new Dimension(10, 50));

        JPanel mainPanel = new JPanel();
        JPanel upPanel = new JPanel();
        upPanel.setPreferredSize(new Dimension(lengthFrame, upWidth));

        JPanel middlePanel = new JPanel();
        middlePanel.setSize(new Dimension(lengthFrame, middleWidth));
        JPanel bottomPanel = new JPanel();
        
        matrixInScroll.setPreferredSize(new Dimension(lengthFrame / 3, upWidth));

        vectorScroll.setPreferredSize(new Dimension((lengthFrame / 3), upWidth));

        JScrollPane outputScroll = new JScrollPane(outputText);
        outputScroll.setPreferredSize(new Dimension((widthFrame / 2) - 30, (lengthFrame - 100)));

        luButton.addActionListener(this);
        invButton.addActionListener(this);
        clearButton.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
 
        upPanel.add(labelOne);
 
        upPanel.add(matrixInScroll);
        upPanel.add(labelTwo);

        upPanel.add(vectorScroll);

        

        JPanel middlePanelCenter = new JPanel();
        BorderLayout border_bis = new BorderLayout();
        middlePanel.setLayout(border_bis);
        middlePanelCenter.setSize(new Dimension(lengthFrame / 2, 20));
        JPanel errorPanel = new JPanel();

        middlePanelCenter.add(luButton);
        middlePanelCenter.add(invButton);
        middlePanelCenter.add(clearButton);
        middlePanelCenter.add(save);
        middlePanelCenter.add(load);

        errorPanel.add(labelError);
        middlePanel.add(labelError, border_bis.NORTH);
        middlePanel.add(middlePanelCenter, border_bis.CENTER);

        BorderLayout bottomLayout = new BorderLayout();
        bottomPanel.setLayout(bottomLayout);
        bottomPanel.add(outputScroll, bottomLayout.NORTH);
        bottomPanel.add(labelError, bottomLayout.SOUTH);
        //bottomPanel.add(, bottomLayout.SOUTH);

        BorderLayout border = new BorderLayout();
        border.setHgap(20);
        border.setVgap(0);
        mainPanel.setLayout(border);
        mainPanel.add(upPanel, border.NORTH);
        mainPanel.add(middlePanel, border.CENTER);
        mainPanel.add(bottomPanel, border.SOUTH);

        return mainPanel;

    }

    public void createFrame() {
        System.out.println("ASSIGNMENT");
        int lengthFrame, widthFrame, middleWidth, upWidth;
        lengthFrame = 500;
        widthFrame = 800;
        middleWidth = 90;
        upWidth = widthFrame / 4;
        JFrame f = new JFrame("Application");
        f.setSize(lengthFrame, widthFrame);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JPanel mainPanel = new JPanel();
        f.pack();
        //f.add(mainPanel);
        //   f.add(assignment.contentPane(lengthFrame, widthFrame, middleWidth, upWidth));
        f.add(this.contentPane(lengthFrame, widthFrame, middleWidth, upWidth));
        f.setSize(lengthFrame, widthFrame);
        //This will center the JFrame in the middle of the screen 
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    public ArrayList<Double> getVectorFromUser() {
        String textFromGui;
        //to take from the JtextArea for now into string
        textFromGui = vectorInput.getText();
        //  System.out.println("the text from the matrix space is :"+textFromGui);
        //we split for each lines
        textFromGui = textFromGui.trim();
        //String[] stringVector = textFromGui.split("\\s");
        String[] stringVector = textFromGui.split(" +");
        int size = stringVector.length;
        ArrayList<Double> vectorFromUser = new ArrayList();

        for (int i = 0; i < size; i++) {
            String valueString = stringVector[i];
            double elementVector = Double.parseDouble(stringVector[i]);
            vectorFromUser.add(i, elementVector);
            //stringMatrixCols[j]
        }
        return vectorFromUser;

    }

    public matrix getMatrixFromUser() {
        String textFromGui;
        //lets try to take from the JtextArea for now into string
        textFromGui = matrixInput.getText();
        textFromGui = textFromGui.trim();
        //System.out.println("the text from the matrix space is :"+textFromGui);
        //we split for each lines
        String[] stringMatrixRows = textFromGui.split("[\n]");
        int rows = stringMatrixRows.length;
        // String[] stringMatrixCols ;//=  textFromGui.split("[\n]");
        //let get the number of row and col of the matrix
        String[] firstLine = stringMatrixRows[0].split(" +");
        int cols = firstLine.length;
        matrix mat = new matrix(rows, cols);

        //we split each string row for each space
        for (int i = 0; i < rows; i++) {
            String valueString = stringMatrixRows[i];
            valueString = valueString.trim();
            String[] stringMatrixCols = valueString.split(" +");
            //we convert all the string of the line into double
            for (int j = 0; j < stringMatrixCols.length; j++) {
                //remove the space at the beginning

                double valueMatrix = Double.parseDouble(stringMatrixCols[j]);
                (mat.v).add((i * stringMatrixCols.length) + j, valueMatrix);
                //stringMatrixCols[j]
            }

        }

        return mat;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == luButton) {
            DecimalFormat df = new DecimalFormat("####0.00000000");

            matrix matrixFromUser = new matrix();
            ArrayList<Double> vectorFromUser = new ArrayList();
            matrixFromUser = getMatrixFromUser();
            if (squareMatrixTest(matrixFromUser) == false) {
                labelError.setText("ERROR IN THE MATRIX INPUT");
            } else {
                vectorFromUser = getVectorFromUser();
                if (problemSize(vectorFromUser, matrixFromUser) == false) {
                    labelError.setText("SIZE OF MATRIX AND VECTOR DON'T MATCH ");
                } else {

                    System.out.println("LU Decomposition with scaled partial pivoting");
                    String titre = "LU Decomposition with scaled partial pivoting \n";
                    matrixFromUser.show();
                    System.out.println("the original vector is:");
                    for (int i = 0; i < vectorFromUser.size(); i++) {
                        System.out.print(vectorFromUser.get(i) + "\t");
                    }
                    System.out.println();

                    String outputMatrixString = matrixFromUser.matrixIntoString(df);
                    //-------------------------------------------------------------------------------
                    // the vector into String
                    //-------------------------------------------------------------------------------
                    String outputVectorString = "original vector" + "\n";
                    String originalVector = vectorIntoString(outputVectorString, vectorFromUser, df);
                    //-------------------------------------------------------------------------------
                    // END    the vector into String
                    //-------------------------------------------------------------------------------

                    int n = matrixFromUser.getNcols();
                    //display the lower and upper matrix
                    matrix lower = new matrix(n, n);
                    matrix upper = new matrix(n, n);
                    ArrayList<Double> solutionVector = new ArrayList(n);

                    matrix reorderMatrix = new matrix(n, n);
                    ArrayList<Double> reorderVector = new ArrayList();
                    matrix p = new matrix(n, n);
                    p = matrixFromUser.reorder();
                    reorderMatrix = p.multi(matrixFromUser);
                    reorderVector = p.multi(vectorFromUser);

                    lower = reorderMatrix.compute_lower();
                    upper = reorderMatrix.compute_upper();
                    if (matrixFromUser.singularTest(upper) == false) {
                        String singular = "Singular Matrix NO LU DECOMPOSITION \n";
                        outputText.setText(titre + "original matrix \n" + outputMatrixString + originalVector + singular);
                        labelError.setText("No LU DECOMPOSITION singular matrix");
                    } else {

                        String lowerMatrixString = lower.matrixIntoString(df);
                        String upperMatrixString = upper.matrixIntoString(df);

                        String solutionLuFactorisation = "Lower Matrix \n" + lowerMatrixString + "Upper Matrix \n" + upperMatrixString;
                        String solutionString = "solution \n";

                        solutionVector = lower.lu_solve(upper, reorderVector);

                        ArrayList<Double> pivotArray = new ArrayList();
                        pivotArray = matrixFromUser.pivotArray(p);

                        String arrayString = "pivot array \n";
                        DecimalFormat dfe = new DecimalFormat("#.#");
                        String arrayResultString = vectorIntoString(arrayString, pivotArray, dfe);

                        String solutionVectorString = vectorIntoString(solutionString, solutionVector, df);

                        outputText.setText(titre + "original matrix \n" + outputMatrixString + originalVector + solutionLuFactorisation + solutionVectorString + arrayResultString);

                    }
                }
            }
        } else {
            if (e.getSource() == invButton) {

                DecimalFormat df = new DecimalFormat("####0.00000000");

                String titre = "Matrix inversion \n";
                matrix matrixFromUser = new matrix();
                ArrayList<Double> vectorFromUser = new ArrayList();
                matrixFromUser = getMatrixFromUser();
                if (squareMatrixTest(matrixFromUser) == false) {
                    labelError.setText("ERROR IN THE MATRIX INPUT");
                } else {
                    vectorFromUser = getVectorFromUser();
                    if (problemSize(vectorFromUser, matrixFromUser) == false) {
                        labelError.setText("SIZE OF MATRIX AND VECTOR DON'T MATCH ");
                    } else {
                        matrixFromUser.show();

                        String outputMatrixString = matrixFromUser.matrixIntoString(df);
                        //-------------------------------------------------------------------------------
                        // the vector into String
                        //-------------------------------------------------------------------------------
                        String outputVectorString = "\n original vector" + "\n";

                        String originalVector = vectorIntoString(outputVectorString, vectorFromUser, df);
                        //-------------------------------------------------------------------------------
                        // END    the vector into String
                        //-------------------------------------------------------------------------------

                        int n = matrixFromUser.getNcols();
                        //display the lower and upper matrix
                        matrix lower = new matrix(n, n);
                        matrix upper = new matrix(n, n);
                        matrix inverseMatrix = new matrix(n, n);

                        matrix reorderMatrix = new matrix(n, n);
                        ArrayList<Double> reorderVector = new ArrayList();
                        matrix p = new matrix(n, n);
                        p = matrixFromUser.reorder();
                        reorderMatrix = p.multi(matrixFromUser);
                        reorderVector = p.multi(vectorFromUser);

                        lower = reorderMatrix.compute_lower();
                        upper = reorderMatrix.compute_upper();
                        if (matrixFromUser.singularTest(upper) == false) {
                            String singular = "Singular Matrix NO LU DECOMPOSITION \n";
                            outputText.setText(titre + "original matrix \n" + outputMatrixString + originalVector + singular);
                            labelError.setText("No LU DECOMPOSITION singular matrix");
                        }

                        String lowerMatrixString = lower.matrixIntoString(df);
                        String upperMatrixString = upper.matrixIntoString(df);

                        String solutionLuFactorisation = "\n Lower Matrix \n" + lowerMatrixString + "\n Upper Matrix \n" + upperMatrixString;

                        //inverseMatrix = reorderMatrix.inverse(lower, upper);

                        inverseMatrix = matrixFromUser.inverse(lower, upper);
                        //inverseMatrix = inverseMatrix.multi(p);
                        
                        inverseMatrix.show();
                        String inverseMatrixString = "\n inverse Matrix \n" + inverseMatrix.matrixIntoString(df);

                        // double det = matrixFromUser.calculateDeter(upper);
                        double det = reorderMatrix.calculateDeter(upper);
                        System.out.println(det);
                        DecimalFormat dft = new DecimalFormat("#.#");
                        String determinantString = "\n Determinant :" + dft.format(det);

                        outputText.setText(titre + "\n original matrix \n" + outputMatrixString + originalVector + solutionLuFactorisation + inverseMatrixString + determinantString);

                    }
                }
            } else {
                if (e.getSource() == clearButton) {
                    matrixInput.setText("");
                    vectorInput.setText("");
                    outputText.setText("");

                } else {
                    if (e.getSource() == save) {
                        System.out.println("save clicked");
                        File fileMat = new File("matrix.txt");
                        File fileVect = new File("vector.txt");

                        matrix matrixA = new matrix();
                        matrixA = getMatrixFromUser();
                        matrixSerialisation(fileMat, matrixA);

                        ArrayList<Double> vectB = new ArrayList();
                        vectB = getVectorFromUser();
                        vectorSerialisation(fileVect, vectB);

                    } else {
                        if (e.getSource() == load) {
                            //DecimalFormat df = new DecimalFormat("####0.00000000");
                            File fileMat = new File("matrix.txt");
                            File fileVect = new File("vector.txt");
                            matrix matrixFromFile = new matrix();
                            readMatrix(fileMat, matrixFromFile);

                            ArrayList<Double> vectorFromFile = new ArrayList();
                            readVector(fileVect, vectorFromFile);
                        }
                    }
                }

            }
        }

    }
    /*
     * to write a matrix into a file
     */

    public String vectorIntoString(String outputVectorString, ArrayList<Double> vect, DecimalFormat df) {

        //DecimalFormat df = new DecimalFormat("####0.00000000");
        for (int i = 0; i < vect.size(); i++) {
            String valueVector = df.format(vect.get(i));
            outputVectorString = outputVectorString + "  " + valueVector;
        }
        outputVectorString = outputVectorString + "\n";

        return outputVectorString;

    }

    public void matrixSerialisation(File file, matrix mat) {
        try {
            if (!file.exists()) {
                System.out.println("file doesnt exist");
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fop);
            oos.writeObject(mat);  // Write the String array to a file
            oos.close();
            System.out.println("Save of matrix Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void vectorSerialisation(File file, ArrayList<Double> vect) {
        try {
            if (!file.exists()) {
                System.out.println("file doesnt exist");
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fop);
            oos.writeObject(vect);  // Write the String array to a file
            oos.close();
            System.out.println("Save of Vector Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void readMatrix(File file, matrix mat) {
        try {
            FileInputStream fop = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fop);
            try {
                DecimalFormat df = new DecimalFormat("####0.####");
                mat = (matrix) oos.readObject();
                String matString = mat.matrixIntoString(df);
                matrixInput.setText(matString);
                //matrixInScroll.setText(matString);
            } catch (ClassNotFoundException ecp) {
                System.out.printf("it is not a matrix");
            }
            oos.close();
            System.out.println("matrix read - Done");

        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public void readVector(File file, ArrayList<Double> vect) {
        try {
            FileInputStream fop = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fop);
            try {
                DecimalFormat df = new DecimalFormat("####0.####");
                vect = (ArrayList<Double>) oos.readObject();
                String vectString = "";
                String vectStringResult = vectorIntoString(vectString, vect, df);
                vectorInput.setText(vectStringResult);
                //matrixInScroll.setText(matString);
            } catch (ClassNotFoundException ecp) {
                System.out.printf("it is not a vector");
            }
            oos.close();
            System.out.println("vector read - Done");

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    public boolean problemSize(ArrayList<Double> vect, matrix m) {
        int vectSize, matrixSize;
        vectSize = vect.size();
        matrixSize = m.getNcols();

        if (vectSize != matrixSize) {
            return false;
        }

        return true;
    }

    public boolean squareMatrixTest(matrix m) {
        int cols = m.getNcols();
        int rows = m.getNrows();
        if (cols != rows) {
            return false;
        }
        return true;
    }
}

