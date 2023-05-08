package fileIO;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class MyBiConsumer implements BiConsumer<Map.Entry<Integer, Set<Integer>>, Map.Entry<Integer, Set<Integer>>> {

    public static void main(String[] args) throws Exception {

    }

    @Override
    public void accept(Map.Entry<Integer, Set<Integer>> v1, Map.Entry<Integer, Set<Integer>> v2) {
        System.out.println(v1);
        System.out.println(v2);
    }
}
