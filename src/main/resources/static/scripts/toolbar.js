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
        var filtersContainer = document.getElementById("states");
        var states = filtersContainer = filtersContainer.getElementsByClassName("dropdown-item");
        for (var i = 0; i  < states.length; i++) {
            states[i].addEventListener("click", function() {
                var state = this.text;
                LeafletMap.panMap(ToolBar.stateCoordinates[state][0], ToolBar.stateCoordinates[state][1], 7);
            })
        }
    },

    initElectionDropdown: function() {
        // Add event listeners to election options to handle updating of election data
        var filtersContainer = document.getElementById("elections");
        var elections = filtersContainer.getElementsByClassName("dropdown-item");
        for (var i = 0; i  < elections.length; i++) {
            elections[i].addEventListener("click", function() {
                // Change highlighted election to the selected one
                var currentElection = filtersContainer.getElementsByClassName("active")[0];
                currentElection.className = currentElection.className.replace("active", "");
                this.className += " active";
                // TODO
                // Send request to backend for election data of selected year
            })
        }
    },

    initFiltersDropdown: function() {
        var toggleFilters = function(element){
             // TODO
            // Toggle the visibility of the indicated element
        };

        var filtersContainer = document.getElementById("filters");
        var options = filtersContainer.getElementsByClassName("dropdown-item");
        for (var i = 0; i  < options.length; i++) {
            options[i].addEventListener("click", function() {
                // Highlights filters dropdown menu elements when active
                if (this.className.includes("active")) {
                    this.className = this.className.replace("active", "");
                }
                else {
                    this.className += " active";
                }
                toggleFilters(this.text);
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
        //toggleMode("insert")
        $('#insert').click( function() {
            LeafletMap.currentMode = LeafletMap.modes.insert;
            ToolBar.toggleEditButtons();

            LeafletMap.map.pm.enableDraw('Polygon');

            // Drawing eventHandlers
            LeafletMap.map.on('pm:create', e => {
                console.log('Drawing end');
                LeafletMap.map.removeLayer(e.layer);

                var precinctShape = "";
                var precinctCoordinates = [];
                var coordinatesList = e.layer._latlngs;
                for (var i = 0; i < coordinatesList.length; i++) {
                    precinctCoordinates.push([coordinatesList[i].lng, coordinatesList[i].lat])
                };
                coloradoPrecincts[0].features.push( 
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
                        "coordinates": [[...precinctCoordinates]]
                    }
                });
                if (LeafletMap.map.hasLayer(LeafletMap.precinctGeojson)) {
                    LeafletMap.map.removeLayer(LeafletMap.precinctGeojson);
                }
                LeafletMap.precinctGeojson = L.geoJson(coloradoPrecincts,{ onEachFeature: LeafletMap.onEachFeature }).addTo(LeafletMap.map);
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
            LeafletMap.currentMode = LeafletMap.modes.modify;
            ToolBar.toggleEditButtons();
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

        });
        $('#cancel-btn').click( function() {
            // TODO
            // Revert all changes made during edit

            // Hide the edit buttons
            LeafletMap.currentMode = LeafletMap.modes.default;
            ToolBar.toggleEditButtons();
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

    }


};

ToolBar.init();
