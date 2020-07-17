
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
                                        + tableGameMode + "მოთამაშეები: 0 ";
                let childInput = document.createElement("input");
                childInput.setAttribute("type", "text");
                childInput.setAttribute("id", "joinPass" + data);
                let childButton = document.createElement("button");
                childButton.setAttribute("id", data);
                childButton.setAttribute("onclick", "joinTable(this.id)");
                childButton.setAttribute("class", "join-buttons");

                childButton.innerHTML = "Join Table";

                innerDiv.appendChild(childLabel);
                innerDiv.appendChild(childInput);
                innerDiv.appendChild(childButton);

                document.getElementById("existingTables").appendChild(innerDiv);
                document.getElementById("createTableDiv").innerHTML = "";
                joinTable(data);

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
                respData = "TRUE";
                if (respData === "TRUE") {
                    document.getElementById("div" + table_id).style.border = "thick solid #0000FF";
                    let allJoinButtons = document.getElementsByClassName("join-buttons");
                    [].forEach.call(allJoinButtons, (joinBtn) => {
                        joinBtn.setAttribute("disabled", "disabled");
                    })
                } else {
                    alert("Sorry! Could not join the table");
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



function fetchData() {

    setInterval(function () {
        let req = new XMLHttpRequest();

        req.onreadystatechange = function () {
            if(this.readyState === 4) {
                if (this.status === 200) {
                    let respData = JSON.parse(this.responseText);
                    console.log(respData);
                    if(respData.isChanged === "TRUE") {
                        console.log("CHANGEeeeeeee");
                        document.getElementById("existingTables").innerHTML ="";
                        [].forEach.call(respData.rooms, (room) => {
                            let innerDiv = document.createElement("div");
                            innerDiv.setAttribute("id", "div" + room.id);
                            let childLabel = document.createElement("label");
                            childLabel.innerHTML = "ხიშტი: " + room.bayonet + ","
                                + room.gameMode + "მოთამაშეები:" + room.players.length;
                            let childInput = document.createElement("input");
                            childInput.setAttribute("type", "text");
                            childInput.setAttribute("id", "joinPass" + room.id);
                            let childButton = document.createElement("button");
                            childButton.setAttribute("id", room.id);
                            childButton.setAttribute("onclick", "joinTable(this.id)");
                            childButton.setAttribute("class", "join-buttons");

                            childButton.innerHTML = "Join Table";

                            innerDiv.appendChild(childLabel);
                            innerDiv.appendChild(childInput);
                            innerDiv.appendChild(childButton);

                            document.getElementById("existingTables").appendChild(innerDiv);
                        });

                    } else {
                        console.log("not CHangeeeeeee")
                    }
                }
            }
        }

        req.open("GET", "/waitingRoom/update", true);
        req.setRequestHeader('Content-Type', 'application/json');
        req.send();

    }, 3000);
}