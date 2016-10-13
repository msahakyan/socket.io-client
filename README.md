# socket.io-client

<p>
Simple chat application based on java socket.IO - client (<a href="https://github.com/socketio/socket.io-client-java">socket.io-client-java</a>)
</p>

# Application description
<p>
This is just simple chat client that communicates with a <b>Socket.IO Node.JS chat server</b>. In the simple scenario client can send and receive messages from the server. Bisides that client can ask to the server for some random command (there are few different types of commands client can receive from the server <i>date | map | checkmark | rate</i>). Regarding to which type of command client has received it makes corresponding parsing and shows loaded results on the recyclerView. After that user can "communicate" with that results selecting one or another value provided by the server. When user selects some result that specific view will be updated and on the same position should appear user's name with corresponding selected value.    
</p>

