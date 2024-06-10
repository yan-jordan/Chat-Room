import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Connected to server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String userName = getUserName(scanner, reader, writer);

            ClientThread clientThread = new ClientThread(socket, userName);
            clientThread.start();

            String message;
            while (true) {
                message = scanner.nextLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUserName(Scanner scanner, BufferedReader reader, PrintWriter writer) throws IOException {
        System.out.print("Enter your username: ");
        String userName = scanner.nextLine();
        writer.println(userName);
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);
        return userName;
    }

    private static class ClientThread extends Thread {
        private Socket socket;
        private String userName;

        public ClientThread(Socket socket, String userName) {
            this.socket = socket;
            this.userName = userName;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}