package plus.axz.common.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xiaoxiang
 * description 用于注册模块和修改器
 */
public class ConfusionModule extends Module {

    public static final String MODULE_NAME = "jackson-confusion-encryption";
    public static final Version VERSION = new Version(1, 0, 0, null, "axz", MODULE_NAME);

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public Version version() {
        return VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addBeanSerializerModifier(new ConfusionSerializerModifier());
        context.addBeanDeserializerModifier(new ConfusionDeserializerModifier());
    }

    /**
     * 注册当前模块
     */
    public static ObjectMapper registerModule(ObjectMapper objectMapper) {
        //CamelCase策略，Java对象属性：personId，序列化后属性：persionId
        //PascalCase策略，Java对象属性：personId，序列化后属性：PersonId
        //SnakeCase策略，Java对象属性：personId，序列化后属性：person_id
        //KebabCase策略，Java对象属性：personId，序列化后属性：person-id
        // 忽略多余字段，抛错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return objectMapper.registerModule(new ConfusionModule());
    }
}
