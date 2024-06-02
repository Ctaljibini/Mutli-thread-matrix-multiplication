public class MultiThreadMultiplication {
  private int[][] matA;
  private int[][] matB;
  private int[][] matC;
  private int numberOfThreads;

  public MultiThreadMultiplication(int[][] matA, int[][] matB) {
      this.matA = matA;
      this.matB = matB;
      this.matC = new int[matA.length][matB[0].length];
      this.numberOfThreads = matA.length;  // Number of threads is equal to the number of rows
  }

  class Worker implements Runnable {
      int row;

      public Worker(int row) {
          this.row = row;
      }

      @Override
      public void run() {
          for (int j = 0; j < matB[0].length; j++) {
              for (int k = 0; k < matA[0].length; k++) {
                  matC[row][j] += matA[row][k] * matB[k][j];
              }
          }
      }
  }

  public void multiply() {
      Thread[] threads = new Thread[numberOfThreads];
      long startTime = System.nanoTime();

      for (int i = 0; i < numberOfThreads; i++) {
          threads[i] = new Thread(new Worker(i));
          threads[i].start();
      }

      for (int i = 0; i < numberOfThreads; i++) {
          try {
              threads[i].join();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }

      long endTime = System.nanoTime();
      System.out.println("Multi-thread time: " + (endTime - startTime) / 1e6 + " ms");
  }

  public int[][] getResultMatrix() {
      return matC;
  }  
}
