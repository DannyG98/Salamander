const SideBar = {
    errorNameMapping : {"Multi Polygon" : "MULTI_POLYGON", "Enclosed" : "ENCLOSED", "Overlap": "OVERLAP", "Unclosed": "UNCLOSED", "Zero Population": "ZERO_POPULATION", "Unproportional Election": "UNPROPORTIONAL_ELEC", "Ghost": "GHOST"},

    init: () => {
        SideBar.initSideBarButtons();
        SideBar.initErrors();
    },

    initSideBarButtons: () => {
        $("#collapse-menu-btn").click(() => {
            $("#mapid").toggleClass('showSide');
            $("#sidebar").toggleClass('showSide');
        });

        $('#submit-demo-data').click(() => {
            let popType = $('#popType')[0].value;
            let inputValue = $('#popValue')[0].value;

            if (inputValue != "" && LeafletMap.currentProps != null) {
                let demoID = LeafletMap.currentProps.demoData.demographicDataID;
                Object.entries(LeafletMap.currentProps.demoData).forEach(([key, ]) => {
                    if (key == popType) {
                        LeafletMap.currentProps.demoData[key] = parseInt(inputValue);
                    }
                });
                let precinctName = LeafletMap.currentProps.canonName;
                DataHandler.uploadDemoData(precinctName, demoID, popType, inputValue);
            }
        });

        $('#submit-elec-data').click(() => {
            let partyType = $('#partyType')[0].value;
            let inputValue = $('#partyValue')[0].value;

            if (inputValue != "" && LeafletMap.currentProps != null) {
                let elecID = null;
                let selectedElection = ToolBar.getSelectedElection();

                let elections = LeafletMap.currentProps.elecData.elections;
                for (let i = 0; i < elections.length; i++) {
                    if (elections[i].year == selectedElection.year && elections[i].type == selectedElection.type) {
                        Object.entries(elections[i]).forEach(([key, value]) => {
                            if (key.toLowerCase() == partyType.toLowerCase() + "votes") {
                                elecID = LeafletMap.currentProps.elecData.elections[i].electionId;
                                LeafletMap.currentProps.elecData.elections[i][key] = inputValue;
                            }
                        });
                    }
                }
                let precinctName = LeafletMap.currentProps.canonName;
                DataHandler.uploadElecData(precinctName, elecID, partyType + "Votes", inputValue);
            }
        });
    },

    initErrors: () => {
        DataHandler.getAllErrorData();
    },
    
    displaySelectedTypeErrors: () => {
        let selectElement = $('#error-type-selection')[0];
        let errorType = SideBar.getErrorName(selectElement.value);
        // 
        let errorSelection = $('#error-selection').empty()[0];
        Object.entries(LeafletMap.errors).forEach(([key, value]) => {
            if (value.etype == errorType && value.affectedDistrict == LeafletMap.currentDistrict) {
                let opt = document.createElement('option');
                opt.value = value.affectedPrct;
                opt.innerHTML = value.precinctDisplayName;
                errorSelection.appendChild(opt);
            }
        });
        
    },

    getErrorName: (name) => {
        let errorName = null;
        Object.entries(SideBar.errorNameMapping).forEach(([key, value]) => {
            if (key == name) {
                errorName = value;
            }
        });
        return errorName;
    },

    findPrecinct: () => {
        let precinctCName = $('#error-selection')[0].value;
        let layers = LeafletMap.precinctLayer._layers;
        Object.entries(layers).forEach(([, value]) => {
            let cName = value.feature.properties.canonName;
            if (precinctCName == cName) {
                LeafletMap.map.fitBounds(value.getBounds());
                LeafletMap.highlightNeighbors(precinctCName);
                LeafletMap.highlightPrecinct(value, LeafletMap.highlightColors.blue, true);
                LeafletMap.changeInfoBoxName(value.feature.properties.displayName);
            }
        });
    },

    errorResolved: () => {
        let precinctCName = $('#error-selection')[0].value;
        Object.entries(LeafletMap.errors).forEach(([key, value]) => {
            if (precinctCName == value.affectedPrct) {
                DataHandler.uploadErrorStatus(value.eid, true);
            }
        });
    },

    uploadNewName: () => {
        let newName = $('#new-name')[0].value;
        let precinctName = LeafletMap.currentProps.canonName;
        DataHandler.uploadNewName(precinctName, newName);
    }
}

SideBar.init();

// var Colorado_errs = [
//     {
//         "id": 1,
//         "district": "4-6",
//         "coordinates" : [
//             -105.15769958496094,
//             39.15349256868936
//         ],
//         "zoom": 12
//     },
//     {
//         "id": 2,
//         "district": "2-5",
//         "coordinates" : [
//             -106.09977722167969,
//             39.41418130031119
//         ],
//         "zoom": 12
//     }
// ]

// function displayErrors(json) {
//     $("#error-list").empty();
//     for (error in json) {
//         $("#error-list").append(
//             "<li><a href='#' data-lat=" + json[error].coordinates[0] + " data-long=" + json[error].coordinates[1] +
//              " data-zoom=" + json[error].zoom + " class=errlist-item id=" + json[error].id + ">Precinct " + json[error].district + "</a></li>"
//         );
//         addCircle(parseFloat(json[error].coordinates[0]), parseFloat(json[error].coordinates[1]), 10000);
//     }

//     $(".errlist-item").click( function () {
//         panMap(parseFloat($(this).attr("data-lat")), parseFloat($(this).attr("data-long")), parseFloat($(this).attr("data-zoom")));
//         addCircle(parseFloat($(this).attr("data-long")), parseFloat($(this).attr("data-lat")), 10000);
//     });
// }


