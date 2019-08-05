function loadMainPage() {
    console.log("Main page is being loaded.");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        console.log("readyState = " + xhttp.readyState
                + ", status = " + xhttp.status);
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            document.getElementsByTagName("body")[0].innerHTML =
                    this.responseText;
        }
    };
    xhttp.open("GET", "/SMSReminder/LoadServlet?pageName=Main", true);
    xhttp.send();
}

function loadSavedPage() {
    console.log("Saved page is being loaded.");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        console.log("readyState = " + xhttp.readyState
                + ", status = " + xhttp.status);
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            document.getElementsByTagName("body")[0].innerHTML =
                    this.responseText;
        }
    };
    xhttp.open("GET", "/SMSReminder/LoadServlet?pageName=Saved", true);
    xhttp.send();
}

function loadEditPage(remId) {
    console.log("Edit page is being loaded.");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        console.log("readyState = " + xhttp.readyState
                + ", status = " + xhttp.status);
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            document.getElementsByTagName("body")[0].innerHTML =
                    this.responseText;
        }
    };
    xhttp.open("GET", "/SMSReminder/LoadServlet?pageName=Edit&remId=" + remId, true);
    xhttp.send();
}

function confirmToDelete(remId) {
    bootbox.confirm({
        message: "Do you want to delete this reminder? This cannot be undone.",
        swapButtonOrder: true,
        buttons: {
            confirm: {
                label: '<i class="fa fa-check"></i> Confirm',
                className: 'btn-success pull-left'
            },
            cancel: {
                label: '<i class="fa fa-times"></i> Cancel',
                className: 'btn-danger pull-right'
            }
        },
        callback: function (result) {
//            window.alert("RESULT: " + result);
            if (result === true)
                window.open("/SMSReminder/saved?actionToTake=Delete&remId=" + remId, "_self");
        }
    });


}