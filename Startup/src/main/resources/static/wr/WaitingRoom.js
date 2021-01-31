let stompClient = null;
let user = null;

function setup() {
    fetchUser();
    stompClient = subscribe('/joker-websocket', '/rooms/update', onUpdate);
}


function onUpdate(response) {
    console.log('triggered on update func');
    console.log(response);
    let lobby = JSON.parse(response);
    console.log(lobby.room);
    switch (lobby.action) {
        case 'CREATE':
            console.log('got here');
            create(lobby);
            break;
        case 'JOIN':
            updateExisting(lobby, true);
            break;
        case 'LEAVE':
            updateExisting(lobby, false);
            break;
        case 'REMOVE':
            remove(lobby.id);
            break;
        default:
            console.log('got defaulted for no reason');
            break;
    }
}

function create(response) {
    let table = idGet(document, 'rooms');
    let room = response.room;
    let row = document.createElement("tr");
    row.setAttribute("id", "room" + room.id);

    let col1 = document.createElement("td");
    let col2 = document.createElement("td");
    let col3 = document.createElement("td");
    let col4 = document.createElement("td");
    let col5 = document.createElement("td");
    let col6 = document.createElement("td");

    let inputElement = document.createElement("input");
    inputElement.setAttribute("type", "password");
    inputElement.setAttribute("id", "joinPass" + room.id);

    let childButton = document.createElement("button");
    childButton.setAttribute("id", room.id);
    childButton.onclick = function() {
        joinTable(this.id);
    }
    childButton.setAttribute("class", "btn btn-primary badge-pill");
    childButton.innerHTML = "შებრძანება";

    if (response.id !== -1) {
        childButton.setAttribute("disabled", "disabled");
    }

    if (response.id !== -1) {
        childButton.setAttribute("disabled", "disabled");
        document.getElementById("createTable").setAttribute("disabled", "disabled");
    } else {
        document.getElementById("createTable").removeAttribute("disabled");
    }

    if (response.id === room.id) {
        if (room.players.length === 4) {
            window.location.href = "/table"
        }

        childButton.setAttribute("class", "btn btn-danger badge-pill");
        childButton.innerHTML = "გამობრძანება";
        childButton.onclick = function () {
            leaveTable();
        }
        childButton.removeAttribute("disabled");
    }

    // col1.innerHTML = room.players[0].username;
    col1.innerHTML = 'afaqa';
    col2.innerHTML = room.bayonet;
    col3.innerHTML = room.gameMode;
    col4.appendChild(inputElement);
    col5.innerHTML = room.players.length + "";
    col6.appendChild(childButton);

    row.appendChild(col1);
    row.appendChild(col2);
    row.appendChild(col3);
    row.appendChild(col4);
    row.appendChild(col5);
    row.appendChild(col6);

    table.appendChild(row);
}

function updateExisting(response, joining) {
    if(joining){

    }else{

    }
}

function remove(id) {

}

function fetchUser(){
    let request = prepareRequest('GET', '/waiting-room'); 

    setSuccessAction(request, function (response) {
        let username = response.responseText;
        console.log(username);
        let userLabel = document.getElementById("username");
        userLabel.innerHTML = username;
        let userRank = document.getElementById('user-rank');
        userRank.innerHTML += username.charAt(username.length - 1);
    });
    // let req = new XMLHttpRequest();
    // req.onreadystatechange = function () {
    //     if(this.readyState === 4 && this.status === 200) {
    //         let username = this.responseText;
    //         console.log(username);
    //         let userLabel = document.getElementById("username");
    //         userLabel.innerHTML = username;
    //         let userRank = document.getElementById('user-rank');
    //         userRank.innerHTML += username.charAt(username.length - 1);
    //     }
    // };

    // req.open("GET", "/waiting-room", true);
    // req.setRequestHeader('Content-Type', 'application/json');
    request.send();
}

function MYcreateTable(){
    console.log("sending dataaaaaa");
    let data = tableData();
    console.log(data);
    stompClient.send('/waiting-room/create', {username:'baro'}, data);
}

function historiesTest() {
    console.log("testing request header");
    stompClient.send('/waiting-room/join');
}

function tableData() {
    const id = 1;
    let tablePass = idGet(document, 'passwordValue').value;
    let element = idGet(document, 'bayonet');
    let tableBayonet = element.options[element.selectedIndex].value;
    let tableGameMode = idGet(document, 'nines').checked ? 'NINES' : 'STANDARD';
    return JSON.stringify({id: id, password: tablePass, bayonet: tableBayonet,
        gameMode: tableGameMode, players: []});
}
