package com.phcarvalho.model;

import com.phcarvalho.controller.MainController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.ChatRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.Game;

import java.rmi.RemoteException;

public class MainModel {

    private MainController controller;
    private ConnectionModel connectionModel;
    private GameModel gameModel;
    private ChatRemoteCommandTemplateAdapter chatRemoteCommandTemplateAdapter;

    public MainModel(MainController controller) {
        this.controller = controller;
        connectionModel = DependencyFactory.getSingleton().get(ConnectionModel.class);
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
        chatRemoteCommandTemplateAdapter = DependencyFactory.getSingleton().get(ChatRemoteCommandTemplateAdapter.class);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand) {
        Game game = Configuration.getSingleton().getGame(sendMessageCommand.getGameId());

        game.getRemoteUserList()
                .forEach(remoteUser -> chatRemoteCommandTemplateAdapter.sendMessage(sendMessageCommand, remoteUser));
    }
}
