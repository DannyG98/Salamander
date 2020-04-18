package com.teammander.salamander.map;

import java.util.List;

public class Polygon {
    List<Coordinate> boundary;

    public Polygon(List<Coordinate> boundary) {
        this.boundary = boundary;
    }

    public List<Coordinate> getBoundary() {
        return this.boundary;
    }

    public void ListBoundary(List<Coordinate> boundary) {
        this.boundary = boundary;
    }
}