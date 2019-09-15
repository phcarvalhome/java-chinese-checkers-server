package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.ConnectionModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionLocalCommandTemplate extends UnicastRemoteObject implements IConnectionCommandTemplate {

    private GameModel gameModel;
    private ConnectedUserModel connectedUserModel;

    public ConnectionLocalCommandTemplate() throws RemoteException {
        super();
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
        connectedUserModel = DependencyFactory.getSingleton().get(ConnectedUserModel.class);
    }

    @Override
    public void connect(ConnectCommand connectCommand) throws RemoteException {
//        gameModel.connect(connectCommand);
        connectedUserModel.add(connectCommand);
    }
}
