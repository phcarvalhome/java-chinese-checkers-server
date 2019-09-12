package com.phcarvalho.model.communication.commandtemplate.remote.socket;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class ChatRemoteCommandTemplate implements IChatCommandTemplate {

    private IConnectionStrategy connectionHandlerStrategy;
    private User remoteUser;

    public ChatRemoteCommandTemplate(User remoteUser) {
        this.remoteUser = remoteUser;
        connectionHandlerStrategy = DependencyFactory.getSingleton().get(IConnectionStrategy.class);
    }

    @Override
    public void sendMessage(SendMessageCommand sendMessageCommand) throws RemoteException {
        connectionHandlerStrategy.send(sendMessageCommand, remoteUser);
    }
}
