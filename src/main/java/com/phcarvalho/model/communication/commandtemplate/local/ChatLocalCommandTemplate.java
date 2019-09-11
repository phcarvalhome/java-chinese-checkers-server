package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;

public class ChatLocalCommandTemplate implements ILocalCommandTemplate {

    private MainModel mainModel;

    public ChatLocalCommandTemplate() {
        mainModel = DependencyFactory.getSingleton().get(MainModel.class);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand){
        mainModel.sendMessage(sendMessageCommand);
    }
}
