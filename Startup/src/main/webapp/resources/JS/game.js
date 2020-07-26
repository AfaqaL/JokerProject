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

Object.freeze(Color);
Object.freeze(Value);

function update() {
    setInterval(function () {
        let xhr = new XMLHttpRequest();
        let url = '/table/update';

        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(this.responseText);

                let table = JSON.parse(this.responseText);
                drawTable(table);
            }
        }

        xhr.send(null);
    }, 5000);
}

function drawTable(table) {
    console.log('Drawing table with id:' + table.id);

    drawCards(table.cards);
    drawPlayedCards(table.playedCards);
}

function drawCards(cards) {
    document.getElementById('hand').innerHTML = '';

    [].forEach.call(cards, (card) => {
        var img = document.createElement('img');
        img.setAttribute("src", "resources/images/" + getCardValue(card.value) + getCardColor(card.color) + ".png");
        console.log(img.getAttribute("src"));
        img.onclick = function () {
            if (card.valid) {
                putCard(card)
            }
        }

        // if (!card.valid) {
        //     img.style.opacity = '0.5';
        // }

        document.getElementById('hand').appendChild(img);
    });
}

function drawPlayedCards(playedCards) {
    document.getElementById('midTable').innerHTML = '';

    let player = 1;
    [].forEach.call(playedCards, (card) => {
        var img = document.createElement('img');
        img.setAttribute("src", "resources/images/" + getCardValue(card.value) + getCardColor(card.color) + ".png");
        img.className = 'card' + player;
        console.log(img.className);
        document.getElementById('midTable').appendChild(img);

        player++;
    });
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
    // TODO: send putCard request to server
    console.log(card.value);
    console.log(card.color);
}
