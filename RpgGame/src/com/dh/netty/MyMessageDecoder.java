package com.dh.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * 鐢╝mf3灏佽鏈嶅姟绔搷搴旂殑鏁版嵁
 * 
 */
public class MyMessageDecoder extends io.netty.handler.codec.ByteToMessageDecoder {
	private final static int MAXLENG = 900000;
	private final static boolean USETXHEAD = true;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		if (!in.isReadable() || in.readableBytes() < 6) {
			in.resetReaderIndex();
			return;
		}

		// in = in.order(ByteOrder.LITTLE_ENDIAN);
		int dataLength = in.readInt();
		if (dataLength > MAXLENG) {
			if (USETXHEAD) {
				analyTxHeader(ctx, in, out);
				return;
			} else {
				throw new Exception("more than max length limit");
			}
		}
		short commandCode = in.readShort();

		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}

		byte[] data = new byte[dataLength];
		in.readBytes(data);

		NettyMessageVO nettyMessageVO = new NettyMessageVO();
		nettyMessageVO.setCommandCode(commandCode);
		nettyMessageVO.setData(data);
		out.add(nettyMessageVO);

	}

	private void analyTxHeader(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.resetReaderIndex();
		byte[] bb = new byte[1024];
		if (in.readByte() == ((byte) 't') && in.readByte() == ((byte) 'g') && in.readByte() == ((byte) 'w')) {

			for (int i = 0; i < 1024; i++) {
				bb[i] = in.readByte();
				String str = new String(bb);
				if (bb[i] == '\n' && str.indexOf("\r\n\r\n") > -1) {
					decode(ctx, in, out);
					break;
				}

			}

		} else {
			throw new Exception("more than max length limit");
		}

	}

}