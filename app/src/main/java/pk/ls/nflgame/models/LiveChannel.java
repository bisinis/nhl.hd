package pk.ls.nflgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class LiveChannel {
    @SerializedName("channel_id")
    private String channelId;

    @SerializedName("channel_name")
    private String channelName;

    @SerializedName("channel_url")
    private String channelUrl;

    @SerializedName("logo")
    private String logo;

    public LiveChannel() {
    }

    public LiveChannel(String channelId, String channelName, String channelUrl, String logo) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.logo = logo;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveChannel that = (LiveChannel) o;
        return Objects.equals(channelId, that.channelId) &&
                Objects.equals(channelName, that.channelName) &&
                Objects.equals(channelUrl, that.channelUrl) &&
                Objects.equals(logo, that.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, channelUrl, logo);
    }
}
