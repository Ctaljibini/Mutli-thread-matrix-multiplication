class SingleThreadMultiplication {
  private int[][] matA;
  private int[][] matB;
  private int[][] matC;

  public SingleThreadMultiplication(int[][] matA, int[][] matB) {
      this.matA = matA;
      this.matB = matB;
      this.matC = new int[matA.length][matB[0].length];
  }

  public void multiply() {
      long startTime = System.nanoTime();
      for (int i = 0; i < matA.length; i++) {
          for (int j = 0; j < matB[0].length; j++) {
              for (int k = 0; k < matA[0].length; k++) {
                  matC[i][j] += matA[i][k] * matB[k][j];
              }
          }
      }
      long endTime = System.nanoTime();
      System.out.println("Single-thread time: " + (endTime - startTime) / 1e6 + " ms");
  }

  public int[][] getResultMatrix() {
      return matC;
  }
}
