import java.io.*;
import java.util.*;
import java.nio.file.*; // file exists and is readable

public class Main implements Runnable {

    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixC;

    private String[] OutputFileName = { "outputPerMatrix.txt", "outputPerRow.txt" };

    public Main(int[][] matrixA, int[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = new int[matrixA.length][matrixB[0].length];
    }

    long startTime = 0, finshTime = 0;

    @Override
    public void run() {
        SingleThread singleThread = new SingleThread();
        multiThread multiThread = new multiThread(matrixA, matrixB);

        System.out.println("*********************************SINGLE-THREAD*******************************");
        startTime = System.nanoTime();
        matrixC = singleThread.matMatrix(matrixA, matrixB); // singla-thread'la çarpma işlemi.
        finshTime = System.nanoTime();
        System.out.println("Multiplay martrices using single-thread: "+ (finshTime - startTime) / 1e6+ " ms."+"\n");
        
        try {
            writeToFile(OutputFileName[0], matrixC); // sonuç matrix output dosyadında yazdir.
        } catch (FileNotFoundException e) {}
        
        System.out.println("*********************************MULTI-THREADS*******************************");
        startTime = System.nanoTime();
        matrixC = multiThread.multiply(); // multi-thread'la çarpma işlemi.
        finshTime = System.nanoTime();
        System.out.println("Multiplay martrices using multi-threads: "+ (finshTime - startTime) / 1e6 + " ms."+"\n");
        System.out.println("****************************************************************");
        
        try {
            writeToFile(OutputFileName[1], matrixC); // sonuç matrix output dosyadında yazdir.
        } catch (FileNotFoundException e) {}
    }

    public static int[][] generateMatrix(int row, int col) {
        int[][] matrix = new int[row][col];
        Random random = new Random();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    public static void writeToFile(String fileName, int[][] matrix) throws FileNotFoundException {
        int row = matrix.length;
        int col = matrix[0].length;

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(row + " " + col);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    writer.print(matrix[i][j] + " ");
                }
                writer.println();
            }
        }
    }

    public static int[][] readFromFile(String fileName) throws IOException {
        int[][] matrix = null;

        // Check if the file exists and is readable
        if (!Files.exists(Paths.get(fileName)) || !Files.isReadable(Paths.get(fileName))) {
            throw new IOException("File does not exist or is not readable: " + fileName);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String line = bufferedReader.readLine();
            String[] size = line.split(" ");

            int row = Integer.parseInt(size[0]);
            int col = Integer.parseInt(size[1]);

            matrix = new int[row][col];

            for (int i = 0; i < row; i++) {
                line = bufferedReader.readLine();
                String[] element = line.split(" ");
                for (int j = 0; j < col; j++) {
                    matrix[i][j] = Integer.parseInt(element[j]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static void main(String[] args) throws IOException {

        String[] InputFileName = { "matrixA.txt", "matrixB.txt" };
        int[][] matrixA;
        int[][] matrixB;
        int rowA, colA, rowB, colB;

        System.out.println("                      WELCOME TO THR MATRIX MULTIPLIER!");

        if (args.length == 0) {
            rowA = 5;
            colA = 5;
            rowB = 5;
            colB = 5;

            matrixA = generateMatrix(rowA, colA);
            writeToFile(InputFileName[0], matrixA);

            matrixB = generateMatrix(rowB, colB);
            writeToFile(InputFileName[1], matrixB);

            Main main1 = new Main(matrixA, matrixB);
            main1.run();
        } else if (args.length == 2) {
            matrixA = readFromFile(args[0]);
            matrixB = readFromFile(args[1]);

            Main main2 = new Main(matrixA, matrixB);
            main2.run();
        } else if (args.length == 4) {
            try {
                rowA = Integer.parseInt(args[0]);
                colA = Integer.parseInt(args[1]);
                rowB = Integer.parseInt(args[2]);
                colB = Integer.parseInt(args[3]);

                matrixA = generateMatrix(rowA, colA);
                writeToFile(InputFileName[0], matrixA);

                matrixB = generateMatrix(rowB, colB);
                writeToFile(InputFileName[1], matrixB);

                Main main3 = new Main(matrixA, matrixB);
                main3.run();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}