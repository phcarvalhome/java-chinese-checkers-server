package com.phcarvalho.model.communication.strategy;

import com.phcarvalho.model.communication.strategy.vo.CommandTemplateSet;
import com.phcarvalho.model.configuration.entity.User;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommandTemplateStrategy implements ICommandTemplateStrategy {

    private Map<User, CommandTemplateSet> commandTemplateSetMap;

    public AbstractCommandTemplateStrategy() {
        commandTemplateSetMap = new HashMap<>();
    }

    @Override
    final public CommandTemplateSet getCommandTemplateSet(User remoteUser) {
        CommandTemplateSet commandTemplateSet = commandTemplateSetMap.get(remoteUser);

        if(commandTemplateSet == null){
            commandTemplateSet = buildCommandTemplateSet(remoteUser);
            commandTemplateSetMap.put(remoteUser, commandTemplateSet);
        }

        return commandTemplateSet;
    }

    @Override
    public void deleteRemoteUser(User remoteUser) {
        commandTemplateSetMap.remove(remoteUser);
    }

    protected abstract CommandTemplateSet buildCommandTemplateSet(User remoteUser);
}
