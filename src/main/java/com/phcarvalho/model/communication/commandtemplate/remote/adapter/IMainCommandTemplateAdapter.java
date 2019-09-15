package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMainCommandTemplateAdapter {

    void addGame(AddGameCommand addGameCommand, User remoteUser);

    void addPlayer(AddPlayerCommand addPlayerCommand, User remoteUser);

    void flagAsReady(FlagAsReadyCommand flagAsReadyCommand, User remoteUser);
}
