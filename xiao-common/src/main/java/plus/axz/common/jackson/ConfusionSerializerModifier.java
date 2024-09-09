package plus.axz.common.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import plus.axz.model.common.annotation.IdEncrypt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxiang
 * description 用于过滤序列化时处理的字段
 */
public class ConfusionSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        List<BeanPropertyWriter> newWriter = new ArrayList<>();
        for (BeanPropertyWriter writer : beanProperties) {
            String name = writer.getType().getTypeName();
            // 针对哪些字段进行处理
            // 1. 标有IdEncrypt注解的
            // 2. 属性是id的
            if (null == writer.getAnnotation(IdEncrypt.class) && !writer.getName().equalsIgnoreCase("id")) {
                newWriter.add(writer);
            } else {
                writer.assignSerializer(new ConfusionSerializer());
                newWriter.add(writer);
            }
        }
        return newWriter;
    }
}
