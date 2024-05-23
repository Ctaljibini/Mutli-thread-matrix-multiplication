

class multiThread {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixC;

    public multiThread(int[][] matrixA, int[][] matrixB){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = new int[matrixA.length][matrixB[0].length];
    }

    private int rowA = matrixA.length;
    private int colA = matrixA[0].length;
    private int rowB = matrixB.length;
    private int colB = matrixB[0].length;
    private int numberOfThread = rowA;

    public void multiplyRow(int row){
        for(int j = 0; j < colB; j++){
            for(int k = 0; k < colA; k++){
                matrixC[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }

    public int[][] multiply() {
        Thread[] threads = new Thread[numberOfThread];

        for (int i = 0; i < numberOfThread; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                multiplyRow(row);
                long endTime = System.currentTimeMillis();
                System.out.println("Thread for row " + row + " took " + (endTime - startTime) + " ms.");
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matrixC;
    }    
}
