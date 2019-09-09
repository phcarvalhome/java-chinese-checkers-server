package com.phcarvalho.view;

import com.phcarvalho.controller.ConnectedUserController;
import com.phcarvalho.model.configuration.entity.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ConnectedUserView extends JPanel {

    public static final String TITLE = "Connected Player";
    public static final int WIDTH = 280;
    public static final int HEIGHT = 180;

    private ConnectedUserController controller;
    private JList<User> list;

    public ConnectedUserView(ConnectedUserController controller) {
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

    public void add(User user) {

    }

    public JList getList() {
        return list;
    }
}
