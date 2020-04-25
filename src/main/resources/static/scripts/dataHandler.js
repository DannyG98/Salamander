const DataHandler = {

    getAllStateData: function() {
        fetch('/state/getAllStates').then(function(response) {
            return response.text();
        }).then(function(text) {
            LeafletMap.statesGeojson = JSON.parse(text);
            // Create a hashmap that maps state canonical names to geojson data
            for (var i = 0; i < LeafletMap.statesGeojson.length; i++) {
                LeafletMap.states[LeafletMap.statesGeojson[i].canonName] = LeafletMap.statesGeojson[i];
                // Need to convert the json from server into geojson
                LeafletMap.statesGeojson[i] = jsonHandler.convertToGeojson(LeafletMap.statesGeojson[i]);
            }
            // Add the state layer
            LeafletMap.stateLayer = L.geoJson(LeafletMap.statesGeojson, {
                onEachFeature: LeafletMap.onEachFeature
            }).addTo(LeafletMap.map);
        });
    },

    // Get district geojson from server
    getDistrictData: function(districtList) {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(districtList)
        }
        fetch('/district/getMultipleDistricts', postTemplate).then(function(response) {
            return response.text();
        }).then(function(text) {
            LeafletMap.districtGeojson = JSON.parse(text);
            for (var i = 0; i < LeafletMap.districtGeojson.length; i++) {
                // Store district objects using canonicalName as key and district object as value
                LeafletMap.districts[LeafletMap.districtGeojson[i].canonName] = LeafletMap.districtGeojson[i];
                // Convert district objects into geoJson format and store it.
                LeafletMap.districtGeojson[i] = jsonHandler.convertToGeojson(LeafletMap.districtGeojson[i]);
            }
            // Add the district layer to the map
            LeafletMap.districtLayer= L.geoJson(LeafletMap.districtGeojson, {
                onEachFeature: LeafletMap.onEachFeature
            }).addTo(LeafletMap.map);
        });
    },
    // Get precinct geojson from server
    getPrecinctData: function(precinctList) {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: precinctList
        }
        fetch('/precinct/getMultiplePrecincts', postTemplate).then(function(response) {
            return response.text();
        }).then(function(text) {
            LeafletMap.precinctGeojson = JSON.parse(text);
            for (var i = 0; i < LeafletMap.precinctGeojson.length; i++) {
                this.precincts[LeafletMap.precinctGeojson[i].canonName] = LeafletMap.precinctGeojson[i];
                LeafletMap.precinctGeojson[i] = jsonHandler.convertToGeojson(LeftletMap.precinctGeojson[i]);
            }
            // Add the district layer
            LeafletMap.precinctLayer= L.geoJson(LeafletMap.precinctGeojson, {
                onEachFeature: LeafletMap.onEachFeature
            }).addTo(LeafletMap.map);
        });
    },

    addOrDeletePrecinctNeighbor: function (precinctName) {

    },

    getMergedPrecinct: function(precinctName1, precinctName2) {
        fetch('/precinct/mergePrecinct?p1={' + precinctName1 + '}&p2={' + precinctName2 +'}').then(function(response) {
            return response.text();
        }).then(function(text) {
            // Remove old precincts and store the merged precinct

        });
    },
    // Send modified data to the server
    uploadPrecinctData: function(precinctList) {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctList)
        }
        fetch('/precinct/multiUploadPrecincts', postTemplate).then(function(response) {
            return response.text();
        }).then(function(text) {
            // Check if reponse is 200 OK
        });
    },

    updatePrecinctData: function() {
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert: 
                // Add all the new precincts to the current list of precincts
                for (var i = 0; i < LeafletMap.tempPrecinctGeojson.length; i++) {
                    LeafletMap.precinctGeojson.push(LeafletMap.tempPrecinctGeojson[i]);
                    LeafletMap.precincts[LeafletMap.tempPrecinctGeojson[i].properties.canonName] = LeafletMap.tempPrecinctGeojson[i]
                }
            
                break;
            case LeafletMap.modes.modify:
                // Replace precinctCoordinates with the new ones from the precinctLayer 
                for (let i in LeafletMap.precinctLayer._layers) {
                    let precinctName = LeafletMap.precinctLayer._layers[i].feature.properties.name;
                    for (let j in LeafletMap.precinctGeojson) {
                        if (precinctName == LeafletMap.precinctGeojson[j].properties.name) {
                            console.log("Precinct coordinates modified");
                            var newPrecinctCoordinates = [];
                            var coordinatesList = LeafletMap.precinctLayer._layers[i]._latlngs[0];
                            for (var k in coordinatesList) {
                                newPrecinctCoordinates.push([coordinatesList[k].lng, coordinatesList[k].lat]);
                            };
                            LeafletMap.precinctGeojson[j].geometry.type = LeafletMap.precinctLayer._layers[i].feature.geometry.type;
                            LeafletMap.precinctGeojson[j].geometry.coordinates = [newPrecinctCoordinates];
                            break;
                        }
                    }
                };
                break;
            case LeafletMap.modes.merge:
                LeafletMap.updatePrecinctLayer();
                break;
            default:
                console.log("INVALID CURRENT MODE");
        }
    },
}