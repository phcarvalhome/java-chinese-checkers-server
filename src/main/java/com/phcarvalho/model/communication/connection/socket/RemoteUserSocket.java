package com.phcarvalho.model.communication.connection.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.commandtemplate.local.socket.CommandInvoker;
import com.phcarvalho.model.communication.protocol.vo.RemoteEvent;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.exception.ConnectionException;
import com.phcarvalho.model.util.LogUtil;
import com.phcarvalho.view.util.DialogUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

public class RemoteUserSocket {

    public static final String CLIENT_CONNECTION = "Client Connection";
    public static final String RECEIVE_REMOTE_COMMAND = "Receive Remote Command";
    public static final String SEND_REMOTE_COMMAND = "Send Remote Command";
    public static final String SOCKET_CLOSING = "Socket Closing";
    public static final String INPUT_STREAM_CLOSING = "Input Stream Closing";
    public static final String OUTPUT_STREAM_CLOSING = "Output Stream Closing";
    public static final String REMOTE_EVENT_SENT = "Remove Event Sent";

    private Socket socket;
    private CommandInvoker commandInvoker;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private User remoteUser;

    private DialogUtil dialogUtil;

    private ConnectedUserModel connectedUserModel;
    private GameModel gameModel;

    public RemoteUserSocket(Socket socket) throws IOException {
        this.socket = socket;
        commandInvoker = DependencyFactory.getSingleton().get(CommandInvoker.class);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        dialogUtil = DependencyFactory.getSingleton().get(DialogUtil.class);
        connectedUserModel = DependencyFactory.getSingleton().get(ConnectedUserModel.class);
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
        setRemoteUser();
        waitRemoteEvent();
    }

    private void setRemoteUser() {
        remoteUser = User.of(socket.getInetAddress().getHostName(), socket.getPort());
        connectedUserModel.add(remoteUser);
    }

    private void waitRemoteEvent() {

        Executors.newSingleThreadExecutor().execute(() -> {

            while(true){

                try {
                    RemoteEvent remoteEvent = (RemoteEvent) objectInputStream.readObject();

                    commandInvoker.execute(remoteEvent);
                } catch (IOException | ClassNotFoundException e) {
                    LogUtil.logError("Error in the remote command receiving! Client: " + remoteUser, RECEIVE_REMOTE_COMMAND, e);
                    connectedUserModel.remove(remoteUser);
                    gameModel.remove(remoteUser);
                    closeResources();

                    return;
                }
            }
        });
    }

    public void closeResources(){

        try {
            socket.close();
        } catch (IOException e) {
            LogUtil.logError("Error in the socket closing! Client: " + remoteUser, SOCKET_CLOSING, e);
        }

        try {
            objectOutputStream.close();
        } catch (IOException e) {
            LogUtil.logError("Error in the output stream closing! Client: " + remoteUser, OUTPUT_STREAM_CLOSING, e);
        }

        try {
            objectInputStream.close();
        } catch (IOException e) {
            LogUtil.logError("Error in the input stream closing! Client: " + remoteUser, INPUT_STREAM_CLOSING, e);
        }
    }

    public void send(ICommand remoteCommand) throws ConnectionException {

        if((socket == null) || (!socket.isConnected()))
//            LogUtil.logError("The client is not connected! Client: " + remoteUser, CLIENT_CONNECTION);
            throw new ConnectionException("The client is not connected! Client: " + remoteUser, CLIENT_CONNECTION);

        try {
            RemoteEvent remoteEvent = new RemoteEvent(remoteCommand);

            objectOutputStream.writeObject(remoteEvent);
            objectOutputStream.reset();
            LogUtil.logInformation(remoteEvent.toString(), REMOTE_EVENT_SENT);
        } catch (IOException e) {
//            LogUtil.logError("Error in the output stream processing! Client: " + remoteUser, OUTPUT_STREAM_PROCESSING, e);
            closeResources();

            throw new ConnectionException("Error in the remote command sending! Client: " + remoteUser, e, SEND_REMOTE_COMMAND);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public User getRemoteUser() {
        return remoteUser;
    }
}
