package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);

            while (true) {
                try {
                    socket = serverSocket.accept();

                    inputStreamReader = new InputStreamReader(socket.getInputStream());
                    outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                    bufferedReader = new BufferedReader(inputStreamReader);
                    bufferedWriter = new BufferedWriter(outputStreamWriter);

                    while (true) {
                        String JournalLog = bufferedReader.readLine();
                        if (JournalLog == null) {
                            break; // Client disconnected
                        }
                        System.out.println("POS: " + JournalLog);

                        bufferedWriter.write("Log Received");
                        bufferedWriter.newLine();
                        bufferedWriter.flush(); // Important: flush to send data immediately
                    }

                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                } finally {
                    // Close client resources
                    try {
                        if (bufferedReader != null) bufferedReader.close();
                        if (bufferedWriter != null) bufferedWriter.close();
                        if (inputStreamReader != null) inputStreamReader.close();
                        if (outputStreamWriter != null) outputStreamWriter.close();
                        if (socket != null) socket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing client resources: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            // Close server socket
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}