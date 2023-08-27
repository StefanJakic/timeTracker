package org.example;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ProgressData {
    //private String yourNameOfProgres?
    public final Long MULTIPLY_MINUTES_FOR_MILISECONDS = 1000L * 60;
    private Stopwatch stopwatch;
    private Boolean isEnd = Boolean.FALSE;

    private Date idTimeStampCreated;
    private Long targetProgress = 0L;
    private Long acctualFocus = 0L;
    private Integer focusRestarted = 0;

    public FocusData getFocusData(Integer i) {
        return focusData.get(i);
    }

    private final HashMap<Integer, FocusData> focusData = new HashMap<>();
    private Long targetFocus = 0L;
    private Long acctualProgress = 0L;

    public Boolean isRuning() {
        return stopwatch.isRunning();
    }

    public ProgressData() {
        // Default constructor needed for Gson
    }

    public ProgressData(Integer targetProgress, Integer targetFocus) {
        //TODO for future check is data is valid

        idTimeStampCreated = Calendar.getInstance().getTime();
        stopwatch = new Stopwatch();
        this.targetProgress = targetProgress * MULTIPLY_MINUTES_FOR_MILISECONDS;
        this.targetFocus = targetFocus * MULTIPLY_MINUTES_FOR_MILISECONDS;
    }


    public void startProgressAndFocus() {
        focusData.put(++focusRestarted, new FocusData(focusRestarted));
        stopwatch.start();
    }

    public long getStopwatchgetElapsedTime() {
        return stopwatch.getElapsedTime();
    }


    public void calculateProgressAndRestartFocus(Boolean isWasDone, Boolean isWillDone, Integer userInputRating, String note) {
        this.acctualFocus = stopwatch.getElapsedTime();
        stopwatch.reset();
        focusData.get(focusRestarted).focusDataEnd(isWasDone, isWillDone, targetFocus, acctualFocus, userInputRating, note, System.currentTimeMillis());
        this.acctualProgress += this.acctualFocus;
    }

    public Boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Boolean end) {
        isEnd = end;
    }

    public Long getTargetProgress() {
        return targetProgress;
    }

    public Long getTargetFocus() {
        return targetFocus;
    }

    public Long getAcctualProgress() {
        return acctualProgress + stopwatch.getElapsedTime();
    }

    public Integer getFocusRestarted() {
        return focusRestarted;
    }

    @Override
    public String toString() {
        return "ProgressData{" +
                "isEnd=" + isEnd +
                ", idTimeStampCreated=" + idTimeStampCreated +
                ", targetProgress=" + targetProgress +
                ", targetFocus=" + targetFocus +
                ", acctualProgress=" + acctualProgress +
                ", acctualFocus=" + acctualFocus +
                ", focusRestarted=" + focusRestarted +
                ", focusData=" + focusData +
                '}';
    }
}
