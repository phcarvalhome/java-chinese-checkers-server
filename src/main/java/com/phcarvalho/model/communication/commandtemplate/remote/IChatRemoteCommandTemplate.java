package com.phcarvalho.model.communication.commandtemplate.remote;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.entity.User;

public interface IChatRemoteCommandTemplate {

    void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser);
}
