package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.configuration.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatCommandTemplateAdapter {

    void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser);
}
