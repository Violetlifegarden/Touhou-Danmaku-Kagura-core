package com.huashanlunjian.amara.music_game_extension;

import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.Function;

/**我是这样考虑的，boss的移动方式和note完全不一样。
 * 所以虽然格式相同，还是要分开写*/
public final class BossMoveFunctions {
    private static final Map<String, Function<double[], Vec3>> bossMoveFunctions = Map.of(
            "example_function", (args) -> {
                args[0] = args[0] + 0.1;
                args[1] = args[1] + 0.1;
                args[2] = args[2] + 0.1;
                return new Vec3(args[0], args[1], args[2]);
            },//它是实现了一个向某方向加速的效果
            "example_function2", (args) -> {
                args[0] = args[0] + args[3];
                args[1] = args[1] + args[3];
                args[2] = args[2] + args[3];
                return new Vec3(args[0], args[1], args[2]);
            }//......
    );
    /**字典的key值是写在对应的json key中的，每个名字对应每个函数*/
    public static Function<double[], Vec3> getFunction(String propertyName) {
        return bossMoveFunctions.get(propertyName);
    }
}

