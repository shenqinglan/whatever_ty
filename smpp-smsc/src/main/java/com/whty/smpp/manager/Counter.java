package com.whty.smpp.manager;

import java.util.TimerTask;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * @ClassName Counter
 * @author Administrator
 * @date 2017-1-12 上午10:50:38
 * @Description TODO(计数器)
 */
public class Counter extends TimerTask
{
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public static int counter = 0;
    
    @Override
    public void run()
    {
        logger.info("COUNTER: {}", counter);
    }
}

