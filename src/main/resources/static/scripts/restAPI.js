// controls whether or not a response will log to the console
var restfulLoggingBool = true;

// template for a POST request
var postTemplate = {
    method: 'post',
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    },
    body: ["d1", "d2", "d3"]
}

function getListOfDistricts(list) {
    var postTemplate = {
        method: 'post',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: list
    }
    fetch('/someEndpoint', postTemplate)
        .then(function(response){
            return response.text();
        }).then(updateGeoJson(newOnes));
}

/* Clones a template and returns it */
function getPostTemplate() {
    var clonedPost = {...postTemplate};
    return clonedPost;
}

function getStateBorders() {
    fetch('/state/getAllStates').then(function(response) {
        return response.text();
    }).then(function(text) {
        return text;
    });
}

/* Wraps the fetch API call. */
function fetchWrapper(url, body, handler) {
    console.log(body);
    fetch(url, body).then(
        function(response) {
            if (handler != null)
                handler(response);
            if (restfulLoggingBool) {
                console.log("recved response");
                return response.text();
            }
        }).then(function(text) {
            console.log(text);
        })
}