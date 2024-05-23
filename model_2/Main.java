import java.io.*;
import java.util.*;
import java.nio.file.*; // file exists and is readable

public class Main {

    public Main(){}

    public static int[][] generateMatrix(int row, int col) {
        int[][] matrix = new int[row][col];
        Random random = new Random();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = random.nextInt(5);
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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

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
            // TODO: handle exception
            e.printStackTrace();
        }
        return matrix;
    }

    public void printer(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
            String[] matrixFiles = new String[] {"matrixA.txt", "matrixB.txt"}; 
            String[] outputFiles = new String[] {"outputPerMatrix.txt", "outputPerRow.txt"};
            
            int rowA, colA, rowB, colB;
            int[][] matrixA;
            int[][] matrixB;
            int[][] matrixC;

            SingleThread singleThread = new SingleThread();
            if(args.length == 2){ // Matrix are read by the user
                matrixA = readFromFile(args[0]);
                matrixB = readFromFile(args[1]);
                
                System.out.println("Multiplication by Single-Thread");
                System.out.println("*******************************");
                matrixC = singleThread.matMatrix(matrixA, matrixB);
                writeToFile(outputFiles[0], matrixC);
                
                multiThread mThread = new multiThread(matrixA, matrixB); 
                System.out.println("Multiplication by Multi-Thread");
                System.out.println("*******************************");
                matrixC = mThread.multiply();
                writeToFile(outputFiles[1], matrixC);
            }else if(args.length == 0){
                matrixA = generateMatrix(10, 10);
                matrixB = generateMatrix(10, 10);

                
                System.out.println("Multiplication by Single-Thread");
                System.out.println("*******************************");
                matrixC = singleThread.matMatrix(matrixA, matrixB);
                writeToFile(outputFiles[0], matrixC);
                
                multiThread mThread = new multiThread(matrixA, matrixB); 
                System.out.println("Multiplication by Multi-Thread");
                System.out.println("*******************************");
                matrixC = mThread.multiply();
                writeToFile(outputFiles[1], matrixC);

            }


            
    }

}
