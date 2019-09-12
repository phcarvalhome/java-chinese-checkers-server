package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainLocalCommandTemplate extends UnicastRemoteObject implements IMainCommandTemplate {

    private GameModel gameModel;

    public MainLocalCommandTemplate() throws RemoteException {
        super();
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
    }

    public void addGame(AddGameCommand addGameCommand){
        gameModel.add(addGameCommand);
    }

    public void addPlayer(AddPlayerCommand addPlayerCommand) {
        gameModel.addPlayer(addPlayerCommand);
    }

    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand) {
        gameModel.flagAsReady(flagAsReadyCommand);
    }
}
