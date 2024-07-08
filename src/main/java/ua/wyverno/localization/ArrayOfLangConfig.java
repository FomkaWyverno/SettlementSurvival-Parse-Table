package ua.wyverno.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.wyverno.jackson.deserializer.JsonArrayOfLangConfigDeserializer;
import ua.wyverno.jackson.deserializer.XmlArrayOfLangConfigDeserializer;
import ua.wyverno.jackson.serializer.ArrayOfLangConfigSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = ArrayOfLangConfigSerializer.class)
@JsonDeserialize(using = JsonArrayOfLangConfigDeserializer.class)
public class ArrayOfLangConfig {

    @JsonProperty("LangConfig")
    private List<LangConfig> configs;

    public ArrayOfLangConfig() {
        this.configs = new ArrayList<>();
    }

    public List<LangConfig> getConfigs() {
        return configs;
    }
}
