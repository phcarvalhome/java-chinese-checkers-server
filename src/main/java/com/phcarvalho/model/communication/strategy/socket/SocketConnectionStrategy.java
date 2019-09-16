package com.phcarvalho.model.communication.strategy.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;
import com.phcarvalho.model.util.LogUtil;
import com.phcarvalho.view.util.DialogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class SocketConnectionStrategy implements IConnectionStrategy {

    public static final String SERVER_CREATION = "Server Creation";
    public static final String CLIENT_CONNECTION = "Client Connection";

    private ServerSocket serverSocket;
    private Map<User, RemoteUserSocketConnection> remoteUserSocketMap;

    private DialogUtil dialogUtil;

    public SocketConnectionStrategy() {
        remoteUserSocketMap = new HashMap<>();
        dialogUtil = DependencyFactory.getSingleton().get(DialogUtil.class);
    }

    @Override
    public void startServer(User localUser) throws RemoteException {

        if((serverSocket != null) && (!serverSocket.isClosed()))
            throw new ConnectionException("The server is already up!", SERVER_CREATION);

        try {
            serverSocket = new ServerSocket(localUser.getPort());
            dialogUtil.showInformation("The server is up!", SERVER_CREATION);
            waitConnectionRequest();
        } catch (IOException e) {
            throw new ConnectionException("Error in the server creation!", e, SERVER_CREATION);
        }
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
        RemoteUserSocketConnection remoteUserSocketConnection = new RemoteUserSocketConnection(socket);

        remoteUserSocketMap.put(remoteUserSocketConnection.getRemoteUser(), remoteUserSocketConnection);
    }

    public void send(ICommand remoteCommand, User remoteUser) throws RemoteException {
        RemoteUserSocketConnection remoteUserSocketConnection = remoteUserSocketMap.get(remoteUser);

        if(remoteUserSocketConnection.getSocket().isConnected())
            remoteUserSocketConnection.send(remoteCommand);
    }
}
