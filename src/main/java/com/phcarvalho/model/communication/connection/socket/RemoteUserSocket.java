package com.phcarvalho.model.communication.connection.socket;

import com.phcarvalho.dependencyfactory.DependencyFactory;
import com.phcarvalho.model.ConnectedUserModel;
import com.phcarvalho.model.GameModel;
import com.phcarvalho.model.communication.commandtemplate.local.socket.CommandInvoker;
import com.phcarvalho.model.communication.protocol.vo.RemoteEvent;
import com.phcarvalho.model.communication.protocol.vo.command.ICommand;
import com.phcarvalho.model.configuration.entity.User;
import com.phcarvalho.model.util.LogUtil;
import com.phcarvalho.view.util.DialogUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

public class RemoteUserSocket {

    public static final String CLIENT_CONNECTION = "Client Connection";
    public static final String IO_STREAM_OPENING = "I/O Stream Opening";
    public static final String INPUT_STREAMING_PROCESSING = "Input Stream Processing";
    public static final String OUTPUT_STREAM_PROCESSING = "Remote Event Sending";
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

    public RemoteUserSocket(Socket socket) {
        this.socket = socket;
        commandInvoker = DependencyFactory.getSingleton().get(CommandInvoker.class);
        buildObjectIOStream();
        dialogUtil = DependencyFactory.getSingleton().get(DialogUtil.class);
        connectedUserModel = DependencyFactory.getSingleton().get(ConnectedUserModel.class);
        gameModel = DependencyFactory.getSingleton().get(GameModel.class);
        setRemoteUser();
        waitRemoteEvent();
    }

    public void buildObjectIOStream(){

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LogUtil.logError("Error in the I/O stream opening! Client: " + remoteUser, IO_STREAM_OPENING, e);
            closeResources();
        }
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
                    LogUtil.logError("Error in the input stream processing! Client: " + remoteUser, INPUT_STREAMING_PROCESSING, e);
                    connectedUserModel.remove(remoteUser);
                    gameModel.remove(remoteUser);
                    closeResources();

                    return;
                }
            }
        });
    }

    private void closeResources(){

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

    public void send(ICommand remoteCommand) {

        if((socket == null) || (!socket.isConnected()))
            LogUtil.logError("The client is not connected! Client: " + remoteUser, CLIENT_CONNECTION);

        try {
            RemoteEvent remoteEvent = new RemoteEvent(remoteCommand);

            objectOutputStream.writeObject(remoteEvent);
            objectOutputStream.reset();
            LogUtil.logInformation(remoteEvent.toString(), REMOTE_EVENT_SENT);
        } catch (IOException e) {
            LogUtil.logError("Error in the output stream processing! Client: " + remoteUser, OUTPUT_STREAM_PROCESSING, e);
            closeResources();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public User getRemoteUser() {
        return remoteUser;
    }
}
