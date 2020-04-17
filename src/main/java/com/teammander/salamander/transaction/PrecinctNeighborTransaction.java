package com.teammander.salamander.transaction;

import java.util.Date;

public class PrecinctNeighborTransaction extends Transaction{

    public PrecinctNeighborTransaction(int tid, String before, String after, String comment, String changedRegion, Date timeStamp) {
        super(tid, before, after, comment, changedRegion, timeStamp);
    }
}
