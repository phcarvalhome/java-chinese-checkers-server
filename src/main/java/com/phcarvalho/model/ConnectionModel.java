package com.phcarvalho.model;

import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.controller.ConnectionController;
import com.phcarvalho.dependencyfactory.DependencyFactory;

import java.rmi.RemoteException;

public class ConnectionModel {

    private ConnectionController controller;
    private IConnectionStrategy connectionHandlerStrategy;

    public ConnectionModel(ConnectionController controller) {
        this.controller = controller;
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionStrategy.class);
    }

    public void startServer(Integer port) throws RemoteException {
        connectionHandlerStrategy.startServer(port);
    }

    public void startServerByCallback(User localUser) {
        Configuration.getSingleton().setLocalUser(localUser);
        controller.startServerByCallback(localUser);
    }
}
