package algorithm;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;

import java.util.concurrent.ThreadFactory;

/**
 * https://blog.csdn.net/dw147258dw/article/details/114982367
 */
public class DisruptorMain {

    public static void main(String[] args) throws Exception {

        // 队列中的元素
        @Data
        class Element {
            private int value;
        }

        // 生产者的线程工厂
        ThreadFactory threadFactory = r -> new Thread(r, "simpleThread");

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = Element::new;

        // 处理Event的handler
        EventHandler<Element> handler = (element, sequence, endOfBatch) ->
                System.out.println("Element: " + element.getValue());

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor<>(factory, 16, threadFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int l = 0; true; l++) {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try {
                // 返回可用位置的元素
                Element element = ringBuffer.get(sequence);
                // 设置该位置元素的值
                element.setValue(l);
            } finally {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }
}
