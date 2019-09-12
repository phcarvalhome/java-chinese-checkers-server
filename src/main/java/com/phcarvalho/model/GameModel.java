package com.phcarvalho.model;

import com.phcarvalho.model.communication.commandtemplate.remote.adapter.BoardRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.MainRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
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

        remoteUserList.forEach(remoteUser -> {

            try {
                mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser);
            } catch (RemoteException e) {
                e.printStackTrace();
                //TODO add handling...
            }
        });
        controller.add(remoteGame);
        list.addElement(remoteGame);
    }

    public void sendAll(User remoteUser) {
        Configuration.getSingleton().getGameMap().values()
                .forEach(gameConfiguration -> sendAll(remoteUser, gameConfiguration));
    }

    private void sendAll(User remoteUser, Game game) {
        AddGameCommand addGameCommand = new AddGameCommand(game, false);

        try {
            mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser);
        } catch (RemoteException e) {
            e.printStackTrace();
            //TODO add handling...
        }
    }

    public void remove(User user) {
        List<Game> gameList = Configuration.getSingleton().removeGame(user);

        gameList.forEach(game -> remove(game));
    }

    private void remove(Game game) {
        AddGameCommand addGameCommand = new AddGameCommand(game, true);
        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> {

            try {
                mainRemoteCommandTemplateAdapter.addGame(addGameCommand, remoteUser);
            } catch (RemoteException e) {
                e.printStackTrace();
                //TODO add handling...
            }
        });
        list.removeElement(game);
    }

    public void addPlayer(AddPlayerCommand addPlayerCommand) {
        Game remoteGame = addPlayerCommand.getGame();
        Game localGame = Configuration.getSingleton().getGame(remoteGame.getId());
        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        localGame.addPlayer(addPlayerCommand.getPlayer());
        remoteGame.getPlayerList().clear();
        remoteGame.getPlayerList().addAll(localGame.getPlayerList());
        remoteUserList.forEach(remoteUser -> {

            try {
                mainRemoteCommandTemplateAdapter.addPlayer(addPlayerCommand, remoteUser);
            } catch (RemoteException e) {
                e.printStackTrace();
                //TODO add handling...
            }
        });
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
        localGame.getRemoteUserList().forEach(remoteUser -> {

            try {
                boardRemoteCommandTemplateAdapter
                        .movePiece(movePieceCommand, remoteUser);
            } catch (RemoteException e) {
                e.printStackTrace();
                //TODO add handling...
            }
        });
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

        remoteUserList.forEach(remoteUser -> {

            try {
                mainRemoteCommandTemplateAdapter
                        .flagAsReady(new FlagAsReadyCommand(flagAsReadyCommand.getPlayer(), localGame), remoteUser);
            } catch (RemoteException e) {
                e.printStackTrace();
                //TODO add handling...
            }
        });
    }

    public DefaultListModel<Game> getList() {
        return list;
    }
}
