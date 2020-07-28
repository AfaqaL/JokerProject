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

function update() {
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

    drawCards(table.cards);
    drawPlayedCards(table.playedCards, table.playerIndex);
    drawSuperior(table.superior);
    drawScores(table.scores);

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
        // TODO: show three cards and call setSuperior method
    }
}

function drawCards(cards) {
    document.getElementById('hand').innerHTML = '';

    [].forEach.call(cards, (card) => {
        let img = document.createElement('img');
        img.src = 'resources/images/' + getCardValue(card.value) + getCardColor(card.color) + '.png';
        img.onclick = function () {
            if (!wait && card.valid) {
                putCard(card)
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

function drawScores(scores) {
    let tr = document.createElement('tr');

    [].forEach.call(scores, (score) => {
        let declaredNum = document.createElement('td');
        declaredNum.innerHTML = '1';

        let scoreTag = document.createElement('td');
        scoreTag.innerHTML = score;

        tr.appendChild(declaredNum);
        tr.appendChild(scoreTag);
    })

    // document.getElementById('scores').appendChild(tr);
}

function drawDeclareNumPanel(invalidCall, maxSize) {
    document.getElementById('sayNum').innerHTML = '';
    document.getElementById("sayNum").style.visibility = 'visible';

    for (let num = 0; num <= maxSize; num++) {
        let button = document.createElement('button');
        button.innerHTML = '' + num;
        button.onclick = function () {
            document.getElementById("sayNum").style.visibility = 'hidden';
            declareNum(num);
        }

        if (num === invalidCall) {
            button.disabled = true;
            button.setAttribute("style", "filter: brightness(25%)")
        }

        document.getElementById('sayNum').appendChild(button);
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
