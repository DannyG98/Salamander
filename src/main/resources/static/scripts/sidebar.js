var Colorado_errs = [
    {
        "id": 1,
        "district": "4-6",
        "coordinates" : [
            -105.15769958496094,
            39.15349256868936
        ],
        "zoom": 12
    },
    {
        "id": 2,
        "district": "2-5",
        "coordinates" : [
            -106.09977722167969,
            39.41418130031119
        ],
        "zoom": 12
    }
]

$(document).ready(function () {

    $("#collapse-menu-btn").click( function() 
    {
        $("#mapid").toggleClass('showSide');
        $("#sidebar").toggleClass('showSide');
    });

    $('#reset-btn').click( function() 
    {
        mymap.setView(usaCoords, 5);
    })

    $('#insert-precinct-btn').click( function()
    {
        if(mode == 'default')
        {
            // Go into ghost mode, allowing user to click on points on the map
            mode = 'ghost'
            // Enable the done selecting button
            $('#done-edit-btn').show()
        }

    })

    $("#error-dropdwn").click( function () {
        if (!$("#error-tab").hasClass("show")) {
            displayErrors(Colorado_errs);
        } 
        else {
            removeCircles();
        }
    });

    $('#insert-comment-btn').click( function() 
    {
        // Allow user to place marker and write a comment for that marker
        if(mode == 'default')
        {
            mode = 'comment'
            $('#done-edit-btn').show()
        }
    })
    $('#done-edit-btn').click( function() {
        if(mode == 'ghost')
        {
            // User is done clicking points for ghost precinct
            // Create geoJSON object for new precinct and push to correct State array
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
                    "type": "Polygon",
                    "coordinates": [[...ghost_points]]
                }
            })
            // Change mode back to default and empty ghost_points
            mode = 'default'
            ghost_points = []
            
            //remove current precinct data and readd in everthing
            if (mymap.hasLayer(precinctGeojson)) {
            //remove precinct geojson and add district
            mymap.removeLayer(precinctGeojson);
            }
            precinctGeojson=L.geoJson(coloradoPrecincts,{
                onEachFeature: onEachFeature
                }).addTo(mymap);

            $('#done-edit-btn').hide()
        }
        if(mode == 'comment')
        {
            mode = 'default'
            $('#done-edit-btn').hide()
        }
    })
});

function displayErrors(json) {
    $("#error-list").empty();
    for (error in json) {
        $("#error-list").append(
            "<li><a href='#' data-lat=" + json[error].coordinates[0] + " data-long=" + json[error].coordinates[1] +
             " data-zoom=" + json[error].zoom + " class=errlist-item id=" + json[error].id + ">Precinct " + json[error].district + "</a></li>"
        );
        addCircle(parseFloat(json[error].coordinates[0]), parseFloat(json[error].coordinates[1]), 10000);
    }

    $(".errlist-item").click( function () {
        panMap(parseFloat($(this).attr("data-lat")), parseFloat($(this).attr("data-long")), parseFloat($(this).attr("data-zoom")));
        addCircle(parseFloat($(this).attr("data-long")), parseFloat($(this).attr("data-lat")), 10000);
    });
}

function stateChangeHandler(stateName) {
    $("#state-name").text(stateName);
}

