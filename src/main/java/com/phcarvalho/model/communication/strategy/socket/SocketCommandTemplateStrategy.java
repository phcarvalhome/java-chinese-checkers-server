package com.phcarvalho.model.communication.strategy.socket;

import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.BoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.ChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.MainRemoteCommandTemplate;
import com.phcarvalho.model.communication.strategy.AbstractCommandTemplateStrategy;
import com.phcarvalho.model.configuration.entity.User;

public class SocketCommandTemplateStrategy extends AbstractCommandTemplateStrategy {

    @Override
    protected CommandTemplateSet buildCommandTemplateSet(User remoteUser) {
        BoardRemoteCommandTemplate boardRemoteCommandTemplate = new BoardRemoteCommandTemplate(remoteUser);
        ChatRemoteCommandTemplate chatRemoteCommandTemplate = new ChatRemoteCommandTemplate(remoteUser);
        MainRemoteCommandTemplate mainRemoteCommandTemplate = new MainRemoteCommandTemplate(remoteUser);

        return new CommandTemplateSet(boardRemoteCommandTemplate, chatRemoteCommandTemplate, mainRemoteCommandTemplate);
    }
}
