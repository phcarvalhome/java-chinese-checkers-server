package com.phcarvalho.model.communication.commandtemplate.remote;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;

public interface IChatRemoteCommandTemplate {

    void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser) throws ConnectionException;
}
