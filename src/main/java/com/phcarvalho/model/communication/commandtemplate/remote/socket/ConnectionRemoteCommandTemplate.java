package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.model.communication.protocol.vo.command.DisconnectCommand;
import com.phcarvalho.model.communication.strategy.socket.SocketConnectionStrategy;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;
import java.util.Objects;

public class ConnectionRemoteCommandTemplate implements IConnectionCommandTemplate {

    private User remoteUser;
    private SocketConnectionStrategy socketConnectionStrategy;

    public ConnectionRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        socketConnectionStrategy = DependencyFactory.getSingleton().get(SocketConnectionStrategy.class);
    }

    @Override
    public void connect(ConnectCommand connectCommand) throws RemoteException {
        socketConnectionStrategy.send(connectCommand, remoteUser);
    }

    @Override
    public void disconnect(DisconnectCommand disconnectCommand) throws RemoteException {
        socketConnectionStrategy.send(disconnectCommand, remoteUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionRemoteCommandTemplate that = (ConnectionRemoteCommandTemplate) o;
        return Objects.equals(remoteUser, that.remoteUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteUser);
    }
}
