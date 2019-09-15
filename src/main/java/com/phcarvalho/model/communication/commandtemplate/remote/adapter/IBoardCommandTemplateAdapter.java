package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBoardCommandTemplateAdapter {

    void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser);

    void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser);

    void movePiece(MovePieceCommand movePieceCommand, User remoteUser);
}
