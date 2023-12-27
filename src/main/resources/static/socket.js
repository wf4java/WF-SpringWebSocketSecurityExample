var service = { };

service.connect = connect;
service.onConnected = onConnected;
service.onError = onError;
service.onMessageReceived = onMessageReceived;



var stompClient = null;


function initService(){
    console.log("Init app")
}

function connect() {
    const headers = {
        Authorization: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2RldGFpbHMiLCJpZCI6IjY1OGE3ZjNjNWRkOTBkMmI3M2JmYmJlOSIsImlzcyI6InNwcmluZy1hcHAiLCJpYXQiOjE3MDM1NzUzNTgsImV4cCI6MTcwNDE4MDE1OH0.YXld6aMbwaxfA4Yqmei0XgA8jKRaTs6R9kw4ZF7woD4"
    };

    const socket = new SockJS('http://localhost:8002/ws/messenger');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, onConnected, onError);

}

function disconnect() {
    stompClient.disconnect();
    setConnected(false);
    console.log("Disconnected");
}




function onConnected() {
    setConnected(true);

    const headers = {
        Authorization: 'Bearer ' + "asddwd123r1jkgjk2"
    };

    //stompClient.connect(headers, onConnected, onError);
    //stompClient.subscribe('/topic/' + $("#user_id").val(), onMessageReceived);



    // Tell your username to the server
    // stompClient.send("/app/hello",
    //     {},
    //     JSON.stringify({sender: "test111", type: 'JOIN'})
    // )
}

function onMessageReceived(payload) {
    console.log("Пришло сообщение: " + payload)
    showGreeting(payload.body)
}


function onError() {
    console.log("Connection error")
}


function sendName() {
    stompClient.send("/messenger/message",
        {},
       JSON.stringify( JSON.parse($("#name").val())));

    //sendName();
}


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}




function showGreeting(message) {
    $("#greetings").prepend("<tr><td>" + message + "</td></tr>");
}







$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});
