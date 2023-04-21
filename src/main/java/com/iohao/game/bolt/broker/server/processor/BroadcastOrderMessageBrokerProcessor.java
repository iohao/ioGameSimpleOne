/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.bolt.broker.server.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.bolt.broker.core.message.BroadcastOrderMessage;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.bolt.broker.server.aware.BrokerServerAware;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * 把逻辑服的广播 顺序的 转发到对外服
 * <pre>
 *     只使用了一个线程
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-07-14
 */

public class BroadcastOrderMessageBrokerProcessor extends AsyncUserProcessor<BroadcastOrderMessage>
        implements BrokerServerAware {
    static final Logger log = IoGameLoggerFactory.getLoggerMsg();
    BrokerServer brokerServer;
    final ExecutorService executorService = ExecutorKit.newSingleThreadExecutor("BroadcastOrderBroker");

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, BroadcastOrderMessage broadcastOrderMessage) {

        ResponseMessage responseMessage = broadcastOrderMessage.getResponseMessage();

        byte[] data = responseMessage.getData();
        IntValue intValue = DataCodecKit.decode(data, IntValue.class);
        log.info("{}", intValue.value);

        BrokerExternalKit.sendMessageToExternals(this.brokerServer, broadcastOrderMessage);
    }

    @Override
    public Executor getExecutor() {
        return executorService;
    }

    @Override
    public String interest() {
        return BroadcastOrderMessage.class.getName();
    }

    @Override
    public void setBrokerServer(BrokerServer brokerServer) {
        this.brokerServer = brokerServer;
    }
}