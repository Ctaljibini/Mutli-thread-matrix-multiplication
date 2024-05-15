import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;


/**
 * Main
 */
public class Main {  
    static String[] fileName = new String[3]; // A, B and output
    static int[][] matixA;
    static int[][] matixB;
    static int[][] matixC; // output
    static int rowA, colA, rowB, colB;

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


    public static void main(String[] args) {   
        
        
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
        }
        else{
            rowA = Integer.parseInt(args[0]);
            colA = Integer.parseInt(args[1]);
            generateMatrix(rowA, colB, args[2]);
        }
        System.out.println(rowA);
        System.out.println(rowB);
        System.out.println(colA);
        System.out.println(colB);
        
        matixA = readMatrix(fileName[0]);
        matixB = readMatrix(fileName[1]); 
        printer(matixA);
        System.out.println("***");
        printer(matixB);

    }
}