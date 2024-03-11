package middleware;


import cn.hutool.core.io.FileUtil;
import org.rocksdb.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lawrence Peng
 */
public class RockDBTesting {

    public static void main(String[] args) {

        // Specify the directory where the LevelDB database will be stored.
        String dbPath = System.getProperty("user.dir") + "\\ignore\\rockdb\\database\\";

        FileUtil.mkdir(dbPath);

        Options options = new Options().setCreateIfMissing(true);

        try (RocksDB db = RocksDB.open(options, dbPath)) {

            for (int i = 0; i < 100; i++) {
                String key = "key" + i;
                String value = "value" + i;
                db.put(key.getBytes(), value.getBytes());
            }

            get(db, "key99");

            AtomicInteger count = new AtomicInteger(0);

            // Create a ReadOptions instance to iterate over all keys.
            ReadOptions readOptions = new ReadOptions();

            // Use a RocksIterator to iterate through all key-value pairs.
            try (RocksIterator iterator = db.newIterator(readOptions)) {
                // Seek to the beginning of the database.
                iterator.seekToFirst();

                while (iterator.isValid()) {

                    count.getAndIncrement();

                    byte[] key = iterator.key();
                    String keyStr = new String(key, StandardCharsets.UTF_8); // Convert the key bytes to a String

                    // Do something with the key, e.g., print it
                    System.out.println("Key: " + keyStr);

                    // Move to the next key-value pair
                    iterator.next();
                }
            }

            System.out.println("count: " + count.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String get(RocksDB db, String key) {
        String result = "";

        byte[] value;

        try {
            value = db.get(key.getBytes());
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }

        if (value != null) {
            result = new String(value);
            System.out.println("Key: " + key + ", Value: " + result);
        }
        return result;
    }

}
