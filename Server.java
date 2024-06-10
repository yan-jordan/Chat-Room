import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 1234;
    private static Set<String> userNames = new HashSet<>();
    private static Set<UserThread> userThreads = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server is running on port 1234");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                String userName = getUserName(socket);
                userNames.add(userName);

                UserThread newUser = new UserThread(socket, userName);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUserName(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("Enter your username:");
        String userName = reader.readLine();
        writer.println("Welcome " + userName);
        return userName;
    }

    private static void broadcastMessage(String message, UserThread sender) {
        for (UserThread user : userThreads) {
            if (user != sender) {
                user.sendMessage(message);
            }
        }
    }

    private static class UserThread extends Thread {
        private Socket socket;
        private String userName;

        public UserThread(Socket socket, String userName) {
            this.socket = socket;
            this.userName = userName;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        userThreads.remove(this);
                        userNames.remove(userName);
                        break;
                    }
                    System.out.println(userName + ": " + message);
                    broadcastMessage(userName + ": " + message, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}