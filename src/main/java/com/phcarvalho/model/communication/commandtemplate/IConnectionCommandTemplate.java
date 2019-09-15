package com.phcarvalho.model.communication.commandtemplate;

import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;

import java.rmi.RemoteException;

public interface IConnectionCommandTemplate extends ICommandTemplate {

    void connect(ConnectCommand connectCommand) throws RemoteException;
}
