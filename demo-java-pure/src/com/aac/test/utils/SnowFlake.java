package com.aac.test.utils;

public class SnowFlake {

    /**
     * 起始的时间戳。
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 序列号占用的位数
     */
    private final static long BIT_SEQUENCE = 12;

    /**
     * 数据中心占用的位数
     */
    private final static long BIT_DATA_CENTER = 5;

    /**
     * 机器标识占用的位数
     */
    private final static long BIT_MACHINE = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << BIT_SEQUENCE);
    private final static long MAX_MACHINE_ID = ~(-1L << BIT_MACHINE);
    private final static long MAX_DATA_CENTER_ID = ~(-1L << BIT_DATA_CENTER);


    /**
     * 序列号
     */
    private static long _sequence = 0L;

    /**
     * 上一次时间戳
     */
    private static long _lastStamp = -1L;


    public static synchronized String nextString(String prefix, Long _dataCenterId, Long _machineId) {
        if (prefix == null) {
            prefix = "SF-";
        }
        if (_dataCenterId != null && _machineId != null) {
            return prefix + nextId(_dataCenterId, _machineId);
        }
        return prefix + nextId();
    }

    public static synchronized String nextString() {
        return nextString("", null, null);
    }

    public static synchronized String nextString(String prefix) {
        return nextString(prefix, null, null);
    }

    public static synchronized long nextId() {
        return nextId(0, 0);
    }

    /**
     * @param _dataCenterId dataCenterId, from 0~63
     * @param _machineId    machineId, from 0~63
     */
    public static synchronized long nextId(long _dataCenterId, long _machineId) {

        if (_dataCenterId > MAX_MACHINE_ID || _dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than " + MAX_MACHINE_ID +
                    " or less than 0, current dataCenterId: " + _dataCenterId);
        }

        if (_machineId > MAX_DATA_CENTER_ID || _machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than " + MAX_DATA_CENTER_ID +
                    " or less than 0, current machineId: " + _machineId);
        }

        long currStamp = System.currentTimeMillis();
        if (currStamp < _lastStamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currStamp == _lastStamp) {
            //相同毫秒内，序列号自增
            _sequence = (_sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (_sequence == 0L) {
                // 这里就是同一个毫秒内，产生的不够用，就占用下一个毫秒的。
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            _sequence = 0L;
        }

        _lastStamp = currStamp;

        return (currStamp - START_STAMP) << BIT_SEQUENCE + BIT_MACHINE + BIT_DATA_CENTER    //时间戳部分
                | _dataCenterId << BIT_SEQUENCE + BIT_MACHINE                               //数据中心部分
                | _machineId << BIT_SEQUENCE                                                //机器标识部分
                | _sequence;                                                                //序列号部分
    }

    /**
     * 这个方法主要是防止产生时间倒退。
     */
    private static long getNextMill() {
        long mill = System.currentTimeMillis();
        while (mill <= _lastStamp) {
            mill = System.currentTimeMillis();
        }
        return mill;
    }

}
