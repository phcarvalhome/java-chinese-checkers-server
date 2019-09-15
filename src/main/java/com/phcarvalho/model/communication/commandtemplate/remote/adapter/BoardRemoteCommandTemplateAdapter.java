package com.phcarvalho.model.communication.commandtemplate.remote.adapter;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyVictoryCommand;
import com.phcarvalho.model.communication.protocol.vo.command.NotifyWithdrawalCommand;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.util.LogUtil;

import java.rmi.RemoteException;

public class BoardRemoteCommandTemplateAdapter extends AbstractCommandTemplateAdapter<IBoardCommandTemplate>
        implements IBoardCommandTemplateAdapter {

    private static final String BOARD_REMOTE_COMMAND = "Board Remote Command";

    private ICommandTemplateFactory commandTemplateFactory;

    public BoardRemoteCommandTemplateAdapter() {
        super();
        commandTemplateFactory = DependencyFactory.getSingleton().get(ICommandTemplateFactory.class);
    }

    @Override
    protected IBoardCommandTemplate buildCommandTemplate(User remoteUser) {
        return commandTemplateFactory.buildBoard(remoteUser);
    }

    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand, User remoteUser) {

        try {
            get(remoteUser).notifyVictory(notifyVictoryCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the notify victory command remote invocation!",
                    BOARD_REMOTE_COMMAND, e);
        }
    }

    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand, User remoteUser) {

        try {
            get(remoteUser).notifyWithdrawal(notifyWithdrawalCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the notify withdrawal command remote invocation!",
                    BOARD_REMOTE_COMMAND, e);
        }
    }

    public void movePiece(MovePieceCommand movePieceCommand, User remoteUser) {

        try {
            get(remoteUser).movePiece(movePieceCommand);
        } catch (RemoteException e) {
            LogUtil.logError("Error in the move piece command remote invocation!",
                    BOARD_REMOTE_COMMAND, e);
        }
    }
}
