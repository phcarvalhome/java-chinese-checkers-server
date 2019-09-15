package com.phcarvalho.model.communication.commandtemplate;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;

import java.rmi.RemoteException;

public interface IChatCommandTemplate extends ICommandTemplate {

    void sendMessage(SendMessageCommand sendMessageCommand) throws RemoteException;
}
