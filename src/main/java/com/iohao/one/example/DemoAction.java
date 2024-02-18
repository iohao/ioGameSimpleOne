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
package com.iohao.one.example;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.protocol.wrapper.WrapperKit;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.common.kit.ExecutorKit;
import com.iohao.game.common.kit.concurrent.IntervalTaskListener;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.external.client.kit.AssertKit;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author 渔民小镇
 * @date 2023-01-06
 */
@ActionController(DemoCmd.cmd)
public class DemoAction {
    /**
     * 示例 here 方法
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(DemoCmd.here)
    public HelloReq here(HelloReq helloReq) {
        HelloReq newHelloReq = new HelloReq();
        newHelloReq.name = helloReq.name + ", I'm here ";
        return newHelloReq;
    }

    /**
     * 示例 异常机制演示
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(DemoCmd.jackson)
    public HelloReq jackson(HelloReq helloReq) {
        String jacksonName = "jackson";
        if (!jacksonName.equals(helloReq.name)) {
            throw new MsgException(100, "异常机制测试，name 必须是 jackson !");
        }

        helloReq.name = helloReq.name + ", hello, jackson !";

        return helloReq;
    }

    /**
     * 示例 返回 List 数据
     *
     * @return list
     */
    @ActionMethod(DemoCmd.list)
    public List<HelloReq> list() {
        // 得到一个 List 列表数据，并返回给请求端
        return IntStream.range(1, 5).mapToObj(id -> {
            HelloReq helloReq = new HelloReq();
            helloReq.name = "data:" + id;
            return helloReq;
        }).toList();
    }

    static {
        Runnable runnable = () -> {
            HelloReq helloReq = new HelloReq();
            helloReq.name = "广播测试";

            // 广播
            BrokerClientHelper
                    .getBroadcastContext()
                    .broadcast(CmdInfo.of(DemoCmd.cmd, DemoCmd.listenValue), helloReq);

            BrokerClientHelper
                    .getBroadcastContext()
                    .broadcast(
                            CmdInfo.of(DemoCmd.cmd, DemoCmd.listenList)
                            , WrapperKit.ofListByteValue(List.of(helloReq))
                    );
        };

        // 定时广播测试
        TaskKit.runInterval(runnable::run, 5, TimeUnit.SECONDS);
    }
}
