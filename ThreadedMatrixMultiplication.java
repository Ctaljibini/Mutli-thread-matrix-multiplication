public class ThreadedMatrixMultiplication {
  static final int ROW_SIZE = 5;
  static final int COL_SIZE = 5;
  static final int NUM_THREADS = ROW_SIZE;

  static double[][] matrixA = new double[ROW_SIZE][COL_SIZE];
  static double[][] matrixB = new double[ROW_SIZE][COL_SIZE];
  static double[][] matrixC = new double[ROW_SIZE][COL_SIZE];

  public static void main(String[] args) {
      // Matrislere rastgele değerler atayalım
      initializeMatrices();

      // Thread'leri başlatıp çalıştıralım
      long startTime = System.currentTimeMillis();
      Thread[] threads = new Thread[NUM_THREADS];

      for (int i = 0; i < NUM_THREADS; i++) {
          final int row = i;
          threads[i] = new Thread(() -> multiplyRow(row));
          threads[i].start();
      }

      // Tüm threadlerin bitmesini bekleyelim
      try {
          for (Thread thread : threads) {
              thread.join();
          }
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      long endTime = System.currentTimeMillis();
      long executionTime = endTime - startTime;

      // Matris C'yi yazdıralım
      System.out.println("Matris C:");
      printMatrix(matrixC);

      // İşlem süresini yazdıralım
      System.out.println("Toplam işlem süresi: " + executionTime + " ms");
  }

  // Matrisleri rastgele değerlerle dolduran yardımcı metot
  static void initializeMatrices() {
      for (int i = 0; i < ROW_SIZE; i++) {
          for (int j = 0; j < COL_SIZE; j++) {
              matrixA[i][j] = Math.random() *10;
              matrixB[i][j] = Math.random() *10;
          }
      }
  }

  // İki matrisin çarpımını bir satırı hesaplayan metot
  static void multiplyRow(int row) {
      for (int j = 0; j < COL_SIZE; j++) {
          for (int k = 0; k < ROW_SIZE; k++) {
              matrixC[row][j] += matrixA[row][k] * matrixB[k][j];
          }
      }
  }

  // Matrisi ekrana yazdıran yardımcı metot
  static void printMatrix(double[][] matrix) {
      for (int i = 0; i < ROW_SIZE; i++) {
          for (int j = 0; j < COL_SIZE; j++) {
              System.out.print(matrix[i][j] + " ");
          }
          System.out.println();
      }
  }
}
