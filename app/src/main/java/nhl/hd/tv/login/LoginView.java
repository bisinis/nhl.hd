package nhl.hd.tv.login;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface LoginView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoading(boolean status);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(int error);
    @StateStrategyType(SkipStrategy.class)
    void showEntry(String username, String password);
    @StateStrategyType(SkipStrategy.class)
    void showErrorExpired(String error);
}
