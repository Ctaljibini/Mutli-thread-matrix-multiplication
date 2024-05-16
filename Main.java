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
    static int[][] matixC = new int[rowA][colB]; // output
    static int numberOfThread = rowA;
    
    static Random random = new Random();
    
    // txt'ten matrix'i ve boyutları
    public static int[][] readMatrix(String fileName){
        int[][] matrix = null;
        try{
            File file = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            
            String line = bufferedReader.readLine();
            String[] size = line.split(" ");
            int row = Integer.parseInt(size[0]);
            int col = Integer.parseInt(size[1]);
            
            matrix = new int[row][col];

            for(int i = 0; i < row; i++){
                line = bufferedReader.readLine();
                String[] elemant = line.split(" ");
                for(int j = 0; j < col; j++){
                    matrix[i][j] = Integer.parseInt(elemant[j]); 
                }
            }
            bufferedReader.close();
        }catch(IOException e){}        
        return matrix;
    }
    public static void printer(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static void generateMatrix(int row, int col, String file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(row + " " + col+ "\n");
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    writer.write(random.nextInt(10) + " ");
                }
                writer.write("\n");
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    static void checkDimension(){
        if(colA != rowB){
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
            System.exit(1);
        }
    }

    static void writeToFile(String extension) throws FileNotFoundException{
        String[] extend = {"outputPerRow.txt", "outputPerMatrix.txt"};
        String outputFileName = "";

        if(extension.equals("row")){
            outputFileName = extend[0];
        }else if(extension.equals("matrix")){
            outputFileName = extend[1];
        }else{
            System.out.println("Invalid extension"); // dosya adı yanlış girildi.
            return;
        }  
        try(PrintWriter writer = new PrintWriter(outputFileName)){
            writer.println(colA + " " + rowB);
            for(int i = 0; i < rowA; i++){
                for(int j = 0; j < colB; j++){
                    writer.print(matixC[i][j] + " ");
                }
                writer.println();
            }
        }
    }
    static void multiplyRow(int row) {
        for (int j = 0; j < colB; j++) {
            for (int k = 0; k < rowA; k++) {
                matixC[row][j] += matixA[row][k] * matixB[k][j];
            }
        }
    }
    
    static void multiThreadPerRow() throws InterruptedException, IOException{
        Thread[] threads = new Thread[numberOfThread];
        for(int i = 0; i < numberOfThread; i++){
            final int row = i;
            threads[i] = new Thread(() -> multiplyRow(row));
            threads[i].start();
        }
        // Tüm threadlerin bitmesini bekleyelim
        try{
            for(Thread thread : threads){
                thread.join();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        writeToFile("row");
        printer(matixC);
    }

    static void matMatrix() throws IOException{
        for(int i = 0; i < rowA; i++){
            for(int j = 0; j < colB; j++){
                for(int k = 0; k < colA; k++){
                    matixC[i][j] += matixA[i][k] * matixB[k][j];
                }
            }
        }
        writeToFile("matrix");
    }

    public static void main(String[] args) throws InterruptedException, IOException {  
        String file1 = "matrixA.txt"; 
        String file2 = "matrixB.txt"; 

        long startTime;
        long finshTime;
        
        
        if(args.length == 2){
            fileName[0] = args[0];
            fileName[1] = args[1];
            
        }else if(args.length == 4){
            try{
                rowA = Integer.parseInt(args[0]);
                colA = Integer.parseInt(args[1]);
                rowB = Integer.parseInt(args[2]);
                colB = Integer.parseInt(args[3]);                
            }catch(NumberFormatException e){}
            checkDimension();
            startTime = System.currentTimeMillis();
            generateMatrix(rowA, colA, file1);
            generateMatrix(rowB, colB, file2);
            finshTime = System.currentTimeMillis();
            System.out.println("Time take for generate matrix is: "+(finshTime - startTime));
           // matixC = new int[rowA][colB];
            startTime = System.currentTimeMillis();
            matMatrix();
            finshTime = System.currentTimeMillis();
            System.out.println("Time take for multi thread is: "+ (finshTime - startTime));
            System.out.println(startTime);
            System.out.println(finshTime);
            //multiThreadPerRow();
           // printer(matixC);
        }else if(args.length == 0){
            /*
            */
            rowA = 2;
            colA = 2;
            rowB = 2;
            colB = 2;

            generateMatrix(rowA, colA, file1);
            generateMatrix(rowB, colB, file2);
            matixC = new int[rowA][colB];
            matMatrix();
        }
        /*
        writeToFile("row");
        writeToFile("matrix");
        rowA = Integer.parseInt(args[0]);
        colA = Integer.parseInt(args[1]);
        generateMatrix(rowA, colA, args[2]);
        * 
        System.out.println(rowA);
        System.out.println(rowB);
        System.out.println(colA);
        System.out.println(colB);
        
        matixA = readMatrix(fileName[0]);
        matixB = readMatrix(fileName[1]); 
        printer(matixA);
        System.out.println("***");
        printer(matixB);
        */
        
    }
}