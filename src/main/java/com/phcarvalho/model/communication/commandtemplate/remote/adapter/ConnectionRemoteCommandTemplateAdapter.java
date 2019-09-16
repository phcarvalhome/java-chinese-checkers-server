package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.ConnectCommand;
import com.phcarvalho.model.communication.protocol.vo.command.DisconnectCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.util.LogUtil;

import java.rmi.RemoteException;

public class ConnectionRemoteCommandTemplateAdapter extends AbstractCommandTemplateAdapter<IConnectionCommandTemplate>
        implements IConnectionCommandTemplateAdapter {

    public static final String CONNECTION_REMOTE_COMMAND = "Connection Remote Command";

    private ICommandTemplateFactory commandTemplateFactory;

    public ConnectionRemoteCommandTemplateAdapter() {
        super();
        commandTemplateFactory = DependencyFactory.getSingleton().get(ICommandTemplateFactory.class);
    }

    @Override
    protected IConnectionCommandTemplate buildCommandTemplate(User remoteUser) {
        return commandTemplateFactory.buildConnection(remoteUser);
    }

    @Override
    public void connect(ConnectCommand connectCommand, User remoteUser) {

        try {
            get(remoteUser).connect(connectCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the connect command remote invocation!", CONNECTION_REMOTE_COMMAND, e);
        }
    }

    @Override
    public void disconnect(DisconnectCommand disconnectCommand, User remoteUser) {

        try {
            get(remoteUser).disconnect(disconnectCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the disconnect command remote invocation!", CONNECTION_REMOTE_COMMAND, e);
        }
    }
}
