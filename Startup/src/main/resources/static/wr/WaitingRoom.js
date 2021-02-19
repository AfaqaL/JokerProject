let stompClient = null;
let user = null;

function setup() {
    setupUser();
    stompClient = subscribe('/joker-websocket', '/rooms/update', onUpdate);
    console.log("username is: " + localStorage.getItem("username"));
    console.log("rank is: " + localStorage.getItem("rank"));
    console.log("id is: " + localStorage.getItem("id"));

}


function onUpdate(response) {
    let lobby = JSON.parse(response);
    console.log(lobby);
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
    if(user.id === response.id){
        childButton.setAttribute("class", "btn btn-primary badge-pill");
        childButton.innerHTML = "შებრძანება";
        childButton.onclick = function() {
            newJoinTable(this.id);
        }
    }else{
        childButton.setAttribute("class", "btn btn-danger badge-pill");
        childButton.innerHTML = "გამობრძანება";
        childButton.onclick = function () {
            leaveTable();
        }
    }

    col1.innerHTML = room.players[0].username;
    col2.innerHTML = room.bayonet;
    col3.innerHTML = room.gameMode;
    col4.appendChild(inputElement);
    col5.innerHTML = '' + room.players.length;
    col6.appendChild(childButton);

    row.appendChild(col1);
    row.appendChild(col2);
    row.appendChild(col3);
    row.appendChild(col4);
    row.appendChild(col5);
    row.appendChild(col6);

    table.appendChild(row);
}

function newJoinTable(id) {
    
}

function updateExisting(response, joining) {
    if(joining){
        
    }else{

    }
}

function remove(id) {

}

function setupUser(){
    user = JSON.parse(localStorage.getItem('user'));

    console.log(user);

    let userLabel = document.getElementById('username');
    userLabel.innerHTML = user.username;
    let userRank = document.getElementById('user-rank');
    userRank.innerHTML += user.rank;
}

function MYcreateTable(){
    console.log("sending dataaaaaa");
    let data = tableData();
    console.log(data);
    stompClient.send('/waiting-room/create/' + 5, {}, data);
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
    return JSON.stringify({
        user: user,
        room: {
            id: id,
            password: tablePass,
            bayonet: tableBayonet,
            gameMode: tableGameMode,
            players: []
        }
    });
}
