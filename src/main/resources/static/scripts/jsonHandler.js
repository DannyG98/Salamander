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
        geoJson["geometry"] = regionJson["geometry"];

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