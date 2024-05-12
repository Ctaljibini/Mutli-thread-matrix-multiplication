import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Main
 */
public class Main {  
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


    public static void main(String[] args) {
        String fileName = args[0];
        String fileName1 = args[1];

   
        
        int[][] matrix1 = readMatrix(fileName);
        int[][] matrix2 = readMatrix(fileName1); 
        printer(matrix1);
        System.out.println("****");
       printer(matrix2);
    }
}