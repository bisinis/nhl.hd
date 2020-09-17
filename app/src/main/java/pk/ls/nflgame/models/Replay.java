package pk.ls.nflgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Replay {
    @SerializedName("replay_name")
    @Expose
    private String replayName;

    @SerializedName("repay_stream_link")
    @Expose
    private String repayStreamLink;

    @SerializedName("date")
    @Expose
    private String date;

    public Replay() {
    }

    public Replay(String replayName, String repayStreamLink, String date) {
        this.replayName = replayName;
        this.repayStreamLink = repayStreamLink;
        this.date = date;
    }

    public String getReplayName() {
        return replayName;
    }

    public void setReplayName(String replayName) {
        this.replayName = replayName;
    }

    public String getRepayStreamLink() {
        return repayStreamLink;
    }

    public void setRepayStreamLink(String repayStreamLink) {
        this.repayStreamLink = repayStreamLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Replay replay = (Replay) o;
        return Objects.equals(replayName, replay.replayName) &&
                Objects.equals(repayStreamLink, replay.repayStreamLink) &&
                Objects.equals(date, replay.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replayName, repayStreamLink, date);
    }
}
