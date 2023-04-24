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
package com.iohao.one.example.multiple;


import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.bolt.broker.client.external.ExternalServer;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalJoinEnum;
import com.iohao.game.simple.SimpleHelper;
import com.iohao.one.example.DemoLogicServer;

import java.util.concurrent.TimeUnit;

/**
 * @author 渔民小镇
 * @date 2023-04-24
 */
public class MyLogicApp {
    public static void main(String[] args) throws InterruptedException {
        /*
         * 1 broadcastContext.broadcastOrder
         */

        // 逻辑服
        var demoLogicServer = new DemoLogicServer();

        BrokerClientApplication.start(demoLogicServer);

        TimeUnit.SECONDS.sleep(11);
    }
}
