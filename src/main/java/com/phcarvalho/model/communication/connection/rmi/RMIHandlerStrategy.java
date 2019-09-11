package com.phcarvalho.model.communication.connection.rmi;

import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

import java.util.List;

public class RMIHandlerStrategy implements IConnectionHandlerStrategy {


    @Override
    public void startServer(Integer port) throws ConnectionException {

    }

    @Override
    public void send(ICommand remoteCommand, User remoteUser) throws ConnectionException {

    }

    @Override
    public void forward(List<User> remoteUserList, ICommand command) throws ConnectionException {

    }

    @Override
    public void setMainModel(MainModel mainModel) {

    }
}
