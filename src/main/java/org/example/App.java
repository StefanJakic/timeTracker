package org.example;

import javax.swing.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {

        //TODO add users&dataFiles register, login, logout

        SwingUtilities.invokeLater(() -> {
            Gui app = null;
            app = new Gui();

            // Add a shutdown hook to save data before exiting
            Gui finalApp = app;
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        finalApp.shutDownHandler();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(" is shutting down.");
                }
            });

            app.setVisible(true);
        });

    }

}
