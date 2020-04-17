package com.teammander.salamander.transaction;

import java.util.Date;

public class PrecinctMergeTransaction extends Transaction {

    public PrecinctMergeTransaction(int tid, String before, String after, String comment, String changedRegion, Date timeStamp) {
        super(tid, before, after, comment, changedRegion, timeStamp);
    }
}
