package com.phcarvalho.model.configuration.vo;

import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.vo.Player;

import java.util.Objects;

public class PlayerConfiguration {

    private User user;
    private Player player;

    public PlayerConfiguration(User user, Player player) {
        this.user = user;
        this.player = player;
    }

    public User getUser() {
        return user;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerConfiguration that = (PlayerConfiguration) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "PlayerConfiguration{" +
                "user=" + user +
                ", player=" + player +
                '}';
    }
}
