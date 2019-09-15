package com.phcarvalho.model.communication.commandtemplate;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;

import java.rmi.RemoteException;

public interface IBoardCommandTemplate extends ICommandTemplate {

    void notifyVictory(NotifyVictoryCommand notifyVictoryCommand) throws RemoteException;

    void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand) throws RemoteException;

    void movePiece(MovePieceCommand movePieceCommand) throws RemoteException;
}
