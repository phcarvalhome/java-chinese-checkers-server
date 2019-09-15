package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.communication.strategy.socket.SocketConnectionStrategy;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;
import java.util.Objects;

public class MainRemoteCommandTemplate implements IMainCommandTemplate {

    private User remoteUser;
    private SocketConnectionStrategy socketConnectionStrategy;

    public MainRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        socketConnectionStrategy = DependencyFactory.getSingleton().get(SocketConnectionStrategy.class);
    }

    @Override
    public void addGame(AddGameCommand addGameCommand) throws RemoteException {
        socketConnectionStrategy.send(addGameCommand, remoteUser);
    }

    @Override
    public void addPlayer(AddPlayerCommand addPlayerCommand) throws RemoteException {
        socketConnectionStrategy.send(addPlayerCommand, remoteUser);
    }

    @Override
    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand) throws RemoteException {
        socketConnectionStrategy.send(flagAsReadyCommand, remoteUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainRemoteCommandTemplate that = (MainRemoteCommandTemplate) o;
        return Objects.equals(remoteUser, that.remoteUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteUser);
    }
}
