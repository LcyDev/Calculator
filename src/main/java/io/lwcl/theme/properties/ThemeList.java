package io.lwcl.theme.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class ThemeList {

    private Set<Theme> themes;

    public Map<String, Theme> getThemesAsMap() {
        return themes.stream().collect(Collectors.toMap(Theme::getName, Function.identity()));
    }
}