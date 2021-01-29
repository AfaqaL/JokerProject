// var stompClient = null;
//
// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }
//
// function connect() {
//     var socket = new SockJS('/joker-websocket');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         setConnected(true);
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/game/greet', function (greeting) {
//             showGreeting(greeting);
//         });
//     });
// }
//
// function disconnect() {
//     if (stompClient !== null) {
//         stompClient.disconnect();
//     }
//     setConnected(false);
//     console.log("Disconnected");
// }
//
// function sendName() {
//     stompClient.send("/joker/hello", {}, "baro lasha mevedi aqane ?");
// }
//
// function showGreeting(message) {
//     console.log(message);
//     $("#greetings").append("<tr><td>" + "davloge tu vera?" + "</td></tr>");
// }
//
// $(function () {
//     $("form").on('submit', function (e) {
//         e.preventDefault();
//     });
//     $( "#connect" ).click(function() { connect(); });
//     $( "#disconnect" ).click(function() { disconnect(); });
//     $( "#send" ).click(function() { sendName(); });
// });
//


function subscribe(socketURL, subscribeURL, triggerFunction){
    console.log("got here");
    let socket = new SockJS(socketURL);
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('connected: ' + frame);
        stompClient.subscribe(subscribeURL, function (response) {
            triggerFunction(response);
        });
    });
    return stompClient;
}