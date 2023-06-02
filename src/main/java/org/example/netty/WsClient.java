package org.example.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.net.URI;

public class WsClient {
    public void dial(String addr) throws Exception {
        URI uri = new URI(addr); // WebSocket 服务器的地址
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 WebSocket 客户端引导程序
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加自定义的 WebSocket 客户端处理器
                            pipeline.addLast(new WebSocketClientHandler());
                        }
                    });
            // 连接到 WebSocket 服务器
            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            // 等待直到连接关闭
            channel.closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup
            group.shutdownGracefully();
        }
    }

    private static class WebSocketClientHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
            // 处理接收到的 WebSocket 帧
            // TODO: 添加您的逻辑代码
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // 处理异常
            cause.printStackTrace();
            ctx.close();
        }
    }
}
