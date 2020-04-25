package com.teammander.salamander.map;

public enum ErrorType {
    DAT_ELECT_MISMATCH("DAT_ELECT_MISMATCH"), 
    DAT_DEMO_MISMATCH("DAT_DEMO_MISMATCH"), 
    PRCT_GAP("PRCT_GAP"), 
    PRCT_OVERLAP("PRCT_OVERLAP"), 
    PRCT_OPEN("PRCT_OPEN"), 
    PRCT_ENCLOSE("PRCT_ENCLOSE");
    private final String text;

    private ErrorType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}