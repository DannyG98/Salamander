/* Takes a region json (received from server) and formats it for geoJson */
const jsonHandler = {
    convertToGeojson: function(regionJson) {
        let geoJson = {
            "type": "Feature",
            "geometry": {
                "type": "",
                "coordinates": []
            },
            "properties": {}
        };
        geoJson["geometry"] = regionJson["geometry"];

        // Place each field besides the geometry inside properties
        let keys = Object.keys(regionJson);
        for (let x in keys) {
            if (keys[x] != "geometry") {
                let prop = keys[x]
                geoJson["properties"][prop] = regionJson[prop];
            }
        }

        return geoJson;
    },
}