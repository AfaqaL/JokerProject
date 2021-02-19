function showPassword(icon) {
    icon.classList.toggle("fa-eye-slash");
    const x = document.getElementById("passID");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function showPasswordForTwo(icon) {
    icon.classList.toggle("fa-eye-slash");
    const x = document.getElementById("passID");
    const y = document.getElementById("confPassID");
    if (x.type === "password") {
        x.type = "text";
        y.type = "text";
    } else {
        x.type = "password";
        y.type = "password";
    }
}
function preventForm(){
    document.getElementById("form-id").onsubmit = function (ev) {
        ev.preventDefault();
    };

}

function formRedirect() {
    let username = document.getElementById('username-inp-id').value;
    let password = document.getElementById('passID').value;

    let url = '/login?username=' + username + '&password=' + password;
    let request = prepareRequest('POST', url);

    setSuccessAction(request, function (response) {
        let loginInfo = JSON.parse(response.responseText);
        console.log(loginInfo);

        if(loginInfo.success === true){
            localStorage.setItem("user", JSON.stringify({
                username: loginInfo.username,
                id: loginInfo.userId,
                rank: loginInfo.rank
            }));
            window.location.href = '../wr/waiting-room.html';
        }else {
            // TODO: redirect to error page
        }
    })

    request.send();
}

