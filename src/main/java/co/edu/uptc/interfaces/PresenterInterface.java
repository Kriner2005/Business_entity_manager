package co.edu.uptc.interfaces;

public interface PresenterInterface<V extends ViewInterface> {
    public void setView(ViewInterface view);

    public void setModel(ModelInterface model);

}
