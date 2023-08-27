package org.example;

import javax.swing.*;
import java.awt.*;

public class SetUpTargets {

    ProgressData progressData;
    JFrame parent;
    Integer progresTargetInMinutes, focusTargerInMinutes, progresHoursInput, progresMinutesInput, focusHoursInput, focusMinutesInput, resultOkOrCancelButton;
    SpinnerModel progresHours, progresMinutes, focusHours, focusMinutes;
    JSpinner progresHoursSpinner, progresMinutesSpinner, focusHoursSpinner, focusMinutesSpinner;
    JLabel labelHeader, labelProgressTarget, labelFocusTarget, labelHours, labelMinutes, labelHours2, labelMinutes2;
    ;
    JPanel componentsPanel;

    private int DEFAULT_PROGRESS_HOURS = 8;
    private int DEFAULT_PROGRESS_MINUTES = 0;
    private int DEFAULT_FOCUS_HOURS = 0;
    private int DEFAULT_FOCUS_MINUTES = 45;

    SetUpTargets(ProgressData progressData, JFrame parent) {


        //Basic
        this.progressData = progressData;

        convertAndSetUpDefaultValues(this.progressData.getTargetProgress(), this.progressData.getTargetFocus());

        this.parent = parent;
        progresHours = new SpinnerNumberModel(DEFAULT_PROGRESS_HOURS, 0, 23, 1);
        progresMinutes = new SpinnerNumberModel(DEFAULT_PROGRESS_MINUTES, 0, 60, 1);
        focusHours = new SpinnerNumberModel(DEFAULT_FOCUS_HOURS, 0, 23, 1);
        focusMinutes = new SpinnerNumberModel(DEFAULT_FOCUS_MINUTES, 0, 60, 1);
        progresHoursSpinner = new JSpinner(progresHours);
        progresMinutesSpinner = new JSpinner(progresMinutes);
        focusHoursSpinner = new JSpinner(focusHours);
        focusMinutesSpinner = new JSpinner(focusMinutes);
        labelHeader = new JLabel("Input target goals:");

        labelHours = new JLabel("hours");
        labelMinutes = new JLabel("minutes");
        labelHours2 = new JLabel("hours");
        labelMinutes2 = new JLabel("minutes");

        componentsPanel = new JPanel(new GridLayout(2, 5));

        labelProgressTarget = new JLabel("Progress target: ");
        componentsPanel.add(labelProgressTarget);
        componentsPanel.add(progresHoursSpinner);
        componentsPanel.add(labelHours);
        componentsPanel.add(progresMinutesSpinner);
        componentsPanel.add(labelMinutes);

        labelFocusTarget = new JLabel("Focus target: ");
        componentsPanel.add(labelFocusTarget);
        componentsPanel.add(focusHoursSpinner);
        componentsPanel.add(labelHours2);
        componentsPanel.add(focusMinutesSpinner);
        componentsPanel.add(labelMinutes2);


    }

    private void convertAndSetUpDefaultValues(Long totalMinutesProgres, Long totalMinutesFocus) {
        //Devide with 60000L to get minutes
        DEFAULT_PROGRESS_HOURS = (int) ((totalMinutesProgres / 60000L) / 60);
        DEFAULT_PROGRESS_MINUTES = (int) ((totalMinutesProgres / 60000L) % 60);
        DEFAULT_FOCUS_HOURS = (int) ((totalMinutesFocus / 60000L) / 60);
        DEFAULT_FOCUS_MINUTES = (int) ((totalMinutesFocus / 60000L) % 60);
    }

    public ProgressData openInputDialog() {
        Object[] components = {labelHeader, componentsPanel};
        resultOkOrCancelButton = JOptionPane.showConfirmDialog(parent, components, "Input Dialog", JOptionPane.OK_CANCEL_OPTION);

        if (resultOkOrCancelButton == JOptionPane.OK_OPTION) {
            //Use data
            progresHoursInput = (int) progresHoursSpinner.getValue();
            progresMinutesInput = (int) progresMinutesSpinner.getValue();
            focusHoursInput = (int) focusHoursSpinner.getValue();
            focusMinutesInput = (int) focusMinutesSpinner.getValue();

            //in minutes 480min 8h
            //1*60, + 60?
            progresTargetInMinutes = (60 * progresHoursInput) + progresMinutesInput;
            focusTargerInMinutes = (60 * focusHoursInput) + focusMinutesInput;

            this.progressData = new ProgressData(progresTargetInMinutes, focusTargerInMinutes);
        } else {
            return null;
        }
        return this.progressData;
    }

}
