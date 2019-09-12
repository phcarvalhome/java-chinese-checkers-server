package com.phcarvalho.model.communication.commandtemplate.local;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatLocalCommandTemplate extends UnicastRemoteObject implements IChatCommandTemplate {

    private MainModel mainModel;

    public ChatLocalCommandTemplate() throws RemoteException {
        super();
        mainModel = DependencyFactory.getSingleton().get(MainModel.class);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand){
        mainModel.sendMessage(sendMessageCommand);
    }
}
