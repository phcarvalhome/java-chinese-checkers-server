package com.phcarvalho.model.communication.strategy;

import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;
import java.util.List;

public interface IConnectionStrategy {

    void startServer(Integer port) throws RemoteException;

    void send(ICommand remoteCommand, User remoteUser) throws RemoteException;

    void forward(List<User> remoteUserList, ICommand command) throws RemoteException;

    void setMainModel(MainModel mainModel);
}
