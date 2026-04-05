package co.edu.uptc.view.interfaces;

public interface IMediator {
    void notify(IColleague sender, String event);

    void showPanel(String panelName);
}