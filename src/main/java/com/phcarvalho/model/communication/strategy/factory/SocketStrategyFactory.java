package com.phcarvalho.model.communication.strategy.factory;

import com.phcarvalho.model.communication.strategy.socket.SocketCommandTemplateStrategy;
import com.phcarvalho.model.communication.strategy.socket.SocketConnectionStrategy;
import com.phcarvalho.model.communication.strategy.ICommandTemplateStrategy;
import com.phcarvalho.model.communication.strategy.IConnectionStrategy;

public class SocketStrategyFactory implements ICommunicationStrategyFactory {

    @Override
    public IConnectionStrategy buildConnectionStrategy() {
        return new SocketConnectionStrategy();
    }

    @Override
    public ICommandTemplateStrategy buildCommandTemplateStrategy() {
        return new SocketCommandTemplateStrategy();
    }
}
