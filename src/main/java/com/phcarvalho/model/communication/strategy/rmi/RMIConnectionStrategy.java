package com.phcarvalho.model.communication.strategy.rmi;

import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.local.BoardLocalCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.local.ChatLocalCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.local.ConnectionLocalCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.local.MainLocalCommandTemplate;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.model.configuration.entity.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class RMIConnectionStrategy implements IConnectionStrategy {

    @Override
    public void startServer(Integer port) throws RemoteException {
        register("SYSTEM", port);
    }

    private void register(String userName, Integer port) throws RemoteException {
        String host = "//localhost/" + userName + "/";
        BoardLocalCommandTemplate boardLocalCommandTemplate = new BoardLocalCommandTemplate();
        String boardLocalCommandTemplateAPIName = host + boardLocalCommandTemplate.getClass().getSimpleName();
        ChatLocalCommandTemplate chatLocalCommandTemplate = new ChatLocalCommandTemplate();
        String chatLocalCommandTemplateAPIName = host + chatLocalCommandTemplate.getClass().getSimpleName();
        MainLocalCommandTemplate mainLocalCommandTemplate = new MainLocalCommandTemplate();
        String mainLocalCommandTemplateAPIName = host + mainLocalCommandTemplate.getClass().getSimpleName();
        ConnectionLocalCommandTemplate connectionLocalCommandTemplate = new ConnectionLocalCommandTemplate();
        String connectionLocalCommandTemplateAPIName = host + connectionLocalCommandTemplate.getClass().getSimpleName();
        List<String> apiNameList = new ArrayList<>();

        apiNameList.add(boardLocalCommandTemplateAPIName);
        apiNameList.add(chatLocalCommandTemplateAPIName);
        apiNameList.add(mainLocalCommandTemplateAPIName);
        apiNameList.add(connectionLocalCommandTemplateAPIName);

        try {
            LocateRegistry.createRegistry(9999);
//        Naming.rebind(boardLocalCommandTemplateAPIName, boardLocalCommandTemplate);
//        Naming.rebind(chatLocalCommandTemplateAPIName, chatLocalCommandTemplate);
//        Naming.rebind(mainLocalCommandTemplateAPIName, mainLocalCommandTemplate);
        Naming.rebind("rmi://192.168.56.1:9999/test", connectionLocalCommandTemplate);
            Registry registry = LocateRegistry.getRegistry(9999);
            System.out.println(registry);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(ICommand remoteCommand, User remoteUser) throws RemoteException {

    }

    @Override
    public void forward(List<User> remoteUserList, ICommand command) throws RemoteException {

    }

    @Override
    public void setMainModel(MainModel mainModel) {

    }
}
