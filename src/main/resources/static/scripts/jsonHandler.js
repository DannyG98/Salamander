/* Takes a region json (received from server) and formats it for geoJson */
var jsonHandler = {
    convert: function(regionJson) {
        var geoJson = {
            "type": "Feature",
            "geometry": {
                "type": "",
                "coordinates": []
            },
            "properties": {}
        };

        // Deal with boundary first
        for (var i = 0; i < regionJson["geometry"]["coordinates"].length; i++) {
            geoJson["geometry"]["coordinates"].push(jsonHandler.convertPolygon(regionJson["geometry"]["coordinates"][i]));
        }
        // Determine if multipolygon or polygon
        if (geoJson["geometry"]["coordinates"].length > 1)
            geoJson["geometry"]["type"] = "MultiPolygon";
        else
            geoJson["geometry"]["type"] = "Polygon";
        
        // Place each field besides the geometry inside properties
        var keys = Object.keys(regionJson);
        for (var x in keys) {
            if (keys[x] != "geometry") {
                var prop = keys[x]
                geoJson["properties"][prop] = regionJson[prop];
            }
        }

        return geoJson;
    },

    convertPolygon: function(polygon) {
        var coordinates = [];
        for (var coord in polygon) {
            coordinates.push([polygon[coord][0], polygon[coord][1]]);
        }
        return coordinates;
    }
}