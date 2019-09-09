package com.phcarvalho.dependencyfactory;

import com.phcarvalho.controller.ConnectedUserController;
import com.phcarvalho.controller.GameController;
import com.phcarvalho.controller.MainController;
import com.phcarvalho.controller.MenuController;
import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.MenuModel;
import com.phcarvalho.model.communication.commandtemplate.local.socket.CommandInvoker;
import com.phcarvalho.model.communication.commandtemplate.remote.IBoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.IChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.IMainRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.BoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.ChatRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.socket.MainRemoteCommandTemplate;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.connection.socket.SocketHandlerStrategy;
import com.phcarvalho.view.ConnectedUserView;
import com.phcarvalho.view.GameView;
import com.phcarvalho.view.MainView;
import com.phcarvalho.view.MenuView;
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

    private Map<Class<?>, Object> dependencyMap;

    private DependencyFactory() {
        dependencyMap = new HashMap<>();
    }

    public void build(){
        dependencyMap.put(DialogUtil.class, new DialogUtil());
        dependencyMap.put(IConnectionHandlerStrategy.class, new SocketHandlerStrategy());
        dependencyMap.put(CommandInvoker.class, new CommandInvoker());

        dependencyMap.put(IMainRemoteCommandTemplate.class, new MainRemoteCommandTemplate());
        dependencyMap.put(IBoardRemoteCommandTemplate.class, new BoardRemoteCommandTemplate());
        dependencyMap.put(IChatRemoteCommandTemplate.class, new ChatRemoteCommandTemplate());

        buildConnectedUserMVC();
        buildGameMVC();
        buildMenuMVC();
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
        get(IConnectionHandlerStrategy.class).setGameModel(gameModel);
    }

    private void buildMenuMVC() {
        MenuController menuController = new MenuController();
        MenuView menuView = new MenuView(menuController);
        MenuModel menuModel = new MenuModel(menuController);

        menuController.setView(menuView);
        menuController.setModel(menuModel);
        dependencyMap.put(MenuView.class, menuView);
        dependencyMap.put(MenuModel.class, menuModel);
        get(IConnectionHandlerStrategy.class).setMenuModel(menuModel);
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
    }

    public <T> T get(Class<T> type){
        Object dependency = dependencyMap.get(type);

        if(dependency == null)
            throw new RuntimeException("The dependency is null! Type: " + type);

        return (T) dependency;
    }
}