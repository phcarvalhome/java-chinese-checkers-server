package com.phcarvalho.controller;

import com.phcarvalho.model.ConnectionModel;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.view.ConnectionView;

import java.rmi.RemoteException;

public class ConnectionController {

    private ConnectionView view;
    private ConnectionModel model;

    public ConnectionController() {
    }

    public void startServer(Integer port) throws RemoteException {
        model.startServer(port);
    }

    public void startServerByCallback(User localUser) {
        view.startServerByCallback(localUser);
    }

    public void setView(ConnectionView view) {
        this.view = view;
    }

    public void setModel(ConnectionModel model) {
        this.model = model;
    }
}
