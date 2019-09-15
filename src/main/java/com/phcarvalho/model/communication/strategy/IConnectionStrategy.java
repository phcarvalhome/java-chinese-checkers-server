package com.phcarvalho.model.communication.strategy;

import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public interface IConnectionStrategy {

    void startServer(User localUser) throws RemoteException;
}
