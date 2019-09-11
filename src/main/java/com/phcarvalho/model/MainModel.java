package com.phcarvalho.model;

import com.phcarvalho.controller.MainController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.IChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

import java.util.List;

public class MainModel {

    private MainController controller;
    private ConnectionModel connectionModel;
    private GameModel gameModel;
    private IChatRemoteCommandTemplate chatRemoteCommandTemplate;

    public MainModel(MainController controller) {
        this.controller = controller;
        connectionModel = DependencyFactory.getSingleton().get(ConnectionModel.class);
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
        chatRemoteCommandTemplate = DependencyFactory.getSingleton().get(IChatRemoteCommandTemplate.class);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand) {
        Game game = Configuration.getSingleton().getGame(sendMessageCommand.getGameId());

        game.getRemoteUserList()
                .forEach(remoteUser -> {

                    try {
                        chatRemoteCommandTemplate.sendMessage(sendMessageCommand, remoteUser);
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                        //TODO add handling...
                    }
                });
    }

    public void startServerByCallback(User localUser) {
        connectionModel.startServerByCallback(localUser);
    }

    public void sendAll(User remoteUser) {
        gameModel.sendAll(remoteUser);
    }
}
