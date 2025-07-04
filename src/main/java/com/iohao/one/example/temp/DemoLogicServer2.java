/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.one.example.temp;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilderParamConfig;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.action.skeleton.core.flow.internal.DefaultActionAfter;
import com.iohao.game.action.skeleton.core.flow.internal.DefaultActionMethodInvoke;
import com.iohao.game.action.skeleton.core.flow.internal.DefaultActionMethodResultWrap;
import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.client.BrokerClient;
import com.iohao.game.bolt.broker.core.client.BrokerClientBuilder;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.one.example.DemoAction;

/**
 * @author 渔民小镇
 * @date 2023-01-06
 */
public class DemoLogicServer2 extends AbstractBrokerClientStartup {
    @Override
    public BarSkeleton createBarSkeleton() {
        var config = new BarSkeletonBuilderParamConfig()
                .setBroadcastLog(true)
                .scanActionPackage(DemoAction.class)
                ;

        // 业务框架构建器
        var builder = config.createBuilder();

        builder
//                .setActionFactoryBean(new DefaultActionFactoryBean())
                .setActionMethodInvoke(new DefaultActionMethodInvoke())
                .setActionMethodResultWrap(new DefaultActionMethodResultWrap())
                .setActionAfter(new DefaultActionAfter())
        ;


        // 添加控制台输出插件
        builder.addInOut(new DebugInOut());
        return builder.build();
    }

    @Override
    public BrokerClientBuilder createBrokerClientBuilder() {
        BrokerClientBuilder builder = BrokerClient.newBuilder();
        builder.appName("demoLogicServer");
        return builder;
    }
}
