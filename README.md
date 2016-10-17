# socket.io-client

<p>
Simple chat application based on java socket.IO - client (<a href="https://github.com/socketio/socket.io-client-java">socket.io-client-java</a>)
</p>

# Application description
<p>
This is just a simple chat client that communicates with a <b>Socket.IO Node.JS chat server</b>. In the simple scenario client can send and receive messages from the server. Bisides that client can ask to the server for some random command (there are few different types of commands client can receive from the server <i>date | map | checkmark | rate</i>). Regarding to which type of command client has received it makes corresponding parsing and shows loaded results on the recyclerView. After that user can "communicate" with that results selecting one or another value provided by the server. When user selects some result that specific view will be updated and on the same position should appear user's name with corresponding selected value.    
</p>

# Protocol
The server implements the following protocol over web sockets:<br/>
`message: { author: "<nick-name>", message: "<message>" }`<br/> 
`command: { author: "<nick-name>", command: { type: "", data: {} } }`<br/>

When you send a message event with the following payload:<br/>
`{ author: "client", message: "hello" }`<br/>
the server will respond:<br/>
`{ author: "Mega bot", message: "Hey client, you said "hello", right? }`<br/> 

Commands may have one of the following types:<br/>
`date`  The server will send `type: date` and `data:timestamp`.<br/>
In response to this command the following buttons on the screen: - `Mon, Tue, Wed, Thu, Fri, Sat, Sun` should be rendered with the correct starting day.<br/>
`map`  The server will send `type: map` and `data:{lat:"", lng:""}`.<br/>
In response to this command, a small map on the screen should be rendered with a marker located at the specified co-ordinates.<br/> 
`rate`  The server will send `type: rate` and `data:[i,j]`, where `0 < i < j <= 5`.<br/>
In response to this command, a simple `RaitingBar` on the screen should be rendered with a corresponding `j` starts and `i` from them by default are selected.<br/>
`complete`  The server will send `type: complete` and `data: ['Yes','No']`.<br/>
In response to this command two simple buttons should be rendered on the screen with the corresponding texts "Yes" and "No".<br/>

# Screenshots from the app
`login fragment`<br/>
<img src="https://cloud.githubusercontent.com/assets/11542701/19443292/8f3fbec8-948c-11e6-846d-d7edc6809b5c.png" width="240px" height="426px" style="float: left; display:inline; margin: 30px;" alt="main feed page"></img>

`chat fragment`<br/>
<img src="https://cloud.githubusercontent.com/assets/11542701/19382731/c1f182b2-9201-11e6-9850-e61c4f384d47.png" width="240px" height="426px" style="margin: 30px;" alt="main feed page with loaded similar movies"></img>


# Used Libraries
<ul>
  <li><b>io.socket:socket.io-client:0.8.1</b> <i>java client for socket.IO</i>
  <li><b>com.google.code.gson:gson:2.7</b> <i>Serializing/Deserializing</i>
  <li><b>com.jakewharton:butterknife:7.0.1</b> <i>View injection</i>
  <li><b>com.squareup.picasso:picasso:2.5.2</b> <i>Image downloading and caching</i>
  <li><b>net.orfjackal.retrolambda:retrolambda:2.3.0</b> <i>java 8 lambda support</i>
</ul>
