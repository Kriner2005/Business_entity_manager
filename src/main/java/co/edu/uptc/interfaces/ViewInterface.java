package co.edu.uptc.interfaces;

public interface ViewInterface<P extends PresenterInterface<?>> {
    void setPresenter(P presenter);

    void start();

    void showMessage(String msg);

    void showError(String msg);

    void showAlert(String msg);
}
