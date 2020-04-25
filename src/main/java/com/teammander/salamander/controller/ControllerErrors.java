package com.teammander.salamander.controller;

public class ControllerErrors {
    private static String unableToFindStr = "Unable to find %s";
    private static String badQueryStr = "Bad request %s for %s";

    public static String unableToFindMsg(String query) {
        return String.format(unableToFindStr, query);
    }

    public static String badQueryMsg(String param, String query) {
        return String.format(badQueryStr, query, param);
    }

}