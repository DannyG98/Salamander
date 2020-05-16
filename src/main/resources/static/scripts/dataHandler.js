const DataHandler = {
    options: {addNeighbor: 'add', removeNeighbor: 'delete'},

    getAllStateData: () => {
        fetch('/state/getAllStates').then((response) => {
            return response.text();
        }).then((text) => {
            let serverData = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.states[canonName] = serverData[i];
                LeafletMap.statesGeojson.push(JsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.stateLayer = L.geoJson(LeafletMap.statesGeojson, {
                onEachFeature: LeafletMap.onEachFeature
            }).addTo(LeafletMap.map);
        });
    },

    getAllDistrictData: (stateCName) => {
        fetch(`/state/${stateCName}/districts`).then((response) => {
            return response.text();
        }).then((text) => {
            LeafletMap.districtGeojson = [];
            let serverData  = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.districts[canonName] = serverData[i];
                LeafletMap.districtGeojson.push(JsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.updateDistrictLayer();
        });
    },

    getAllPrecinctData: (districtCName) => {
        fetch(`/district/${districtCName}/precincts`).then((response) => {
            return response.text();
        }).then((text) => {
            LeafletMap.precinctGeojson = [];
            let serverData  = JSON.parse(text);
            for (let i = 0; i < serverData.length; i++) {
                let canonName = serverData[i].canonName;
                LeafletMap.precincts[canonName] = serverData[i];
                LeafletMap.precinctGeojson.push(JsonHandler.convertToGeojson(serverData[i]));
            }
            LeafletMap.updatePrecinctLayer();
        });
    },

    getDistrictData: (districtList) => {
        if (districtList != null) {
            let postTemplate = {
                method: 'post',
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(districtList)
            }
            fetch('/district/getMultipleDistricts', postTemplate).then((response) => {
                return response.text();
            }).then((text) => {
                let serverData  = JSON.parse(text);
                for (let i = 0; i < serverData.length; i++) {
                    let canonName = serverData[i].canonName;
                    LeafletMap.districts[canonName] = serverData[i];
                    LeafletMap.districtGeojson.push(JsonHandler.convertToGeojson(serverData[i]));
                }
                LeafletMap.updateDistrictLayer();
            });
        }
    },
   
    getPrecinctData: (precinctList) => {
        if (precinctList != null) {
            let postTemplate = {
                method: 'post',
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(precinctList)
            }
            fetch('/precinct/getMultiplePrecincts', postTemplate).then((response) => {
                return response.text();
            }).then((text) => {
                let serverData = JSON.parse(text);
                for (let i = 0; i < serverData.length; i++) {
                    let canonName = serverData[i].canonName;
                    LeafletMap.precincts[canonName] = serverData[i];
                    LeafletMap.precinctGeojson.push(JsonHandler.convertToGeojson(serverData[i]));
                }
                LeafletMap.updatePrecinctLayer();
            });
        }
    },

    changePrecinctNeighbor: (precinctName, precinctNeighbors, option) => {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctNeighbors)
        }
        fetch('/precinct/modifyNeighbor?p=' + precinctName + '&op=' + option, postTemplate).then((response) => {
            if(response.ok) {
                // Request the updated precincts
                DataHandler.getAllPrecinctData(LeafletMap.currentDistrict);
            }
        })
    },

    mergePrecincts: (precinctList) => {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctList)
        }
        // TODO waiting for endpoint to be completed
        fetch('/precinct/mergePrecinct', postTemplate).then((response) => {
            return response.text();
        }).then(() => {
            DataHandler.getAllPrecinctData(LeafletMap.currentDistrict); 
        });
    },

    uploadPrecinctData: (precinctObjects) => {
        let postTemplate = {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(precinctObjects)
        }
        fetch('/precinct/multiUploadPrecincts', postTemplate);
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
            case LeafletMap.modes.merge: {
                DataHandler.mergePrecincts();
                break;
            }
            case LeafletMap.modes.modify: {
                // Replace precinctCoordinates with the new ones from the precinctLayer 
                // Replace only the precincts that were changed
                DataHandler.replacePrecinctCoordinates();
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

    replacePrecinctCoordinates: () => {
        let precinctObjects = [];
        for (let i in LeafletMap.precinctLayer._layers) {
            let precinctName = LeafletMap.precinctLayer._layers[i].feature.properties.canonName;
            if (LeafletMap.modifiedPrecincts.indexOf(precinctName) != -1) {
                let newPrecinctCoordinates = [];
                let coordinatesList = LeafletMap.precinctLayer._layers[i]._latlngs[0];
                // Leaflet stores as latlng but server uses lnglat
                for (let k in coordinatesList) {
                    newPrecinctCoordinates.push([coordinatesList[k].lng, coordinatesList[k].lat]);
                };
                LeafletMap.precincts[precinctName].geometry.type = LeafletMap.precinctLayer._layers[i].feature.geometry.type;
                LeafletMap.precincts[precinctName].geometry.coordinates = [newPrecinctCoordinates];
                precinctObjects.push(LeafletMap.precincts[precinctName]);
            }
        };
        DataHandler.updatePrecinctGeojson();
        DataHandler.uploadPrecinctData(precinctObjects);
        
    },

    updatePrecincts: (precinctList) => {
        LeafletMap.precinctGeojson = [];
        for (let i = 0; i < precinctList.length; i++) {
            let canonName = precinctList[i];
            let geojson = JsonHandler.convertToGeojson(LeafletMap.precincts[canonName]);
            LeafletMap.precinctGeojson.push(geojson);
        }
        LeafletMap.updatePrecinctLayer();
    },

    updateDistricts: (districtList) => {
        LeafletMap.districtGeojson = [];
        for (let i = 0; i < districtList.length; i++) {
            let canonName = districtList[i];
            let geojson = JsonHandler.convertToGeojson(LeafletMap.districts[canonName]);
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
    },

    updatePrecinctGeojson: () => {
        LeafletMap.precinctGeojson = [];
        let precinctCNames = LeafletMap.districts[LeafletMap.currentDistrict].precinctCNames;
        for (let i in precinctCNames) {
            let precinctGeojson = JsonHandler.convertToGeojson(LeafletMap.precincts[precinctCNames[i]]);
            LeafletMap.precinctGeojson.push(precinctGeojson);
        }
    }
}