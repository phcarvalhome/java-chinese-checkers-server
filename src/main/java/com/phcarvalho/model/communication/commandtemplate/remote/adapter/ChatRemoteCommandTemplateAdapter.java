package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateStrategy;
import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.RemoteException;

public class ChatRemoteCommandTemplateAdapter {

    private ICommandTemplateStrategy commandTemplateStrategy;

    public ChatRemoteCommandTemplateAdapter() {
        commandTemplateStrategy = DependencyFactory.getSingleton().get(ICommandTemplateStrategy.class);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser) throws RemoteException {
        CommandTemplateSet commandTemplateSet = commandTemplateStrategy.getCommandTemplateSet(remoteUser);

        commandTemplateSet.getChatCommandTemplate().sendMessage(sendMessageCommand);
    }
}
