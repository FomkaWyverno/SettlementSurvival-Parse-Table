package ua.wyverno.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.LangConfig;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class XmlArrayOfLangConfigDeserializer extends JsonDeserializer<ArrayOfLangConfig> {
    @Override
    public ArrayOfLangConfig deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ArrayOfLangConfig arrayOfLangConfig = new ArrayOfLangConfig();
        List<LangConfig> configs = arrayOfLangConfig.getConfigs();
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);


        JsonNode langConfigArray = rootNode.get("LangConfig");

        Iterator<JsonNode> elements = langConfigArray.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            LangConfig config = jsonParser.getCodec().readValue(element.traverse(), LangConfig.class);
            configs.add(config);
        }

        return arrayOfLangConfig;
    }
}
