let stompClient = null;

function Connect() {
    let socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (card) {
            onReceive(card);
        });
    });

}

function Disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
        stompClient = null;
    }
    console.log("Disconnected");
}

function Send() {
    if(stompClient !== null){
        stompClient.send("app/hello", {}, "trying to get to server");
    }
}

function onReceive(param){
    console.log("receiving...");
    console.log(param);
}