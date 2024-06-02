import java.io.*;
import java.util.*;


class MainClass {

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

    public static int[][] readFromFile(String fileName){
        int[][] matrix = null;
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
    public static void main(String[] args) throws FileNotFoundException {
        String[] InputFileName = { "matrixA.txt", "matrixB.txt" };
        String[] OutputFileName = { "outputPerMatrix.txt", "outputPerRow.txt" };

        int[][] matA;
        int[][] matB;

        if (args.length == 2) {
            matA = readFromFile(args[0]);
            matB = readFromFile(args[1]);
        } else {
            int defulde = 5;
            matA = generateMatrix(defulde , defulde); // Default random matrix size
            writeToFile(InputFileName[0], matA);

            matB = generateMatrix(defulde, defulde); 
            writeToFile(InputFileName[1], matB);
        }

        if (args.length == 4) {
            try {
                int rowA = Integer.parseInt(args[0]);
                int colA = Integer.parseInt(args[1]);
                int rowB = Integer.parseInt(args[2]);
                int colB = Integer.parseInt(args[3]);

                if(colA != rowB){
                    System.out.println("****************************************************************\n");
                    System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
                    System.exit(1);
                }

                matA = generateMatrix(rowA, colA);
                writeToFile(InputFileName[0], matA);

                matB = generateMatrix(rowB, colB);
                writeToFile(InputFileName[1], matB);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (matA[0].length != matB.length) {
            System.out.println("Matrix multiplication is not possible due to dimension conflicts!");
            return;
        }

        // Single thread multiplication
        SingleThreadMultiplication singleThread = new SingleThreadMultiplication(matA, matB);
        singleThread.multiply();
        int[][] resultSingle = singleThread.getResultMatrix();
        writeToFile(OutputFileName[0], resultSingle);

        

        // Multi thread multiplication
        MultiThreadMultiplication multiThread = new MultiThreadMultiplication(matA, matB);
        multiThread.multiply();
        int[][] resultMulti = multiThread.getResultMatrix();
        writeToFile(OutputFileName[1], resultMulti);
        

    }
}
