const ToolBar = {
    stateCoordinates: {"colorado": [39.5501, -105.7821], "florida": [27.6648, -81.5158], "west virginia": [38.5976, -80.4549]},

    init: () => {
        ToolBar.initEventHandlers();
    },

    initEventHandlers: () => {
        ToolBar.initStateDropdown();
        ToolBar.initElectionDropdown();
        ToolBar.initFiltersDropdown();
        ToolBar.initToolsDropDown();
    },

    initStateDropdown: () => {
        // Add an event handler to move map to the selected state
        const states = $('#states').find('.dropdown-item');
        for (let i = 0; i  < states.length; i++) {
            states[i].addEventListener("click", () => {
                // Move map view to the selected state
                const state = states[i].text.toLowerCase();
                LeafletMap.panMap(ToolBar.stateCoordinates[state][0], ToolBar.stateCoordinates[state][1], 7);

                // Highlight the selected state in the dropdown
                const currentState = $('#states').find(".active")[0];
                if (currentState != null) {
                    currentState.className = currentState.className.replace("active", "");
                }
                this.className += " active";
                DataHandler.getDistrictData(LeafletMap.states[state.toLowerCase()].districtCNames);
            })
        }
    },

    initElectionDropdown: () => {
        // Add event listeners to election options to handle updating of election data
        const elections = $('#elections').find('.dropdown-item');
        for (let i = 0; i  < elections.length; i++) {
            elections[i].addEventListener("click", function() {
                // Change highlighted election to the selected one
                let currentElection = $('#elections').find(".active")[0];
                currentElection.className = currentElection.className.replace("active", "");
                this.className += " active";
                // TODO
                // Send request to backend for election data of selected year
            })
        }
    },

    initFiltersDropdown: () => {
        const options = $('#filters').find('.dropdown-item');
        for (let i = 0; i  < options.length; i++) {
            options[i].addEventListener("click", () => {
                // Highlights filters dropdown menu elements when selected
                if (options[i].className.includes("active")) {
                    options[i].className = options[i].className.replace(/active/g, "");
                    ToolBar.enableFilter(options[i].id, false);
                }
                else {
                    options[i].className += " active";
                    ToolBar.enableFilter(options[i].id, true);
                }
            })
        }
    },

    initToolsDropDown: () => {
        ToolBar.addNeighborHandler();
        ToolBar.removeNeighborHandler();
        ToolBar.insertPrecinctHandler();
        ToolBar.mergePrecinctHandler();
        ToolBar.modifyPrecinctHanlder();
        ToolBar.createCommentHandler();
        ToolBar.resetMapHandler();
        ToolBar.editButtonHandler();
    },

    addNeighborHandler: () => {
        $('#add').click(() => {
            if (LeafletMap.currentMode == LeafletMap.modes.default) {
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer) && LeafletMap.currentPrecinct != null) {
                    LeafletMap.currentMode = LeafletMap.modes.add;
                    LeafletMap.selectedPrecincts = [];
                    LeafletMap.precinctBeingChanged = LeafletMap.currentPrecinct;
                    ToolBar.toggleEditButtons();
                }
                else {
                    alert("Please select a precinct first.")
                }
            }
        });
    },

    removeNeighborHandler: () => {
        $('#remove').click(() => {
            if (LeafletMap.currentMode == LeafletMap.modes.default) {
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer) && LeafletMap.currentPrecinct != null) {
                    LeafletMap.currentMode = LeafletMap.modes.remove;
                    LeafletMap.selectedPrecincts = [];
                    LeafletMap.precinctBeingChanged = LeafletMap.currentPrecinct;
                    ToolBar.toggleEditButtons();
                }
                else {
                    alert("Please select a precinct first.")
                }
            }
        });
    },

    insertPrecinctHandler: () => {
        $('#insert').click(() => {
            if (LeafletMap.currentMode == LeafletMap.modes.default) {
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    LeafletMap.currentMode = LeafletMap.modes.insert;
                    ToolBar.toggleEditButtons();
                    LeafletMap.map.pm.enableDraw('Polygon');
                    LeafletMap.map.on('pm:create', e => {
                        LeafletMap.map.removeLayer(e.layer);
                        let precinctShape = e.shape;
                        let newPrecinctCoordinates = [];
                        let coordinatesList = e.layer._latlngs[0];
                        for (let i = 0; i < coordinatesList.length; i++) {
                            newPrecinctCoordinates.push([coordinatesList[i].lng, coordinatesList[i].lat])
                        };
                        LeafletMap.tempPrecinctGeojson.push( 
                        {
                            "type": "Feature",
                            "properties": 
                            {
                                'district': LeafletMap.currentDistrict,
                                'canonName': "ghost_" + LeafletMap.ghostCounter,
                                "displayName": "Ghost Precinct",
                                "demoData": {
                                    "demographicDataID": 1,
                                    "population": 0,
                                    "whitePop": 0,
                                    "blackPop": 0,
                                    "asianPop": 0,
                                    "otherPop": 0
                                },
                                "elecData": {
                                    "democratic": 0,
                                    "republican": 0,
                                    "green": 0,
                                    "libertarian": 0,
                                    "other": 0
                                },
                            },
                            "geometry": 
                            {
                                "type": precinctShape,
                                "coordinates": [newPrecinctCoordinates]
                            }
                        });
                        LeafletMap.updateTempLayer();
                        LeafletMap.map.pm.enableDraw('Polygon');
                    });
                }
                else {
                    alert("Please select a district first.");
                }
            }
        });
    },

    mergePrecinctHandler: () => {
        $('#merge').click(() => {
            if (LeafletMap.currentMode == LeafletMap.modes.default) {
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    LeafletMap.currentMode = LeafletMap.modes.merge;
                    LeafletMap.selectedPrecincts = [];
                    LeafletMap.precinctBeingChanged = LeafletMap.currentPrecinct;
                    ToolBar.toggleEditButtons();
                }
                else {
                    alert("Please select a district first.");
                }
            }
        });
    },

    modifyPrecinctHanlder: () => {
        $('#modify').click(() => {
            if (LeafletMap.currentMode == LeafletMap.modes.default || LeafletMap.currentMode == LeafletMap.modes.modify) {
                // Should only work if precincts are showing
                if (LeafletMap.map.hasLayer(LeafletMap.precinctLayer)) {
                    let element = $('#modify')[0];
                    if (element.className.includes("active")) {
                        element.className = element.className.replace(/active/g, "");
                        LeafletMap.precinctLayer.pm.disable();
                        LeafletMap.currentMode = LeafletMap.modes.default;
                    }
                    else {
                        element.className = element.className += " active";
                        LeafletMap.currentMode = LeafletMap.modes.modify;
                    }
                }
                else {
                    alert("Please select a district first.");
                }
            }
        });
    },

    createCommentHandler: () => {
        $('#create').click(() => {
        });
    },

    resetMapHandler: () => {
        $('#reset').click(() => {
            LeafletMap.map.setView(LeafletMap.usaCoordinates, 5);
        });
    },

    editButtonHandler: () => {
        $('#done-btn').click(() => {
            DataHandler.updatePrecinctData();
            LeafletMap.resetMapFunctionalities();
        });
        $('#cancel-btn').click(() => {
            // Revert all changes made during edit
            LeafletMap.resetMapFunctionalities();

        });
    },

    toggleEditButtons: () => {
        if (LeafletMap.currentMode == LeafletMap.modes.default) {
            $('#done-btn').hide();
            $('#cancel-btn').hide();
        }
        else {
            $('#done-btn').show();
            $('#cancel-btn').show();
        }
    },

    enableFilter: (filterid, option) => {
        if (filterid == 'district-filter')
            LeafletMap.enableDistrictLayer(!option);
        else if (filterid == 'precinct-filter') 
            LeafletMap.enablePrecinctLayer(!option);
        else if (filterid == 'national-parks-filter')
            console.log('no national park data');
    },

    enableAllFilters: (option) => {
        switch(option) {
            case true:
                var filters = $('#filters').find('.dropdown-item');
                for (var i = 0; i < filters.length; i++) {
                    filters[i].className += " active";
                }
                break;
            case false:
                var filters = $('#filters').find('.active');
                for (var i = 0; i < filters.length; i++) {
                    filters[i].className = filters[i].className.replace(/active/g, "");
                }
                break;
        }
    },
    // Updates GUI element to indicate no state is selected
    unselectState: () => {
        const currentState = $('#states').find(".active")[0];
        if (currentState != null) {
            // Disable the current highlighted state
            currentState.className = currentState.className.replace(/active/g, "");
        }
    },
};

ToolBar.init();
