package com.cta;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Properties;

public class HelloWorld {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "cta-poc");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        StreamsConfig streamingConfig = new StreamsConfig(props);
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String, String> simpleFirstStream = streamBuilder
            .stream("hello", Consumed.with(stringSerde, stringSerde))
            .mapValues((ValueMapper<? super String, ? extends String>) String::toUpperCase);
        simpleFirstStream.print(Printed.toSysOut());

        KafkaStreams kafkaStreams = new KafkaStreams(streamBuilder.build(), streamingConfig);

        kafkaStreams.start();
    }
}
