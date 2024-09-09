package plus.axz.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * @author xiaoxiang
 * description: 用于序列化自增数字的混淆
 */
@Log4j2
public class ConfusionSerializer extends JsonSerializer<Object> {

    @Override
    public  void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, IOException {
        try {
            if (value != null) {
                jsonGenerator.writeString(value.toString());
                return;
            }
        }catch (Exception e){
            log.error("ConfusionSerializer error", e);
        }
        serializers.defaultSerializeValue(value, jsonGenerator);
    }
}
