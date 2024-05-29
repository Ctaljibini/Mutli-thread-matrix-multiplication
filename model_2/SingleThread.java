

public class SingleThread {

    public SingleThread() {}

    public int[][] matMatrix(int[][] matrixA, int[][] matrixB){
        int rowA = matrixA.length;
        int colA = matrixA[0].length;
        int colB = matrixB[0].length;
        int rowB = matrixB.length;

        if(colA != rowB){
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
            System.exit(1);
        }

        long startTime, endTime;

        int[][] matrixC = new int[rowA][colB];
        startTime = System.nanoTime();
        for(int i = 0; i < rowA; i++){
            for(int j = 0; j < colB; j++){
                for(int k = 0; k < rowB; k++){
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        endTime = System.nanoTime();
        System.out.println("Thread for single-thread " + (endTime - startTime)/1e6 + " ms.");
        return matrixC;
    }
}
