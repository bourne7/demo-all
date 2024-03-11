package middleware;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lawrence Peng
 */
public class LevelDBTesting {

    public static void main(String[] args) {

        // Get the working directory
        // or you could use "." to specify the working directory.
        String workingDirectory = System.getProperty("user.dir");

        // Specify the directory where the LevelDB database will be stored.
        String dbPath = "./ignore/leveldb/database";

        // Open or create a LevelDB instance.
        Options options = new Options().createIfMissing(true);

        try (DB db = Iq80DBFactory.factory.open(new File(dbPath), options)) {

            for (int i = 0; i < 100; i++) {
                String key = "key" + i;
                String value = "value" + i;
                db.put(key.getBytes(), value.getBytes());
            }

            get(db, "key99");

            AtomicInteger count = new AtomicInteger(0);
            // get values by prefix
            db.forEach((entry) -> {
                String keyString = new String(entry.getKey());
                if (keyString.startsWith("key1")) {
                    System.out.println("Key: " + keyString + ", Value: " + new String(entry.getValue()));
                    count.getAndIncrement();
                }
            });

            System.out.println("count: " + count.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String get(DB db, String key) {
        String result = null;
        // Retrieve data from the database.
        byte[] value = db.get(key.getBytes());
        if (value != null) {
            result = new String(value);
            System.out.println("Key: " + key + ", Value: " + result);
        }
        return result;
    }

}
