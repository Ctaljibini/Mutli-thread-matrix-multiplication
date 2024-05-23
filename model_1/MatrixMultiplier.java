import java.io.*;
import java.util.*;

class MatrixMultiplier {
    static int arow, acol, brow, bcol;
    static int[][] arra;
    static int[][] arrb;
    static long[][] arrc;
    static String[] files = new String[3];

    static class Element {
        int row;
        int col;

        Element(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static void generateMatrix() throws IOException {
        String[] prompts = {"Enter the row size for matrix A(<1001):", "Enter the column size for matrix A(<1001):",
                "Enter the row size for matrix B(<1001):", "Enter the column size for matrix B(<1001):"};

        Random rand = new Random();

        for (int k = 0; k < 2; k++) {
            try (PrintWriter writer = new PrintWriter(files[k])) {
                System.out.println(prompts[k]);
                int row = Integer.parseInt(System.console().readLine());
                System.out.println(prompts[k + 1]);
                int col = Integer.parseInt(System.console().readLine());

                writer.println("row=" + row + " col=" + col);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        writer.print(rand.nextInt(10) + " ");
                    }
                    writer.println();
                }
            }
        }
    }
    static void writeToFile(String extension) throws IOException {
        String[] methods = {"Method: A thread per row", "Method: A thread per element", "Method: A thread per matrix"};
        String[] extend = {"_per_row.txt", "_per_element.txt", "_per_matrix.txt"};
    
        String extendValue = "";
        if (extension.equals("row")) {
            extendValue = "_per_row.txt";
        } else if (extension.equals("element")) {
            extendValue = "_per_element.txt";
        } else if (extension.equals("matrix")) {
            extendValue = "_per_matrix.txt";
        } else {
            System.out.println("Invalid extension");
            return;
        }
    
        try (PrintWriter writer = new PrintWriter(files[2] + extendValue)) {
            writer.println(methods[Arrays.asList(extend).indexOf(extension)]);
            writer.println("row=" + arow + " col=" + bcol);
            for (int i = 0; i < arow; i++) {
                for (int j = 0; j < bcol; j++) {
                    writer.print(arrc[i][j] + " ");
                }
                writer.println();
            }
        }
    }
    
    
    static void setRowsCols(int id, String str) {
        String[] parts = str.split(" ");
        String[] rowsCols = parts[0].split("=");
        int rows = Integer.parseInt(rowsCols[1]);
        rowsCols = parts[1].split("=");
        int cols = Integer.parseInt(rowsCols[1]);
    
        if (id == 1) {
            arow = rows;
            acol = cols;
        } else {
            brow = rows;
            bcol = cols;
        }
    }
    

    static void readMatrix() throws IOException {
        String[] str = new String[2];

        for (int k = 0; k < 2; k++) {
            try (BufferedReader br = new BufferedReader(new FileReader(files[k]))) {
                str[k] = br.readLine();
                setRowsCols(k + 1, str[k]);

                if (k == 0)
                    arra = new int[arow][acol];
                else
                    arrb = new int[brow][bcol];

                for (int i = 0; i < (k == 0 ? arow : brow); i++) {
                    StringTokenizer tokenizer = new StringTokenizer(br.readLine());
                    for (int j = 0; j < (k == 0 ? acol : bcol); j++) {
                        if (k == 0)
                            arra[i][j] = Integer.parseInt(tokenizer.nextToken());
                        else
                            arrb[i][j] = Integer.parseInt(tokenizer.nextToken());
                    }
                }
            }
        }

        arrc = new long[arow][bcol];
    }

    static void checkDimension() {
        if (acol != brow) {
            System.out.println("Matrix Multiplication is not possible due to dimension conflicts");
            System.exit(5);
        }
    }

    static void matMat() {
        checkDimension();
        for (int i = 0; i < arow; i++) {
            for (int j = 0; j < bcol; j++) {
                long sum = 0;
                for (int k = 0; k < acol; k++) {
                    sum += arra[i][k] * arrb[k][j];
                }
                arrc[i][j] = sum;
            }
        }
    }

    static void rowMat(int row) {
        checkDimension();
        for (int j = 0; j < bcol; j++) {
            long sum = 0;
            for (int k = 0; k < acol; k++) {
                sum += arra[row][k] * arrb[k][j];
            }
            arrc[row][j] = sum;
        }
    }

    static void elementMat(Element ele) {
        checkDimension();
        long sum = 0;
        for (int k = 0; k < acol; k++) {
            sum += arra[ele.row][k] * arrb[k][ele.col];
        }
        arrc[ele.row][ele.col] = sum;
    }

    static void multPerMatrix() throws IOException {
        matMat();
        writeToFile("matrix");
    }

    static void multPerRow() throws InterruptedException, IOException {
        checkDimension();
        Thread[] threads = new Thread[arow];
        for (int i = 0; i < arow; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> rowMat(finalI));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        writeToFile("row");
    }

    static void multPerElement() throws InterruptedException, IOException {
        checkDimension();
        Thread[][] threads = new Thread[arow][bcol];
        for (int i = 0; i < arow; i++) {
            for (int j = 0; j < bcol; j++) {
                int finalI = i;
                int finalJ = j;
                threads[i][j] = new Thread(() -> elementMat(new Element(finalI, finalJ)));
                threads[i][j].start();
            }
        }
        for (Thread[] thread : threads) {
            for (Thread value : thread) {
                value.join();
            }
        }
        writeToFile("element");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 3) {
            System.arraycopy(args, 0, files, 0, 3);
        } else if (args.length == 0) {
            files[0] = "a.txt";
            files[1] = "b.txt";
            files[2] = "c.txt";
        } else {
            System.out.println("You should enter the names of the 3 files");
            System.exit(1);
        }

        generateMatrix();
        readMatrix();

        long startTime = System.nanoTime();
        multPerMatrix();
        long endTime = System.nanoTime();
        System.out.println("Time taken for matrix threads: " + (endTime - startTime) / 1000000 + " milliseconds");

        startTime = System.nanoTime();
        multPerRow();
        endTime = System.nanoTime();
        System.out.println("Time taken for row threads: " + (endTime - startTime) / 1000000 + " milliseconds");

        startTime = System.nanoTime();
        multPerElement();
        endTime = System.nanoTime();
        System.out.println("Time taken for element threads: " + (endTime - startTime) / 1000000 + " milliseconds");
    }
}
