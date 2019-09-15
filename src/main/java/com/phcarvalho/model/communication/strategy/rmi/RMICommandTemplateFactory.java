package com.phcarvalho.model.communication.strategy.rmi;

import com.phcarvalho.model.communication.commandtemplate.IBoardCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IChatCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IConnectionCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.IMainCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.CommandTemplateEnum;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RMICommandTemplateFactory implements ICommandTemplateFactory {

    @Override
    public IConnectionCommandTemplate buildConnection(User remoteUser) {
        return (IConnectionCommandTemplate) lookup(remoteUser, CommandTemplateEnum.CONNECTION);
    }

    private Remote lookup(User remoteUser, CommandTemplateEnum commandTemplate){
        User localUser = Configuration.getSingleton().getLocalUser();
        String apiName = "rmi://" + localUser.getHost() + ":" + localUser.getPort() + "/" +
                remoteUser.getName().replace(" ", "") + "/" + commandTemplate.name();

        try {
            return Naming.lookup(apiName);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            //todo handler it
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    @Override
    public IBoardCommandTemplate buildBoard(User remoteUser) {
        return (IBoardCommandTemplate) lookup(remoteUser, CommandTemplateEnum.BOARD);
    }

    @Override
    public IChatCommandTemplate buildChat(User remoteUser) {
        return (IChatCommandTemplate) lookup(remoteUser, CommandTemplateEnum.CHAT);
    }

    @Override
    public IMainCommandTemplate buildMain(User remoteUser) {
        return (IMainCommandTemplate) lookup(remoteUser, CommandTemplateEnum.MAIN);
    }
}
