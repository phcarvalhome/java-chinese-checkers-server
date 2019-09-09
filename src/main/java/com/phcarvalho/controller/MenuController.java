package com.phcarvalho.controller;

import com.phcarvalho.model.MenuModel;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.view.MenuView;

public class MenuController {

    private MenuView view;
    private MenuModel model;

    public MenuController() {
    }

    public void startServer(Integer port) {
        model.startServer(port);
    }

    public void setLocalUser(User localUser) {
        view.setLocalUser(localUser);
    }

    public void setView(MenuView view) {
        this.view = view;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
}
