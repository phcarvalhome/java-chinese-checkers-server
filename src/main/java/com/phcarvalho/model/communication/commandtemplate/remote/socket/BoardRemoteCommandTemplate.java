package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.communication.strategy.socket.SocketConnectionStrategy;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;
import java.util.Objects;

public class BoardRemoteCommandTemplate implements IBoardCommandTemplate {

    private User remoteUser;
    private SocketConnectionStrategy socketConnectionStrategy;

    public BoardRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        socketConnectionStrategy = DependencyFactory.getSingleton().get(SocketConnectionStrategy.class);
    }

    @Override
    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand) throws RemoteException {
        socketConnectionStrategy.send(notifyVictoryCommand, remoteUser);
    }

    @Override
    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand) throws RemoteException {
        socketConnectionStrategy.send(notifyWithdrawalCommand, remoteUser);
    }

    @Override
    public void movePiece(MovePieceCommand movePieceCommand) throws RemoteException {
        socketConnectionStrategy.send(movePieceCommand, remoteUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRemoteCommandTemplate that = (BoardRemoteCommandTemplate) o;
        return Objects.equals(remoteUser, that.remoteUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteUser);
    }
}
