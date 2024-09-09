package plus.axz.common.jackson;

import plus.axz.model.common.annotation.IdEncrypt;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

import java.util.Iterator;

/**
 * @author xiaoxiang
 * description 用于过滤反序列化时处理的字段
 */
public class ConfusionDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public BeanDeserializerBuilder updateBuilder(final DeserializationConfig config, final BeanDescription beanDescription, final BeanDeserializerBuilder builder) {
        Iterator it = builder.getProperties();
        while (it.hasNext()) {
            SettableBeanProperty p = (SettableBeanProperty) it.next();
            if ((null != p.getAnnotation(IdEncrypt.class)||p.getName().equalsIgnoreCase("id"))) {
                JsonDeserializer<Object> current = p.getValueDeserializer();
                builder.addOrReplaceProperty(p.withValueDeserializer(new ConfusionDeserializer(p.getValueDeserializer(),p.getType())), true);
            }
        }
        return builder;
    }
}
