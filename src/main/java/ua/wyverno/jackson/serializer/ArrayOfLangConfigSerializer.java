package ua.wyverno.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.LangConfig;

import java.io.IOException;
import java.util.List;

public class ArrayOfLangConfigSerializer extends JsonSerializer<ArrayOfLangConfig> {
    @Override
    public void serialize(ArrayOfLangConfig arrayOfLangConfig, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<LangConfig> configs = arrayOfLangConfig.getConfigs();

        jsonGenerator.writeStartObject();
        configs.forEach(lConfig -> {
            try {
                //jsonGenerator.writeStringField(lConfig.id(),lConfig.langList().lang().langValue());
                jsonGenerator.writeObjectField(lConfig.getClass().getSimpleName(), lConfig);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        jsonGenerator.writeEndObject();
    }
}
