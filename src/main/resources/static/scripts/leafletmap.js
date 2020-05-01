const LeafletMap = {
    
    modes: {default: 0, insert: 1, merge: 2, modify: 3, add: 4, remove: 5},
    currentMode: 0,
    stateLayer: null,
    districtLayer: null,
    precinctLayer: null,
    tempLayer: null,
    statesGeojson: [],
    districtGeojson: [],
    precinctGeojson: [],
    tempPrecinctGeojson: [],
    infoBox: L.control(),
    states: {},
    districts: {},
    precincts: {},
    ghostCounter: 0,
    currentState: null,
    currentDistrict: null,
    currentPrecinct: null,
    precinctBeingChanged: null,
    selectedPrecincts: [],
    modifiedPrecincts: [],
    usaCoordinates: [39.51073, -96.4247],

    highlightColors: {red: '#FF0000', blue: '#1a0aff', green: '#32CD32'},
    // The iteractive map that is going to be displayed on the webpage
    map: L.map('mapid', { minZoom: 5, maxZoom: 18, maxBounds: [[20.396308, -135.848974], [49.384358, -55.885444]] }),

    init: () => {
        LeafletMap.map.setView(LeafletMap.usaCoordinates, 5);
        LeafletMap.initData();
        LeafletMap.initLeafletLayers();
        LeafletMap.initZoomHandlers();
        LeafletMap.initInfoBox();
    },

    initData: () => {
        DataHandler.getAllStateData();
        DataHandler.getAllDistrictData();
        DataHandler.getAllPrecinctData();
    },

    initLeafletLayers: () => {
        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
            id: 'mapbox/streets-v11',
            accessToken: 'pk.eyJ1IjoiZGFuZzk4IiwiYSI6ImNrNmlsbGZqNTAyYzgzZHFtcjczMmI2Z3EifQ.N6aBfLfiwLfyTn_Iz0TvIw'
        }).addTo(LeafletMap.map);
    },

    initZoomHandlers: () => {
        LeafletMap.map.on('zoomend', function (e) {
            // Display only the state borders when zoomed out too far from the districts
            let zoomLevel = LeafletMap.map.getZoom();
            if (zoomLevel <= 6) {
                LeafletMap.enableStateLayer(true);
                LeafletMap.enableDistrictLayer(false);
                LeafletMap.enablePrecinctLayer(false);
                ToolBar.enableAllFilters(true);
            }    
            // Display only the district borders when zoomed out too far from the precincts
            else if (zoomLevel == 8 && LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                LeafletMap.enableStateLayer(false);
                LeafletMap.enableDistrictLayer(true);
                LeafletMap.enablePrecinctLayer(false);
                ToolBar.enableAllFilters(true);
                let filter = $('#district-filter')[0];
                filter.className = filter.className.replace(/active/g, "");
            }  
            console.log("Current Zoom Level =" + zoomLevel)
         });
    },

    initInfoBox: () => {
        // Create an function that creates a information box in the top right corner and populates it with the given props
        LeafletMap.infoBox.update = (props) => {
            // TODO
            // Need to update properties to their correct name in the geojson from server
            Window._div.innerHTML = '<h4>U.S State Data</h4>' + (props ?
                '<b>' + props.displayName + '</b><br />' + 'Democratic: ' +  + '%' +
                '</b><br>Republican: ' +  + '%' + '</b><br />White: ' +  + '%' +
                '</b><br />Other: ' +  + '%' + '</b><br />African American: '
                +  + '%' + '</b><br />Population:'
                : '');

            //  '<b>' + props.name + '</b><br />' + props.density + ' people / mi<sup>2</sup>'
            //  : 'Hover states for more details');
        };
        LeafletMap.infoBox.onAdd = () => {
            Window._div = L.DomUtil.create('div', 'info');
            LeafletMap.infoBox.update();
            return Window._div;
        };
        LeafletMap.infoBox.addTo(LeafletMap.map);

    },

    onEachFeature: (feature, layer) => {
        layer.on({
            //mouseover: LeafletMap.highlightFeature,
            //mouseout: LeafletMap.resetHighlight,
            click: LeafletMap.onClickHandler
        });
    },
  
    highlightFeature: (event, hexColor, bringToFront) => {
        let layer = event.target;
        layer.setStyle({
            weight: 4,
            color: hexColor,
            dashArray: '',
            fillOpacity: 0.2
        });
        if (bringToFront) {
            layer.bringToFront();  
        }
        else {
            layer.bringToBack();
        }
        LeafletMap.infoBox.update(layer.feature.properties);
    },

    highlightNeighbors: (precinctCName) => {
        LeafletMap.precinctLayer.resetStyle();
        let neighborCNames = LeafletMap.precincts[precinctCName].neighborCNames;
        let layers = LeafletMap.precinctLayer._layers;
        Object.entries(layers).forEach(([, value]) => {
            let cName = value.feature.properties.canonName;
            if (neighborCNames.indexOf(cName) != -1) {
                value.setStyle({
                    weight: 4,
                    color: LeafletMap.highlightColors.red,
                    dashArray: '',
                    fillOpacity: 0.2
                });
            }
        });
    },

    resetHighlight: (event) => {
        if (LeafletMap.map.hasLayer(LeafletMap.stateLayer)) {
            LeafletMap.stateLayer.resetStyle(event.target);
            LeafletMap.infoBox.update();
        }
        else if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
            LeafletMap.precinctLayer.resetStyle(event.target);
            LeafletMap.infoBox.update();
        }
        else if (LeafletMap.map.hasLayer(LeafletMap.districtLayer)) {
            LeafletMap.districtLayer.resetStyle(event.target);
            LeafletMap.infoBox.update();
        }
    },

    togglePrecinctSelection: (event) => {
        if (LeafletMap.selectedPrecincts.indexOf(LeafletMap.currentPrecinct) === -1) {
            LeafletMap.highlightFeature(event, LeafletMap.highlightColors.green, false);
            LeafletMap.selectedPrecincts.push(LeafletMap.currentPrecinct);
        }
        else {
            LeafletMap.precinctLayer.resetStyle(event.target);
            let index = LeafletMap.selectedPrecincts.indexOf(LeafletMap.currentPrecinct);
            LeafletMap.selectedPrecincts.splice(index, 1);
        }
    },

    allowPrecinctModification: (event) => {
        // Only want one precinct to be modified at a time
        if (LeafletMap.currentPrecinct != event.target.feature.properties.canonName) {
            LeafletMap.precinctLayer.pm.disable();
            event.target.pm.enable();
        }
        LeafletMap.precinctLayer.on('pm:edit', e => {
            let canonName = e.sourceTarget.feature.properties.canonName;
            let index = LeafletMap.modifiedPrecincts.indexOf(canonName);
            // indexOf() returns -1 if the element is not found
            if (index === -1) {
                LeafletMap.modifiedPrecincts.push(canonName);
            }
        });
    },

    onClickHandler: (event) => {
        LeafletMap.infoBox.update(event.target.feature.properties);
        const canonicalName = event.target.feature.properties.canonName;
        // The clicked layer is a state layer
        if (LeafletMap.states[canonicalName] != null) {
            LeafletMap.currentState = canonicalName;
            // Responsible for updating toolbar GUI and updating districtGeoson
            LeafletMap.stateLayerHandler(canonicalName);
            LeafletMap.enableStateLayer(false)
            ToolBar.enableAllFilters(true);
            // Disable district filter 
            let filter = $('#district-filter')[0];
            filter.className = filter.className.replace(/active/g, "");
            LeafletMap.zoomToFeature(event);
        }
        // The clicked layer is a district
        else if (LeafletMap.districts[canonicalName] != null) {
            LeafletMap.currentDistrict = canonicalName;
            // Responsible for updating precinctGeoson
            LeafletMap.districtLayerHandler(canonicalName);
            LeafletMap.enableDistrictLayer(false);
            LeafletMap.enablePrecinctLayer(true);
            ToolBar.enableAllFilters(true);
            // Disable precinct filter 
            let filter = $('#precinct-filter')[0];
            filter.className = filter.className.replace(/active/g, "");
            LeafletMap.zoomToFeature(event);
        }
          // The clicked layer is a precinct
        else if (LeafletMap.precincts[canonicalName] != null) {
            LeafletMap.currentPrecinct = canonicalName;
            LeafletMap.precinctLayerHandler(canonicalName, event);
        }
        LeafletMap.changeInfoBoxName(event.target.feature.properties.canonName);
    },

    stateLayerHandler: (stateCanonName) => {
        // Update the states dropdown menu UI
        const statesDropdownElements = $('#states').find(".dropdown-item");
        for (let i = 0; i < statesDropdownElements.length; i++) {
            if (statesDropdownElements[i].text.toLowerCase() == stateCanonName) {
                ToolBar.unselectState();
                statesDropdownElements[i].className += " active";
                // Only request district data that has not been request before
                let districtCNames = LeafletMap.states[stateCanonName].districtCNames;
                let requestList = [];
                let currentList = [];
                for (let j = 0; j < districtCNames.length; j++) {
                    if (LeafletMap.districts[districtCNames[j]] == null) {
                        requestList.push(districtCNames[j]);
                    }
                    else {
                        currentList.push(districtCNames[j]);
                    }
                }
                if (requestList.length != 0) {
                    DataHandler.getDistrictData(requestList);
                }
                // Display the districts that are already on the client
                DataHandler.updateDistricts(currentList);
                break;
            }
        }
    },

    districtLayerHandler: (districtCanonName) => {
        // Only request the precincts that are not stored on the client
        let precinctCNames = LeafletMap.districts[districtCanonName].precinctCNames;
        let requestList = [];
        let currentList = [];
        for (let i = 0; i < precinctCNames.length; i++) {
            if (LeafletMap.precincts[precinctCNames[i]] == null) {
                requestList.push(precinctCNames[i]);
            }
            else {
                currentList.push(precinctCNames[i]);
            }
        }
        if (requestList.length != 0) {
            DataHandler.getPrecinctData(requestList);
        }
        DataHandler.updatePrecincts(currentList);
    },

    precinctLayerHandler: (precinctCanonName, event) => {
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.default: {
                LeafletMap.highlightNeighbors(precinctCanonName);
                LeafletMap.highlightFeature(event, LeafletMap.highlightColors.blue, true);
                break;
            }
            case LeafletMap.modes.merge: {
                LeafletMap.togglePrecinctSelection(event);
                break;
            }
            case LeafletMap.modes.modify: {
                LeafletMap.allowPrecinctModification(event);
                break;
            }
            case LeafletMap.modes.add: {
                
                let neighborCNames = LeafletMap.precincts[LeafletMap.precinctBeingChanged].neighborCNames;
                // Only allow the precinct to be selected if it is not a neighbor already and it is not the precinct being changed
                if (neighborCNames.indexOf(LeafletMap.currentPrecinct) === -1 && LeafletMap.currentPrecinct != LeafletMap.precinctBeingChanged) {
                    LeafletMap.togglePrecinctSelection(event);
                }
                break;
            }
            case LeafletMap.modes.remove: {
                let neighborCNames = LeafletMap.precincts[LeafletMap.precinctBeingChanged].neighborCNames;
                if(neighborCNames.indexOf(LeafletMap.currentPrecinct != -1) && LeafletMap.currentPrecinct != LeafletMap.precinctBeingChanged) {
                    LeafletMap.togglePrecinctSelection(event);
                }
            }
        }
    },

    zoomToFeature: (e) => {
        LeafletMap.map.fitBounds(e.target.getBounds());
    },

    panMap: (lat, long, zoom) => {
        LeafletMap.map.setView([lat, long], zoom);
    },
    
    enableStateLayer: (option) => {
        switch(option) {
            case true:
                if (!LeafletMap.map.hasLayer(LeafletMap.stateLayer)) {
                    LeafletMap.stateLayer = L.geoJson(LeafletMap.statesGeojson, { 
                        onEachFeature: LeafletMap.onEachFeature 
                    }).addTo(LeafletMap.map); }
                break;
            case false:
                if (LeafletMap.map.hasLayer(LeafletMap.stateLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.stateLayer); }
                break;
        }
    },

    enableDistrictLayer: (option) => {
        switch(option) {
            case true:
                if (!LeafletMap.map.hasLayer(LeafletMap.districtLayer)) { 
                    LeafletMap.districtLayer = L.geoJson(LeafletMap.districtGeojson, {
                        onEachFeature: LeafletMap.onEachFeature 
                    }).addTo(LeafletMap.map).bringToFront(); }
                break;
            case false:
                if (LeafletMap.map.hasLayer(LeafletMap.districtLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.districtLayer); 
                }
                break;
        }
    },

    enablePrecinctLayer: (option) => {
        switch(option) {
            case true:
                if (!LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, {
                         onEachFeature: LeafletMap.onEachFeature 
                    }).addTo(LeafletMap.map);
                    LeafletMap.precinctLayer.bringToFront(); }
                if (!LeafletMap.map.hasLayer(LeafletMap.tempLayer)) {
                    LeafletMap.tempLayer = L.geoJson(LeafletMap.tempPrecinctGeojson, {
                        onEachFeature: LeafletMap.onEachFeature 
                    }).addTo(LeafletMap.map); }
                break;
                
            case false:
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    LeafletMap.map.removeLayer(LeafletMap.precinctLayer); }
                if (LeafletMap.map.hasLayer(LeafletMap.tempLayer)) {
                    LeafletMap.map.removeLayer(LeafletMap.tempLayer); }
                break;
        }
    },

    updateTempLayer: () => {
        if (LeafletMap.map.hasLayer(LeafletMap.tempLayer)) { 
            LeafletMap.map.removeLayer(LeafletMap.tempLayer);
        }
        LeafletMap.tempLayer = L.geoJson(LeafletMap.tempPrecinctGeojson,{ 
            onEachFeature: LeafletMap.onEachFeature 
        }).addTo(LeafletMap.map);
    },

    updatePrecinctLayer: () => {
        if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) { 
            LeafletMap.map.removeLayer(LeafletMap.precinctLayer); 
        }
        LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, {
            onEachFeature: LeafletMap.onEachFeature 
        }).addTo(LeafletMap.map);
    },

    updateDistrictLayer: () => {
        if (LeafletMap.map.hasLayer(LeafletMap.districtLayer)) { 
            LeafletMap.map.removeLayer(LeafletMap.districtLayer); 
        }
        LeafletMap.districtLayer = L.geoJson(LeafletMap.districtGeojson, {
            onEachFeature: LeafletMap.onEachFeature
        }).addTo(LeafletMap.map);
    },

    resetMapFunctionalities: () => {
        // Reset all temp variables and revert back to normal map functionality
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert:
                // Need to remove all tempJson precincts from map and empty tempPrecinctGeojson
                if (LeafletMap.map.hasLayer(LeafletMap.tempLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.tempLayer);
                }
                LeafletMap.tempPrecinctGeojson = [];
                LeafletMap.tempLayer = null;
                LeafletMap.updatePrecinctLayer();
                LeafletMap.map.pm.disableDraw();
                break;
            case LeafletMap.modes.modify:
                LeafletMap.modifiedPrecincts = [];
                LeafletMap.updatePrecinctLayer();
                break;
            case LeafletMap.modes.merge:
                LeafletMap.selectedPrecincts = [];
                LeafletMap.updatePrecinctLayer();
                break;
            case LeafletMap.modes.add:
                LeafletMap.selectedPrecincts = [];
                LeafletMap.precinctBeingChanged = null;
                LeafletMap.updatePrecinctLayer();
                break;
            case LeafletMap.modes.remove:
                LeafletMap.selectedPrecincts = [];
                LeafletMap.precinctBeingChanged = null;
                LeafletMap.updatePrecinctLayer();
            default:
                console.log("INVALID CURRENT MODE");
        }
        LeafletMap.currentPrecinct = null;
        LeafletMap.currentMode = LeafletMap.modes.default;
        ToolBar.toggleEditButtons();
    },

    changeInfoBoxName: (stateName) => {
        $("#state-name").text(stateName);
    }
}
LeafletMap.init();