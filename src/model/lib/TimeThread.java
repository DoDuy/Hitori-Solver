package model.lib;

import model.Config;

/**
 * 26-11-2015
 * @author doduy
 */
public class TimeThread extends Thread{
    private final StopWatch swatch;
    
    public TimeThread(){
        super();
        swatch =  new StopWatch();
    }
    
    @Override
    public void start(){
        swatch.Start();
        super.start();
    }
    
    public boolean isTimeOut(){
        return swatch.getNanoTimeNow()*0.0000000002 > Config.TIMEOUT;
    }
}
