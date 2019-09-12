package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class BoardRemoteCommandTemplate implements IBoardCommandTemplate {

    private IConnectionStrategy connectionHandlerStrategy;
    private User remoteUser;

    public BoardRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionStrategy.class);
    }

    @Override
    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand) throws RemoteException {
        connectionHandlerStrategy.send(notifyVictoryCommand, remoteUser);
    }

    @Override
    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand) throws RemoteException {
        connectionHandlerStrategy.send(notifyWithdrawalCommand, remoteUser);
    }

    @Override
    public void movePiece(MovePieceCommand movePieceCommand) throws RemoteException {
        connectionHandlerStrategy.send(movePieceCommand, remoteUser);
    }
}
