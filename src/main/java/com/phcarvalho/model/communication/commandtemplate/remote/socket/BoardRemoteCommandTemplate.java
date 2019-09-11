package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.IBoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

public class BoardRemoteCommandTemplate implements IBoardRemoteCommandTemplate {

    private IConnectionHandlerStrategy connectionHandlerStrategy;

    public BoardRemoteCommandTemplate() {
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionHandlerStrategy.class);
    }

    @Override
    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser) throws ConnectionException {
        connectionHandlerStrategy.send(notifyVictoryCommand, remoteUser);
    }

    @Override
    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser) throws ConnectionException {
        connectionHandlerStrategy.send(notifyWithdrawalCommand, remoteUser);
    }

    @Override
    public void movePiece(MovePieceCommand movePieceCommand, User remoteUser) throws ConnectionException {
        connectionHandlerStrategy.send(movePieceCommand, remoteUser);
    }
}
