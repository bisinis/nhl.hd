package nhl.hd.tv.translation;


import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import nhl.hd.tv.models.LiveChannel;
import nhl.hd.tv.models.Replay;


public interface TranslationView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslations(List<String> links);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void deleteTranslations(int position);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void addTranslation(String url);

    @StateStrategyType(SkipStrategy.class)
    void showError(int id);
    @StateStrategyType(SkipStrategy.class)
    void back();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLiveChannels(List<LiveChannel> list);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showReplays(List<Replay> list);
}
