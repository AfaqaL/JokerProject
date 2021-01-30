

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