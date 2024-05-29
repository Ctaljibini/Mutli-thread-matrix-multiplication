
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
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
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

        long startTime[] = new long[numberOfThreads];
        long endTime[] = new long[numberOfThreads];
        long totalTime = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            final int row = i; // row'u final yapıyoruz ki her thread sabit bir row üzerinde çalışsın
            threads[i] = new Thread(() -> multiplyRow(row));
            startTime[i] = System.nanoTime();
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
                endTime[i] = System.nanoTime();

                System.out.println("Thread "+i+" time is: "+(endTime[i] - startTime[i])/1e6 +" ms." );
                totalTime += (endTime[i] - startTime[i])/1e6;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }  
        System.out.println("total time is: "+totalTime + " ms.");
        return matrixC;
    }   
}