package az.company.cardstream;

import az.company.cardstream.adapter.out.external.model.CardEntity;
import az.company.cardstream.application.CardService;
import az.company.cardstream.config.KafkaConfig;
import az.company.cardstream.config.KafkaStreamsHealthIndicator;
import az.company.cardstream.serde.AppSerdes;
import az.company.cardstream.transformer.CardTransformer;
import az.company.cardstream.type.KCardOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;

/**
 * @Author QasimovEY on 14.05.21
 */
@Slf4j
@SpringBootApplication
public class CardStreamApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CardStreamApplication.class, args);

        KafkaConfig kafkaConfig = context.getBean(KafkaConfig.class);
        final CardService cardService = context.getBean(CardService.class);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getApplicationId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, KCardOrderEntity> KS0 = builder.stream(
                kafkaConfig.getConsumerTopic(),
                Consumed.with(AppSerdes.String(), AppSerdes.kCardOrderEntitySerde()));

        KS0
                .mapValues((key, kCardOrderEntity) -> {
                    log.info(">>>> Processing Operation Id {} Started ... <<<<", key);
                    CardEntity cardEntity = null;
                    try {
                        cardEntity = cardService.onboardOrder(kCardOrderEntity);
                    } catch (Exception ex) {
                        log.info(">>>> Processing Operation Id {} Failed,Reason {} ... <<<<", key, ex.getMessage());
                        ex.printStackTrace();
                        return null;
                    }
                    var result = CardTransformer.toKafkaEntity(cardEntity);
                    log.info(">>>> Processing PackageID {} Finished ... <<<<", key);
                    return result;
                })
                .to(kafkaConfig.getProducerTopic(), Produced.with(AppSerdes.String(), AppSerdes.cardEntitySerde()));

        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);

        final KafkaStreamsHealthIndicator healthIndicator = context.getBean(KafkaStreamsHealthIndicator.class);

        streams.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> {
            healthIndicator.setException(new Exception(throwable));
        });
        healthIndicator.setKafkaStreams(streams);

        streams.start();
        log.info(" >>>> Kafka streams started ... <<<< ");
        log.info("Stream is ready to listen to onboard card orders (^_^) ...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            log.info(" >>>> Kafka streams was closed ... <<<< ");
        }));

    }
}
