package ua.wyverno.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ua.wyverno.Main;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.Lang;
import ua.wyverno.localization.LangConfig;
import ua.wyverno.localization.LangList;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonArrayOfLangConfigDeserializer extends JsonDeserializer<ArrayOfLangConfig> {
    @Override
    public ArrayOfLangConfig deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ArrayOfLangConfig arrayOfLangConfig = new ArrayOfLangConfig();
        List<LangConfig> configs = arrayOfLangConfig.getConfigs();

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            LangConfig langConfig = new LangConfig(entry.getKey(), new LangList(new Lang("Ukrainian", entry.getValue().asText())));

            configs.add(langConfig);
        }

        return arrayOfLangConfig;
    }
}
