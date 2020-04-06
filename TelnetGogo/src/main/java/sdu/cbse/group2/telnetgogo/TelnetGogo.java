package sdu.cbse.group2.telnetgogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class TelnetGogo {

    private static final String HOST = "localhost";
    private static final int PORT = 2019;
    private static final int TIME_OUT_MILLIS = 500;

    public TelnetGogo() {
        //TODO Execute "telnetd start" somehow.
    }

    public synchronized void execute(String command, Consumer<List<String>> consumer) {
        Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        }).execute(new Runnable() {
            private final List<String> result = new ArrayList<>();
            private Socket socket;
            private PrintWriter out;
            private BufferedReader in;

            @Override
            public void run() {
                try {
                    openConnection();
                    out.println(command);
                    String from;
                    while ((from = in.readLine()) != null) {
                        result.add(from);
                    }
                } catch (SocketTimeoutException ignored) {
                    // Thrown when the readLine waits for information that'll never arrive.
                    consumer.accept(result);
                    try {
                        closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void openConnection() throws IOException {
                socket = new Socket(HOST, PORT);
                socket.setSoTimeout(TIME_OUT_MILLIS);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

            private void closeConnection() throws IOException {
                in.close();
                out.close();
                socket.close();
            }
        });
    }
}
