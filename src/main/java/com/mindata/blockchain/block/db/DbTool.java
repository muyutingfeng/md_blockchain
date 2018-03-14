package com.mindata.blockchain.block.db;

import com.mindata.blockchain.socket.common.Const;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
@Component
public class DbTool {
    @Resource
    private RocksDB rocksDB;

    /**
     * 数据库key value
     * @param key
     * key
     * @param value
     * value
     */
    public void put(String key, String value) {
        try {
            rocksDB.put(key.getBytes(Const.CHARSET), value.getBytes(Const.CHARSET));
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * get By Key
     * @param key
     * key
     * @return
     * value
     */
    public String get(String key) {
        try {
            byte[] bytes = rocksDB.get(key.getBytes(Const.CHARSET));
            if (bytes != null) {
                return new String(bytes);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * remove by key
     * @param key key
     */
    public void remove(String key) {
        try {
            rocksDB.delete(rocksDB.get(key.getBytes(Const.CHARSET)));
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
