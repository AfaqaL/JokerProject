function fetchData() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(this.readyState === 4) {
            if (this.status === 200) {
                let historiesDiv = document.getElementById("userGames");
                let data = JSON.parse(this.responseText);
                console.log(data);
                [].forEach.call(data, (tableHistory) => {
                    let gameDiv = document.createElement("div");
                    gameDiv.setAttribute("class", "gameHistoryInfo");
                    drawTableHistory(tableHistory.name1, tableHistory.score1, gameDiv);
                    drawTableHistory(tableHistory.name2, tableHistory.score2, gameDiv);
                    drawTableHistory(tableHistory.name3, tableHistory.score3, gameDiv);
                    drawTableHistory(tableHistory.name4, tableHistory.score4, gameDiv);
                    historiesDiv.appendChild(gameDiv);
                    insertBR(historiesDiv);
                    insertBR(historiesDiv);
                });

            } else {
                console.log(this.statusText);
            }
        }
    };

    req.open("GET", "/histories/getHistories", true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.send();
}

function insertBR(div) {
    let br = document.createElement("br");
    div.appendChild(br);
}

function drawTableHistory(id, score, gameDiv) {
    let label = document.createElement("label");
    label.innerHTML = "მომხმარებელი: " + id + ",  ქულა: " + score;
    label.setAttribute("class", "userInfo");
    gameDiv.appendChild(label);
    insertBR(gameDiv);
}