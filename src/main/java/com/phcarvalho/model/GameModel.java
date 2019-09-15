package com.phcarvalho.model;

import com.phcarvalho.model.communication.commandtemplate.remote.adapter.BoardRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.MainRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.*;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.vo.Player;
import com.phcarvalho.controller.GameController;
import com.phcarvalho.dependencyfactory.DependencyFactory;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.List;

public class GameModel {

    private GameController controller;
    private MainRemoteCommandTemplateAdapter mainRemoteCommandTemplateAdapter;
    private BoardRemoteCommandTemplateAdapter boardRemoteCommandTemplateAdapter;
    private DefaultListModel<Game> list;

    public GameModel(GameController controller) {
        this.controller = controller;
        mainRemoteCommandTemplateAdapter = DependencyFactory.getSingleton().get(MainRemoteCommandTemplateAdapter.class);
        boardRemoteCommandTemplateAdapter = DependencyFactory.getSingleton().get(BoardRemoteCommandTemplateAdapter.class);
        list = new DefaultListModel();
    }

    public void add(AddGameCommand addGameCommand) {
        Game remoteGame = addGameCommand.getGame();

        Configuration.getSingleton().addGame(remoteGame);

        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser));
        controller.add(remoteGame);
        list.addElement(remoteGame);
    }

    //TODO isso aqui nao é do select/add player?
//    public void connect(ConnectCommand connectCommand) {
//        User remoteUser = connectCommand.getSourceUser();
//        Configuration.getSingleton().getGameMap().values()
//                .forEach(game -> connect(remoteUser, game));
//    }
//
//    private void connect(User remoteUser, Game game) {
//        AddGameCommand addGameCommand = new AddGameCommand(game, false);
//
//        try {
//            mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            //TODO add handling...
//        }
//    }

    public void remove(User user) {
        List<Game> gameList = Configuration.getSingleton().removeGame(user);

        gameList.forEach(game -> remove(game));
    }

    private void remove(Game game) {
        AddGameCommand addGameCommand = new AddGameCommand(game, true);
        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser));
        list.removeElement(game);
    }

    public void addPlayer(AddPlayerCommand addPlayerCommand) {
        Game remoteGame = addPlayerCommand.getGame();
        Game localGame = Configuration.getSingleton().getGame(remoteGame.getId());
        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        localGame.addPlayer(addPlayerCommand.getPlayer());
        remoteGame.getPlayerList().clear();
        remoteGame.getPlayerList().addAll(localGame.getPlayerList());
        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplateAdapter.addPlayer(addPlayerCommand, remoteUser));
    }

    public void movePiece(MovePieceCommand movePieceCommand) {
        Game remoteGame = movePieceCommand.getGame();
        Game localGame = Configuration.getSingleton().getGame(remoteGame.getId());
        Player turnPlayer = localGame.getTurnPlayer();
        List<Player> playerList = localGame.getPlayerList();
        int turnPlayerIndex = playerList.indexOf(turnPlayer);
        int nextTurnPlayerIndex = (turnPlayerIndex + 1) % playerList.size();
        Player nextTurnPlayer = playerList.get(nextTurnPlayerIndex);

        remoteGame.setTurnPlayer(nextTurnPlayer);
        localGame.setTurnPlayer(nextTurnPlayer);
        localGame.getRemoteUserList().forEach(remoteUser -> boardRemoteCommandTemplateAdapter
                .movePiece(movePieceCommand, remoteUser));
    }

    public void flagAsReady(FlagAsReadyCommand flagAsReadyCommand) {
        Game remoteGame = flagAsReadyCommand.getGame();
        Game localGame = Configuration.getSingleton().getGame(remoteGame.getId());
        List<User> remoteUserList;

        localGame.addReadyPlayer(flagAsReadyCommand.getPlayer());

        if(localGame.isGameStarted())
            remoteUserList = Configuration.getSingleton().getRemoteUserList();
        else
            remoteUserList = localGame.getRemoteUserList();

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplateAdapter
                .flagAsReady(new FlagAsReadyCommand(flagAsReadyCommand.getPlayer(), localGame), remoteUser));
    }

    public DefaultListModel<Game> getList() {
        return list;
    }
}
