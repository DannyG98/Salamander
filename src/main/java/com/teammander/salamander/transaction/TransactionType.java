package com.teammander.salamander.transaction;

public enum TransactionType {
    NEW_PRECINCT("NEW_PRECINCT"),
    MERGE_PRECINCT("MERGE_PRECINCT"),
    CHANGE_DEMODATA("CHANGE_DEMODATA"),
    CHANGE_ELECDATA("CHANGE_ELECDATA"),
    CHANGE_BOUNDARY("CHANGE_BOUNDARY");
    private final String text;

    private TransactionType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}