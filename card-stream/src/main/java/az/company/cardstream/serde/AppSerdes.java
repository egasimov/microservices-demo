package az.company.cardstream.serde;

import az.company.cardstream.type.KCardEntity;
import az.company.cardstream.type.KCardOrderEntity;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for Serdes
 */

public class AppSerdes extends Serdes {


    static final class KCardEntitySerde extends WrapperSerde<KCardEntity> {
        KCardEntitySerde() {
            super(new JsonSerializer<KCardEntity>(), new JsonDeserializer<KCardEntity>());
        }
    }

    public static Serde<KCardEntity> cardEntitySerde() {
        KCardEntitySerde serde = new KCardEntitySerde();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, KCardEntity.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }


    static final class KCardOrderEntitySerde extends WrapperSerde<KCardOrderEntity> {
        KCardOrderEntitySerde() {
            super(new JsonSerializer<KCardOrderEntity>(), new JsonDeserializer<KCardOrderEntity>());
        }
    }

    public static Serde<KCardOrderEntity> kCardOrderEntitySerde() {
        KCardOrderEntitySerde serde = new KCardOrderEntitySerde();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, KCardOrderEntity.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }


}
