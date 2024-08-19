package views;

import java.awt.event.ActionListener;

import config.App;

public class ReturningView extends View {

    public ReturningView() {
        super("Returning", App.getwWidth(100), App.getwWidth(100));
    }

    @Override
    public void setActionListeners(ActionListener listener) {
        super.setActionListeners(listener);
    }
}
