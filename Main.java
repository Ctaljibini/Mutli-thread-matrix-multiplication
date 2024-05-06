import java.util.Random;

/**
 * Main
 */
public class Main {


    public static int[][] creatMatirx(int size){
        Random random = new Random();
        int matrix[][] = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = random.nextInt(1_000);
            }
        }
        return matrix;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("sueda");
        
        int size = 0;
        try{
            size = Integer.parseInt(args[0]);
        }catch(Exception e){
            System.out.println("geçerli bir sayi gir"+e);
        }        
        
        for(int i = 0; i < size; i++){
            MultiThread multiThread = new MultiThread(i, size);
            multiThread.start();
            multiThread.join();
        }
    }   
}

