package com.dh.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用amf3封装服务端响应的数据
 * 
 */
public class MyMessageEncoder extends io.netty.handler.codec.MessageToByteEncoder<NettyMessageVO> {
	private final static byte[] SECXML = ("<cross-domain-policy> " + "<allow-access-from domain=\"*\" to-ports=\"1025-29999\"/>" + "</cross-domain-policy> " + "\n\0").getBytes();
	private final static byte[] TGW = "tgw_l7_forward\r\nHost:s2.app1103970448.qqopenapp.com:8000\r\n\r\n".getBytes();

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessageVO msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		// out.order(ByteOrder.LITTLE_ENDIAN);
		// System.err.println("msg.getDataLength() = " + msg.getDataLength());

		if (msg.getCommandCode() == -1) {
			out.writeBytes(SECXML);
		} else if (msg.getCommandCode() == -2) {
			out.writeBytes(TGW);
		} else {

			int dataLength = msg.getDataLength();
			out.ensureWritable(4 + dataLength);
			out.writeInt(dataLength);
			out.writeShort(msg.getCommandCode());
			if (dataLength > 0) {
				out.writeBytes(msg.getData());
			}
		}
	}

}
