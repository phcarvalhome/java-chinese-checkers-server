package com.phcarvalho.model.communication.commandtemplate.remote;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.configuration.entity.User;

public interface IBoardRemoteCommandTemplate {

    void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser);

    void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser);

    void movePiece(MovePieceCommand movePieceCommand, User remoteUser);
}
