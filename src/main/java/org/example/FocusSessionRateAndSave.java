package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FocusSessionRateAndSave {

    ProgressData progressData;
    JFrame parent;
    ButtonGroup isWasDoneButtonsGroup;
    ButtonGroup isWillDoneButtonsGroup;
    JRadioButton isWasDoneNoteButtonYes, isWasDoneNoteButtonNo, isWillDoneNoteButtonYes, isWillDoneNoteButtonNo;
    Boolean isWasDone;
    Boolean isWillDone;
    JPanel checkboxPanel;
    Integer userInputRating = -1;
    JTextArea textArea;
    JPanel componentsPanel;

    JPanel buttonPanel;
    JButton clear;

    JLabel labelRate, labelRateOutput;

    public void setNewProgressData(ProgressData progressData) {
        this.progressData = progressData;
    }

    ;

    public FocusSessionRateAndSave(ProgressData progressData, JFrame parent) {
        this.progressData = progressData;
        this.parent = parent;

        textArea = new JTextArea(5, 20);


        isWasDoneNoteButtonYes = new JRadioButton("[Yes]isWasDoneNote");
        isWasDoneNoteButtonYes.setBounds(100, 50, 100, 30);
        isWasDoneNoteButtonNo = new JRadioButton("[No]");
        isWasDoneNoteButtonNo.setBounds(100, 100, 100, 30);
        isWasDoneButtonsGroup = new ButtonGroup();
        isWasDoneButtonsGroup.add(isWasDoneNoteButtonYes);
        isWasDoneButtonsGroup.add(isWasDoneNoteButtonNo);
        //gorupWill
        isWillDoneNoteButtonYes = new JRadioButton("[Yes]isWillDoneNote");
        isWillDoneNoteButtonYes.setBounds(100, 50, 100, 30);
        isWillDoneNoteButtonNo = new JRadioButton("[No]");
        isWillDoneNoteButtonNo.setBounds(100, 100, 100, 30);
        isWillDoneButtonsGroup = new ButtonGroup();
        isWillDoneButtonsGroup.add(isWillDoneNoteButtonYes);
        isWillDoneButtonsGroup.add(isWillDoneNoteButtonNo);


        checkboxPanel = new JPanel(new GridLayout(2, 2));
        checkboxPanel.add(isWasDoneNoteButtonYes);
        checkboxPanel.add(isWasDoneNoteButtonNo);
        checkboxPanel.add(isWillDoneNoteButtonYes);
        checkboxPanel.add(isWillDoneNoteButtonNo);


        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        labelRate = new JLabel("Please rate your progress (0-5):");
        labelRateOutput = new JLabel("Please rate your progress (0-5):");

        JButton[] starButtons = new JButton[6];

        labelRateOutput.setText("You rated: / star(s)");
        for (int i = 1; i <= 5; i++) {
            starButtons[i] = new JButton("\u2605"); // Unicode character for a star
            final int rating = i;
            starButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userInputRating = rating;
                    labelRateOutput.setText("You rated: " + rating + " star(s)");
                }
            });
        }

        clear = new JButton("-Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInputRating = -1;
                labelRateOutput.setText("You rated: / star(s)");
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(clear);
        for (int i = 1; i <= 5; i++) {
            buttonPanel.add(starButtons[i]);
        }


        componentsPanel = new JPanel(new GridLayout(0, 1));

        componentsPanel.add(checkboxPanel);
        componentsPanel.add(labelRateOutput);
        componentsPanel.add(labelRate);
        componentsPanel.add(buttonPanel);

        componentsPanel.add(new JScrollPane(textArea));
    }

    public Boolean openRatingDialog() {

        int result = JOptionPane.showConfirmDialog(parent, componentsPanel, "Rating Dialog", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {

            populateConstructorAndCLearGuiRatingDialog();

            return true;
        }
        return false;
    }

    public void populateConstructorAndCLearGuiRatingDialog() {
        isWasDone = null;
        isWillDone = null;
        if (isWasDoneNoteButtonYes.isSelected()) {
            isWasDone = Boolean.TRUE;
        } else if (isWasDoneNoteButtonNo.isSelected()) {
            isWasDone = Boolean.FALSE;
        }
        if (isWillDoneNoteButtonYes.isSelected()) {
            isWillDone = Boolean.TRUE;
        } else if (isWillDoneNoteButtonNo.isSelected()) {
            isWillDone = Boolean.FALSE;
        }
        progressData.calculateProgressAndRestartFocus(isWasDone, isWillDone, userInputRating, textArea.getText());

        isWasDoneButtonsGroup.clearSelection();
        isWillDoneButtonsGroup.clearSelection();
        isWasDone = null;
        userInputRating = -1;
        textArea.setText("");
    }

}
