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
import com.iohao.one.example.HelloReq;
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
        // 模拟请求的主路由
        inputCommandCreate.cmd = DemoCmd.cmd;

        // ---------------- 模拟请求 1-0 ----------------
        ofCommand(DemoCmd.here).setTitle("here").setRequestData(() -> {
            HelloReq helloReq = new HelloReq();
            helloReq.name = "1";
            return helloReq;
        }).callback(result -> {
            HelloReq value = result.getValue(HelloReq.class);
            log.info("value : {}", value);
        });

        // ---------------- 模拟请求 1-1 ----------------
        ofCommand(DemoCmd.jackson).setTitle("jackson").setRequestData(() -> {
            HelloReq helloReq = new HelloReq();
            helloReq.name = "1";
            return helloReq;
        }).callback(result -> {
            // 不会进入到这里，因为发生了异常。 1-1 action 的逻辑要求 name 必须是 jackson。
            HelloReq value = result.getValue(HelloReq.class);
            log.info("value : {}", value);
        });

        // ---------------- 模拟请求 1-2 ----------------
        // 因为服务器返回的是 List， 当 action 返回值是 List 时，框架会使用 ByteValueList 来包装
        ofCommand(DemoCmd.list).setTitle("list").callback(result -> {
            // 得到 list 数据
            List<HelloReq> list = result.listValue(HelloReq.class);
            log.info("list : {}", list);
        });

        ofListen(result -> {
            HelloReq value = result.getValue(HelloReq.class);
            log.info("value : {}", value);
        }, DemoCmd.listenValue, "listenValue");

        ofListen(result -> {
            List<HelloReq> helloReqList = result.listValue(HelloReq.class);
            log.info("helloReqList : {}", helloReqList);
        }, DemoCmd.listenList, "listenList");
    }
}