package com.phcarvalho.model.communication.connection;

import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.MenuModel;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;

import java.util.List;

public interface IConnectionHandlerStrategy {

    void startServer(Integer port);

    void send(ICommand remoteCommand, User remoteUser);

    void forward(List<User> remoteUserList, ICommand command);

    void setMenuModel(MenuModel menuModel);

    void setGameModel(GameModel gameModel);
}
