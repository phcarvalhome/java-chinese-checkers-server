package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.IMainRemoteCommandTemplate;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.configuration.entity.User;

public class MainRemoteCommandTemplate implements IMainRemoteCommandTemplate {

    private IConnectionHandlerStrategy connectionHandlerStrategy;

    public MainRemoteCommandTemplate() {
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionHandlerStrategy.class);
    }

    @Override
    public void addGame(AddGameCommand addGameCommand, User remoteUser){
        connectionHandlerStrategy.send(addGameCommand, remoteUser);
    }

    @Override
    public void addPlayer(AddPlayerCommand addPlayerCommand, User remoteUser) {
        connectionHandlerStrategy.send(addPlayerCommand, remoteUser);
    }

    @Override
    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand, User remoteUser) {
        connectionHandlerStrategy.send(flagAsReadyCommand, remoteUser);
    }
}
