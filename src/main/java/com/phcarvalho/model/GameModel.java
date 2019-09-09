package com.phcarvalho.model;

import com.phcarvalho.controller.GameController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.IBoardRemoteCommandTemplate;
import com.phcarvalho.model.communication.commandtemplate.remote.IMainRemoteCommandTemplate;
import com.phcarvalho.model.communication.protocol.vo.command.MovePieceCommand;
import com.phcarvalho.model.communication.protocol.vo.command.FlagAsReadyCommand;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.model.communication.protocol.vo.command.AddGameCommand;
import com.phcarvalho.model.communication.protocol.vo.command.AddPlayerCommand;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.vo.Player;

import javax.swing.*;
import java.util.List;

public class GameModel {

    private GameController controller;
    private IMainRemoteCommandTemplate mainRemoteCommandTemplate;
    private IBoardRemoteCommandTemplate boardRemoteCommandTemplate;
    private DefaultListModel<Game> list;

    public GameModel(GameController controller) {
        this.controller = controller;
        mainRemoteCommandTemplate = DependencyFactory.getSingleton().get(IMainRemoteCommandTemplate.class);
        boardRemoteCommandTemplate = DependencyFactory.getSingleton().get(IBoardRemoteCommandTemplate.class);
        list = new DefaultListModel();
    }

    public void add(AddGameCommand addGameCommand) {
        Game remoteGame = addGameCommand.getGame();

        Configuration.getSingleton().addGame(remoteGame);

        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplate.addGame(addGameCommand, remoteUser));
        controller.add(remoteGame);
        list.addElement(remoteGame);
    }

    public void sendAll(User remoteUser) {
        Configuration.getSingleton().getGameMap().values()
                .forEach(gameConfiguration -> sendAll(remoteUser, gameConfiguration));
    }

    private void sendAll(User remoteUser, Game game) {
        AddGameCommand addGameCommand = new AddGameCommand(game, false);

        mainRemoteCommandTemplate.addGame(addGameCommand, remoteUser);
    }

    public void remove(User user) {
        List<Game> gameList = Configuration.getSingleton().removeGame(user);

        gameList.forEach(game -> remove(game));
    }

    private void remove(Game game) {
        AddGameCommand addGameCommand = new AddGameCommand(game, true);
        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> {
            mainRemoteCommandTemplate.addGame(addGameCommand, remoteUser);
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
        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplate.addPlayer(addPlayerCommand, remoteUser));
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
        localGame.getRemoteUserList().forEach(remoteUser -> boardRemoteCommandTemplate
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

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplate
                .flagAsReady(new FlagAsReadyCommand(flagAsReadyCommand.getPlayer(), localGame), remoteUser));
    }

    public DefaultListModel<Game> getList() {
        return list;
    }
}
