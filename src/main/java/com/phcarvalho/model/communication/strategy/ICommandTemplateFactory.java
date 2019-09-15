package com.phcarvalho.model.communication.strategy;

import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.configuration.entity.User;

public interface ICommandTemplateFactory {

    IConnectionCommandTemplate buildConnection(User remoteUser);

    IBoardCommandTemplate buildBoard(User remoteUser);

    IChatCommandTemplate buildChat(User remoteUser);

    IMainCommandTemplate buildMain(User remoteUser);
}
