package assignment;

import lib.synchronization.Barrier;

public class BarrierImpl implements Barrier {
    private int nArrivedSoFar;
    private int nTotal;

    public BarrierImpl(int nTotal){
        this.nTotal = nTotal;
        this.nArrivedSoFar = 0;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        this.nArrivedSoFar++;
        if(this.nArrivedSoFar == this.nTotal){
            notifyAll();
        }
        else{
            while (this.nArrivedSoFar < this.nTotal){
                wait();
            }
        }
    }
}
