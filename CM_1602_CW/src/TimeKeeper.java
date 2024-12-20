public class TimeKeeper {
    private long startTime;
    private long endTime;
    private boolean running;

    public void start(){
        if(!running){
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop(){
        if(running){
            endTime = System.currentTimeMillis();
            running = false;
        }
    }

    public long executionTime(){
        if(running){
            return System.currentTimeMillis() - startTime;
        }else{
            return endTime-startTime;
        }
    }
}