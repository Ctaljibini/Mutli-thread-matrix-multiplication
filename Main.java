import java.io.*;
import java.util.*;

/**
 * Main
 */
public class Main {
    static String[] fileName = new String[3]; // A, B and output
    static int rowA, colA, rowB, colB;

    static int[][] matrixA = new int[rowA][colA];
    static int[][] matrixB = new int[rowB][colB];
    static int[][] matrixC = new int[rowA][colB]; // output matrix.
    static int numberOfThread = rowA;
    
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
        String[] extend = {"outputPerRows.txt", "outputPerMatrix.txt"}; // two cases. case one is singel thread, if the second situation is uo to namber of rows.
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
                    writer.print(matrixC[i][j] + " ");
                }
                writer.println();
            }
        }
    }

    static void multiplyRow(int row, int[][] matrixA, int[][] matrixB) {
        for (int j = 0; j < colB; j++) {
            for (int k = 0; k < colA; k++) { // Corrected to colA
                matrixC[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }
    static void multiThreadPerRow(int[][] matrixA, int[][] matrixB) throws InterruptedException, IOException {
      long[] startTime = new long[numberOfThread];
      long[] finishTime = new long[numberOfThread];
      Thread[] threads = new Thread[numberOfThread];
      
      matrixC = new int[rowA][colB];

      for(int i = 0; i < numberOfThread; i++) {
        final int row = i;
        threads[i] = new Thread(() -> multiplyRow(row, matrixA, matrixB));
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
        writeToFile("row");
    }

    static void matMatrix(int[][] matrixA, int[][] matrixB) throws IOException {
        long startTime = System.nanoTime(); 
        for (int i = 0; i < rowA; i++) {
            for (int j = 0; j < colB; j++) {
                for (int k = 0; k < rowB; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        long finshTime = System.nanoTime();
        System.out.println("Singel thread time is: "+(finshTime - startTime));
        writeToFile("matrix");
    }


    

    public static void main(String[] args) throws InterruptedException, IOException {
        String file1 = "matrixA.txt";
        String file2 = "matrixB.txt";


        if (args.length == 2) {
            fileName[0] = args[0];
            fileName[1] = args[1];
            matrixA = readMatrix(fileName[0]);
            matrixB = readMatrix(fileName[1]);
            rowA = matrixA.length;
            colA = matrixA[0].length;
            rowB = matrixB.length;
            colB = matrixB[0].length;
            numberOfThread = rowA;
            checkDimension();
            matrixC = new int[rowA][colB];
            System.out.println("Single-thead: ");
            matMatrix(matrixA, matrixB);
            System.out.println("Multi-threade: ");
            multiThreadPerRow(matrixA, matrixB);
        } else if (args.length == 4) {
            // argumaent check is ture input.
            try {
                rowA = Integer.parseInt(args[0]);
                colA = Integer.parseInt(args[1]);
                rowB = Integer.parseInt(args[2]);
                colB = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            numberOfThread = rowA;
            checkDimension();
            generateMatrix(rowA, colA, file1);
            generateMatrix(rowB, colB, file2);
            matrixA = readMatrix(file1);
            matrixB = readMatrix(file2);
            matrixC = new int[rowA][colB];
            System.out.println("Single-thead: ");
            matMatrix(matrixA, matrixB);
            System.out.println("Multi-threade: ");
            multiThreadPerRow(matrixA, matrixB);

        } else if (args.length == 0) {
            // Default values
            rowA = 1;
            colA = 1;
            rowB = 1;
            colB = 1000;
            
            numberOfThread = rowA;
            matrixC = new int[rowA][colB];

            generateMatrix(rowA, colA, file1);
            matrixA = readMatrix(file1);
            
            generateMatrix(rowB, colB, file2);
            matrixB = readMatrix(file2);

            
            checkDimension();
            System.out.println("Single-thead: ");
            matMatrix(matrixA, matrixB);
            System.out.println("Multi-threade: ");
            multiThreadPerRow(matrixA, matrixB);            
        }
    }
}
