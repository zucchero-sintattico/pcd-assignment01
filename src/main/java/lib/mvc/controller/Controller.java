package lib.mvc.controller;

import lib.mvc.view.View;

public interface Controller {
    void setView(final View view);
    <V extends View> V getView();
}

