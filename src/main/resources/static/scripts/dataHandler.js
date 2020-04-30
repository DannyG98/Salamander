const DataHandler = {
    options: {addNeighbor: 'add', removeNeighbor: 'delete'},

    getAllStateData: () => {
        fetch('/state/getAllStates').then(function(response) {
            return response.text();
        }).then(function(text) {
            let serverData = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.states[canonName] = serverData[i];
                LeafletMap.statesGeojson.push(jsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.stateLayer = L.geoJson(LeafletMap.statesGeojson, {
                onEachFeature: LeafletMap.onEachFeature
            }).addTo(LeafletMap.map);
        });
    },

    getAllDistrictData: () => {
        fetch('/district/getAllDistricts').then(function(response) {
            return response.text();
        }).then(function(text) {
            let serverData = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.districts[canonName] = serverData[i];
            }
        });
    },

    getAllPrecinctData: () => {
        fetch('/precinct/getAllPrecincts').then(function(response) {
            return response.text();
        }).then(function(text) {
            let serverData = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.precincts[canonName] = serverData[i];
            }
        });
    },

    getDistrictData: (districtList) => {
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
            let serverData  = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.districts[canonName] = serverData[i];
                LeafletMap.districtGeojson.push(jsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.updateDistrictLayer();
        });
    },
   
    getPrecinctData: (precinctList) => {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctList)
        }
        fetch('/precinct/getMultiplePrecincts', postTemplate).then(function(response) {
            return response.text();
        }).then(function(text) {
            let serverData = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.precincts[canonName] = serverData[i];
                LeafletMap.precinctGeojson.push(jsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.updatePrecinctLayer();
        });
    },

    changePrecinctNeighbor: (precinctName, precinctNeighbors, option) => {
        // TODO need to modify end points to accept list of neighbors to be added
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctNeighbors)
        }
        fetch('/precinct/getMultiplePrecincts', postTemplate).then(function(response) {
            return response.text();
        }).then(function(text) {
            // Check for updateSuccess?
        });
    },

    getMergedPrecinct: (precinctName1, precinctName2) => {
        fetch('/precinct/mergePrecinct?p1={' + precinctName1 + '}&p2={' + precinctName2 +'}').then(function(response) {
            return response.text();
        }).then(function(text) {
            // Remove old precincts and store the merged precinct

        });
    },

    uploadPrecinctData: (precinctList) => {
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

    updatePrecinctData: () => {
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert: {
                // Add all the new precincts to the current list of precincts
                for (let i = 0; i < LeafletMap.tempPrecinctGeojson.length; i++) {
                    LeafletMap.precinctGeojson.push(LeafletMap.tempPrecinctGeojson[i]);
                    LeafletMap.precincts[LeafletMap.tempPrecinctGeojson[i].properties.canonName] = LeafletMap.tempPrecinctGeojson[i]
                }
                break;
            }
            case LeafletMap.modes.modify: {
                // Replace precinctCoordinates with the new ones from the precinctLayer 
                // Replace only the precincts that were changed
                for (let i in LeafletMap.precinctLayer._layers) {
                    let precinctName = LeafletMap.precinctLayer._layers[i].feature.properties.name;
                    for (let j in LeafletMap.precinctGeojson) {
                        if (precinctName == LeafletMap.precinctGeojson[j].properties.name) {
                            let newPrecinctCoordinates = [];
                            let coordinatesList = LeafletMap.precinctLayer._layers[i]._latlngs[0];
                            for (let k in coordinatesList) {
                                newPrecinctCoordinates.push([coordinatesList[k].lng, coordinatesList[k].lat]);
                            };
                            LeafletMap.precinctGeojson[j].geometry.type = LeafletMap.precinctLayer._layers[i].feature.geometry.type;
                            LeafletMap.precinctGeojson[j].geometry.coordinates = [newPrecinctCoordinates];
                            break;
                        }
                    }
                };
                break;
            }
            case LeafletMap.modes.merge: {
                LeafletMap.updatePrecinctLayer();
                break;
            }
            case LeafletMap.modes.add: {
                DataHandler.updateNeighbors(DataHandler.options.addNeighbor);
                break;        
            }
            case LeafletMap.modes.remove: {
                DataHandler.updateNeighbors(DataHandler.options.removeNeighbor);
            }
            default:
                console.log("INVALID CURRENT MODE");
        }
    },

    updatePrecincts: (precinctList) => {
        LeafletMap.precinctGeojson = [];
        for (let i = 0; i < precinctList.length; i++) {
            let canonName = precinctList[i];
            let geojson = jsonHandler.convertToGeojson(LeafletMap.precincts[canonName]);
            LeafletMap.precinctGeojson.push(geojson);
        }
        LeafletMap.updatePrecinctLayer();
    },

    updateDistricts: (districtList) => {
        LeafletMap.districtGeojson = [];
        for (let i = 0; i < districtList.length; i++) {
            let canonName = districtList[i];
            let geojson = jsonHandler.convertToGeojson(LeafletMap.districts[canonName]);
            LeafletMap.districtGeojson.push(geojson);
        }
        LeafletMap.updateDistrictLayer();
    },

    updateNeighbors: (option) => {
        let precinctNeighbors = LeafletMap.precincts[LeafletMap.precinctBeingChanged].neighborCNames;
        switch (option) {
            case DataHandler.options.addNeighbor: {
                LeafletMap.precincts[LeafletMap.precinctBeingChanged].neighborCNames = precinctNeighbors.concat(LeafletMap.selectedPrecincts);
                DataHandler.changePrecinctNeighbor(LeafletMap.precinctBeingChanged, LeafletMap.selectedPrecincts, DataHandler.options.addNeighbor);
                break;
            }
            case DataHandler.options.removeNeighbor: {
                // Get the difference between the precinctNeighbors list and selectedPrecincts
                LeafletMap.precincts[LeafletMap.precinctBeingChanged].neighborCNames = precinctNeighbors.filter(cName => !LeafletMap.selectedPrecincts.includes(cName));
                DataHandler.changePrecinctNeighbor(LeafletMap.precinctBeingChanged, LeafletMap.selectedPrecincts, DataHandler.options.removeNeighbor);
                break;
            }
            default:
                console.log("Error updating neighbors");
        }
    }
}