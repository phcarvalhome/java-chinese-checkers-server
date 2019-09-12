package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionLocalCommandTemplate extends UnicastRemoteObject implements IConnectionCommandTemplate {

    private MainModel mainModel;

    public ConnectionLocalCommandTemplate() throws RemoteException {
        super();
        mainModel = DependencyFactory.getSingleton().get(MainModel.class);
    }

    @Override
    public void connect(ConnectCommand connectCommand) throws RemoteException {

    }
}
