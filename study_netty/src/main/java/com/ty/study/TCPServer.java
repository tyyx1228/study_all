package com.ty.study;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.ServerSocket;
import java.nio.charset.Charset;

/**
 * @author relax tongyu
 * @create 2019-10-09 20:31
 **/
public class TCPServer {
    public static void main(String[] args) {
        OioEventLoopGroup boss = new OioEventLoopGroup();
        OioEventLoopGroup worker = new OioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker);
            server.option(ChannelOption.SO_BACKLOG, 12);
            server.childOption(ChannelOption.SO_KEEPALIVE, true);
            server.channel(OioServerSocketChannel.class);
            server.childHandler(new ChannelInitializer<OioSocketChannel>() {

                protected void initChannel(OioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LineBasedFrameDecoder(4096));
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println(msg);
                            String string = msg.toString();
                            int num = Integer.parseInt(string.split("-")[1]);

                            if(num % 2 == 0){
                                ctx.writeAndFlush(Unpooled.buffer().writeBytes("偶数已识别处理\n".getBytes()));
                                System.out.println("Server 偶数已识别处理");
                            }
                            super.channelRead(ctx, msg);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            cause.printStackTrace();
                            super.exceptionCaught(ctx, cause);
                        }
                    });
                }
            });
            ChannelFuture f = server.bind(8211).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
