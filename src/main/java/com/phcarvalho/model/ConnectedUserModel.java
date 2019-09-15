package com.phcarvalho.model;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.ConnectionRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.controller.ConnectedUserController;

import javax.swing.*;

public class ConnectedUserModel {

    private ConnectedUserController controller;
    private ConnectionRemoteCommandTemplateAdapter connectionRemoteCommandTemplateAdapter;
    private DefaultListModel<User> list;

    public ConnectedUserModel(ConnectedUserController controller) {
        this.controller = controller;
        connectionRemoteCommandTemplateAdapter = DependencyFactory.getSingleton()
                .get(ConnectionRemoteCommandTemplateAdapter.class);
        list = new DefaultListModel();
    }

    public void add(ConnectCommand connectCommand) {
        User sourceUser = connectCommand.getSourceUser();

        Configuration.getSingleton().addRemoteUser(sourceUser);
        controller.add(sourceUser);
        list.addElement(sourceUser);
        connectionRemoteCommandTemplateAdapter.connect(connectCommand, sourceUser);
    }

    public void remove(User user) {
        Configuration.getSingleton().removeRemoteUser(user);
        list.removeElement(user);
    }

    public DefaultListModel<User> getList() {
        return list;
    }
}
