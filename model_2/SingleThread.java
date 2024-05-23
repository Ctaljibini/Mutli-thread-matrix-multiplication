

public class SingleThread {

    public SingleThread() {
        // No-argument constructor
    }

    public int[][] matMatrix(int[][] matrixA, int[][] matrixB){
        int rowA = matrixA.length;
        int colB = matrixB[0].length;
        int rowB = matrixB.length;

        int[][] matrixC = new int[rowA][colB];
        for(int i = 0; i < rowA; i++){
            for(int j = 0; j < colB; j++){
                for(int k = 0; k < rowB; k++){
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }

    public void printer(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
