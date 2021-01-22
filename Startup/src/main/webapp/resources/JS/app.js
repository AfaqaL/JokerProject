var stompClient = null;

function myconnect() {
    alert("Connecting!!");
    let socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (card) {
            myreceive(card.body);
        });
    });
}

function mydisconnect() {
    alert("Disconnecting");
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function myreceive(card) {
    console.log(card);
}

function mysend(){
    stompClient.send("app/hello", {}, "managed to get here")
}