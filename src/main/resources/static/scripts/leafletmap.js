const LeafletMap = {
    
    modes: {default: 0, insert: 1, merge: 2, modify: 3},
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
    selectedPrecincts: [],
    modifiedPrecincts: [],
    usaCoordinates: [39.51073, -96.4247],
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
        LeafletMap.statesGeojson = DataHandler.getAllStateData();
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
            // Display only the state borders when zoomed out too far
            let zoomLevel = LeafletMap.map.getZoom();
            if (zoomLevel <= 6) {
                LeafletMap.enableStateLayer(true);
                LeafletMap.enableDistrictLayer(false);
                LeafletMap.enablePrecinctLayer(false);
                ToolBar.enableAllFilters(true);
                ToolBar.unselectState();
            }    
            else if (zoomLevel == 7 && LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
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
                : 'Hover for more detail');

            //  '<b>' + props.name + '</b><br />' + props.density + ' people / mi<sup>2</sup>'
            //  : 'Hover states for more details');
        };
        LeafletMap.infoBox.onAdd = () => {
            Window._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
            LeafletMap.infoBox.update();
            return Window._div;
        };
        // Add to the leaflet map
        LeafletMap.infoBox.addTo(LeafletMap.map);

    },

    onEachFeature: (feature, layer) => {
        layer.on({
            mouseover: LeafletMap.highlightFeature,
            mouseout: LeafletMap.resetHighlight,
            click: LeafletMap.onClickHandler
        });
    },

    highlightFeature: (e) => {
        let layer = e.target;

        layer.setStyle({
            weight: 4,
            color: '#1a0aff',
            dashArray: '',
            fillOpacity: 0.2
        });

        if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
            //layer.bringToFront();
        }
        LeafletMap.infoBox.update(layer.feature.properties);
    },

    resetHighlight: (e) => {
        if (LeafletMap.map.hasLayer(LeafletMap.stateLayer)) {
            LeafletMap.stateLayer.resetStyle(e.target);
            LeafletMap.infoBox.update();
        }
        else if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
            LeafletMap.precinctLayer.resetStyle(e.target);
            LeafletMap.infoBox.update();
        }
        else if (LeafletMap.map.hasLayer(LeafletMap.districtLayer)) {
            LeafletMap.districtLayer.resetStyle(e.target);
            LeafletMap.infoBox.update();
        }
    },

    onClickHandler: (e) => {
        const canonicalName = e.target.feature.properties.canonName;

        // The clicked layer is a state layer
        if (LeafletMap.states[canonicalName] != null) {
            LeafletMap.currentState = canonicalName;
            // Responsible for updating toolbar GUI and updating districtGeoson
            LeafletMap.stateLayerHandler(canonicalName);
            LeafletMap.enableStateLayer(false)
             // Enable all the options in filter dropdown menu
            ToolBar.enableAllFilters(true);
            // Disable district filter 
            let filter = $('#district-filter')[0];
            filter.className = filter.className.replace(/active/g, "");
        }
        // The clicked layer is a district
        else if (LeafletMap.districts[canonicalName] != null) {
            LeafletMap.currentDistrict = canonicalName;
            // Responsible for updating precinctGeoson
            LeafletMap.districtLayerHandler(canonicalName);
            LeafletMap.enableDistrictLayer(false);
            ToolBar.enableAllFilters(true);
            // Disable precinct filter 
            let filter = $('#precinct-filter')[0];
            filter.className = filter.className.replace(/active/g, "");
        }
          // The clicked layer is a precinct
        else if (LeafletMap.precincts[canonicalName] != null) {
            LeafletMap.precinctLayerHandler(canonicalName, e);
        }
        LeafletMap.zoomToFeature(e);
        stateChangeHandler(e.target.feature.properties.canonName);
    },

    stateLayerHandler: (stateCanonName) => {
        // Update the states dropdown menu UI
        const statesDropdownElements = $('#states').find(".dropdown-item");
        for (let i = 0; i < statesDropdownElements.length; i++) {
            if (statesDropdownElements[i].text.toLowerCase() == stateCanonName) {
                ToolBar.unselectState();
                statesDropdownElements[i].className += " active";
                // Get district from server
                DataHandler.getDistrictData(LeafletMap.states[stateCanonName].districtCNames);
                break;
            }
        }
    },

    districtLayerHandler: (districtCanonName) => {
        DataHandler.getPrecinctData(LeafletMap.districts[districtCanonName].precinctCNames);
    },

    precinctLayerHandler: (precinctCanonName, event) => {
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.merge:
                if (selectedPrecincts.length == 2) {
                    selectedPrecincts.shift();
                }
                break;
            case LeafletMap.modes.modify:
                    ToolBar.toggleEditButtons();
                    event.target.pm.enable();
                    LeafletMap.precinctLayer.on('pm:edit', e => {
                        console.log(e);
                    });
                break;
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
                    LeafletMap.stateLayer = L.geoJson(LeafletMap.statesGeojson, 
                        { onEachFeature: LeafletMap.onEachFeature }, 
                        { style: { pmIgnore: true } 
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
                    LeafletMap.districtLayer = L.geoJson(LeafletMap.districtGeojson, 
                        { onEachFeature: LeafletMap.onEachFeature }, 
                        { style: { pmIgnore: true } 
                    }).addTo(LeafletMap.map).bringToFront(); }
                break;
            case false:
                if (LeafletMap.map.hasLayer(LeafletMap.districtLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.districtLayer); }
                break;
        }
    },

    enablePrecinctLayer: (option) => {
        switch(option) {
            case true:
                if (!LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, 
                        { onEachFeature: LeafletMap.onEachFeature }, 
                        { style: { pmIgnore: false } 
                    }).addTo(LeafletMap.map);
                    LeafletMap.precinctLayer.bringToFront(); }
                if (!LeafletMap.map.hasLayer(LeafletMap.tempLayer)) {
                    LeafletMap.tempLayer = L.geoJson(LeafletMap.tempPrecinctGeojson, 
                        { onEachFeature: LeafletMap.onEachFeature }, 
                        { style: { pmIgnore: false } 
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

    updatePrecinctLayer: () => {
        if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) { 
            LeafletMap.map.removeLayer(LeafletMap.precinctLayer); 
            LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
        }
    },

    resetMapFunctionalities: () => {
        // Reset all temp variables and revert back to normal map functionality
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert:
                // Need to remove all tempJson precincts from map and empty tempPrecinctGeojson
                if (LeafletMap.map.hasLayer(LeafletMap.tempLayer)) { LeafletMap.map.removeLayer(LeafletMap.tempLayer);}
                LeafletMap.tempPrecinctGeojson = [];
                LeafletMap.tempLayer = null;
                // Refresh the precinct layers
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.precinctLayer);
                    LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
                }
                LeafletMap.map.pm.disableDraw();
                break;
            case LeafletMap.modes.modify:
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) { 
                    LeafletMap.map.removeLayer(LeafletMap.precinctLayer);
                    LeafletMap.precinctLayer = L.geoJson(LeafletMap.precinctGeojson, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
                }
                break;
            default:
                console.log("INVALID CURRENT MODE");
        }
        LeafletMap.currentMode = LeafletMap.modes.default;
        ToolBar.toggleEditButtons();
    },
    
}
LeafletMap.init();