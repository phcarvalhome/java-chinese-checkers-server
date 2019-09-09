package com.phcarvalho.model;

import com.phcarvalho.controller.ConnectedUserController;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;

import javax.swing.*;

public class ConnectedUserModel {

    private ConnectedUserController controller;
    private DefaultListModel<User> list;

    public ConnectedUserModel(ConnectedUserController controller) {
        this.controller = controller;
        list = new DefaultListModel();
    }

    public void add(User user) {
        Configuration.getSingleton().addRemoteUser(user);
        controller.add(user);
        list.addElement(user);
    }

    public void remove(User user) {
        Configuration.getSingleton().removeRemoteUser(user);
        list.removeElement(user);
    }

    public DefaultListModel<User> getList() {
        return list;
    }
}
