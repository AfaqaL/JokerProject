function fetchData() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(this.readyState === 4 &&this.status === 200) {
            let historiesDiv = document.getElementById("userGames");
            let data = JSON.parse(this.responseText);
            console.log(data);
            [].forEach.call(data, (tableHistory) => {
                let gameDiv = document.createElement("div");
                gameDiv.setAttribute("class", "card col-sm-6");
                let label = document.createElement("h4");
                label.innerHTML = "მომხმარებლები: ";
                label.setAttribute("class", "subtitle text-white")
                gameDiv.appendChild(label);
                drawTableHistory(tableHistory.name1, tableHistory.score1, gameDiv);
                drawTableHistory(tableHistory.name2, tableHistory.score2, gameDiv);
                drawTableHistory(tableHistory.name3, tableHistory.score3, gameDiv);
                drawTableHistory(tableHistory.name4, tableHistory.score4, gameDiv);
                historiesDiv.appendChild(gameDiv);

                let newRow = document.createElement("div");
                newRow.setAttribute("class", "w-100 d-none d-sm-block d-md-block d-lg-block d-xl-none");
                historiesDiv.appendChild(newRow);
                insertBR(historiesDiv);
                insertBR(historiesDiv);
            });

        } else {
            console.log(this.statusText);
        }
    };

    req.open("GET", "/histories/get-histories", true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.send();
}

function insertBR(div) {
    let br = document.createElement("br");
    div.appendChild(br);
}

function drawTableHistory(id, score, gameDiv) {
    let label = document.createElement("label");
    label.innerHTML = id + " - " + score;
    label.setAttribute("class", "userInfo");
    gameDiv.appendChild(label);
    insertBR(gameDiv);
}