package nhl.hd.tv.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class FullData {
    @SerializedName("live_channes")
    @Expose
    private List<LiveChannel> liveChannels;

    @SerializedName("live_games")
    @Expose
    private List<LiveGame> liveGames;

    @SerializedName("replays")
    @Expose
    private List<Replay> replays;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message ;

    public List<LiveChannel> getLiveChannels() {
        return liveChannels;
    }

    public void setLiveChannels(List<LiveChannel> liveChannels) {
        this.liveChannels = liveChannels;
    }

    public List<LiveGame> getLiveGames() {
        return liveGames;
    }

    public void setLiveGames(List<LiveGame> liveGames) {
        this.liveGames = liveGames;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullData fullData = (FullData) o;
        return Objects.equals(liveChannels, fullData.liveChannels) &&
                Objects.equals(liveGames, fullData.liveGames) &&
                Objects.equals(replays, fullData.replays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(liveChannels, liveGames, replays);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
}
