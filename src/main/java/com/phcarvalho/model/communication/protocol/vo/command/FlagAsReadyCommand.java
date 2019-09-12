package com.phcarvalho.model.communication.protocol.vo.command;

import com.phcarvalho.model.vo.Player;
import com.phcarvalho.model.communication.protocol.vo.CommandTypeEnum;
import com.phcarvalho.model.configuration.entity.Game;

public class FlagAsReadyCommand extends AbstractCommand {

    private Player player;
    private Game game;

    public FlagAsReadyCommand(Player player, Game game/*, boolean isGameStarted*/) {
        this.player = player;
        this.game = game;
    }

    @Override
    public CommandTypeEnum getType() {
        return CommandTypeEnum.FLAG_AS_READY;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "FlagAsReadyCommand{" +
                "player=" + player +
                ", game=" + game +
                '}';
    }
}
