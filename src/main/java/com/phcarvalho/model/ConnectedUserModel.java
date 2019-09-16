package com.phcarvalho.model;

import com.phcarvalho.controller.ConnectedUserController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.ConnectionRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.MainRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.model.communication.protocol.vo.command.DisconnectCommand;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.model.configuration.entity.User;

import javax.swing.*;

public class ConnectedUserModel {

    private ConnectedUserController controller;
    private ConnectionRemoteCommandTemplateAdapter connectionRemoteCommandTemplateAdapter;
    private MainRemoteCommandTemplateAdapter mainRemoteCommandTemplateAdapter;
    private DefaultListModel<User> list;

    public ConnectedUserModel(ConnectedUserController controller) {
        this.controller = controller;
        connectionRemoteCommandTemplateAdapter = DependencyFactory.getSingleton()
                .get(ConnectionRemoteCommandTemplateAdapter.class);
        mainRemoteCommandTemplateAdapter = DependencyFactory.getSingleton()
                .get(MainRemoteCommandTemplateAdapter.class);
        list = new DefaultListModel();
    }

    public void add(ConnectCommand connectCommand) {
        User sourceUser = connectCommand.getSourceUser();

        Configuration.getSingleton().addRemoteUser(sourceUser);
        controller.add(sourceUser);
        list.addElement(sourceUser);
        connectionRemoteCommandTemplateAdapter.connect(connectCommand, sourceUser);
        addGameList(sourceUser);
    }

    private void addGameList(User sourceUser) {
        Configuration.getSingleton().getGameMap().values()
                .forEach(game -> mainRemoteCommandTemplateAdapter.addGame(new AddGameCommand(game), sourceUser));
    }

    public void remove(DisconnectCommand disconnectCommand) {
        User sourceUser = disconnectCommand.getSourceUser();

        remove(sourceUser);
        connectionRemoteCommandTemplateAdapter.disconnect(disconnectCommand, sourceUser);
    }

    private void remove(User sourceUser) {
        Configuration.getSingleton().removeRemoteUser(sourceUser);
        controller.remove(sourceUser);
        list.removeElement(sourceUser);
    }

    public void remove(AddPlayerCommand addPlayerCommand) {
        User sourceUser = addPlayerCommand.getSourceUser();

        remove(sourceUser);

        Integer gameId = addPlayerCommand.getGame().getId();

        Configuration.getSingleton().getGame(gameId).removeUser(sourceUser);
        Configuration.getSingleton().getRemoteUserList()
                .forEach(remoteUser -> mainRemoteCommandTemplateAdapter.addPlayer(addPlayerCommand, remoteUser));
    }

    public DefaultListModel<User> getList() {
        return list;
    }
}
