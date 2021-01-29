let stompClient = null;

function setup() {
    fetchUser();
    stompClient = subscribe('/joker-websocket', '/rooms/update', onUpdate);
}


function onUpdate(response) {

}

function fetchUser(){
    console.log("got here");
    let req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if(this.readyState === 4 && this.status === 200) {
            let username = this.responseText;
            console.log(username);
            let userLabel = document.getElementById("username");
            userLabel.innerHTML = username;
            let userRank = document.getElementById('user-rank');
            userRank.innerHTML += username.charAt(username.length - 1);
        }
    };

    req.open("GET", "/waiting-room", true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.send();
}

function MYcreateTable(){
    console.log("sending dataaaaaa");
    let data = tableData();
    console.log(data);
    stompClient.send('/waiting-room/create', {}, data);
}

function tableData() {
    const id = 1;
    let tablePass = $("#passwordValue").val();
    let element = document.getElementById("bayonet");
    let tableBayonet = element.options[element.selectedIndex].value;
    let tableGameMode;
    let ninesRadioBtn = document.getElementById('nines');
    if(ninesRadioBtn.checked){
        tableGameMode = 'NINES';
    }else{
        tableGameMode = 'STANDARD';
    }
    let waitingRoomData = JSON.stringify({id: id, password: tablePass, bayonet: tableBayonet,
        gameMode: tableGameMode, players: []});

    return waitingRoomData;
}
