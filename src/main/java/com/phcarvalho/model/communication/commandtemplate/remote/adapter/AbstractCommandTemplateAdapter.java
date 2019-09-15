package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.model.configuration.entity.User;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommandTemplateAdapter<T extends Remote> {

    private Map<User, T> userMap;

    protected AbstractCommandTemplateAdapter() {
        userMap = new HashMap<>();
    }

    final protected T get(User remoteUser){
        T commandTemplate = userMap.get(remoteUser);

        if(commandTemplate == null){
            commandTemplate = buildCommandTemplate(remoteUser);
            userMap.put(remoteUser, commandTemplate);
        }

        return commandTemplate;
    }

    protected abstract T buildCommandTemplate(User remoteUser);
}
