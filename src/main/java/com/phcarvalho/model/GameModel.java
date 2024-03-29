package com.phcarvalho.model;

import com.phcarvalho.controller.GameController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.BoardRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.commandtemplate.remote.adapter.MainRemoteCommandTemplateAdapter;
import com.phcarvalho.model.communication.protocol.vo.command.*;
import com.phcarvalho.model.configuration.Configuration;
import com.phcarvalho.model.configuration.entity.Game;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.vo.Player;

import javax.swing.*;
import java.util.List;

public class GameModel {

    private GameController controller;
    private MainRemoteCommandTemplateAdapter mainRemoteCommandTemplateAdapter;
    private BoardRemoteCommandTemplateAdapter boardRemoteCommandTemplateAdapter;
    private ConnectedUserModel connectedUserModel;
    private DefaultListModel<Game> list;

    public GameModel(GameController controller) {
        this.controller = controller;
        mainRemoteCommandTemplateAdapter = DependencyFactory.getSingleton().get(MainRemoteCommandTemplateAdapter.class);
        boardRemoteCommandTemplateAdapter = DependencyFactory.getSingleton().get(BoardRemoteCommandTemplateAdapter.class);
        connectedUserModel = DependencyFactory.getSingleton().get(ConnectedUserModel.class);
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

    public void addOrRemovePlayer(AddPlayerCommand addPlayerCommand) {

        if(addPlayerCommand.isDelete())
            removePlayer(addPlayerCommand);
        else
            addPlayer(addPlayerCommand);
    }

    private void removePlayer(AddPlayerCommand addPlayerCommand) {
        connectedUserModel.remove(addPlayerCommand);

        Integer gameId = addPlayerCommand.getGame().getId();
        Game game = Configuration.getSingleton().getGame(gameId);

        if((game.getPlayerList().size() == 1) && (game.isGameStarted())){
            notifyVictory(new NotifyVictoryCommand(game.getId(), game.getPlayerList().get(0)));
            remove(game);
        }
    }

    public void notifyVictory(NotifyVictoryCommand notifyVictoryCommand) {
        Game game = Configuration.getSingleton().getGame(notifyVictoryCommand.getGameId());

        game.getRemoteUserList().forEach(remoteUser -> boardRemoteCommandTemplateAdapter
                .notifyVictory(notifyVictoryCommand, remoteUser));
    }

    public void remove(Game game) {
        Configuration.getSingleton().removeGame(game.getId());

        List<User> remoteUserList = Configuration.getSingleton().getRemoteUserList();

        remoteUserList.forEach(remoteUser -> mainRemoteCommandTemplateAdapter
                .addGame(new AddGameCommand(game, true), remoteUser));
        list.removeElement(game);
    }

    private void addPlayer(AddPlayerCommand addPlayerCommand) {
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

    public void notifyWithdrawal(NotifyWithdrawalCommand notifyWithdrawalCommand) {
        Game game = Configuration.getSingleton().getGame(notifyWithdrawalCommand.getGameId());

        game.getRemoteUserList().forEach(remoteUser -> boardRemoteCommandTemplateAdapter
                .notifyWithdrawal(notifyWithdrawalCommand, remoteUser));

        removePlayer(new AddPlayerCommand(notifyWithdrawalCommand.getPlayer(), game, true));
    }

    public DefaultListModel<Game> getList() {
        return list;
    }
}
