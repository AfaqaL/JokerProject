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
const JokerMode = {
    UNDER: "UNDER",
    TAKE: "TAKE",
    GIVE: "GIVE",
    OVER: "OVER"
}
const GameMode = {
    STANDARD: "STANDARD",
    NINES: "NINES"
}
Object.freeze(Color);
Object.freeze(Value);
Object.freeze(PlayAction);
Object.freeze(JokerMode);
Object.freeze(GameMode);

let wait = true;
let declareSuperior = false;
let declare = true;

function update(gameMode) {
    drawGrid(gameMode);
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
    }, 1000);
}

let isFirst;

function drawTable(table) {
    if (!table.changed) return;
    if (table.action === PlayAction.WAIT) {
        wait = true;
    }
    if (table.action === PlayAction.PUT) {
        wait = false;
    }
    if (table.action === PlayAction.DECLARE && declare) {
        wait = true;
        declare = false;
        drawDeclareNumPanel(table.invalidCall, table.cards.length, table.currentRound, table.currentStage, table.playerIndex);
    }

    if (table.action === PlayAction.DECLARE_SUPERIOR && !declareSuperior) {
        declareSuperior = true;
        wait = true;
        drawDeclareSuperiorPanel();
    }
    isFirst = table.first;
    let index = table.playerIndex;
    let isFinished = table.roundFinished[index];
    extendTable(table.currentStage);
    updateDeclare(table.currentRound, table.currentStage, table.declares, isFinished);
    if (isFinished) {
        declareSuperior = false;
        updateScore(table.currentRound, table.currentStage, table.scores);
    }
    drawCards(table.cards, table.isFirst);
    drawPlayedCards(table.playedCards, table.playerIndex);
    if (table.action !== PlayAction.DECLARE_SUPERIOR) {
        drawSuperior(table.superior);
    }
    drawCurrentTakenState(table.taken, table.playerIndex)

}

function drawGrid(gameMode) {
    let table = document.getElementById("pointGrid");
    for (let i = 0; i < 4; i++) {
        let tbody = document.createElement('tbody');
        tbody.id = 'tbody' + i;
        if (i === 0) tbody.style.display = 'block';
        else tbody.style.display = 'none';
        let numRows = (i % 2 === 0 && gameMode === GameMode.STANDARD) ? 8 : 4;
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

        console.log(wait);
        if (!card.valid || wait) {
            img.setAttribute("style", "filter: brightness(25%)")
        }

        img.onclick = function () {
            if (!wait && card.valid) {
                if (card.value === Value.JOKER) {
                    if (isFirst) firstPlayerToPlayHasJoker(card);
                    else chooseJokerActionPanel(card);
                } else {
                    putCard(card);
                }
            }
        }

        document.getElementById('hand').appendChild(img);
    });
}

function drawPlayedCards(playedCards, playerIndex) {
    document.getElementById('midTable').innerHTML = '';

    for (let i = 1; i <= 4; i++) {
        let card = playedCards[(playerIndex + i) % 4];
        if (card.color === Color.NO_COLOR && card.value === Value.ACE) {
            continue;
        }

        let img = document.createElement('img');
        if (card.value === Value.JOKER) img.src = 'resources/images/$.png';
        else img.src = 'resources/images/' + getCardValue(card.value) + getCardColor(card.color) + '.png';
        if (card.value === Value.JOKER) {
            console.log(card.value);
            console.log(card.jokerMode);
            console.log(card.color);
            let label = document.createElement('h5');
            label.className = 'label' + i;
            label.innerHTML = setLabel(card);
            document.getElementById('midTable').appendChild(label);
        }
        img.className = 'card' + i;
        document.getElementById('midTable').appendChild(img);

    }
}

function drawSuperior(superior) {
    if (superior === null) return;

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
        if (num === 0) button.innerHTML = '-';
        button.onclick = function () {
            document.getElementById("sayNum").style.display = 'none';
            declareNum(num);
            declare = true;
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
    for (let i = 0; i < 5; i++) {
        let button = document.createElement('button');
        button.className = getButtonClass(i);
        button.onclick = function () {
            document.getElementById("sup-btn-group").style.display = 'none';
            setSuperior(button.className);
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
        default :
            return '';
    }
}

function putCard(card) {
    let cards = document.getElementById("hand").childNodes;
    [].forEach.call(cards, (unusedCard) => {
        unusedCard.valid = false;
        unusedCard.setAttribute("style", "filter: brightness(25%)")

    })
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

function setSuperior(color) {
    let xhr = new XMLHttpRequest();
    let url = '/table/set-superior';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    let data;
    if (color === Color.NO_COLOR) {
        data = JSON.stringify({value: Value.JOKER, color: color});
    } else {
        data = JSON.stringify({value: Value.ACE, color: color});
    }
    xhr.send(data);
}


function insertRows(tbody, numRows) {
    for (let i = 0; i < numRows + 1; i++) {
        let tr = document.createElement('tr');
        if (i === numRows) tr.className = 'game_points';
        tbody.appendChild(tr);
        for (let j = 0; j < 8; j++) {
            let td = document.createElement('td');
            if (i === numRows && j % 2 === 1) td.innerHTML = '0.0';
            tr.appendChild(td);
        }
    }
}

function addFinalPoints(table) {
    let tbody = document.createElement('tbody');
    tbody.id = 'tbody4';
    tbody.style.display = 'block';
    let final_points = document.createElement('tr');
    final_points.id = 'finalPoints';
    for (let i = 0; i < 8; i++) {
        let td = document.createElement('td');
        if (i % 2 === 1) td.innerHTML = 0.0;
        final_points.appendChild(td);
    }
    tbody.appendChild(final_points);
    table.appendChild(tbody)
}


function updateScore(round, stage, scores) {
    let index = 0;
    console.log(round);
    console.log(stage);
    [].forEach.call(scores, (score) => {
        if (score !== -1) {
            let tbody;
            let row;
            if (round === 0) {
                stage--;
                tbody = document.getElementById('tbody' + stage);
                row = tbody.rows;
                round += row.length - 1;
                console.log(row.length)
            }else{
                tbody = document.getElementById('tbody' + stage);
                row = tbody.rows;
            }
            let col = row[round - 1].cells;
            col[index * 2 + 1].innerHTML = score;
            score /= 100.0;
            col = row[row.length - 1].cells;
            col[index * 2 + 1].innerHTML = (parseFloat(col[index * 2 + 1].innerHTML) + score).toFixed(1);
            let final_points = document.getElementById('finalPoints');
            col = final_points.cells;
            col[index * 2 + 1].innerHTML = (parseFloat(col[index * 2 + 1].innerHTML) + score).toFixed(1);
        }
        index++;
    });
}

function updateDeclare(round, stage, declares) {
    let index = 0;
    [].forEach.call(declares, (num) => {
        if (num !== -1) {
            let tbody = document.getElementById('tbody' + stage);
            let row = tbody.rows;
            let col = row[round].cells;
            col[index * 2].innerHTML = num;
            if (num === 0) col[index * 2].innerHTML = '-';
        }
        index++;
    });
}

function extendTable(stage) {
    let currentStage = document.getElementById('tbody' + stage);
    if (currentStage.style.display === 'none') {
        for (let i = 0; i < 4; i++) {
            let tbody = document.getElementById('tbody' + i);
            tbody.style.display = 'none';
        }
        currentStage.style.display = 'block';
    }

    document.getElementById("pointGrid").onclick = function () {
        let i;
        let tbody;
        let tbody1 = document.getElementById('tbody' + 0);
        let tbody2 = document.getElementById('tbody' + 1);
        if (tbody1.style.display === 'none' || tbody2.style.display === 'none') {
            for (i = 0; i < 4; i++) {
                tbody = document.getElementById('tbody' + i);
                tbody.style.display = 'block';
            }
        } else {
            for (i = 0; i < 4; i++) {
                tbody = document.getElementById('tbody' + i);
                if (i !== stage) tbody.style.display = 'none';
            }
        }
    }
}

function getButtonClass(value) {
    switch (value) {
        case 0:
            return 'CLUBS';
        case 1:
            return 'DIAMONDS';
        case 2:
            return 'SPADES';
        case 3:
            return 'HEARTS';
    }
    return 'NO_COLOR';
}

function chooseJokerActionPanel(joker) {
    document.getElementById('joker-activated').innerHTML = '';
    document.getElementById('joker-activated').style.display = 'block';
    let button_strong = document.createElement('button');
    button_strong.innerHTML = 'მოჯოკვრა';
    button_strong.onclick = function () {
        document.getElementById('joker-activated').style.display = 'none';
        joker.jokerMode = JokerMode.OVER;
        joker.color = Color.NO_COLOR;
        putCard(joker)
    }

    let button_weak = document.createElement('button');
    button_weak.innerHTML = 'გატანება';
    button_weak.onclick = function () {
        document.getElementById('joker-activated').style.display = 'none';
        joker.jokerMode = JokerMode.UNDER;
        joker.color = Color.NO_COLOR;
        putCard(joker)
    }
    document.getElementById('joker-activated').appendChild(button_strong);
    document.getElementById('joker-activated').appendChild(button_weak);
}

function firstPlayerToPlayHasJoker(card) {
    let wrapper = document.getElementById('joker-first-wrapper');
    wrapper.style.display = 'block';

    let labels = document.getElementById('joker-first-labels');
    addLabelsToJokerPanel(labels)
    let high_card = document.getElementById('high-card');
    addButtonsToJokerPanel(high_card, wrapper, card);
    let low_card = document.getElementById('low-card');
    addButtonsToJokerPanel(low_card, wrapper, card);
}

function addButtonsToJokerPanel(elem, wrapper, card) {
    elem.innerHTML = '';
    for (let i = 0; i < 4; i++) {
        let button = document.createElement('button');
        button.className = getButtonClass(i);
        button.onclick = function () {
            if (elem.id === 'high-card') card.jokerMode = JokerMode.GIVE;
            else card.jokerMode = JokerMode.TAKE;
            card.color = button.className;
            wrapper.style.display = 'none';
            putCard(card)
        }
        elem.appendChild(button);
    }
}

function addLabelsToJokerPanel(labels) {
    labels.innerHTML = '';
    let high_label = document.createElement('label');
    high_label.id = 'label-high';
    high_label.innerHTML = 'მაღალი';
    let low_label = document.createElement('label');
    low_label.id = 'label-low';
    low_label.innerHTML = 'წაიღოს';
    labels.appendChild(high_label);
    labels.appendChild(low_label)
}

function drawCurrentTakenState(taken, playerIndex) {
    for (let i = 1; i <= 4; i++) {
        let take = taken[(playerIndex + i) % 4];
        let player = document.getElementById('p' + i);
        let score = document.getElementById('score' + i);
        if (score === null) {
            score = document.createElement('p');
            score.id = 'score' + i;
            score.className = 'score';
        } else {
            score.innerHTML = ''
        }
        score.innerHTML = take;
        player.appendChild(score);
    }
}

function setLabel(card){
    if (card.jokerMode === JokerMode.OVER) return "მოჯოკვრა";
    if (card.jokerMode === JokerMode.UNDER) return "გატანება";
    if (card.jokerMode === JokerMode.GIVE) {
        switch (card.color) {
            case Color.CLUBS:
                return "მაღალი ჯვარი";
            case Color.DIAMONDS:
                return "მაღალი აგური";
            case Color.SPADES:
                return "მაღალი ყვავი";
            case Color.HEARTS:
                return "მაღალი გული";
        }
    }else {
        switch (card.color) {
            case Color.CLUBS:
                return "წაიღოს ჯვარმა";
            case Color.DIAMONDS:
                return "წაიღოს აგურმა";
            case Color.SPADES:
                return "წაიღოს ყვავმა";
            case Color.HEARTS:
                return "წაიღოს გულმა";
        }
    }
}