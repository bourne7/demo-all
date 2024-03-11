package com.aac.test.color;


import java.io.*;
import java.util.*;

public class PaintPoints {

    private static String FILE_PATH;
    private static String FILE_PATH_OUT;


    public static void main(String[] args) {

        // 读取 resource 文件夹里面的 原始数据
        FILE_PATH = PaintPoints.class.getClassLoader().getResource("color_point/gc_1000_5").getFile();
        FILE_PATH_OUT = FILE_PATH + "_solution.txt";

        System.out.println(FILE_PATH);
        System.out.println(FILE_PATH_OUT);

        PaintPoints paintPoints = new PaintPoints();

        // 读取数据
        Map<Integer, Set<Integer>> original_map = paintPoints.readMethod(FILE_PATH);

        // 上色过程
        Map<Integer, Integer> result_map = paintPoints.process(original_map);

        // 校验
        paintPoints.validate(result_map);

        // 输出到文件
        paintPoints.usePrintWriter(new File(FILE_PATH_OUT), result_map);

    }

    private Map<Integer, Integer> process(Map<Integer, Set<Integer>> original_map) {
        Map<Integer, Integer> result_map = new HashMap<>();

        int current_color = 0;
        do {
            // 每使用一种新的颜色的时候, 已经上 当前颜色的点 的所有相邻点。
            // 最大的作用就是每一次给一个点着色以后, 用于移出对应的2个点之间的连接关系.
            Set<Integer> neighbor_points = new HashSet<>();

            // 第一次找一个点, 由于外层循环的条件是 为着色的点还存在, 所以这里一定可以找到一个未着色的点.
            Integer point_to_be_color = find_point(original_map, neighbor_points);

            do {
                // 上色
                result_map.put(point_to_be_color, current_color);

                // 将已经上色的点 的所有临近点加到 临时set 里面去。
                neighbor_points.addAll(original_map.get(point_to_be_color));

                // 将剩下的点里面已经有的这个点的要删除。这里要删除的原因是之前加入数据的时候，做了冗余，也就是每一个关系都体现了2次。
                for (Map.Entry<Integer, Set<Integer>> entry : original_map.entrySet()) {
                    entry.getValue().remove(point_to_be_color);
                }

                // 移出已经上色的点。
                original_map.remove(point_to_be_color);

                // 涂完一个点以后，找下一个。
                point_to_be_color = find_point(original_map, neighbor_points);
            } while (point_to_be_color != -1);

            // 清除临时Set
            neighbor_points.clear();

            // 一种颜色用完了
            current_color++;

        } while (original_map.size() > 0);

        System.out.println("color used: " + current_color);

        return result_map;
    }

    // 找出未上色的点里面，相邻点最多的。
    private Integer find_point(Map<Integer, Set<Integer>> original_map, Set<Integer> neighbor_points) {
        Map.Entry<Integer, Set<Integer>> entry = original_map.entrySet().stream().filter(v -> !neighbor_points.contains(v.getKey()))
                .max((v1, v2) -> (v1.getValue().size() - v2.getValue().size())).orElse(null);

        if (entry != null) {
            return entry.getKey();
        } else {
            return -1;
        }
    }

    /**
     * 校验数据
     */
    private void validate(Map<Integer, Integer> result_map) {
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(FILE_PATH));
            in.readLine();
            line = in.readLine();
            while (line != null) {
                String[] array = line.split(" ");
                Integer int1 = Integer.parseInt(array[0]);
                Integer int2 = Integer.parseInt(array[1]);
                if (result_map.get(int1).equals(result_map.get(int2))) {
                    throw new RuntimeException("失败！出错的点是：" + int1 + " " + int1);
                }
                line = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, Set<Integer>> readMethod(String fileName) {
        Map<Integer, Set<Integer>> rowDataMap = new TreeMap<>();
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            in.readLine();
            line = in.readLine();
            while (line != null) {
                String[] array = line.split(" ");
                Integer int1 = Integer.parseInt(array[0]);
                Integer int2 = Integer.parseInt(array[1]);
                addEdgeToDataMap(int1, int2, rowDataMap);
                addEdgeToDataMap(int2, int1, rowDataMap);
                line = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowDataMap;
    }

    private void addEdgeToDataMap(Integer point, Integer point_linked, Map<Integer, Set<Integer>> original_map) {
        if (original_map.get(point) == null) {
            Set<Integer> tempSet = new HashSet<>();
            tempSet.add(point_linked);
            original_map.put(point, tempSet);
        } else {
            original_map.get(point).add(point_linked);
        }
    }

    private void usePrintWriter(File txtFile, Map<Integer, Integer> coloredMap) {
        try {
            if (txtFile.exists()) {
                txtFile.delete();
            } else {
                txtFile.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(new FileWriter(txtFile, true));

            Set<Integer> tempSet = new HashSet<>(coloredMap.values());
            printWriter.println(tempSet.size());

            for (int i = 0; i < coloredMap.size(); i++) {
                printWriter.print(coloredMap.get(i));
                printWriter.print(" ");
            }

            printWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
