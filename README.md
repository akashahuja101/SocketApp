# SocketApp
A web-based calculator for simple expressions using Network-based Socket programming.
There are two versions of calculator:
* One uses TCP as Transport Protocol
* Other uses UDP for transport and implements basic stop-and-wait reliability at the application layer

The interaction flow between the client and server is as follows:

1. The server starts and waits for a connection to be established by the client.
2. When an expression is received, the server will:
   * Find the result of the expression and store it in a local variable.
   * Write the result and the corresponding number of strings "Socket Programming" to the connection established by the client.
3. Finally, the client will receive the result from the socket and display it to the user.
