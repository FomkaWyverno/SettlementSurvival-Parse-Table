package ua.wyverno.table;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.localization.ArrayOfLangConfig;
import ua.wyverno.localization.Lang;
import ua.wyverno.localization.LangConfig;
import ua.wyverno.localization.LangList;
import ua.wyverno.util.TableUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Iterator;

public class TableSettlement {
    private static final Logger logger = LoggerFactory.getLogger(TableSettlement.class);

    private ArrayOfLangConfig langConfig;
    public TableSettlement(String urlTable) throws IOException {
        Workbook workbook = new XSSFWorkbook(URI.create(urlTable).toURL().openStream());
        DataFormatter formatter = new DataFormatter();

        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        this.langConfig = new ArrayOfLangConfig();

        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();

            logger.trace("--------------------------");
            logger.trace("Table-name: {}", sheet.getSheetName());
            logger.trace("--------------------------");

            logger.trace("Start parsing!");

            TableHeader tableHeader = new TableHeader(sheet.getRow(0));

            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next(); // skip first row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (!TableUtil.isEmptyRow(row)) {
                    String id = formatter.formatCellValue(row.getCell(tableHeader.getColumnIndexByName("ID")));
                    String translate = formatter.formatCellValue(row.getCell(tableHeader.getColumnIndexByName("Translate")));

                    logger.info(String.format("ID: %-25.25s\tTranslate: %-50.50s", id, translate));

                    if (!translate.isEmpty()) {
                        this.langConfig.getConfigs().add(new LangConfig(id, new LangList(new Lang("Ukrainian", translate))));
                        logger.debug("Translate has for ID: {}", id);
                    } else {
                        logger.debug("Translate not has for ID: {}", id);
                    }
                }
            }
        }
    }

    public ArrayOfLangConfig getLangConfig() {
        return langConfig;
    }
}
