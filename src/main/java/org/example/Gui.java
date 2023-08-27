package org.example;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Gui extends JFrame {
    private static Integer DEFAULT_MINUTES_PROGRES = 480;
    private static Integer DEFAULT_MINUTES_FOCUS = 45;
    ProgressData progressData;
    private JProgressBar progressBar;
    private boolean isGreenColorSet = false;
    private JLabel stopwatchLabel, stopwatchLabel2, stopwatchLabel3;
    long elapsedTime, hours, minutes, seconds, elapsedTime2, hours2, minutes2, seconds2, elapsedTime3, hours3, minutes3, seconds3;
    private Timer timer;
    JTextPane paneText;
    StyledDocument StyledDocument;
    SimpleAttributeSet simpleAttributeSet;
    JButton startButton, restartButton, restartProgressButton, startProgressButton;
    Color defaultButtonColor;
    JPanel checkboxPanel, checkboxPanel2, buttonPanel, contentPanel;
    FocusSessionRateAndSave focusSessionRateAndSave;
    SetUpTargets setUpTargets;

    //TODO here is chance to add functionality to customize for user
    //example ""C:\\Users\\Jhon\\Desktop.notesAboutProject.txt\\"
    String filePathWereYouWriteNote = "";
    FileManager fm = new FileManager(filePathWereYouWriteNote);


    public Gui() {
        initializeComponents();
        initializeUI();
        initializeTimer();
    }

    private void updateTimerLabelsAndProgressBar() {
        stopwatchLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        stopwatchLabel2.setText(String.format("%02d:%02d:%02d", hours2, minutes2, seconds2));
        stopwatchLabel3.setText(String.format("%02d:%02d:%02d", hours3, minutes3, seconds3));

        //clock1
        elapsedTime = progressData.getStopwatchgetElapsedTime();
        hours = elapsedTime / 3600000;
        minutes = (elapsedTime % 3600000) / 60000;
        seconds = (elapsedTime % 60000) / 1000;

        //clock2
        elapsedTime2 = progressData.getTargetProgress() - progressData.getAcctualProgress();
        hours2 = elapsedTime2 / 3600000;
        minutes2 = (elapsedTime2 % 3600000) / 60000;
        seconds2 = (elapsedTime2 % 60000) / 1000;

        //clock3
        elapsedTime3 = progressData.getAcctualProgress();
        hours3 = elapsedTime3 / 3600000;
        minutes3 = (elapsedTime3 % 3600000) / 60000;
        seconds3 = (elapsedTime3 % 60000) / 1000;

        //progres bar in %
        /*
        //Formula for procentage for progress bar representation
        //target * x% = actual * 100
        //x% = (actual * 100) / target
        Integer procenti = (int)((progressData.getAcctualProgress() * 100) / progressData.getTargetProgress());
         */
        progressBar.setValue((int) ((progressData.getAcctualProgress() * 100) / progressData.getTargetProgress()));

        //green color
        if (elapsedTime > progressData.getTargetFocus() && !isGreenColorSet) {
            isGreenColorSet = true;
            paneText.setBackground(Color.GREEN);
        }
    }

    private void initializeTimer() {
        timer = new Timer(10, e -> updateTimerLabelsAndProgressBar());
        timer.setInitialDelay(0);
    }


    private void initializeComponents() {

        this.progressData = fm.getLastObject();

        if (this.progressData == null) {
            this.progressData = new ProgressData(DEFAULT_MINUTES_PROGRES, DEFAULT_MINUTES_FOCUS); //TODO NISI JOS NAPRAVIO CITANJE IZ FAJLA
        }

        startButton = new JButton("Start");
        startButton.setEnabled(true);
        restartButton = new JButton("Restart");
        restartButton.setEnabled(false);
        restartProgressButton = new JButton("Restart Progress");
        restartProgressButton.setEnabled(true);
        startProgressButton = new JButton("Start Progres");
        startProgressButton.setEnabled(false);
        defaultButtonColor = restartButton.getBackground();

        if (progressData.getIsEnd().equals(Boolean.TRUE)) {
            //WE use this to load state of previous object, for example: if user force restart pc, or foce to close app
            //next time when he run app, we want to ensure, that it continue where he was last time
            startButton.setEnabled(false);
            restartButton.setEnabled(false);
            restartProgressButton.setEnabled(false);
            startProgressButton.setEnabled(true);

        }
        setUpTargets = new SetUpTargets(this.progressData, this);

        focusSessionRateAndSave = new FocusSessionRateAndSave(this.progressData, this);


        System.out.println("OVO SE ISPISUJE JEDNOM.");

        // Set window title
        setTitle("Z+ Visibility App");

        paneText = new JTextPane();
        //Center
        StyledDocument = paneText.getStyledDocument();
        simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(simpleAttributeSet, StyleConstants.ALIGN_CENTER);
        StyledDocument.setParagraphAttributes(0, StyledDocument.getLength(), simpleAttributeSet, false);

        paneText.setText("restarted: " + progressData.getFocusRestarted() + " | " + (progressData.getTargetProgress() / (60 * 1000)) + "h");
        paneText.setBackground(Color.WHITE);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue((int) ((progressData.getAcctualProgress() * 100) / progressData.getTargetProgress()));


        stopwatchLabel = new JLabel("00:00:000");
        stopwatchLabel.setFont(new Font("Arial", Font.BOLD, 24));
        stopwatchLabel.setHorizontalAlignment(SwingConstants.CENTER);

        stopwatchLabel2 = new JLabel("00:00:000");
        stopwatchLabel2.setFont(new Font("Arial", Font.BOLD, 24));
        stopwatchLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        stopwatchLabel3 = new JLabel("00:00:000");
        stopwatchLabel3.setFont(new Font("Arial", Font.BOLD, 24));
        stopwatchLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initializeUI() {
        setTitle("Z+ Visibility App");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);

        //ON CLOSE
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        Gui.this,
                        "Are you sure you want to exit?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    // User chose to exit
                    dispose(); // Close the JFrame
                }
            }
        });
        checkboxPanel = new JPanel(new GridLayout(2, 2));
        setVisible(true);


        checkboxPanel = new JPanel(new GridLayout(2, 2));

        checkboxPanel2 = new JPanel(new GridLayout(5, 0));

        buttonPanel = new JPanel(new GridLayout(1, 0));
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);


        JPanel buttonPanel2 = new JPanel(new GridLayout(1, 0));
        buttonPanel2.add(startProgressButton);
        buttonPanel2.add(restartProgressButton);

        checkboxPanel2.add(stopwatchLabel);
        checkboxPanel2.add(stopwatchLabel2);
        checkboxPanel2.add(stopwatchLabel3);
        checkboxPanel2.add(buttonPanel);
        checkboxPanel2.add(buttonPanel2);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(paneText, BorderLayout.NORTH);
        contentPanel.add(checkboxPanel2, BorderLayout.CENTER);
        contentPanel.add(progressBar, BorderLayout.SOUTH);
        add(contentPanel);

        progressBar.setValue((int) ((progressData.getAcctualProgress() * 100) / progressData.getTargetProgress()));
        System.out.println("KAD ISPSSS");
        getContentPane().setBackground(Color.WHITE);

        // Configure layout and add components
        // ...

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonClicked();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartButtonClicked();
            }
        });

        restartProgressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartProgressButtonClicked();
            }
        });

        startProgressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startProgressButtonClicked();
            }
        });
    }

    private void startButtonClicked() {
        timer.start();
        progressData.startProgressAndFocus();
        System.out.println("startButton: " + progressData.toString());
        startButton.setEnabled(false);
        restartButton.setEnabled(true);

        if (progressData.getFocusRestarted() > 1) {
            fm.writeCurrentMomentToNote("BREAK DURATION: " + progressData.getFocusData(progressData.getFocusRestarted()).getFocusDataDiference(progressData.getFocusData(progressData.getFocusRestarted() - 1)) + "-------------------what is first to execute?");
        } else if (progressData.getFocusRestarted() == 1) {
            fm.writeCurrentMomentToNote("First sesion-------------------what is first to execute?");
        }
    }

    private void restartButtonClicked() {
        Boolean isRated = focusSessionRateAndSave.openRatingDialog();

        if (!isRated) return;

        System.out.println("restartButton(pre): " + progressData.toString());
        timer.restart();

        isGreenColorSet = false;
        paneText.setBackground(Color.WHITE);

        System.out.println("restartButton(posle): " + progressData.toString());

        paneText.setText("restarted: " + progressData.getFocusRestarted() + " | " + (progressData.getTargetProgress() / (60 * 1000)) + "h");
        System.out.println("RESTART KLIKNUT!");
        restartButton.setEnabled(false);
        startButton.setEnabled(true);

        if (progressData.getFocusRestarted() > 0) {
            fm.writeCurrentMomentToNote(progressData.getFocusData(progressData.getFocusRestarted()).getRepresentationForTxt());
        }
    }

    private void restartProgressButtonClicked() {
        if (restartButton.isEnabled()) {

            JOptionPane.showMessageDialog(contentPanel,
                    "First you must finish focus sesssion",
                    "restartProgress Warning!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        restartProgressButton.setEnabled(false);
        startButton.setEnabled(false);
        timer.restart();

        this.progressData.setIsEnd(Boolean.TRUE);

        fm.appendObject(progressData);
        startProgressButton.setEnabled(true);
    }


    private void startProgressButtonClicked() {
        ProgressData isSetUp = setUpTargets.openInputDialog();
        if (null == isSetUp) return;

        progressData = isSetUp;
        focusSessionRateAndSave.setNewProgressData(progressData);

        System.out.println("startProgressButton: " + progressData.toString());
        startProgressButton.setEnabled(false);
        restartProgressButton.setEnabled(true);
        startButton.setEnabled(true);

        paneText.setText("restarted: " + progressData.getFocusRestarted() + " | " + (progressData.getTargetProgress() / (60 * 1000)) + "h");
    }

    public void shutDownHandler() throws IOException {
        if (this.progressData == null) {
            return;
        }

        if (this.progressData.getIsEnd()) {
            return;
        }

        if (this.progressData.isRuning()) {
            focusSessionRateAndSave.populateConstructorAndCLearGuiRatingDialog();
            if (progressData.getFocusRestarted() > 0) {
                fm.writeCurrentMomentToNote(progressData.getFocusData(progressData.getFocusRestarted()).getRepresentationForTxt());
            }
        }
        fm.overwriteLastObject(this.progressData);

    }

}
