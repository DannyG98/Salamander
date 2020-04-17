package com.teammander.salamander.transaction;

import java.util.Date;

public class DemographicDataTransaction extends Transaction {

    public DemographicDataTransaction(int tid, String before, String after, String comment, String changedRegion, Date timeStamp) {
        super(tid, before, after, comment, changedRegion, timeStamp);
    }
}
