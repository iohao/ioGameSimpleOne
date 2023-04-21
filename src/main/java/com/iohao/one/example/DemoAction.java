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

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;

/**
 * @author 渔民小镇
 * @date 2023-01-06
 */
@ActionController(1)
public class DemoAction {
    @ActionMethod(3)
    public void order() {

        // 模拟 所有玩家准备 -> 开始游戏 -> 发牌 业务
        var broadcastContext = BrokerClientHelper.getBroadcastOrderContext();

        CmdInfo cmdInfo = CmdInfo.getCmdInfo(1, 3);
        for (int i = 1; i <= 100; i++) {
            IntValue intValue = new IntValue();
            intValue.value = i;

            broadcastContext.broadcastOrder(cmdInfo, intValue);
        }

//        BrokerClientItem item = (BrokerClientItem) broadcastContext;
//        Channel channel = item.getConnection().getChannel();
//        String shortText = channel.id().asShortText();
//        System.out.println(shortText);

    }
}
