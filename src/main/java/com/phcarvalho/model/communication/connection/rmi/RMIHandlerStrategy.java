package com.phcarvalho.model.communication.connection.rmi;

import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.MenuModel;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;

import java.util.List;

public class RMIHandlerStrategy implements IConnectionHandlerStrategy {

    @Override
    public void startServer(Integer port) {

    }

    @Override
    public void send(ICommand remoteCommand, User remoteUser) {

    }

    @Override
    public void forward(List<User> remoteUserList, ICommand command) {

    }

    @Override
    public void setMenuModel(MenuModel menuModel) {

    }

    @Override
    public void setGameModel(GameModel gameModel) {

    }
}
