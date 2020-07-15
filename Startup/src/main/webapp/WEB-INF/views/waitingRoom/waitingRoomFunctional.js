function createTable() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(this.readyState === 4) {
            if (this.status === 200) {
                let data = JSON.parse(this.responseText);

                console.log(data.username);
                let tables = document.getElementById("existingTables");
                let newTable = document.createElement("button");
                newTable.setAttribute("id", "123");
                newTable.innerHTML = "Click ME";
                tables.appendChild(newTable);
            } else {
                console.log(this.statusText);
            }
        }
    }

    req.open("POST", "/waitingRoom", true);
    req.setRequestHeader('Content-Type', 'application/json');
    let waitingRoomData = JSON.stringify({id: 1, password: "blaa", bayonet : 100, gameMode: 'NINES', players: []});
    req.send(waitingRoomData);

}

function enterTable() {
    return;
}