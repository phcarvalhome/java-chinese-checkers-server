package com.phcarvalho.view;

import com.phcarvalho.controller.GameController;
import com.phcarvalho.model.configuration.entity.Game;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GameView extends JPanel {

    public static final String TITLE = "Game";
    public static final int WIDTH = 280;
    public static final int HEIGHT = 180;

    private GameController controller;
    private JList<Game> list;

    public GameView(GameController controller) {
        super(new GridBagLayout());
        this.controller = controller;
        list = new JList();
        initialize();
    }

    private void initialize() {
        TitledBorder titledBorder = new TitledBorder(TITLE);

        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBorder(titledBorder);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;

        JScrollPane scrollPane = new JScrollPane(list);

        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(scrollPane, gridBagConstraints);
    }

    public void add(Game game) {

    }

    public JList getList() {
        return list;
    }
}
