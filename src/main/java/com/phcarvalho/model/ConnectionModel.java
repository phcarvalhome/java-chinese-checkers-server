package com.phcarvalho.model;

import com.phcarvalho.controller.ConnectionController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

public class ConnectionModel {

    private ConnectionController controller;
    private IConnectionHandlerStrategy connectionHandlerStrategy;

    public ConnectionModel(ConnectionController controller) {
        this.controller = controller;
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionHandlerStrategy.class);
    }

    public void startServer(Integer port) throws ConnectionException {
        connectionHandlerStrategy.startServer(port);
    }

    public void startServerByCallback(User localUser) {
        Configuration.getSingleton().setLocalUser(localUser);
        controller.startServerByCallback(localUser);
    }
}
