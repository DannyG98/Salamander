const SideBar = {
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
                LeafletMap.infoBox.update(LeafletMap.currentProps);
                // DataHandler.uploadDemoData();
            }
        });

        $('#submit-elec-data').click(() => {
            let partyType = $('#partyType')[0].value;
            let inputValue = $('#partyValue')[0].value;

            if (inputValue != "" && LeafletMap.currentProps != null) {
                let elecID = LeafletMap.currentProps.elecData.electionDataId;
                let selectedElection = ToolBar.getSelectedElection();

                let elections = LeafletMap.currentProps.elecData.elections;
                for (let i = 0; i < elections.length; i++) {
                    if (elections[i].year == selectedElection.year && elections[i].type == selectedElection.type) {
                        Object.entries(elections[i]).forEach(([key, value]) => {
                            if (key.toLowerCase() == partyType.toLowerCase() + "votes") {
                                LeafletMap.currentProps.elecData.elections[i][key] = inputValue;
                            }
                        });
                    }
                }
                LeafletMap.infoBox.update(LeafletMap.currentProps);
                // DataHandler.uploadDemoData();
            }
        });
    },

    initErrors: () => {
        // Call DataHandler to get errors from server
        let errorsList = [1,2,3,4,5,6];
        let errorSelection = $('#error-selection')[0];
        for(let i = 0; i < errorsList.length; i++) {
            let opt = document.createElement('option');
            opt.value = i;
            opt.innerHTML = 'Error ' + i;
            errorSelection.appendChild(opt);
        }
    },
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


