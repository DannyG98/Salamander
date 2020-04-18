/* Takes a region json (received from server) and formats it for geoJson */
function convert(serverjson) {
    var geoJson = {
        "type": "Feature",
        "geometry": {
            "type": "",
            "coordinates": []
        },
        "properties": {}
    };

    // Deal with boundary first
    for (var x in Object.keys(serverjson["shape"])) {
        geoJson["geometry"]["coordinates"]
            .push(convertPolygon(serverjson["shape"][x]));
    }
    // Determine if multipolygon or polygon
    if (geoJson["geometry"]["coordinates"].length > 1)
        geoJson["geometry"]["type"] = "MultiPolygon";
    else
        geoJson["geometry"]["type"] = "Polygon";
    
    // Place each field besides the shape inside properties
    var keys = Object.keys(serverjson);
    for (var x in keys) {
        if (x != "shape") {
            var prop = keys[x]
            geoJson["properties"][prop] = serverjson[prop];
        }
    }

    return geoJson;
}

function convertPolygon(polygon) {
    var coordinates = [];
    for (var coord in polygon["boundary"]) {
        coordinates.push([polygon["boundary"][coord]["longitude"], polygon["boundary"][coord]["latitude"]]);
    }
    return coordinates;
}