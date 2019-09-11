package com.phcarvalho.model.communication.connection;

import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

import java.util.List;

public interface IConnectionHandlerStrategy {

    void startServer(Integer port) throws ConnectionException;

    void send(ICommand remoteCommand, User remoteUser) throws ConnectionException;

    void forward(List<User> remoteUserList, ICommand command) throws ConnectionException;

    void setMainModel(MainModel mainModel);
}
