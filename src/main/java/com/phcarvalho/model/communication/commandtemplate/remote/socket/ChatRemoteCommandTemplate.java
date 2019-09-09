package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.IChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.entity.User;

public class ChatRemoteCommandTemplate implements IChatRemoteCommandTemplate {

    private IConnectionHandlerStrategy connectionHandlerStrategy;

    public ChatRemoteCommandTemplate() {
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionHandlerStrategy.class);
    }

    @Override
    public void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser){
        connectionHandlerStrategy.send(sendMessageCommand, remoteUser);
    }
}
