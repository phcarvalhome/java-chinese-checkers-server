package com.phcarvalho.model.communication.strategy;

import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.model.configuration.entity.User;

public interface ICommandTemplateStrategy {

    CommandTemplateSet getCommandTemplateSet(User remoteUser);

    void deleteRemoteUser(User remoteUser);
}
