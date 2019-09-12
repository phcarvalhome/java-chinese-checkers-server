package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateStrategy;
import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class MainRemoteCommandTemplateAdapter {

    private ICommandTemplateStrategy commandTemplateStrategy;

    public MainRemoteCommandTemplateAdapter() {
        commandTemplateStrategy = DependencyFactory.getSingleton().get(ICommandTemplateStrategy.class);
    }

    public void addGame(AddGameCommand addGameCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getMainCommandTemplate().addGame(addGameCommand);
    }

    public void addPlayer(AddPlayerCommand addPlayerCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getMainCommandTemplate().addPlayer(addPlayerCommand);
    }

    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getMainCommandTemplate().flagAsReady(flagAsReadyCommand);
    }
}
