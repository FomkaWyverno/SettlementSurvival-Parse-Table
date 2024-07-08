package ua.wyverno;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.wyverno.jackson.deserializer.JsonArrayOfLangConfigDeserializer;
import ua.wyverno.jackson.deserializer.XmlArrayOfLangConfigDeserializer;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.Lang;
import ua.wyverno.localization.LangConfig;
import ua.wyverno.localization.LangList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main( String[] args ) throws IOException {
        Path jsonPath = Paths.get("settlement-survival-ua.json");
        Path xmlOriginal = Paths.get("settlement-survival-ua-original.json");
        Path xmlDebug = Paths.get("settlement-survival-debug.json");
        Path xmlPath = Paths.get("Lang_Ukrainian-with-code.xml");

        ObjectMapper mapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        ArrayOfLangConfig uaConfig = mapper.readValue(xmlOriginal.toFile(),
                ArrayOfLangConfig.class);

        ArrayOfLangConfig debugLangConfig = new ArrayOfLangConfig();
        AtomicInteger counter = new AtomicInteger(0);
        uaConfig.getConfigs().forEach(lConfig -> {
            String id = lConfig.id();
            String langType = "Ukrainian";
            String value = "!" + Integer.toString( counter.getAndIncrement(), 36) + "! " + lConfig.langList().lang().langValue();
            debugLangConfig.getConfigs().add(new LangConfig(id, new LangList(new Lang(langType, value))));
        });

        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlPath.toFile(), debugLangConfig);
        mapper.writerWithDefaultPrettyPrinter().writeValue(xmlDebug.toFile(), debugLangConfig);
    }
}
