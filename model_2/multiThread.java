
class multiThread {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] matrixC;
    
    private int rowA;
    private int colA;
    private int rowB;
    private int colB;
    private int numberOfThreads;

    public multiThread(int[][] matrixA, int[][] matrixB){
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = new int[matrixA.length][matrixB[0].length];

        this.rowA = matrixA.length;
        this.colA = matrixA[0].length;
        this.rowB = matrixB.length;
        this.colB = matrixB[0].length;
        this.numberOfThreads = rowA;

        if(colA != rowB){
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts!");
            System.exit(1);
        }
    }

    public void multiplyRow(int row){
        for(int j = 0; j < colB; j++){
            for(int k = 0; k < colA; k++){
                matrixC[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }

    public int[][] multiply() {
        
        Thread[] threads = new Thread[numberOfThreads];

        long startTime = 0;
        long endTime = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            final int row = i; // row'u final yapıyoruz ki her thread sabit bir row üzerinde çalışsın
            threads[i] = new Thread(() -> multiplyRow(row));
            startTime = System.nanoTime();
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
                endTime = System.nanoTime();
                System.out.println("Thread "+i+" time is: "+(endTime - startTime)/1e6 +" ms." );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matrixC;
    }   
}