package com.phcarvalho.controller;

import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.view.GameView;

public class GameController {

    private GameView view;
    private GameModel model;

    public GameController() {
    }

    public void initializeList() {
        view.getList().setModel(model.getList());
    }

    public void add(Game game) {
        view.add(game);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void setModel(GameModel model) {
        this.model = model;
    }
}
