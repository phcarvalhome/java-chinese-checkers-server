package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.model.communication.protocol.vo.command.DisconnectCommand;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnectionCommandTemplateAdapter {

    void connect(ConnectCommand connectCommand, User remoteUser);

    void disconnect(DisconnectCommand disconnectCommand, User remoteUser);
}
