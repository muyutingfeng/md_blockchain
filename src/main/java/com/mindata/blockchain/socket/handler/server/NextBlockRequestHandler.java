package com.mindata.blockchain.socket.handler.server;

import com.mindata.blockchain.ApplicationContextProvider;
import com.mindata.blockchain.block.Block;
import com.mindata.blockchain.core.manager.DbBlockManager;
import com.mindata.blockchain.socket.base.AbstractBlockHandler;
import com.mindata.blockchain.socket.body.BlockBody;
import com.mindata.blockchain.socket.packet.BlockPacket;
import com.mindata.blockchain.socket.packet.PacketBuilder;
import com.mindata.blockchain.socket.packet.PacketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * 获取某个区块下一块的请求，发起方带着自己的lastBlock hash，接收方则将自己的区块中，在传来的hash后面的那块返回回去。<p>
 * 如A传来了3，而我本地有5个区块，则返回区块4。
 * @author wuweifeng wrote on 2018/3/16.
 */
public class NextBlockRequestHandler extends AbstractBlockHandler<BlockBody> {
    private Logger logger = LoggerFactory.getLogger(TotalBlockInfoRequestHandler.class);

    @Override
    public Class<BlockBody> bodyClass() {
        return BlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, BlockBody blockBody, ChannelContext channelContext) throws Exception {
        logger.info("收到<请求某Block的下一Block>消息，请求者的block为：" + Json.toJson(blockBody));
        //传来的Block，如果为null，说明发起方连一个Block都没有
        Block block = blockBody.getBlock();

        Block nextBlock = ApplicationContextProvider.getBean(DbBlockManager.class).getNextBlock(block);
        BlockPacket blockPacket = new PacketBuilder<BlockBody>().setType(PacketType
                .NEXT_BLOCK_INFO_RESPONSE).setBody(new BlockBody(nextBlock)).build();
        Aio.send(channelContext, blockPacket);

        return null;
    }
}
