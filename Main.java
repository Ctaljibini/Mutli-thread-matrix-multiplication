import java.io.*;
import java.util.*;

/**
 * Main
 */
public class Main {
    static String[] fileName = new String[3]; // A, B and output
    static int rowA, colA, rowB, colB;

    static int[][] matixA = new int[rowA][colA];
    static int[][] matixB = new int[rowB][colB];
    static int[][] matixC = new int[rowA][colB]; // output matrix.
    static int numberOfThread;
    
    static Random random = new Random();
    
    // txt'ten matrix'i ve boyutları oku
    public static int[][] readMatrix(String fileName) {
        int[][] matrix = null;
        try {
            File file = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            
            String line = bufferedReader.readLine();
            String[] size = line.split(" ");
            int row = Integer.parseInt(size[0]);
            int col = Integer.parseInt(size[1]);
            
            matrix = new int[row][col];

            for (int i = 0; i < row; i++) {
                line = bufferedReader.readLine();
                String[] elemant = line.split(" ");
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = Integer.parseInt(elemant[j]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static void printer(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void generateMatrix(int row, int col, String file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(row + " " + col + "\n");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    writer.write(random.nextInt(10) + " ");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void checkDimension() {
        if (colA != rowB) {
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
            System.exit(1);
        }
    }

    static void writeToFile(String extension) throws FileNotFoundException {
        String[] extend = {"outputPerRow.txt", "outputPerMatrix.txt"};
        String outputFileName = "";

        if (extension.equals("row")) {
            outputFileName = extend[0];
        } else if (extension.equals("matrix")) {
            outputFileName = extend[1];
        } else {
            System.out.println("Invalid extension"); // dosya adı yanlış girildi.
            return;
        }
        try (PrintWriter writer = new PrintWriter(outputFileName)) {
            writer.println(rowA + " " + colB);
            for (int i = 0; i < rowA; i++) {
                for (int j = 0; j < colB; j++) {
                    writer.print(matixC[i][j] + " ");
                }
                writer.println();
            }
        }
    }

    static void multiplyRow(int row) {
        for (int j = 0; j < colB; j++) {
            for (int k = 0; k < colA; k++) { // Corrected to colA
                matixC[row][j] += matixA[row][k] * matixB[k][j];
            }
        }
    }
    static void multiThreadPerRow() throws InterruptedException, IOException {
      long[] startTime = new long[numberOfThread];
      long[] finishTime = new long[numberOfThread];
      Thread[] threads = new Thread[numberOfThread];
      for(int i = 0; i < numberOfThread; i++) {
        final int row = i;
        threads[i] = new Thread(() -> multiplyRow(row));
        startTime[i] = System.currentTimeMillis();
        threads[i].start();
        }
      int i = 0;
      for(Thread thread : threads) {
          thread.join();
          finishTime[i] = System.currentTimeMillis();
          System.out.println("Thread "+ i +" time: "+(finishTime[i] - startTime[i]));
          ++i;
        }
        //System.out.println("thread "+i+" time: "+ (finishTime - startTime));
        writeToFile("row");
        //printer(matixC);
    }

    static void matMatrix() throws IOException {
        for (int i = 0; i < rowA; i++) {
            for (int j = 0; j < colB; j++) {
                for (int k = 0; k < colA; k++) {
                    matixC[i][j] += matixA[i][k] * matixB[k][j];
                }
            }
        }
        writeToFile("matrix");
        printer(matixC);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String file1 = "matrixA.txt";
        String file2 = "matrixB.txt";

        long startTime;
        long finshTime;

        if (args.length == 2) {
            fileName[0] = args[0];
            fileName[1] = args[1];
            matixA = readMatrix(fileName[0]);
            matixB = readMatrix(fileName[1]);
            rowA = matixA.length;
            colA = matixA[0].length;
            rowB = matixB.length;
            colB = matixB[0].length;
            checkDimension();
            matixC = new int[rowA][colB];
            numberOfThread = rowA;
            multiThreadPerRow();
        } else if (args.length == 4) {
            try {
                rowA = Integer.parseInt(args[0]);
                colA = Integer.parseInt(args[1]);
                rowB = Integer.parseInt(args[2]);
                colB = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            checkDimension();
            startTime = System.currentTimeMillis();
            generateMatrix(rowA, colA, file1);
            generateMatrix(rowB, colB, file2);
            finshTime = System.currentTimeMillis();
            System.out.println("Time taken to generate matrices: " + (finshTime - startTime) + " ms");

            matixA = readMatrix(file1);
            matixB = readMatrix(file2);
            matixC = new int[rowA][colB];
            numberOfThread = rowA;

            startTime = System.currentTimeMillis();
            multiThreadPerRow();
            finshTime = System.currentTimeMillis();
            System.out.println("Time taken for multi-threaded row multiplication: " + (finshTime - startTime) + " ms");
        } else if (args.length == 0) {
            // Default values
            rowA = 3;
            colA = 3;
            rowB = 3;
            colB = 3;

            matixA = new int[][] {{1, 4,3}, {2,5, 4}, {4, 3, 2}};
            matixB = new int[][] {{4, 5, 3}, {5, 4, 3}, {1, 5, 2}};
            matixC = new int[rowA][colB];
            numberOfThread = rowA;

            multiThreadPerRow();
        }
    }
}
