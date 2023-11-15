
/*
 * Websocket Chat room Application,
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */


// Global socket variable
var socket;

/* On message from server funciton */
/*
 * Method responsible to handle response from server
 */
function onMessage(event) {
    var data = JSON.parse(event.data);

    // Response action param is join
    if (data.action === "join") {
        
        // build forum
        buildForum(data.username, data.forum);
        
        var ta = document.getElementById('ta');
        for(let post of data.posts){
            ta.innerHTML += post + "\n";
        }

    // Response action param is update (updates the textarea)
    } else if (data.action === "update") {
       populateTextarea(data.posts);
    }
}

// Method to populate the text area given the posts from a forum
function populateTextarea(posts){
     var ta = document.getElementById('ta');
     ta.innerHTML += posts + "\n";
}

/* Build forum method */
function buildForum(username, forum) {
    var content = document.getElementById('content');
    content.innerHTML = "";
    content.style.display = "flex";
    content.style.flexDirection = "column";
    
    var title = document.createElement('h1');
    title.innerHTML = forum + " chat room";

    var textarea = document.createElement('textarea');
    textarea.setAttribute('id', 'ta');
    textarea.setAttribute('readonly', 'true');

    var inputbox = document.createElement('input');
    inputbox.setAttribute('type', 'text');
    inputbox.setAttribute('placeholder', 'Message');

    var inputbutton = document.createElement('input');
    inputbutton.setAttribute('type', 'button');
    inputbutton.setAttribute('value', 'Post');

    inputbutton.style.width = "400px";
    inputbox.style.width = "400px";
    
    content.appendChild(title);
    content.appendChild(textarea);
    content.appendChild(inputbox);
    content.appendChild(inputbutton);


    /* On click button for posting message to forum */
    inputbutton.onclick = () => {
        var message = username + ": " + inputbox.value;

        // prevent user posting blank messages
        if(inputbox.value.length <= 0){
            alert("Cannot post an blank message");
            return;
        }

        var info = {
            action: "update",
            forum: forum,
            msg: message
        };

        socket.send(JSON.stringify(info)); // Send message to server

        // Reset input box
        inputbox.value = "";
    };
}


/* Show form */
function showForm(){
    document.getElementById("formForum").style.display = "";
}

/* Hide form */
function hideForm() {
    document.getElementById("formForum").style.display = "none";
}

/* Submit forum */
function formSubmit() {

    // Create reference for socket
    socket = new WebSocket("ws://localhost:8080/websocket/users");

    // When socket connection made
    socket.onopen = () => {
        console.log("Connection made with websocket!");

        // Get values from user
        var form = document.getElementById("formForum");
        var username = form.elements["username"].value;
        var topic = form.elements["topic"].value;
        hideForm();
        document.getElementById("formForum").reset();
        
        // Prevent empty usernames
        if(username.length <= 0){
            socket.close();
            showForm();
            
            alert("Username cannot be empty");
        }
        
        var user = {
            action: "join",
            username: username,
            forum: topic
        };
        
        // send info to server
        socket.send(JSON.stringify(user));
    };
    
    // On socket close
    socket.onclose = () => {
        console.log("Connection closed!");
    };
    
    socket.onmessage = onMessage;

}
