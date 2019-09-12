package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class MainRemoteCommandTemplate implements IMainCommandTemplate {

    private IConnectionStrategy connectionHandlerStrategy;
    private User remoteUser;

    public MainRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionStrategy.class);
    }

    @Override
    public void addGame(AddGameCommand addGameCommand) throws RemoteException {
        connectionHandlerStrategy.send(addGameCommand, remoteUser);
    }

    @Override
    public void addPlayer(AddPlayerCommand addPlayerCommand) throws RemoteException {
        connectionHandlerStrategy.send(addPlayerCommand, remoteUser);
    }

    @Override
    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand) throws RemoteException {
        connectionHandlerStrategy.send(flagAsReadyCommand, remoteUser);
    }
}
