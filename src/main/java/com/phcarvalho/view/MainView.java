package com.phcarvalho.view;

import com.phcarvalho.controller.MainController;
import com.phcarvalho.dependencyfactory.DependencyFactory;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public static final String TITLE = "Chinese Checkers - Server";

    private MainController controller;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private ConnectedUserView connectedUserView;
    private GameView gameView;
    private MenuView menuView;

    public MainView(MainController controller) {
        this.controller = controller;
        mainPanel = new JPanel(new GridBagLayout());
        topPanel = new JPanel(new GridBagLayout());
        bottomPanel = new JPanel(new GridBagLayout());
        connectedUserView = DependencyFactory.getSingleton().get(ConnectedUserView.class);
        gameView = DependencyFactory.getSingleton().get(GameView.class);
        menuView = DependencyFactory.getSingleton().get(MenuView.class);
        initialize();
    }

    private void initialize() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        mainPanel.add(topPanel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        topPanel.add(connectedUserView, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        topPanel.add(gameView, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        mainPanel.add(bottomPanel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        bottomPanel.add(menuView, gridBagConstraints);

        setTitle(TITLE);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public ConnectedUserView getConnectedUserView() {
        return connectedUserView;
    }

    public GameView getGameView() {
        return gameView;
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
