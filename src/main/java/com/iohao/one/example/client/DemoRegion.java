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
package com.iohao.one.example.client;


import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.one.example.DemoCmd;
import com.iohao.one.example.HelloMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
public class DemoRegion extends AbstractInputCommandRegion {
    static final Logger log = LoggerFactory.getLogger(DemoRegion.class);

    @Override
    public void initInputCommand() {
        // Setting cmd. cn:设置主路由
        inputCommandCreate.cmd = DemoCmd.cmd;

        // ---------------- request 1-0 ----------------
        ofCommand(DemoCmd.here).setTitle("here").setRequestData(() -> {
            HelloMessage helloMessage = new HelloMessage();
            helloMessage.name = "1";
            return helloMessage;
        }).callback(result -> {
            HelloMessage value = result.getValue(HelloMessage.class);
            log.info("{}", value);
        });

        // ---------------- request 1-1 ----------------
        ofCommand(DemoCmd.jackson).setTitle("Jackson").setRequestData(() -> {
            HelloMessage helloMessage = new HelloMessage();
            helloMessage.name = "1";
            return helloMessage;
        }).callback(result -> {
            // We won't get in here because an exception happened.
            // The logic for action 1-1 requires the name to be Jackson.
            // cn: 不会进入到这里，因为发生了异常。 1-1 action 的逻辑要求 name 必须是 Jackson。
            HelloMessage value = result.getValue(HelloMessage.class);
            log.info("{}", value);
        });

        // ---------------- request 1-2 ----------------
        ofCommand(DemoCmd.list).setTitle("list").callback(result -> {
            // We get list data because the server returns a List
            // cn: 得到 list 数据，因为服务器返回的是 List
            List<HelloMessage> list = result.listValue(HelloMessage.class);
            log.info("{}", list);
        });

        // ---------------- Broadcast Listener. cn: 广播监听 ----------------
        ofListen(result -> {
            HelloMessage value = result.getValue(HelloMessage.class);
            log.info("{}", value);
        }, DemoCmd.listenValue, "listenValue");

        ofListen(result -> {
            List<HelloMessage> helloMessageList = result.listValue(HelloMessage.class);
            log.info("{}", helloMessageList);
        }, DemoCmd.listenList, "listenList");
    }
}