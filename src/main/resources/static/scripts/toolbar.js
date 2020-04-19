var ToolBar = {
    stateCoordinates: {"Colorado": [39.5501, -105.7821], "Florida": [27.6648, -81.5158], "Maryland": [39.0458, -76.6413]},

    init: function() {
        this.initEventHandlers();
    },
    // Initiate event handlers for the elements of each dropdown
    initEventHandlers: function() {
        this.initStateDropdown();
        this.initElectionDropdown();
        this.initFiltersDropdown();
        this.initToolsDropDown();
    },

    initStateDropdown: function() {
        // Add an event handler to move map to the selected state
        var states = $('#states').find('.dropdown-item');
        for (var i = 0; i  < states.length; i++) {
            states[i].addEventListener("click", function() {
                LeafletMap.panMap(ToolBar.stateCoordinates[state][0], ToolBar.stateCoordinates[state][1], 7);
            })
        }
    },

    initElectionDropdown: function() {
        // Add event listeners to election options to handle updating of election data
        var elections = $('#elections').find('.dropdown-item');
        for (var i = 0; i  < elections.length; i++) {
            elections[i].addEventListener("click", function() {
                // Change highlighted election to the selected one
                var currentElection = $('#elections').find(".active")[0];
                currentElection.className = currentElection.className.replace("active", "");
                this.className += " active";
                // TODO
                // Send request to backend for election data of selected year
            })
        }
    },

    initFiltersDropdown: function() {
        var options = $('#filters').find('.dropdown-item');
        for (var i = 0; i  < options.length; i++) {
            options[i].addEventListener("click", function() {
                // Highlights filters dropdown menu elements when active
                if (this.className.includes("active")) {
                    this.className = this.className.replace(/active/g, "");
                    ToolBar.enableFilter(this.id, false);
                }
                else {
                    this.className += " active";
                    ToolBar.enableFilter(this.id, true);
                }
            })
        }
    },

    initToolsDropDown: function() {
        this.insertPrecinctHandler();
        this.mergePrecinctHandler();
        this.modifyPrecinctHanlder();
        this.createCommentHandler();
        this.resetMapHandler();
        this.editButtonHandler();
    },

    insertPrecinctHandler: function() {
        $('#insert').click( function() {
            // Go to precinct level
            LeafletMap.map.setZoom(8);
            // Change mode to enable different map functionality
            LeafletMap.currentMode = LeafletMap.modes.insert;
            ToolBar.toggleEditButtons();

            LeafletMap.map.pm.enableDraw('Polygon');
            // Limit zoom level to precinct level 
            LeafletMap.map.options.minZoom = 8;

            // EventHandler for when a precinct is created
            LeafletMap.map.on('pm:create', e => {
                LeafletMap.map.removeLayer(e.layer);
                var precinctShape = e.shape;
                var newPrecinctCoordinates = [];
                var coordinatesList = e.layer._latlngs[0];
                for (var i = 0; i < coordinatesList.length; i++) {
                    newPrecinctCoordinates.push([coordinatesList[i].lng, coordinatesList[i].lat])
                };
                
3                // Add new precinct boundaries to a temporary variable
                LeafletMap.tempPrecinctBoundaries.push( 
                {
                    "type": "Feature",
                    "properties": 
                    {
                        "name": "Ghost Precinct",
                        "density": 0,
                        "population": 0,
                        "Democratic":0,
                        "Republican":0,
                        "White":0,
                        "Hispanic":0,
                        "AfricanAmerican":0
                    },
                    "geometry": 
                    {
                        "type": precinctShape,
                        "coordinates": [newPrecinctCoordinates]
                    }
                });
                // Remove current temp geojson if it exists
                if (LeafletMap.map.hasLayer(LeafletMap.tempGeojson)) { LeafletMap.map.removeLayer(LeafletMap.tempGeojson);}
                // Add the new updated temp geojson
                LeafletMap.tempGeojson = L.geoJson(LeafletMap.tempPrecinctBoundaries,{ onEachFeature: LeafletMap.onEachFeature }).addTo(LeafletMap.map);

                // Allow user to draw more than one precinct
                LeafletMap.map.pm.enableDraw('Polygon');
            });
        });
    },

    mergePrecinctHandler: function() {
        $('#merge').click( function() {
            LeafletMap.currentMode = LeafletMap.modes.merge;
            ToolBar.toggleEditButtons();
        });
    },

    modifyPrecinctHanlder: function() {
        $('#modify').click( function() {
            // Go to precinct level
            LeafletMap.map.setZoom(8);

            // Zooming recreates the precinct layers so must wait for precinctGeojson to be updated first
            setTimeout (function () {
                // Allow precinct borders to be edited
                LeafletMap.precinctGeojson.pm.enable();
            }, 300);
            // Switch map mode
            LeafletMap.currentMode = LeafletMap.modes.modify;
            ToolBar.toggleEditButtons();

            // Limit zoom level to precinct level 
            LeafletMap.map.options.minZoom = 8;

        });
    },

    createCommentHandler: function() {
        $('#create').click( function() {
            LeafletMap.currentMode = LeafletMap.modes.create;
            ToolBar.toggleEditButtons();
        });
    },

    resetMapHandler: function() {
        $('#reset').click( function() 
        {
            LeafletMap.map.setView(LeafletMap.usaCoordinates, 5);
        });
    },

    editButtonHandler: function() {
        $('#done-btn').click( function() {
            // Update the PrecinctData on client
            ToolBar.updatePrecinctData();
            // TODO
            // Send updated data to server

            // Reset map functionalities
            ToolBar.resetMapFunctionalities();
        });
        $('#cancel-btn').click( function() {
            // TODO
            // Revert all changes made during edit
            ToolBar.resetMapFunctionalities();

        });
    },

    toggleEditButtons: function() {
        if (LeafletMap.currentMode == LeafletMap.modes.default) {
            $('#done-btn').hide();
            $('#cancel-btn').hide();
        }
        else {
            $('#done-btn').show();
            $('#cancel-btn').show();
        }
    },

    resetMapFunctionalities: function() {
        // Reset all temp variables and revert back to normal map functionality
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert:
                // Need to remove all tempJson precincts from map and empty tempPrecinctBoundaries
                if (LeafletMap.map.hasLayer(LeafletMap.tempGeojson)) { LeafletMap.map.removeLayer(LeafletMap.tempGeojson);}
                LeafletMap.tempPrecinctBoundaries = [];
                LeafletMap.tempGeojson = null;
                // Refresh the precinct layers
                if (LeafletMap.map.hasLayer(LeafletMap.precinctGeojson)) { 
                    LeafletMap.map.removeLayer(LeafletMap.precinctGeojson);
                    LeafletMap.precinctGeojson = L.geoJson(coloradoPrecincts, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
                }
                LeafletMap.map.pm.disableDraw();
                break;
            case LeafletMap.modes.modify:
                if (LeafletMap.map.hasLayer(LeafletMap.precinctGeojson)) { 
                    LeafletMap.map.removeLayer(LeafletMap.precinctGeojson);
                    LeafletMap.precinctGeojson = L.geoJson(coloradoPrecincts, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
                }
                break;
            default:
                console.log("INVALID CURRENT MODE");
        }
        LeafletMap.currentMode = LeafletMap.modes.default;
        LeafletMap.map.options.minZoom = 5;
        ToolBar.toggleEditButtons();
    },

    updatePrecinctData: function() {
        switch(LeafletMap.currentMode) {
            case LeafletMap.modes.insert: 
                // Add all the new precincts to the current list of precincts
                for (var i = 0; i < LeafletMap.tempPrecinctBoundaries.length; i++) {
                    coloradoPrecincts.push(LeafletMap.tempPrecinctBoundaries[i]);
                }
                // Update the GUI
                if (LeafletMap.map.hasLayer(LeafletMap.precinctGeojson)) { 
                    LeafletMap.map.removeLayer(LeafletMap.precinctGeojson); 
                    LeafletMap.precinctGeojson = L.geoJson(coloradoPrecincts, { onEachFeature: LeafletMap.onEachFeature }, { style: { pmIgnore: false } }).addTo(LeafletMap.map);
                }
                break;
            case LeafletMap.modes.modify:
                // Replace precinctCoordinates with the new ones from the precinctGeojson layer
                for (var i in LeafletMap.precinctGeojson._layers) {
                    var precinctName = LeafletMap.precinctGeojson._layers[i].feature.properties.name;
                    for (var j in coloradoPrecincts) {
                        if (precinctName == coloradoPrecincts[j].properties.name) {
                            console.log("Update the coordinates");
                            var newPrecinctCoordinates = [];
                            var coordinatesList = LeafletMap.precinctGeojson._layers[i]._latlngs[0];
                            for (var k in coordinatesList) {
                                newPrecinctCoordinates.push([coordinatesList[k].lng, coordinatesList[k].lat]);
                            };
                            coloradoPrecincts[j].geometry.type = LeafletMap.precinctGeojson._layers[i].feature.geometry.type;
                            coloradoPrecincts[j].geometry.coordinates = [newPrecinctCoordinates];
                            break;
                        }
                    }
                };
                break;
            default:
                console.log("INVALID CURRENT MODE");
        }
    },

    enableFilter: function(filterid, option) {
        if (filterid == 'district-filter')
            LeafletMap.enableDistrictLayer(!option);
        else if (filterid == 'precinct-filter') 
            LeafletMap.enablePrecinctLayer(!option);
        else if (filterid == 'national-parks-filter')
            console.log('no national park data');
    }
};

ToolBar.init();
