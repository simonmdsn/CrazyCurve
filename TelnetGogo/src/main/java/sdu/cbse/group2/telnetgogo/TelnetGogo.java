package sdu.cbse.group2.telnetgogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetGogo {

    private static final String HOST = "localhost";
    private static final int PORT = 2019;
    private static final String GROUP = "sdu.cbse.group2";
    private Socket s = null;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public TelnetGogo() {
        try {
            s = new Socket(HOST, PORT);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            s.setSoTimeout(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String command) {
        out.println(command);
    }

    public String read() {
        String from;
        String output = "";
        try {
            while ((from = in.readLine()) != null) {
                System.out.println(from);
                output += from + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String format(String string) {
        String formatString = "";
        for (String s : string.split("\n")) {
            if (s.contains(GROUP)) {
                formatString.concat(s);
            }
        }
        return formatString;
    }
}
