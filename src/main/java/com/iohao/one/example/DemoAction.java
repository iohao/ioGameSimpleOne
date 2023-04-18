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
import com.iohao.game.action.skeleton.core.exception.MsgException;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author 渔民小镇
 * @date 2023-01-06
 */
@ActionController(1)
public class DemoAction {
    /**
     * 示例 here 方法
     *
     * @param helloReq helloReq
     * @return HelloReq
     */
    @ActionMethod(0)
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
    @ActionMethod(1)
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
    @ActionMethod(2)
    public List<HelloReq> list() {
        // 得到一个 List 列表数据，并返回给请求端
        return IntStream.range(1, 5).mapToObj(id -> {
            HelloReq helloReq = new HelloReq();
            helloReq.name = "data:" + id;
            return helloReq;
        }).toList();
    }
}
