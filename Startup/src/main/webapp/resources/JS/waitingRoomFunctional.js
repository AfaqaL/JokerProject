function createTable() {
    let req = new XMLHttpRequest();
    req.open("POST", "/waitingRoom/create", true);
    req.setRequestHeader('Content-Type', 'application/json');

    /* get new tables properties from user */
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
                if (respData !== "TRUE") {
                    alert("Sorry! Could not join the table, try again");
                }
            }
        }
    };
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
                    if (respData.isChanged === "TRUE") {

                        /* new data incoming! */
                        console.log("Channfvvtgbthtgged");
                        //                  document.getElementById("rooms").innerHTML = "";
                        $("#rooms").empty();
                        //                  console.log("baroo");
                        /* new list of rooms (some removed, some added) */
                        [].forEach.call(respData.rooms, (room) => {

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
                            childButton.setAttribute("onclick", "joinTable(this.id)");
                            childButton.setAttribute("class", "btn btn-primary badge-pill");
                            childButton.innerHTML = "შებრძანება";

                            if (respData.tableId !== -1) {
                                childButton.setAttribute("disabled", "disabled");
                            }

                            if (respData.tableId !== -1) {
                                childButton.setAttribute("disabled", "disabled");
                                document.getElementById("createTable").setAttribute("disabled", "disabled");
                            }

                            col1.innerHTML = room.players[0].username;
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

                            document.getElementById("rooms").appendChild(row);
                            /* end */
                        });

                    } else {
                        console.log("Stays Same")
                    }
                }

            }
        };

        req.open("GET", "/waitingRoom/update", true);
        req.setRequestHeader('Content-Type', 'application/json');
        req.send();

    }, 1500);
}