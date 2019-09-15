package com.phcarvalho.view;

import com.phcarvalho.controller.ConnectionController;
import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.view.util.DialogUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

public class ConnectionView extends JPanel {

    public static final int DEFAULT_PORT = 9999;
    public static final String START_SERVER = "Start Server";
    public static final String EMPTY_LABEL = "-";

    private ConnectionController controller;
    private DialogUtil dialogUtil;
    private JButton startServerButton;
    private JLabel serverStatusLabel;
    private JLabel serverStatusValueLabel;
    private JLabel localPortLabel;
    private JLabel localPortValueLabel;

    public ConnectionView(ConnectionController controller) {
        super(new GridBagLayout());
        this.controller = controller;
        dialogUtil = DependencyFactory.getSingleton().get(DialogUtil.class);
        startServerButton = new JButton(START_SERVER);
        serverStatusLabel = new JLabel("Status:");
        serverStatusValueLabel = new JLabel("Down");
        localPortLabel = new JLabel("Port:");
        localPortValueLabel = new JLabel(EMPTY_LABEL);
        initialize();
    }

    private void initialize() {
        TitledBorder titledBorder = new TitledBorder("Connection");

        titledBorder.setTitleJustification(TitledBorder.CENTER);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        titledBorder.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBorder(titledBorder);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        startServerButton.addActionListener(actionEvent -> startServer());
        startServerButton.setPreferredSize(new Dimension(160, 30));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        add(startServerButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        add(serverStatusLabel, gridBagConstraints);

        serverStatusValueLabel.setPreferredSize(new Dimension(200, 40));
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        add(serverStatusValueLabel, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        add(localPortLabel, gridBagConstraints);

        localPortValueLabel.setPreferredSize(new Dimension(50, 40));
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(2, 4, 2, 4);;
        add(localPortValueLabel, gridBagConstraints);
    }

    private void startServer(){
        Integer port = getPort();

        if(port != null) {

            try {
                User localUser = buildLocalUser(port);

                controller.startServer(localUser);
                serverStatusValueLabel.setText("Up");
                localPortValueLabel.setText(localUser.getPort().toString());
                startServerButton.setEnabled(false);
            } catch (RemoteException e) {
                dialogUtil.showError(e.getMessage(), START_SERVER, e);
            }
        }
    }

    private Integer getPort() {
        String portAsString = dialogUtil.showInput("What is the port?", START_SERVER);

        if(portAsString == null)
            return null;

        if(portAsString.isEmpty())
            return DEFAULT_PORT;

        try {
            return Integer.valueOf(portAsString);
        } catch (NumberFormatException e) {
            dialogUtil.showError("The port must be an integer!", START_SERVER);
        }

        return getPort();
    }

    private User buildLocalUser(Integer port) {
        String host = getLocalhost();

        return User.ofServer(host, port);
    }

    private String getLocalhost() {
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            //todo handle it...
            e.printStackTrace();
        }

        return inetAddress.getHostAddress();
    }
}
