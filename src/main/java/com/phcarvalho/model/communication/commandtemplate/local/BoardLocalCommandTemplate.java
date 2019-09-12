package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BoardLocalCommandTemplate extends UnicastRemoteObject implements IBoardCommandTemplate {

    private GameModel gameModel;

    public BoardLocalCommandTemplate() throws RemoteException {
        super();
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
    }

    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand){

    }

    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand){

    }

    public void movePiece(MovePieceCommand movePieceCommand){
        gameModel.movePiece(movePieceCommand);
    }
}
