

public class SingleThread {

    public SingleThread() {
        // No-argument constructor
    }

    public int[][] matMatrix(int[][] matrixA, int[][] matrixB){
        int rowA = matrixA.length;
        int colB = matrixB[0].length;
        int rowB = matrixB.length;
        long startTime, endTime;

        int[][] matrixC = new int[rowA][colB];
        startTime = System.currentTimeMillis();
        for(int i = 0; i < rowA; i++){
            for(int j = 0; j < colB; j++){
                for(int k = 0; k < rowB; k++){
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Thread for single-thread " + (endTime - startTime) + " ms.");
        return matrixC;
    }
}
