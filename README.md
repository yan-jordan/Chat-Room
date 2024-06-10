# Chat Room Application

This is a simple command-line chat room application implemented in Java. It consists of a server and a client component that communicate over TCP sockets. Multiple clients can connect to the server, and messages sent by any client are broadcasted to all other connected clients.

## Features

- Server listens for incoming client connections on a specified port
- Clients can connect to the server by providing the server's IP address and port
- Each client is assigned a unique username upon connection
- Clients can send text messages that are broadcasted to all other connected clients
- Server and client components are implemented using multi-threading for concurrent operations

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your system

### Running the Application

1. Clone the repository or download the source code.
2. Open a terminal or command prompt and navigate to the project directory.
3. Compile the Java files:
4. Start the server by running the compiled `Server` class:
You should see the message "Server is running..." printed to the console.
5. Open additional terminal windows or command prompts and run the `Client` class for each client you want to connect:
6. When prompted, enter a username for each client.
7. Once connected, you can start sending messages from any client. The messages will be broadcasted to all other connected clients.

## Code Structure

### Server.java

This class contains the server-side implementation of the chat room application.

#### Main Classes and Methods

- `Server`: This is the main class that sets up the server socket and listens for incoming client connections.
- `main(String[] args)`: The entry point of the application. It creates the server socket, accepts client connections, and creates a new `UserThread` for each connected client.
- `UserThread`: This class extends the `Thread` class and represents a separate thread for each connected client. It handles communication with the client and broadcasts messages to other clients.
- `run()`: The entry point for the thread. It continuously reads messages from the client and broadcasts them to other clients.
- `sendMessage(String message)`: This method sends a message to the client.
- `getUserName(Socket socket)`: This method prompts the client for a username and returns the entered username.
- `broadcastMessage(String message, UserThread sender)`: This method broadcasts a message to all connected clients except the sender.

### Client.java

This class contains the client-side implementation of the chat room application.

#### Main Classes and Methods

- `Client`: This is the main class that sets up the client socket and handles communication with the server.
- `main(String[] args)`: The entry point of the application. It connects to the server, prompts for a username, creates a `ClientThread` for receiving messages, and sends messages to the server.
- `ClientThread`: This class extends the `Thread` class and represents a separate thread for receiving messages from the server.
- `run()`: The entry point for the thread. It continuously reads messages from the server and prints them to the console.
- `getUserName(Scanner scanner, BufferedReader reader, PrintWriter writer)`: This method prompts the user for a username, sends it to the server, and returns the entered username.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please send it to my email [pouyanmehdibeik@gmail.com]
