package co.edu.uptc.interfaces;

public interface ViewInterface {
    void setPresenter(PresenterInterface<ViewInterface> presenter);

    void start();

    void showMessage(String msg);

    void showError(String msg);

    void showAlert(String msg);
}
