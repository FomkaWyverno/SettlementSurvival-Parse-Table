package ua.wyverno;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.Lang;
import ua.wyverno.localization.LangConfig;
import ua.wyverno.localization.LangList;
import ua.wyverno.table.TableSettlement;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class Main {

    private static final Path xmlPathUkr = Paths.get("Lang_Ukrainian.xml");
    private static final Path jsonOriginalPath = Paths.get("Lang_English-original.json");
    private static final Path jsonOriginalWithCode = Paths.get("Lang_English-with-code.json");

    public static void main(String[] args ) throws IOException {
        TableSettlement tableSettlement = new TableSettlement(Config.getInstance().getTableUrl());

        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();

        Path originalJson = Config.getInstance().isWithCode() ? jsonOriginalWithCode : jsonOriginalPath;
        ArrayOfLangConfig originalLangConfig = jsonMapper.readValue(originalJson.toFile(), ArrayOfLangConfig.class);
        ArrayOfLangConfig tableLangConfig = tableSettlement.getLangConfig();
        ArrayOfLangConfig mergingLangConfig = mergingLangConfig(originalLangConfig, tableLangConfig);
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(xmlPathUkr.toFile(), mergingLangConfig);

//        XmlMapper xmlMapper = new XmlMapper();
//        ArrayOfLangConfig arrayOfLangConfig = xmlMapper.readValue(new File("Lang_Ukrainian-with-code.xml"), ArrayOfLangConfig.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("Lang_English-with-code.json"), arrayOfLangConfig);
    }

    public static ArrayOfLangConfig mergingLangConfig(ArrayOfLangConfig original, ArrayOfLangConfig secondArray) {
        ArrayOfLangConfig merging = new ArrayOfLangConfig();
        original.getConfigs().forEach(lConfig -> {
            String id = lConfig.id();
            boolean hasKey = false;
            for (LangConfig secondLConfig : secondArray.getConfigs()) {
                if (id.equals(secondLConfig.id())) {
                    hasKey = true;
                    merging.getConfigs().add(new LangConfig(id,
                            new LangList(new Lang("Ukrainian", secondLConfig.langList().lang().langValue()))));
                    break;
                }
            }

            if (!hasKey) {
                merging.getConfigs().add(lConfig);
            }
        });

        return merging;
    }
}
