package com.phcarvalho.model.communication.strategy.socket;

import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.BoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.ChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.ConnectionRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.MainRemoteCommandTemplate;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.entity.User;

public class SocketCommandTemplateFactory implements ICommandTemplateFactory {

    @Override
    public IConnectionCommandTemplate buildConnection(User remoteUser) {
        return new ConnectionRemoteCommandTemplate(remoteUser);
    }

    @Override
    public IBoardCommandTemplate buildBoard(User remoteUser) {
        return new BoardRemoteCommandTemplate(remoteUser);
    }

    @Override
    public IChatCommandTemplate buildChat(User remoteUser) {
        return new ChatRemoteCommandTemplate(remoteUser);
    }

    @Override
    public IMainCommandTemplate buildMain(User remoteUser) {
        return new MainRemoteCommandTemplate(remoteUser);
    }
}
