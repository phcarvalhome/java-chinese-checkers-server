package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.SendMessageCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.util.LogUtil;

import java.rmi.RemoteException;

public class ChatRemoteCommandTemplateAdapter extends AbstractCommandTemplateAdapter<IChatCommandTemplate>
        implements IChatCommandTemplateAdapter {

    private static final String CHAT_REMOTE_COMMAND = "Chat Remote Command";

    private ICommandTemplateFactory commandTemplateFactory;

    public ChatRemoteCommandTemplateAdapter() {
        super();
        commandTemplateFactory = DependencyFactory.getSingleton().get(ICommandTemplateFactory.class);
    }

    @Override
    protected IChatCommandTemplate buildCommandTemplate(User remoteUser) {
        return commandTemplateFactory.buildChat(remoteUser);
    }

    public void sendMessage(SendMessageCommand sendMessageCommand, User remoteUser) {

        try {
            get(remoteUser).sendMessage(sendMessageCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the send message command remote invocation!",
                    CHAT_REMOTE_COMMAND, e);
        }
    }
}
