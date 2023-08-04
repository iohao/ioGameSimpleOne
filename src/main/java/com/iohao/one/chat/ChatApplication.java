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
package com.iohao.one.chat;

import com.iohao.game.external.core.micro.PipelineContext;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.micro.WebSocketMicroBootstrapFlow;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 简单的网页聊天室
 * <pre>
 *     本地启动项目后，通过以下 WebSocket 测试工具网页，输入 ws://127.0.0.1:10100/websocket 并连接
 *     可以多用几个浏览器来连接，连接后相当于一个简单的聊天室，可发送消息。
 *
 *     WebSocket 测试工具
 *     <a href="https://www.wetools.com/websocket">WebSocket 测试工具-1</a>
 *     <a href="http://wstool.js.org">WebSocket 测试工具-2</a>
 * </pre>
 *
 * @author 渔民小镇
 * @date 2023-08-04
 */
public class ChatApplication {
    public static void main(String[] args) {
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(10100);
        builder.setting().setMicroBootstrapFlow(new WebSocketMicroBootstrapFlow() {
            public void pipelineFlow(PipelineContext context) {
                super.httpHandler(context);
                super.websocketHandler(context);
                context.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                    static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                        channelGroup.add(ctx.channel());
                    }

                    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
                        var message = String.format("[user:%s]:[%s]", ctx.channel().id().asShortText(), msg.text());
                        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
                    }
                });
            }
        });

        new NettyRunOne()
                .setExternalServer(builder.build())
                .startup();
    }
}
