package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.ConnectionModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.DisconnectCommand;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionLocalCommandTemplate extends UnicastRemoteObject implements IConnectionCommandTemplate {

    private ConnectedUserModel connectedUserModel;

    public ConnectionLocalCommandTemplate() throws RemoteException {
        super();
        connectedUserModel = DependencyFactory.getSingleton().get(ConnectedUserModel.class);
    }

    @Override
    public void connect(ConnectCommand connectCommand) throws RemoteException {
        connectedUserModel.add(connectCommand);
    }

    @Override
    public void disconnect(DisconnectCommand disconnectCommand) throws RemoteException {
        connectedUserModel.remove(disconnectCommand);
    }
}
