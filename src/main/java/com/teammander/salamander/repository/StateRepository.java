package com.teammander.salamander.repository;

import com.teammander.salamander.data.DemographicData;
import com.teammander.salamander.data.Election;
import com.teammander.salamander.data.ElectionData;
import com.teammander.salamander.map.Coordinate;
import com.teammander.salamander.map.District;
import com.teammander.salamander.map.State;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class StateRepository {
    //EntityManager em;
    List <State> allStates;

    public State getState(String stateCanonName){ return null; }

    public void updateChanged(){
    }

    public List <State> getAllStates() {
        //stubbed values for now ==get boundary from database once db is initialized===
        List<Coordinate> coloradoBoundaries= Arrays.asList(new Coordinate(-107.919731, 41.003906), new Coordinate(-105.728954, 40.998429), new Coordinate(-104.053011, 41.003906), new Coordinate(-102.053927, 41.003906), new Coordinate(-102.053927, 40.001626),
                new Coordinate(-102.042974, 36.994786), new Coordinate(-103.001438, 37.000263), new Coordinate(-104.337812, 36.994786), new Coordinate(-106.868158, 36.994786), new Coordinate(-107.421329, 37.000263),
                new Coordinate(-109.042503, 37.000263), new Coordinate(-109.042503, 38.166851), new Coordinate(-109.058934, 38.27639), new Coordinate(-109.053457, 39.125316), new Coordinate(-109.04798, 40.998429), new Coordinate(-107.919731, 41.003906));
        DemographicData coDemo= new DemographicData(1, 10,12,16,40, 15);
        State colorado = new State("01", "Colorado", coloradoBoundaries, coDemo, null, null);


        List<Coordinate> floridaBoundaries= Arrays.asList(new Coordinate(-85.497137,30.997536),new Coordinate(-85.004212,31.003013),new Coordinate(-84.867289,30.712735),new Coordinate(-83.498053,30.647012),new Coordinate(-82.216449,30.570335),new Coordinate(-82.167157,30.356734),
                new Coordinate(-82.046664,30.362211),new Coordinate(-82.002849,30.564858),new Coordinate(-82.041187,30.751074),new Coordinate(-81.948079,30.827751),new Coordinate(-81.718048,30.745597),new Coordinate(-81.444201,30.707258),new Coordinate(-81.383954,30.27458),
                new Coordinate(-81.257985,29.787132),new Coordinate(-80.967707,29.14633),new Coordinate(-80.524075,28.461713),new Coordinate(-80.589798,28.41242),new Coordinate(-80.56789,28.094758),new Coordinate(-80.381674,27.738757),new Coordinate(-80.091397,27.021277),
                new Coordinate(-80.03115,26.796723),new Coordinate(-80.036627,26.566691),new Coordinate(-80.146166,25.739673),new Coordinate(-80.239274,25.723243),new Coordinate(-80.337859,25.465826),new Coordinate(-80.304997,25.383672),new Coordinate(-80.49669,25.197456),
                new Coordinate(-80.573367,25.241272),new Coordinate(-80.759583,25.164595),new Coordinate(-81.077246,25.120779),new Coordinate(-81.170354,25.224841),new Coordinate(-81.126538,25.378195),new Coordinate(-81.351093,25.821827),new Coordinate(-81.526355,25.903982),
                new Coordinate(-81.679709,25.843735),new Coordinate(-81.800202,26.090198),new Coordinate(-81.833064,26.292844),new Coordinate(-82.041187,26.517399),new Coordinate(-82.09048,26.665276),new Coordinate(-82.057618,26.878877),new Coordinate(-82.172634,26.917216),
                new Coordinate(-82.145249,26.791246),new Coordinate(-82.249311,26.758384),new Coordinate(-82.566974,27.300601),new Coordinate(-82.692943,27.437525),new Coordinate(-82.391711,27.837342),new Coordinate(-82.588881,27.815434),new Coordinate(-82.720328,27.689464),
                new Coordinate(-82.851774,27.886634),new Coordinate(-82.676512,28.434328),new Coordinate(-82.643651,28.888914),new Coordinate(-82.764143,28.998453),new Coordinate(-82.802482,29.14633),new Coordinate(-82.994175,29.179192),new Coordinate(-83.218729,29.420177),
                new Coordinate(-83.399469,29.518762),new Coordinate(-83.410422,29.66664),new Coordinate(-83.536392,29.721409),new Coordinate(-83.640454,29.885717),new Coordinate(-84.02384,30.104795),new Coordinate(-84.357933,30.055502),new Coordinate(-84.341502,29.902148),
                new Coordinate(-84.451041,29.929533),new Coordinate(-84.867289,29.743317),new Coordinate(-85.310921,29.699501),new Coordinate(-85.299967,29.80904),new Coordinate(-85.404029,29.940487),new Coordinate(-85.924338,30.236241),new Coordinate(-86.29677,30.362211),
                new Coordinate(-86.630863,30.395073),new Coordinate(-86.910187,30.373165),new Coordinate(-87.518128,30.280057),new Coordinate(-87.37025,30.427934),new Coordinate(-87.446927,30.510088),new Coordinate(-87.408589,30.674397),new Coordinate(-87.633143,30.86609),new Coordinate(-87.600282,30.997536),new Coordinate(-85.497137,30.997536));
        DemographicData flDemo= new DemographicData(2, 11,1,60,4, 15);
        State florida = new State("02", "Florida", floridaBoundaries, flDemo, null, null);


        List<Coordinate> marylandBoundaries= Arrays.asList(new Coordinate(-79.477979,39.722302),new Coordinate(-75.786521,39.722302),new Coordinate(-75.693413,38.462606),new Coordinate(-75.047134,38.451652),new Coordinate(-75.244304,38.029928),new Coordinate(-75.397659,38.013497),
                new Coordinate(-75.671506,37.95325),new Coordinate(-75.885106,37.909435),new Coordinate(-75.879629,38.073743),new Coordinate(-75.961783,38.139466),new Coordinate(-75.846768,38.210667),new Coordinate(-76.000122,38.374975),new Coordinate(-76.049415,38.303775),
                new Coordinate(-76.257538,38.320205),new Coordinate(-76.328738,38.500944),new Coordinate(-76.263015,38.500944),new Coordinate(-76.257538,38.736453),new Coordinate(-76.191815,38.829561),new Coordinate(-76.279446,39.147223),new Coordinate(-76.169907,39.333439),
                new Coordinate(-76.000122,39.366301),new Coordinate(-75.972737,39.557994),new Coordinate(-76.098707,39.536086),new Coordinate(-76.104184,39.437501),new Coordinate(-76.367077,39.311532),new Coordinate(-76.443754,39.196516),new Coordinate(-76.460185,38.906238),
                new Coordinate(-76.55877,38.769315),new Coordinate(-76.514954,38.539283),new Coordinate(-76.383508,38.380452),new Coordinate(-76.399939,38.259959),new Coordinate(-76.317785,38.139466),new Coordinate(-76.3616,38.057312),new Coordinate(-76.591632,38.216144),
                new Coordinate(-76.920248,38.292821),new Coordinate(-77.018833,38.446175),new Coordinate(-77.205049,38.358544),new Coordinate(-77.276249,38.479037),new Coordinate(-77.128372,38.632391),new Coordinate(-77.040741,38.791222),new Coordinate(-76.909294,38.895284),new Coordinate(-77.035264,38.993869),
                new Coordinate(-77.117418,38.933623),new Coordinate(-77.248864,39.026731),new Coordinate(-77.456988,39.076023),new Coordinate(-77.456988,39.223901),new Coordinate(-77.566527,39.306055),new Coordinate(-77.719881,39.322485),new Coordinate(-77.834897,39.601809),new Coordinate(-78.004682,39.601809),
                new Coordinate(-78.174467,39.694917),new Coordinate(-78.267575,39.61824),new Coordinate(-78.431884,39.623717),new Coordinate(-78.470222,39.514178),new Coordinate(-78.765977,39.585379),new Coordinate(-78.963147,39.437501),new Coordinate(-79.094593,39.470363),new Coordinate(-79.291763,39.300578), new Coordinate(-79.488933,39.20747),new Coordinate(-79.477979,39.722302));
        DemographicData mlDemo= new DemographicData(3, 21,6,61,4, 15);
        State maryland = new State("03", "Maryland", marylandBoundaries, mlDemo, null, null);

        allStates.add(colorado);
        allStates.add(florida);
        allStates.add(maryland);
        //======================
        return allStates;
    }
}
