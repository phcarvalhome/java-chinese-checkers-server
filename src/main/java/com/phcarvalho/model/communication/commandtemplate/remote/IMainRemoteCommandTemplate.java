package com.phcarvalho.model.communication.commandtemplate.remote;

import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

public interface IMainRemoteCommandTemplate {

    void addGame(AddGameCommand addGameCommand, User remoteUser) throws ConnectionException;

    void addPlayer(AddPlayerCommand addPlayerCommand, User remoteUser) throws ConnectionException;

    void flagAsReady(FlagAsReadyCommand flagAsReadyCommand, User remoteUser) throws ConnectionException;
}
