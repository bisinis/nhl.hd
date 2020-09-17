package pk.ls.nflgame.translation;


import moxy.InjectViewState;
import moxy.MvpPresenter;
import pk.ls.nflgame.database.LocalDatabase;
import pk.ls.nflgame.helper.UpdateDatabse;
import pk.ls.nflgame.models.FullData;

import java.util.ArrayList;


@InjectViewState
public class TranslationPresenter extends MvpPresenter<TranslationView> implements UpdateDatabse {
    private ArrayList<String> links = new ArrayList<>();
    private String title = null;

    public void setLink(String link, ArrayList<String> links) {
        this.links = links;

        if (link != null){
            this.links = new ArrayList<>();
            this.links.add(link);
        }
        if (this.links!=null){
            getViewState().showTranslations(this.links);
        }
        if (title!=null){
            showList(title);
        }
    }

    public boolean addLink(String link){
        if (links.size()<=4){
            links.add(link);
            getViewState().addTranslation(link);
            return true;
        }
        return false;
    }

    public void back(){
        getViewState().back();
    }


    public void showList(String title){
        this.title = title;
        if (title.equals("Live Channels")){
            getViewState().showLiveChannels(LocalDatabase.fullData.getLiveChannels());
        }else if (title.equals("Replays")){
            getViewState().showReplays(LocalDatabase.fullData.getReplays());
        }
    }

    @Override
    public void updateDatabase(FullData fullData) {
        if (!LocalDatabase.fullData.equals(fullData))
            return;
        LocalDatabase.fullData = fullData;
        if (title.equals("Live Channels")){
            getViewState().showLiveChannels(fullData.getLiveChannels());
        }else if (title.equals("Replays")){
            getViewState().showReplays(fullData.getReplays());
        }
    }
}
