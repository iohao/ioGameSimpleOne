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
package com.iohao.one.example;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;

/**
 * 装箱、拆箱示例
 * <pre>
 *     框架支持基础类型的装箱、拆箱
 *
 *     <a href="https://www.yuque.com/iohao/game/ieimzn">业务参数自动装箱、拆箱基础类型文档</a>
 * </pre>
 *
 * @author 渔民小镇
 * @date 2023-02-11
 */
@ActionController(6)
public class BoxingAction {
    @ActionMethod(10)
    public int int2int(int value) {
        return value + 1;
    }

    @ActionMethod(20)
    public long long2long(long value) {
        return value + 1;
    }

    @ActionMethod(40)
    public boolean bool2bool(boolean boolValue) {
        return boolValue;
    }

    @ActionMethod(30)
    public String string2string(String s) {
        return s + 1;
    }
}
