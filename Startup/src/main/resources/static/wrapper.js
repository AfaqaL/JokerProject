

function prepareRequest(method, url){
    let req = new XMLHttpRequest();
    req.open(method, url, true);
    req.setRequestHeader('Content-Type', 'application/json');
    return req;
}

function setSuccessAction(request, func){
    request.onreadystatechange = function () {
        if(this.readyState === 4 && this.status === 200) {
            func(this);
        }
    };
}

function idGet(doc, id){
    return doc.getElementById(id);
}

function subscribe(socketURL, subscribeURL, triggerFunction){
    console.log("got here");
    let socket = new SockJS(socketURL);
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('connected: ' + frame);
        stompClient.subscribe(subscribeURL, function (response) {
            triggerFunction(response.body);
        });
    });
    return stompClient;
}