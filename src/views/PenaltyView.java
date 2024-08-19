package views;

import java.awt.event.ActionListener;

import config.App;

public class PenaltyView extends View {

    public PenaltyView() {
        super("Penalty", App.getwWidth(100), App.getwWidth(100));
    }

    @Override
    public void setActionListeners(ActionListener listener) {
        super.setActionListeners(listener);
    }
}
