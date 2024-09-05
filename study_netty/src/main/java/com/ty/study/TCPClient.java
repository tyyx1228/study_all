package com.ty.study;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;

/**
 * @author relax tongyu
 * @create 2019-10-09 21:17
 **/
public class TCPClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        OioEventLoopGroup eventExecutors = new OioEventLoopGroup();

       try{
           Bootstrap client = new Bootstrap();
           client.group(eventExecutors)
                   .channel(OioSocketChannel.class)
                   .handler(new ChannelInitializer<OioSocketChannel>() {
                       protected void initChannel(OioSocketChannel ch) throws Exception {

                           ChannelPipeline pipeline = ch.pipeline();

                           pipeline.addLast(new LineBasedFrameDecoder(4094));
                           pipeline.addLast(new StringDecoder());



                           pipeline.addLast(new ChannelInboundHandlerAdapter() {

                               @Override
                               public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                   System.out.println("channelActive");


                                   for (int i = 0; i < 100; i++) {
                                       ByteBuf buffer = Unpooled.buffer();
                                       buffer.writeBytes(("tongyu-"+ i +"\n").getBytes());
                                       ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
//                                       channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
//                                           public void operationComplete(Future<? super Void> future) throws Exception {
//                                               Object object = future.get();
//
//                                               System.out.println("futrue = " + future);
//                                           }
//                                       });
                                       Thread.sleep(10);
                                   }

//                                   ctx.channel().writeAndFlush("tongyu\n".getBytes());
                                   super.channelActive(ctx);
                               }


                               @Override
                               public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                   System.out.println("收到返回：" + msg);
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

           ChannelFuture f = client.connect("localhost", 8211).sync();

//           f.channel().writeAndFlush("sdflsdkflsd\n".getBytes());

           f.channel().closeFuture().sync();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           eventExecutors.shutdownGracefully();
       }


    }

}
