package com.adarsh.oms.oms_core.config;

import com.adarsh.oms.oms_events.avro.MarketTickEvent;
import com.adarsh.oms.oms_events.avro.OrderPlacedEvent;
import com.adarsh.oms.oms_events.avro.RiskApprovedEvent;
import com.adarsh.oms.oms_events.avro.TradeExecutedEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    private Map<String, Object> baseProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return props;
    }

    // OrderPlacedEvent
    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> orderPlacedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> orderPlacedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent>();
        factory.setConsumerFactory(orderPlacedConsumerFactory());
        return factory;
    }

    // RiskApprovedEvent
    @Bean
    public ConsumerFactory<String, RiskApprovedEvent> riskApprovedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RiskApprovedEvent> riskApprovedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, RiskApprovedEvent>();
        factory.setConsumerFactory(riskApprovedConsumerFactory());
        return factory;
    }

    // TradeExecutedEvent
    @Bean
    public ConsumerFactory<String, TradeExecutedEvent> tradeExecutedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TradeExecutedEvent> tradeExecutedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, TradeExecutedEvent>();
        factory.setConsumerFactory(tradeExecutedConsumerFactory());
        return factory;
    }

    // MarketDataEvent
    @Bean
    public ConsumerFactory<String, MarketTickEvent> marketDataConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MarketTickEvent> marketDataKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, MarketTickEvent>();
        factory.setConsumerFactory(marketDataConsumerFactory());
        return factory;
    }
}