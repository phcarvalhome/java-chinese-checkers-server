package com.phcarvalho.dependencyfactory;

import com.phcarvalho.controller.ConnectedUserController;
import com.phcarvalho.controller.ConnectionController;
import com.phcarvalho.controller.GameController;
import com.phcarvalho.controller.MainController;
import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.ConnectionModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.commandtemplate.local.socket.CommandInvoker;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.BoardRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.ChatRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.ConnectionRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.MainRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.strategy.ICommandTemplateFactory;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.model.communication.strategy.factory.ICommunicationStrategy;
import com.phcarvalho.model.communication.strategy.socket.SocketConnectionStrategy;
import com.phcarvalho.view.ConnectedUserView;
import com.phcarvalho.view.ConnectionView;
import com.phcarvalho.view.GameView;
import com.phcarvalho.view.MainView;
import com.phcarvalho.view.util.DialogUtil;

import java.util.HashMap;
import java.util.Map;

public class DependencyFactory {

    private static DependencyFactory singleton;

    public static DependencyFactory getSingleton(){

        if(singleton == null)
            singleton = new DependencyFactory();

        return singleton;
    }

    private ICommunicationStrategy communicationStrategyFactory;
    private Map<Class<?>, Object> dependencyMap;

    private DependencyFactory() {
        dependencyMap = new HashMap<>();
    }

    public void build(){
        dependencyMap.put(DialogUtil.class, new DialogUtil());
        dependencyMap.put(SocketConnectionStrategy.class, new SocketConnectionStrategy());
        dependencyMap.put(IConnectionStrategy.class, communicationStrategyFactory.buildConnectionStrategy());
        dependencyMap.put(ICommandTemplateFactory.class, communicationStrategyFactory.buildCommandTemplateStrategy());
        dependencyMap.put(CommandInvoker.class, new CommandInvoker());

        dependencyMap.put(ConnectionRemoteCommandTemplateAdapter.class, new ConnectionRemoteCommandTemplateAdapter());
        dependencyMap.put(MainRemoteCommandTemplateAdapter.class, new MainRemoteCommandTemplateAdapter());
        dependencyMap.put(BoardRemoteCommandTemplateAdapter.class, new BoardRemoteCommandTemplateAdapter());
        dependencyMap.put(ChatRemoteCommandTemplateAdapter.class, new ChatRemoteCommandTemplateAdapter());

        buildConnectedUserMVC();
        buildGameMVC();
        buildConnectionMVC();
        buildMainMVC();

        get(CommandInvoker.class).buildCommandObserverMap();
    }

    private void buildConnectedUserMVC() {
        ConnectedUserController connectedUserController = new ConnectedUserController();
        ConnectedUserView connectedUserView = new ConnectedUserView(connectedUserController);
        ConnectedUserModel connectedUserModel = new ConnectedUserModel(connectedUserController);

        connectedUserController.setView(connectedUserView);
        connectedUserController.setModel(connectedUserModel);
        connectedUserController.initializeList();
        dependencyMap.put(ConnectedUserView.class, connectedUserView);
        dependencyMap.put(ConnectedUserModel.class, connectedUserModel);
    }

    private void buildGameMVC() {
        GameController gameController = new GameController();
        GameView gameView = new GameView(gameController);
        GameModel gameModel = new GameModel(gameController);

        gameController.setView(gameView);
        gameController.setModel(gameModel);
        gameController.initializeList();
        dependencyMap.put(GameView.class, gameView);
        dependencyMap.put(GameModel.class, gameModel);
//        get(IConnectionHandlerStrategy.class).setGameModel(gameModel);
    }

    private void buildConnectionMVC() {
        ConnectionController connectionController = new ConnectionController();
        ConnectionView connectionView = new ConnectionView(connectionController);
        ConnectionModel connectionModel = new ConnectionModel(connectionController);

        connectionController.setView(connectionView);
        connectionController.setModel(connectionModel);
        dependencyMap.put(ConnectionView.class, connectionView);
        dependencyMap.put(ConnectionModel.class, connectionModel);
//        get(IConnectionHandlerStrategy.class).setConnectionModel(connectionModel);
    }

    private void buildMainMVC() {
        MainController mainController = new MainController();
        MainView mainView = new MainView(mainController);
        MainModel mainModel = new MainModel(mainController);

        mainController.setView(mainView);
        mainController.setModel(mainModel);
        dependencyMap.put(MainView.class, mainView);
        dependencyMap.put(MainModel.class, mainModel);
        get(DialogUtil.class).setMainView(mainView);
//        get(IConnectionStrategy.class).setMainModel(mainModel);
        //TODO talvez fazer o set de cada model que foi colocado lá...
    }

    public <T> T get(Class<T> classType){
        Object dependency = dependencyMap.get(classType);

        if(dependency == null)
            throw new RuntimeException("The dependency is null! Type: " + classType);

        return (T) dependency;
    }

    public void setCommunicationStrategyFactory(ICommunicationStrategy communicationStrategyFactory) {
        this.communicationStrategyFactory = communicationStrategyFactory;
    }
}
