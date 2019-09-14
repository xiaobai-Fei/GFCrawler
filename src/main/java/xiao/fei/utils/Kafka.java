package xiao.fei.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Arrays;

public class Kafka {

    private static KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(PropertiesUtil.properties);

    private static KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PropertiesUtil.properties);

    public void consumer() {
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtil.getProperty("kafka.topics")));

        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                //TODO 发送给
            }
        }
    }

    public void producer(ProducerRecord[] producerRecords) {

        for (ProducerRecord producerRecord:producerRecords) {
            kafkaProducer.send(producerRecord);

        }

    }
}
