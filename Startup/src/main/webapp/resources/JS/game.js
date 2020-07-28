const Color = {
    NO_COLOR: "NO_COLOR",
    CLUBS: "CLUBS",
    DIAMONDS: "DIAMONDS",
    SPADES: "SPADES",
    HEARTS: "HEARTS"
};

const Value = {
    SIX: "SIX",
    SEVEN: "SEVEN",
    EIGHT: "EIGHT",
    NINE: "NINE",
    TEN: "TEN",
    JACK: "JACK",
    QUEEN: "QUEEN",
    KING: "KING",
    ACE: "ACE",
    JOKER: "JOKER"
};

const PlayAction = {
    PUT: "PUT",
    WAIT: "WAIT",
    DECLARE: "DECLARE",
    DECLARE_SUPERIOR: "DECLARE_SUPERIOR"
};

Object.freeze(Color);
Object.freeze(Value);
Object.freeze(PlayAction);

let wait = true;
let declareSuperior = false;

function update() {
    drawGrid();
    setInterval(function () {
        let xhr = new XMLHttpRequest();
        let url = '/table/update';

        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                let table = JSON.parse(this.responseText);
                drawTable(table);
            }
        }

        xhr.send(null);
    }, 5000);
}

function drawTable(table) {
    if (!table.changed) return;

    extendTable(table.currentStage);
    updateDeclare(table.currentRound, table.currentStage, table.playerIndex, table.declares[table.playerIndex])
    updateScore(table.currentRound, table.currentStage, table.playerIndex, table.scores[table.playerIndex]);
    drawCards(table.cards);
    drawPlayedCards(table.playedCards, table.playerIndex);
    drawSuperior(table.superior);

    if (table.action === PlayAction.DECLARE) {
        drawDeclareNumPanel(table.invalidCall, table.cards.length);
    }
    if (table.action === PlayAction.WAIT) {
        wait = true;
    }
    if (table.action === PlayAction.PUT) {
        wait = false;
    }
    if (table.action === PlayAction.DECLARE_SUPERIOR) {
        drawDeclareSuperiorPanel();
        declareSuperior = true;
    }
}

function drawGrid() {
    let table = document.getElementById("pointGrid");
    for (var i = 0; i < 4; i++) {
        var tbody = document.createElement('tbody');
        tbody.id = 'tbody' + i;
        if (i === 0) tbody.style.display = 'block';
        else tbody.style.display = 'none';
        var numRows = (i % 2 === 0) ? 9 : 4;
        insertRows(tbody, numRows);
        table.appendChild(tbody);
    }
    addFinalPoints(table);
}

function drawCards(cards) {
    document.getElementById('hand').innerHTML = '';

    [].forEach.call(cards, (card) => {
        let img = document.createElement('img');
        img.src = 'resources/images/' + getCardValue(card.value) + getCardColor(card.color) + '.png';
        img.onclick = function () {
            if (!wait && card.valid) {
                if (declareSuperior) {
                    setSuperior(card);
                    declareSuperior = false;
                } else {
                    putCard(card);
                }
            }
        }

        if (!card.valid) {
            img.setAttribute("style", "filter: brightness(25%)")
        }

        document.getElementById('hand').appendChild(img);
    });
}

function drawPlayedCards(playedCards, playerIndex) {
    document.getElementById('midTable').innerHTML = '';

    for (let i = 1; i <= 4; i++) {
        let card = playedCards[(playerIndex + i) % 4];
        let img = document.createElement('img');
        img.src = 'resources/images/' + getCardValue(card.value) + getCardColor(card.color) + '.png';
        img.className = 'card' + i;

        document.getElementById('midTable').appendChild(img);
    }
}

function drawSuperior(superior) {
    let img = document.createElement('img');
    img.src = 'resources/images/' + getCardValue(superior.value) + getCardColor(superior.color) + '.png';
    img.alt = 'superior_card';

    let figcaption = document.createElement('figcaption');
    figcaption.innerHTML = 'კოზირი';

    let figure = document.createElement('figure');
    figure.appendChild(img);
    figure.appendChild(figcaption);

    let superiorCard = document.getElementById('superior_card');
    superiorCard.innerHTML = '';
    superiorCard.appendChild(figure);
}


function drawDeclareNumPanel(invalidCall, maxSize) {
    document.getElementById('sayNum').innerHTML = '';
    document.getElementById("sayNum").style.display = 'block';

    for (let num = 0; num <= maxSize; num++) {
        let button = document.createElement('button');
        button.innerHTML = '' + num;
        button.onclick = function () {
            document.getElementById("sayNum").style.display = 'none';
            declareNum(num);
        }

        if (num === invalidCall) {
            button.disabled = true;
            button.setAttribute("style", "filter: brightness(25%)")
        }

        document.getElementById('sayNum').appendChild(button);
    }
}

function drawDeclareSuperiorPanel() {
    document.getElementById('sup-btn-group').innerHTML = '';
    document.getElementById("sup-btn-group").style.display = 'block';
    for (let i = 0; i < 5; i++){
        let button = document.createElement('button');
        button.className = getButtonClass(i);
        button.onclick = function (){
            document.getElementById("sup-btn-group").style.display = 'none';
        }
        document.getElementById('sup-btn-group').appendChild(button);
    }
}
function getCardValue(value) {
    switch (value) {
        case Value.SIX:
            return '6';
        case Value.SEVEN:
            return '7';
        case Value.EIGHT:
            return '8';
        case Value.NINE:
            return '9';
        case Value.TEN:
            return '10';
        case Value.JACK:
            return 'J';
        case Value.QUEEN:
            return 'Q';
        case Value.KING:
            return 'K';
        case Value.ACE:
            return 'A';
        case Value.JOKER:
            return '$';
    }
}

function getCardColor(color) {
    switch (color) {
        case Color.NO_COLOR:
            return '';
        case Color.CLUBS:
            return 'C';
        case Color.DIAMONDS:
            return 'D';
        case Color.SPADES:
            return 'S';
        case Color.HEARTS:
            return 'H';
    }
}

function putCard(card) {
    let xhr = new XMLHttpRequest();
    let url = '/table/put';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    let data = JSON.stringify(card);
    xhr.send(data);
}

function declareNum(num) {
    let xhr = new XMLHttpRequest();
    let url = '/table/declare';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    let data = JSON.stringify({number: num});
    xhr.send(data);
}

function setSuperior(card) {
    let xhr = new XMLHttpRequest();
    let url = '/table/set-superior';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    let data = JSON.stringify(card);
    xhr.send(data);
}

function insertRows(tbody, numRows) {
    for (var i = 0; i < numRows + 1; i++) {
        var tr = document.createElement('tr');
        if (i === numRows) tr.className = 'game_points';
        tbody.appendChild(tr);
        for (var j = 0; j < 8; j++) {
            var td = document.createElement('td');
            if (i === numRows && j % 2 === 1) td.innerHTML = '0.0';
            tr.appendChild(td);
        }
    }
}

function addFinalPoints(table) {
    var tbody = document.createElement('tbody');
    tbody.id = 'tbody4';
    tbody.style.display = 'block';
    var final_points = document.createElement('tr');
    final_points.id = 'finalPoints';
    for (var i = 0; i < 8; i++) {
        var td = document.createElement('td');
        if (i % 2 === 1) td.innerHTML = 0.0;
        final_points.appendChild(td);
    }
    tbody.appendChild(final_points);
    table.appendChild(tbody)
}

function updateScore(round, stage, playerIndex, score) {
    console.log(stage)
    if (score === -1) return;
    var tbody = document.getElementById('tbody' + stage);
    var row = tbody.rows;
    var col = row[round].cells;
    if (col[playerIndex * 2 + 1].innerHTML !== '') return;
    col[playerIndex * 2 + 1].innerHTML = score;
    console.log(row.length);
    col = row[row.length - 1].cells;
    col[playerIndex * 2 + 1].innerHTML = parseFloat(col[playerIndex * 2 + 1].innerHTML) + score;
    var final_points = document.getElementById('finalPoints');
    col = final_points.cells;
    col[playerIndex * 2 + 1].innerHTML = parseFloat(col[playerIndex * 2 + 1].innerHTML) + score;



}

function updateDeclare(round, stage, playerIndex, declare) {
    if (declare === -1) return;
    var tbody = document.getElementById('tbody' + stage);
    var row = tbody.rows;
    var col = row[round].cells;
    if (col[playerIndex * 2].innerHTML !== '') return;
    col[playerIndex * 2].innerHTML = declare;

}

function extendTable(stage) {
    var currentStage = document.getElementById('tbody' + stage);
    if (currentStage.style.display === 'none'){
        for (var i = 0; i < 4; i++) {
            var tbody = document.getElementById('tbody' + i);
            tbody.style.display = 'none';
        }
        currentStage.style.display = 'block';
    }

    document.getElementById("pointGrid").onclick = function () {
        let tbody;
        var tbody1 = document.getElementById('tbody' + 0);
        var tbody2 = document.getElementById('tbody' + 1);
        if (tbody1.style.display === 'none' || tbody2.style.display === 'none') {
            for (var i = 0; i < 4; i++) {
                tbody = document.getElementById('tbody' + i);
                tbody.style.display = 'block';
            }
        } else {
            for (var i = 0; i < 4; i++) {
                tbody = document.getElementById('tbody' + i);
                if (i !== stage) tbody.style.display = 'none';
            }
        }
    }
}

function getButtonClass(value) {
    switch (value) {
        case 0:
            return 'club';
        case 1:
            return 'diamond';
        case 2:
            return 'spade';
        case 3:
            return 'heart';
    }
    return 'no_color';
}