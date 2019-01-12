package de.seliba.tablist;

/*
Tablist created by Seliba
*/

public class TickHelper implements Runnable {

    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];
    public static long LAST_TICK= 0L;
    public static long timeNextTickCheck = 0;
    public static double lastTickCount = 20.0D;

    public static double getTPS()
    {
        return getTPS(100);
    }

    public static double getTPS(int ticks)
    {
        try {
            if (TICK_COUNT< ticks) {
                return 20.0D;
            }
            int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
            long elapsed = System.currentTimeMillis() - TICKS[target];

            if(timeNextTickCheck != 60L) {
                timeNextTickCheck++;
            } else {
                timeNextTickCheck = 0;
                lastTickCount = ticks / (elapsed / 1000.0D);
                return lastTickCount;
            }
            return lastTickCount;
        } catch(ArrayIndexOutOfBoundsException ex) {
            //Do nothing
            return lastTickCount;
        }
    }

    public static long getElapsed(int tickID)
    {
        if (TICK_COUNT- tickID >= TICKS.length)
        {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    @Override
    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }

}
