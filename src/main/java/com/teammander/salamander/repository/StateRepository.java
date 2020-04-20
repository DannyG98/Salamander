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

        List<Position> coloradoBoundaries = Arrays.asList(new Position(-107.919731, 41.003906),
                new Position(-105.728954, 40.998429), new Position(-104.053011, 41.003906),
                new Position(-102.053927, 41.003906), new Position(-102.053927, 40.001626),
                new Position(-102.042974, 36.994786), new Position(-103.001438, 37.000263),
                new Position(-104.337812, 36.994786), new Position(-106.868158, 36.994786),
                new Position(-107.421329, 37.000263), new Position(-109.042503, 37.000263),
                new Position(-109.042503, 38.166851), new Position(-109.058934, 38.27639),
                new Position(-109.053457, 39.125316), new Position(-109.04798, 40.998429),
                new Position(-107.919731, 41.003906));
        DemographicData coDemo = new DemographicData(1, 10, 12, 16, 40, 15);
        Geometry coloradoGeometry = new Polygon(Arrays.asList(coloradoBoundaries));
        Set<String> coDistricts = Stream.of("colorado_d1", "colorado_d2", "colorado_d3").collect(Collectors.toSet());
        State colorado = new State("colorado", "Colorado", coloradoGeometry, coDemo, null, coDistricts);


        List<Position> floridaBoundaries= Arrays.asList(new mil.nga.sf.geojson.Position(-85.497137,30.997536),new Position(-85.004212,31.003013),new Position(-84.867289,30.712735),new Position(-83.498053,30.647012),new Position(-82.216449,30.570335),new Position(-82.167157,30.356734),
                new Position(-82.046664,30.362211),new Position(-82.002849,30.564858),new Position(-82.041187,30.751074),new Position(-81.948079,30.827751),new Position(-81.718048,30.745597),new Position(-81.444201,30.707258),new Position(-81.383954,30.27458),
                new Position(-81.257985,29.787132),new Position(-80.967707,29.14633),new Position(-80.524075,28.461713),new Position(-80.589798,28.41242),new Position(-80.56789,28.094758),new Position(-80.381674,27.738757),new Position(-80.091397,27.021277),
                new Position(-80.03115,26.796723),new Position(-80.036627,26.566691),new Position(-80.146166,25.739673),new Position(-80.239274,25.723243),new Position(-80.337859,25.465826),new Position(-80.304997,25.383672),new Position(-80.49669,25.197456),
                new Position(-80.573367,25.241272),new Position(-80.759583,25.164595),new Position(-81.077246,25.120779),new Position(-81.170354,25.224841),new Position(-81.126538,25.378195),new Position(-81.351093,25.821827),new Position(-81.526355,25.903982),
                new Position(-81.679709,25.843735),new Position(-81.800202,26.090198),new Position(-81.833064,26.292844),new Position(-82.041187,26.517399),new Position(-82.09048,26.665276),new Position(-82.057618,26.878877),new Position(-82.172634,26.917216),
                new Position(-82.145249,26.791246),new Position(-82.249311,26.758384),new Position(-82.566974,27.300601),new Position(-82.692943,27.437525),new Position(-82.391711,27.837342),new Position(-82.588881,27.815434),new Position(-82.720328,27.689464),
                new Position(-82.851774,27.886634),new Position(-82.676512,28.434328),new Position(-82.643651,28.888914),new Position(-82.764143,28.998453),new Position(-82.802482,29.14633),new Position(-82.994175,29.179192),new Position(-83.218729,29.420177),
                new Position(-83.399469,29.518762),new Position(-83.410422,29.66664),new Position(-83.536392,29.721409),new Position(-83.640454,29.885717),new Position(-84.02384,30.104795),new Position(-84.357933,30.055502),new Position(-84.341502,29.902148),
                new Position(-84.451041,29.929533),new Position(-84.867289,29.743317),new Position(-85.310921,29.699501),new Position(-85.299967,29.80904),new Position(-85.404029,29.940487),new Position(-85.924338,30.236241),new Position(-86.29677,30.362211),
                new Position(-86.630863,30.395073),new Position(-86.910187,30.373165),new Position(-87.518128,30.280057),new Position(-87.37025,30.427934),new Position(-87.446927,30.510088),new Position(-87.408589,30.674397),new Position(-87.633143,30.86609),new Position(-87.600282,30.997536),new Position(-85.497137,30.997536));
        DemographicData flDemo= new DemographicData(2, 11,1,60,4, 15);
        Geometry floridaGeometry = new Polygon(Arrays.asList(floridaBoundaries));
        State florida = new State("florida", "Florida", floridaGeometry, flDemo, null, null);


        List<Position> marylandBoundaries= Arrays.asList(new Position(-79.477979,39.722302),new Position(-75.786521,39.722302),new Position(-75.693413,38.462606),new Position(-75.047134,38.451652),new Position(-75.244304,38.029928),new Position(-75.397659,38.013497),
                new Position(-75.671506,37.95325),new Position(-75.885106,37.909435),new Position(-75.879629,38.073743),new Position(-75.961783,38.139466),new Position(-75.846768,38.210667),new Position(-76.000122,38.374975),new Position(-76.049415,38.303775),
                new Position(-76.257538,38.320205),new Position(-76.328738,38.500944),new Position(-76.263015,38.500944),new Position(-76.257538,38.736453),new Position(-76.191815,38.829561),new Position(-76.279446,39.147223),new Position(-76.169907,39.333439),
                new Position(-76.000122,39.366301),new Position(-75.972737,39.557994),new Position(-76.098707,39.536086),new Position(-76.104184,39.437501),new Position(-76.367077,39.311532),new Position(-76.443754,39.196516),new Position(-76.460185,38.906238),
                new Position(-76.55877,38.769315),new Position(-76.514954,38.539283),new Position(-76.383508,38.380452),new Position(-76.399939,38.259959),new Position(-76.317785,38.139466),new Position(-76.3616,38.057312),new Position(-76.591632,38.216144),
                new Position(-76.920248,38.292821),new Position(-77.018833,38.446175),new Position(-77.205049,38.358544),new Position(-77.276249,38.479037),new Position(-77.128372,38.632391),new Position(-77.040741,38.791222),new Position(-76.909294,38.895284),new Position(-77.035264,38.993869),
                new Position(-77.117418,38.933623),new Position(-77.248864,39.026731),new Position(-77.456988,39.076023),new Position(-77.456988,39.223901),new Position(-77.566527,39.306055),new Position(-77.719881,39.322485),new Position(-77.834897,39.601809),new Position(-78.004682,39.601809),
                new Position(-78.174467,39.694917),new Position(-78.267575,39.61824),new Position(-78.431884,39.623717),new Position(-78.470222,39.514178),new Position(-78.765977,39.585379),new Position(-78.963147,39.437501),new Position(-79.094593,39.470363),new Position(-79.291763,39.300578), new Position(-79.488933,39.20747),new Position(-79.477979,39.722302));
        DemographicData mlDemo= new DemographicData(3, 21,6,61,4, 15);
        Geometry marylandGeometry = new Polygon(Arrays.asList(marylandBoundaries));
        State maryland = new State("maryland", "Maryland", marylandGeometry, mlDemo, null, null);

        insertState(colorado);
        insertState(florida);
        insertState(maryland);
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
