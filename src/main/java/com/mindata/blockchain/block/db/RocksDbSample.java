package com.mindata.blockchain.block.db;

import com.mindata.blockchain.socket.common.Const;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
public class RocksDbSample {
    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws RocksDBException, UnsupportedEncodingException {
        Options options = new Options().setCreateIfMissing(true);
        RocksDB db = RocksDB.open(options, "./rocksDB");
        RocksIterator iter = db.newIterator();
        iter.seekToFirst();
        while (iter.isValid()) {
            String key = new String(iter.key(), StandardCharsets.UTF_8);
            String val = new String(iter.value(), StandardCharsets.UTF_8);
            System.out.println(key + " -> " + val);
            iter.next();
        }
        db.put("key".getBytes(Const.CHARSET), "value".getBytes(Const.CHARSET));

        System.out.println(new String(db.get("key".getBytes(Const.CHARSET))));
    }
}
