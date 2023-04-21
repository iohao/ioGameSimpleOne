/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.one.example;

import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author 渔民小镇
 * @date 2023-01-06
 */
public class DemoWebsocketClient {
    static final Logger log = IoGameLoggerFactory.getLoggerCommonStdout();
    WebSocketClient webSocketClient;
    Map<Integer, Consumer<byte[]>> consumerCallbackMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 这里模拟游戏客户端
        DemoWebsocketClient demoWebsocketClient = new DemoWebsocketClient();
        demoWebsocketClient.connect();
        // 发送数据到服务器
        demoWebsocketClient.testOrderMsg();
    }

    void testOrderMsg() {
        AtomicInteger theIntValue = new AtomicInteger();
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicBoolean atomicBoolean = new AtomicBoolean();

        // 游戏框架内置的协议， 与游戏前端相互通信的协议
        // 路由、子路由
        ExternalMessage externalMessage = ExternalKit.createExternalMessage(1, 3);
        // 发送请求、回调
        sendMsg(externalMessage, data -> {
            IntValue intValue = DataCodecKit.decode(data, IntValue.class);
            System.out.println(intValue.value);

            int i = atomicInteger.incrementAndGet();
            if (i != intValue.value) {
                if (atomicBoolean.compareAndSet(false, true)) {
                    theIntValue.set(i);
                    log.info("------乱序了-----{}", theIntValue);
                }
            }
        });

        ExecutorKit.newSingleScheduled("s").schedule(() -> {
            if (atomicBoolean.get()) {
                log.info("------乱序了-----{}", theIntValue);
            }
        }, 1, TimeUnit.SECONDS);
    }

    void sendMsg(ExternalMessage externalMessage, Consumer<byte[]> consumerCallback) {
        // 路由与回调关联
        int cmdMerge = externalMessage.getCmdMerge();
        this.consumerCallbackMap.put(cmdMerge, consumerCallback);

        // 将游戏对外服协议转为 pb 字节
        byte[] bytes = DataCodecKit.encode(externalMessage);

        // 发送数据到游戏服务器
        this.webSocketClient.send(bytes);
    }

    private void connect() throws URISyntaxException {
        // 连接游戏服务器的地址
        var wsUrl = "ws://127.0.0.1:10100/websocket";

        this.webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收服务器返回的消息
                byte[] dataContent = byteBuffer.array();
                ExternalMessage message = DataCodecKit.decode(dataContent, ExternalMessage.class);
//                System.out.println();
//                log.info("收到消息 ExternalMessage ========== \n{}", message);
                byte[] data = message.getData();

                if (data == null) {
                    return;
                }

                int cmdMerge = message.getCmdMerge();
//                log.info("执行对应的回调 : {} - {}", CmdKit.getCmd(cmdMerge), CmdKit.getSubCmd(cmdMerge));
                // 执行对应的回调
                DemoWebsocketClient
                        .this
                        .consumerCallbackMap
                        .get(cmdMerge)
                        .accept(data);
            }

            @Override
            public void onMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
            }

            @Override
            public void onError(Exception ex) {
            }

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }
        };

        // 开始连接服务器
        this.webSocketClient.connect();
    }

}
