package pk.ls.nflgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LiveGame {
    @SerializedName("game_name")
    @Expose
    private String gameName;

    @SerializedName("stream_link")
    @Expose
    private String streamLink;

    private boolean statusCheck = false;

    public LiveGame() {
    }

    public LiveGame(String gameName, String streamLink) {
        this.gameName = gameName;
        this.streamLink = streamLink;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public boolean isStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveGame liveGame = (LiveGame) o;
        return statusCheck == liveGame.statusCheck &&
                Objects.equals(gameName, liveGame.gameName) &&
                Objects.equals(streamLink, liveGame.streamLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, streamLink, statusCheck);
    }
}
