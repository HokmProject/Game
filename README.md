# Game
You can use the "PROTOTYPE" directory to test your code before commiting and updating the "GAME" directory
Can be used for Testing the GUI , Server & Client Connections 

# Hokm Game

This is a simple implementation of the card game Hokm, with a server-client architecture using Java. The game allows players to join specific games using a token or join a random game using a GUI implemented with Swing.

## Project Structure

- `ClientGUI.java`: Implements the client-side graphical user interface.
- `Client.java`: Handles the client-side networking.
- `ClientHandler.java`: Handles communication between the server and each client.
- `Server.java`: Manages the server-side logic, including creating and managing games.
- `Game.java`: Represents the game logic and state.
- `Player.java`: Represents a player in the game.
- `Card.java`: Represents a card in the game.
- `Team.java`: Represents a team of players.
- `Set.java`: Represents a set of cards in the game.
- `Result.java`: Represents the result of a game.

## Requirements

- Java 8 or later
- A terminal or command prompt to run the server and client

## How to Run

### Server

1. Compile the server-side classes:

    ```sh
    javac Server.java ClientHandler.java Game.java Player.java Card.java Team.java Set.java Result.java
    ```

2. Run the server:

    ```sh
    java Server
    ```

### Client

1. Compile the client-side classes:

    ```sh
    javac ClientGUI.java Client.java
    ```

2. Run the client:

    ```sh
    java ClientGUI
    ```

## Usage

1. Start the server first. It will listen for incoming client connections.
2. Start the client. A GUI will appear allowing you to:
   - Enter a game token and click "Join Game" to join a specific game.
   - Click "Join Random Game" to join any available game.
3. Interact with the game through the GUI. The text area displays messages from the server.

## Classes and Methods

### ClientGUI

- **sendRequest()**: Sends a request to the server.
- **joinGame()**: Joins a specific game using a token.
- **joinRandomGame()**: Joins a random game.

### Client

- **sendRequest(Object request)**: Sends a request to the server.
- **receiveResponse()**: Receives a response from the server.

### ClientHandler

- **run()**: Listens for client requests and handles them.
- **handleRequest(Object request)**: Processes client requests.
- **sendResponse(Object response)**: Sends a response to the client.

### Server

- **createNewGame(Game game)**: Creates a new game.
- **addClient(ClientHandler clientHandler)**: Adds a client to the server.
- **removeClient(ClientHandler clientHandler)**: Removes a client from the server.
- **getGames()**: Returns the list of games.
- **start()**: Starts the server and listens for client connections.

### Game

- Represents the game state and logic.

### Player

- Represents a player in the game.

### Card

- Represents a card in the game.

### Team

- Represents a team of players.

### Set

- Represents a set of cards in the game.

### Result

- Represents the result of a game.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
