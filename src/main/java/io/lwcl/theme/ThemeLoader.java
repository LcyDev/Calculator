package io.lwcl.theme;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.lwcl.theme.properties.Theme;
import io.lwcl.theme.properties.ThemeList;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class ThemeLoader {

    private ThemeLoader() {
        throw new AssertionError("Constructor is not allowed");
    }

    public static Map<String, Theme> loadThemes() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        ObjectMapper mapperJSON = new ObjectMapper(new JsonFactory());

        mapper.findAndRegisterModules();
//        mapperJSON.findAndRegisterModules();
        try {
//            ThemeList themeListJSON = mapperJSON.readValue(new File("src/main/resources/themes.json"), ThemeList.class);

            ThemeList themeList = mapper.readValue(new File("src/main/resources/themes.yml"), ThemeList.class);
            return themeList.getThemesAsMap();
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }
}
