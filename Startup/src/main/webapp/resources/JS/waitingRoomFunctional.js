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

    req.open("POST", "/waitingRoom/create", true);
    req.setRequestHeader('Content-Type', 'application/json');

    let waitingRoomData = prepareJsonData();
    req.send(waitingRoomData);

}

/**
 * Helper function for CreateTable button
 * @returns {string} JSON parsable Room configuration
 * which is sent to WaitingRoomController
 */
function prepareJsonData(){
    const id = 1;
    let tablePass = $("#passwordValue").val();
    // let tablePass = document.getElementById('passwordValue').value;
    console.log(tablePass);
    // let tableBayonet = document.getElementById('bayonetValue').value;
    let tableBayonet = $("#bayonetValue").val();
    console.log(tableBayonet);

    let tableGameMode;
    let ninesRadioBtn = document.getElementById('nines');
    if(ninesRadioBtn.checked){
        tableGameMode = 'NINES';
    }else{
        tableGameMode = 'STANDARD';
    }
    console.log(tableGameMode);
    return JSON.stringify({id: id, password: tablePass, bayonet: tableBayonet, gameMode: tableGameMode, players: []});
}

function enterTable() {
    return;
}