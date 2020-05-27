package sdu.cbse.group2.telnetgogo;

import org.apache.felix.gogo.runtime.CommandNotFoundException;
import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import sdu.cbse.group2.common.services.TelnetSPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TelnetGogo implements TelnetSPI {

    private static final String HOST = "localhost";
    private static final int PORT = 2019;
    private static final int TIME_OUT_MILLIS = 500;

    @Override
    public synchronized void execute(String command, Consumer<List<String>> response) {
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
                    response.accept(result);
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

    // Invoked via Declarative Services.
    public void telnetStarter(CommandProcessor commandProcessor) {
        try (CommandSession commandSession = commandProcessor.createSession(System.in, System.out, System.err)) {
            commandSession.execute("telnetd start");
        } catch (CommandNotFoundException e) {
            // Retry if the framework isn't fully initialized.
            Executors.newSingleThreadScheduledExecutor().schedule(() -> telnetStarter(commandProcessor), 100, TimeUnit.MILLISECONDS);
        } catch (Exception ignored) {
            // Execute throws a StackOverFlow exception. This does not interfere with the actual execution i.e. we can ignore it.
        }
    }
}
