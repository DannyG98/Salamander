const SideBar = {
    init: () => {
        SideBar.initSideBarButton();
    },

    initSideBarButton: () => {
        $("#collapse-menu-btn").click( function() {
            $("#mapid").toggleClass('showSide');
            $("#sidebar").toggleClass('showSide');
        });
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


