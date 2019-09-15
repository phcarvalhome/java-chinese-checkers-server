package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.util.LogUtil;

import java.rmi.RemoteException;

public class MainRemoteCommandTemplateAdapter extends AbstractCommandTemplateAdapter<IMainCommandTemplate>
        implements IMainCommandTemplateAdapter {

    public static final String MAIN_REMOTE_COMMAND = "Main Remote Command";

    private ICommandTemplateFactory commandTemplateFactory;

    public MainRemoteCommandTemplateAdapter() {
        super();
        commandTemplateFactory = DependencyFactory.getSingleton().get(ICommandTemplateFactory.class);
    }

    @Override
    protected IMainCommandTemplate buildCommandTemplate(User remoteUser) {
        return commandTemplateFactory.buildMain(remoteUser);
    }

    public void addGame(AddGameCommand addGameCommand, User remoteUser) {

        try {
            get(remoteUser).addGame(addGameCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the add game command remote invocation!",
                    MAIN_REMOTE_COMMAND, e);
        }
    }

    public void addPlayer(AddPlayerCommand addPlayerCommand, User remoteUser) {

        try {
            get(remoteUser).addPlayer(addPlayerCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the add player command remote invocation!",
                    MAIN_REMOTE_COMMAND, e);
        }
    }

    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand, User remoteUser) {

        try {
            get(remoteUser).flagAsReady(flagAsReadyCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the flag as ready command remote invocation!",
                    MAIN_REMOTE_COMMAND, e);
        }
    }
}
