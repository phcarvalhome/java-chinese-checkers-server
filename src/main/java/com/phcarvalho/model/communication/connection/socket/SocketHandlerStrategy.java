package com.phcarvalho.model.communication.connection.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.MainModel;
import com.phcarvalho.model.communication.connection.IConnectionHandlerStrategy;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;
import com.phcarvalho.model.util.LogUtil;
import com.phcarvalho.view.util.DialogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class SocketHandlerStrategy implements IConnectionHandlerStrategy {

    public static final String SERVER_CREATION = "Server Creation";
    public static final String CLIENT_CONNECTION = "Client Connection";

    private ServerSocket serverSocket;
    private Map<User, RemoteUserSocket> remoteUserSocketMap;

    private DialogUtil dialogUtil;

    private MainModel mainModel;

    public SocketHandlerStrategy() {
        remoteUserSocketMap = new HashMap<>();
        dialogUtil = DependencyFactory.getSingleton().get(DialogUtil.class);
    }

    @Override
    public void startServer(Integer port) throws ConnectionException {

        if((serverSocket != null) && (!serverSocket.isClosed()))
            throw new ConnectionException("The server is already up!", SERVER_CREATION);

        try {
            serverSocket = new ServerSocket(port);
            dialogUtil.showInformation("The server is up!", SERVER_CREATION);
            startServerByCallback(port);
            waitConnectionRequest();
        } catch (IOException e) {
            throw new ConnectionException("Error in the server creation!", e, SERVER_CREATION);
        }
    }

    private void startServerByCallback(Integer port) {
        User localUser = User.ofServer(serverSocket.getInetAddress().getHostName(), port);

        mainModel.startServerByCallback(localUser);
    }

    private void waitConnectionRequest() {
        Executors.newSingleThreadExecutor().execute(() -> {

            while(true){

                try {
                    Socket socket = serverSocket.accept();

                    LogUtil.logInformation("Client connection accepted!", CLIENT_CONNECTION);
                    addRemoteUserSocket(socket);
                } catch (IOException e) {
                    LogUtil.logError("Error in the connection request waiting!", SERVER_CREATION, e);
                }
            }
        });
    }

    private void addRemoteUserSocket(Socket socket) throws IOException {
        RemoteUserSocket remoteUserSocket = new RemoteUserSocket(socket);

        remoteUserSocketMap.put(remoteUserSocket.getRemoteUser(), remoteUserSocket);
        mainModel.sendAll(remoteUserSocket.getRemoteUser());
    }

    @Override
    public void send(ICommand remoteCommand, User remoteUser) throws ConnectionException {
        RemoteUserSocket remoteUserSocket = remoteUserSocketMap.get(remoteUser);

        if(remoteUserSocket.getSocket().isConnected())
            remoteUserSocket.send(remoteCommand);
    }

    @Override
    public void forward(List<User> remoteUserList, ICommand command) throws ConnectionException {

        for (User remoteUser : remoteUserList)
            send(command, remoteUser);
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }
}
