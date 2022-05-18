package Server;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class Server {

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(55555);
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/gameConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Game game = new Game(p);
            try {
                Player playerOne = new Player("Player 1", serverSocket.accept(), game);
                playerOne.sendMessageToPlayer(p);
                playerOne.sendMessageToPlayer("Player 1");
                Player playerTwo = new Player("Player 2", serverSocket.accept(), game);
                game.setPlayerOne(playerOne);
                playerTwo.sendMessageToPlayer(p);
                game.setPlayerTwo(playerTwo);
                playerOne.start();
                playerTwo.start();
                game.thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[]args) throws IOException {
        Server server = new Server();
    }
}
