package com.teammander.salamander.repository;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.District;
import com.teammander.salamander.map.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mil.nga.sf.geojson.Geometry;
import mil.nga.sf.geojson.Polygon;
import mil.nga.sf.geojson.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class StateRepository {
    // EntityManager em;
    HashMap<String, State> allStates;

    @Autowired
    public StateRepository() {
        allStates = new HashMap<>();
    }

    public State findState(String stateCanonName){ 
        return allStates.get(stateCanonName);
    }

    public void insertState(State state) {
        allStates.put(state.getCanonName(), state);
    }

    public List <State> getAllStates() {
        return new ArrayList<State>(allStates.values());
    }
}
