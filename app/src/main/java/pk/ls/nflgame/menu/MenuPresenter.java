package pk.ls.nflgame.menu;



import moxy.InjectViewState;
import moxy.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.database.LocalDatabase;
import pk.ls.nflgame.helper.UpdateDatabse;
import pk.ls.nflgame.models.FullData;
import pk.ls.nflgame.models.LiveGame;

@InjectViewState
public class MenuPresenter extends MvpPresenter<MenuView> implements UpdateDatabse {
    ArrayList<LiveGame> listLinks = new ArrayList<>();
    private int tab = 0;

    public boolean addLinks(LiveGame link){
        if (listLinks.size() >= 4){
            getViewState().showError(R.string.error_max_links);
            return false;
        }else
            listLinks.add(link);
        return true;
    }
    public void deleteLinks(LiveGame link){
        listLinks.remove(link);
    }
    public void resetLinks(){
        listLinks = new ArrayList<>();
    }
    public void clickTabs(int tab){
        this.tab = tab;
        switch (tab){
            case 0:
                getViewState().showLiveChannels(LocalDatabase.fullData.getLiveChannels());
                break;
            case 1:
                getViewState().showLiveGames(updateGames(LocalDatabase.fullData.getLiveGames()));
                break;
            case 2:
                getViewState().showReplays(LocalDatabase.fullData.getReplays());
                break;
        }
    }
    public void clickGame(String link){
        getViewState().showTranslation(link);
    }
    public void showTranslations(){
        ArrayList<String> stringList = new ArrayList<>();
        for (int i=0;i<listLinks.size();i++){
            stringList.add(listLinks.get(i).getStreamLink());
        }
        if (listLinks.size()==0){
            getViewState().showError(R.string.error_min_links);
        }else{
            getViewState().showTranslations(stringList);
        }
    }

    @Override
    public void updateDatabase(FullData fullData) {
        if (fullData.getStatus().equals("error"))
            getViewState().showErrorExpired(fullData.getMessage());
        if (!fullData.getStatus().equals("success")) {
            getViewState().backToLogin();
            return;
        }
        if (LocalDatabase.fullData.equals(fullData))
            return;
        LocalDatabase.fullData = fullData;
        switch (tab){
            case 0:
                getViewState().showLiveChannels(LocalDatabase.fullData.getLiveChannels());
                break;
            case 1:
                getViewState().showLiveGames(updateGames(LocalDatabase.fullData.getLiveGames()));
                break;
            case 2:
                getViewState().showReplays(LocalDatabase.fullData.getReplays());
                break;
        }
    }

    public LiveGame checkGame(LiveGame liveGame){
        for (int a=0;a<listLinks.size();a++){
            if (listLinks.get(a).equals(liveGame)){
                liveGame.setStatusCheck(true);
                return liveGame;
            }
        }
        return liveGame;
    }

    public List<LiveGame> updateGames(List<LiveGame> listOld){
        System.out.println("updateGames");
        for (int i=0;i<listLinks.size();i++){
            for (int a=0;a<listOld.size();a++){
                if (listLinks.get(i).equals(listOld.get(a))){
                    listOld.get(a).setStatusCheck(true);
                    break;
                }
            }
        }

        return listOld;
    }
}