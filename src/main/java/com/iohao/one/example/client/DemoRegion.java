/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.one.example.client;


import com.iohao.game.action.skeleton.protocol.wrapper.ByteValueList;
import com.iohao.game.common.kit.log.IoGameLoggerFactory;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.one.example.DemoCmd;
import com.iohao.one.example.HelloReq;
import org.slf4j.Logger;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
public class DemoRegion extends AbstractInputCommandRegion {
    static final Logger log = IoGameLoggerFactory.getLoggerCommonStdout();

    @Override
    public void initInputCommand() {
        // 模拟请求的主路由
        inputCommandCreate.cmd = DemoCmd.cmd;

        // 模拟请求参数
        HelloReq helloReq = new HelloReq();
        helloReq.name = "塔姆";

        // ---------------- 模拟请求 1-0 ----------------
        ofCommand(DemoCmd.here).callback(HelloReq.class, result -> {
            HelloReq value = result.getValue();
            log.info("value : {}", value);
        }).setDescription("here").setRequestData(helloReq);

        // ---------------- 模拟请求 1-1 ----------------
        ofCommand(DemoCmd.jackson).callback(HelloReq.class, result -> {
            // 不会进入到这里，因为发生了异常。 1-1 action 的逻辑要求 name 必须是 jackson。
            HelloReq value = result.getValue();
            log.info("value : {}", value);
        }).setDescription("jackson").setRequestData(helloReq);

        // ---------------- 模拟请求 1-2 ----------------
        // 因为服务器返回的是 List， 当 action 返回值是 List 时，框架会使用 ByteValueList 来包装
        ofCommand(DemoCmd.list).callback(ByteValueList.class, result -> {
            // 得到 list 数据
            List<HelloReq> list = result.toList(HelloReq.class);
            log.info("list : {}", list);
        }).setDescription("list");
    }
}