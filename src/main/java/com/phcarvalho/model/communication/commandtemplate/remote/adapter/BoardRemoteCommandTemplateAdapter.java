package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateStrategy;
import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class BoardRemoteCommandTemplateAdapter {

    private ICommandTemplateStrategy commandTemplateStrategy;

    public BoardRemoteCommandTemplateAdapter() {
        commandTemplateStrategy = DependencyFactory.getSingleton().get(ICommandTemplateStrategy.class);
    }

    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getBoardCommandTemplate().notifyVictory(notifyVictoryCommand);
    }

    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getBoardCommandTemplate().notifyWithdrawal(notifyWithdrawalCommand);
    }

    public void movePiece(MovePieceCommand movePieceCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getBoardCommandTemplate().movePiece(movePieceCommand);
    }
}
