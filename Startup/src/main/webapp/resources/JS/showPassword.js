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

