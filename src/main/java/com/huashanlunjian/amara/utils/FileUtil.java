package com.huashanlunjian.amara.utils;

import com.huashanlunjian.amara.Amara;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static net.minecraft.resources.ResourceLocation.isValidNamespace;
import static net.minecraft.resources.ResourceLocation.isValidPath;

public class FileUtil {
    public static boolean isValidResourceLocation(String pLocation) {
        String[] astring = decompose(pLocation, ':');
        return isValidNamespace(StringUtils.isEmpty(astring[0]) ? "minecraft" : astring[0]) && isValidPath(astring[1]);
    }

    protected static String[] decompose(String pLocation, char pSeparator) {
        String[] astring = new String[]{"minecraft", pLocation};
        int i = pLocation.indexOf(pSeparator);
        if (i >= 0) {
            astring[1] = pLocation.substring(i + 1);
            if (i >= 1) {
                astring[0] = pLocation.substring(0, i);
            }
        }

        return astring;
    }

    public static ResourceLocation getResourceLocation(String pLocation) {
        return ResourceLocation.fromNamespaceAndPath(Amara.MOD_ID, pLocation);
    }
    /**这里说明一下，目前是加载单个谱面。如果是多个谱面需要再创建一个新的*/
    public static boolean containsFileWithExtension(Path dir, String extension) {
        if (!Files.isDirectory(dir)) return false;

        try (Stream<Path> files = Files.list(dir)) {
            return files
                    .filter(Files::isRegularFile)
                    .anyMatch(p -> p.getFileName().toString().endsWith(extension));
        }catch (IOException e){
            return false;
        }
    }
    public static boolean containsSimpleFileWithExtension(Path dir, String extension) {
        return dir.toString().endsWith(extension);
    }
}
