package model.lib;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import model.Config;

/**
 *
 * @author DoDuy
 */
public class StopWatch {
    public static final int CPU_TIME = 1;
    public static final int USER_TIME = 2;
    public static final int TOTAL_TIME = 3;
    
    private int flag; //0 is stop 1 is running 2 is pause 
    private long timeCounter;//
    private long timeStart;
    private ThreadMXBean tBean;
    private final int selected_timer;
    
    public StopWatch(){
        selected_timer = Config.SELECTED_TIMER;
        flag = 0;
        timeCounter = 0;
        timeStart = 0;
        if(selected_timer != TOTAL_TIME){
            tBean = ManagementFactory.getThreadMXBean();
            if (this.tBean.isThreadCpuTimeSupported())
                this.tBean.setThreadCpuTimeEnabled(true);
        }
    }
    
    public boolean isStart(){
        return flag == 1;
    }
    
    public boolean isStop(){
        return flag == 0;
    }
    
    public boolean isPause(){
        return flag == 2;
    }
    
    public long getNanoTime(){
        return timeCounter;
    }
    
    public long getNanoTimeNow(){
        return timeCounter + getTime() - timeStart;
    }
    
    private long getTime(){
        switch(selected_timer){
            case CPU_TIME:
                return tBean.getCurrentThreadCpuTime();
            case USER_TIME:
                return tBean.getCurrentThreadUserTime();
            case TOTAL_TIME:
                return System.nanoTime();
        }
        return 0;
    }
    
    public boolean Start(){
        if (flag != 0) return false;
        flag = 1;
        timeCounter = 0;
        timeStart = getTime();
        return true;
    }
    
    public boolean Stop(){
        if(flag == 0) return false;
        if(flag == 1)
            timeCounter = timeCounter + getTime() - timeStart;
        flag = 0;
        return true;
    }
    
    public boolean Pause(){
        if(flag != 1) return false;
        flag = 2;
        timeCounter = timeCounter + getTime() - timeStart;
        return true;
    }
    
    public boolean Continue(){
        if(flag != 2) return false;
        flag = 1;
        timeStart = getTime();
        return true;
    }
    
    public void Reset(){
        flag = 0;
        timeCounter = 0;
        timeStart = 0;
    }
}