
function createTable() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(this.readyState === 4) {
            if (this.status === 200) {
                let data = this.responseText;

                let innerDiv = document.createElement("div");
                innerDiv.setAttribute("id", "div" + data);
                let childLabel = document.createElement("label");
                childLabel.innerHTML = "ხიშტი: " + tableBayonet + ","
                                        + tableGameMode + "მოთამაშეები:1";
                let childInput = document.createElement("input");
                childInput.setAttribute("type", "text");
                childInput.setAttribute("id", "joinPass" + data);
                let childButton = document.createElement("button");
                childButton.setAttribute("id", data);
                childButton.setAttribute("onclick", "joinTable(this.id)");

                childButton.innerHTML = "Join Table";

                innerDiv.appendChild(childLabel);
                innerDiv.appendChild(childInput);
                innerDiv.appendChild(childButton);

                document.getElementById("existingTables").appendChild(innerDiv);
                document.getElementById("createTableDiv").innerHTML = "";


            } else {
                console.log(this.statusText);
            }
        }
    }

    req.open("POST", "/waitingRoom/create", true);
    req.setRequestHeader('Content-Type', 'application/json');

    const id = 1;
    let tablePass = $("#passwordValue").val();
    let tableBayonet = $("#bayonetValue").val();
    let tableGameMode;
    let ninesRadioBtn = document.getElementById('nines');
    if(ninesRadioBtn.checked){
        tableGameMode = 'NINES';
    }else{
        tableGameMode = 'STANDARD';
    }
    let waitingRoomData = JSON.stringify({id: id, password: tablePass, bayonet: tableBayonet,
                                                gameMode: tableGameMode, players: []});
    req.send(waitingRoomData);

}

function joinTable(table_id) {
    let req = new XMLHttpRequest();

    req.onreadystatechange = function () {
        if(this.readyState === 4) {
            if (this.status === 200) {
                let respData = this.responseText;
                if (respData) {
                    console.log("baro");
                } else {
                    console.log("ninikichas deerxa");
                }
            }
        }
    }
    req.open("POST", "/waitingRoom/join", true);
    req.setRequestHeader('Content-Type', 'application/json');

    let tryPassword = document.getElementById("joinPass" + table_id).value;
    let data = JSON.stringify({id:table_id, password:tryPassword});
    req.send(data);

}