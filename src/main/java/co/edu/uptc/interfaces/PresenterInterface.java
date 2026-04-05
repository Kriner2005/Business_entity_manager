package co.edu.uptc.interfaces;

public interface PresenterInterface<V extends ViewInterface<?>> {
    public void setView(V view);

    public void setModel(ModelInterface model);

}
