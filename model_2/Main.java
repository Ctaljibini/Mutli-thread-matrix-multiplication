import java.util.Random;


public class Main {
    protected int rowA, colA, rowB, colB;
    protected int[][] matrixA;
    protected int[][] matrixB;
    protected int[][] matrixC;

    public Main(int rowA, int colA, int rowB, int colB, int[][] matrixA, int[][] matrixB){
        this.rowA = rowA;
        this.colA = colA;
        this.rowB = rowB;
        this.colB = colB;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = new int[rowA][colB];

    }

    
    public static int[][] generateMatrix(int row, int col){
        int[][] matrix = new int[row][col];
        Random random = new Random();

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                matrix[i][j] = random.nextInt(5);
            }
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


    public static void main(String[] args) {
      int rowA = 2, colA = 2, rowB = 2, colB = 2;
      
      int[][] matrixA = new int[rowA][colA];
      int[][] matrixB = new int[rowB][colB];
      int[][] matrixC = new int[rowA][colB];

      matrixA = generateMatrix(rowA, colB);
      matrixB = generateMatrix(rowB, colB);
      
      Main m = new Main(rowA, colA, rowB, colB, matrixA, matrixB);
      m.printer(matrixA);
      System.out.println();
      m.printer(matrixB);
      System.out.println();

      SingleThread singleThread = new SingleThread();
      matrixC = singleThread.matMatrix(matrixA, matrixB);
      m.printer(matrixC);      
    }
  
}
