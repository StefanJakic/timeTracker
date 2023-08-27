package org.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FocusData {
    private Boolean isAchivedGoal = Boolean.FALSE;
    private Integer id;
    private Boolean isWasDone;
    private Boolean isWillDone;
    private Long targetFocus;
    private Long acctualFocus;
    Integer userInputRating;
    String note;
    private Long currentTimeMillisStart;
    private Long currentTimeMillisFinish;
    private Date startedFocusDate;
    private Date endFocusDate;

    private transient SimpleDateFormat dateFormat = new SimpleDateFormat("dd, MM, yy HH:mm");

    public FocusData() {
    }

    public FocusData(Integer id) {
        this.id = id;
        this.startedFocusDate = Calendar.getInstance().getTime();
    }

    public void focusDataEnd(Boolean isWasDone, Boolean isWillDone, Long targetFocus, Long acctualFocus, Integer userInputRating, String note, Long currentTimeMillisFinish) {
        //this.id = id;
        this.endFocusDate = Calendar.getInstance().getTime();
        this.isWasDone = isWasDone;
        this.isWillDone = isWillDone;
        this.targetFocus = targetFocus;
        this.acctualFocus = acctualFocus;
        this.userInputRating = userInputRating;
        this.note = note;
        this.currentTimeMillisFinish = currentTimeMillisFinish;

        if (acctualFocus > this.targetFocus) {
            this.isAchivedGoal = Boolean.TRUE;
        }
        this.currentTimeMillisStart = this.currentTimeMillisFinish - this.acctualFocus;

        //TODO here we cann add Note, NumberOfRate(Asterkis)
    }

    public String getFocusDataDiference(FocusData focusData) {
        Long miliSeconds = this.startedFocusDate.getTime() - focusData.endFocusDate.getTime();
        return geHoursMinutesRepresentation(miliSeconds);
    }

    private String geHoursMinutesRepresentation(Long elapsedTime) {
        Long hours = elapsedTime / 3600000;
        Long minutes = (elapsedTime % 3600000) / 60000;
        Long seconds = (elapsedTime % 60000) / 1000;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getRepresentationForTxt() {
        String s = "isGoalAcchived+" + isAchivedGoal + "WasDone=//" + isWasDone + "WillDone" + isWillDone +
                "//Rate:" + userInputRating + "|" + dateFormat.format(startedFocusDate) + "---" + dateFormat.format(endFocusDate) + "|" + "durationOfSesion:" + geHoursMinutesRepresentation(acctualFocus);
        return s;
    }


    @Override
    public String toString() {
        return "FocusData{" +
                "isAchivedGoal=" + isAchivedGoal +
                ", id=" + id +
                ", isWasDone=" + isWasDone +
                ", isWillDone=" + isWillDone +
                ", targetFocus=" + targetFocus +
                ", acctualFocus=" + acctualFocus +
                ", userInputRating=" + userInputRating +
                ", note=" + note +
                ", currentTimeMillisStart=" + currentTimeMillisStart +
                ", currentTimeMillisFinish=" + currentTimeMillisFinish +
                '}';
    }
}
