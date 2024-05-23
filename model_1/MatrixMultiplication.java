public class MatrixMultiplication {

  private static class Worker implements Runnable {
      private final int row;
      private final int[][] A;
      private final int[][] B;
      private final int[][] C;

      public Worker(int row, int[][] A, int[][] B, int[][] C) {
          this.row = row;
          this.A = A;
          this.B = B;
          this.C = C;
      }

      @Override
      public void run() {
          int columnsB = B[0].length;
          for (int j = 0; j < columnsB; j++) {
              C[row][j] = 0;
              for (int k = 0; k < B.length; k++) {
                  C[row][j] += A[row][k] * B[k][j];
              }
          }
      }
  }

  public static int[][] multiply(int[][] A, int[][] B) {
      int rowsA = A.length;
      int columnsA = A[0].length;
      int rowsB = B.length;
      int columnsB = B[0].length;
      long startTime = 0, finshTime = 0;
      if (columnsA != rowsB) {
          throw new IllegalArgumentException("Number of columns in A must be equal to number of rows in B");
      }

      int[][] C = new int[rowsA][columnsB];
      Thread[] threads = new Thread[rowsA];

      for (int i = 0; i < rowsA; i++) {
          threads[i] = new Thread(new Worker(i, A, B, C));
          startTime = System.currentTimeMillis();
          threads[i].start();
      }

      for (int i = 0; i < rowsA; i++) {
          try {
              threads[i].join();
              finshTime = System.currentTimeMillis();
              System.out.println("time is: "+ (finshTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
      }

      return C;
  }

  public static void printMatrix(int[][] matrix) {
      for (int[] row : matrix) {
          for (int value : row) {
              System.out.print(value + " ");
          }
          System.out.println();
      }
  }

  public static void main(String[] args) {
      int[][] A = {
          {1, 2, 3},
          {4, 5, 6},
          {7, 8, 9}
      };

      int[][] B = {
          {9, 8, 7},
          {6, 5, 4},
          {3, 2, 1}
      };

      int[][] C = multiply(A, B);

      System.out.println("Matrix A:");
      printMatrix(A);
      System.out.println("Matrix B:");
      printMatrix(B);
      System.out.println("Matrix C (Result of A x B):");
      printMatrix(C);
  }
}
