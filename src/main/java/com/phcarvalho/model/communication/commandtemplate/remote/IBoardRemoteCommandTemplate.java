package com.phcarvalho.model.communication.commandtemplate.remote;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

public interface IBoardRemoteCommandTemplate {

    void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser) throws ConnectionException;

    void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser) throws ConnectionException;

    void movePiece(MovePieceCommand movePieceCommand, User remoteUser) throws ConnectionException;
}
