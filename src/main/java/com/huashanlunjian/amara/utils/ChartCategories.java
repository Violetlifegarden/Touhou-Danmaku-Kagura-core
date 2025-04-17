package com.huashanlunjian.amara.utils;

import java.nio.file.Path;
import java.util.List;

import static com.huashanlunjian.amara.utils.FileUtil.containsFileWithExtension;
import static com.huashanlunjian.amara.utils.FileUtil.containsSimpleFileWithExtension;

public enum ChartCategories {
    DEFAULT(".json"),
    ARCAEA(".aff"),
    //PHIRA("json"),
    MALODY(".mc"),
    OSU(".osu");


    private final String category;

    ChartCategories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    public static ChartCategories getChartCategory(Path path) {
        //.chart是默认谱面文件
        for (ChartCategories extension : List.of(ChartCategories.values())){
            if (containsSimpleFileWithExtension(path, extension.getCategory())){
                return extension;
            }
        }
        return null;
    }
    public static ChartCategories getChartCategoryByPath(Path path) {
        //.chart是默认谱面文件
        for (ChartCategories extension : List.of(ChartCategories.values())){
            if (containsFileWithExtension(path, extension.getCategory())){
                return extension;
            }
        }
        return null;
    }
}
