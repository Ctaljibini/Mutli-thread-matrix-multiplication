/**
 * MultiThread
 */
public class MultiThread extends Thread{
    private int threadNumer;
    private int numberOfThread; 
    public MultiThread(int threadNumer, int numberOfThread){
        this.threadNumer = threadNumer;
        this.numberOfThread = numberOfThread;
    } 
    @Override
    public void run(){
        for(int i = 0; i < numberOfThread; i++){
            System.out.println(i + " fome thread: "+ threadNumer);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {}
        }
    } 
}