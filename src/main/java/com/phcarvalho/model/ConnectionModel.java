package com.phcarvalho.model;

import com.phcarvalho.controller.ConnectionController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class ConnectionModel {

    private ConnectionController controller;
    private IConnectionStrategy connectionStrategy;

    public ConnectionModel(ConnectionController controller) {
        this.controller = controller;
        connectionStrategy = DependencyFactory.getSingleton().get(IConnectionStrategy.class);
    }

    public void startServer(User localUser) throws RemoteException {
        Configuration.getSingleton().setLocalUser(localUser);
        connectionStrategy.startServer(localUser);
    }
}
