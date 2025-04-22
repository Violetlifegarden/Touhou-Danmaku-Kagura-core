package com.huashanlunjian.amara.music_game_extension;

import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.function.Function;
/*"note": [
 *           {
 *               "type": "tap",
 *               "time": 0,
 *               "xspeed": 0,
 *               "yspeed": 0,
 *               "zspeed": 0,
 *               "events": [
 *                   {
 *                       "type": "render",
 *                       "functions": "",
 *                       "args": [1,2,3,...]
 *                   },
 *                   {
 *                       "type": "change_speed",
 *                       "functions": "example",
 *                       "args": [1,2,3]
 *                   }
 *               ]
 *           }
 *       ],
*/

/**这个类用来储存关于Note曲线运动的函数.
 * 输入的数据是从谱面读取的，
 * 输出由x,y,z组成的三元数组。
 * 实际上，由于每个note存在的时间很短，
 * 没必要单独设定一个时间戳。每个note至多只需要其中的一个函数即可.*/
public final class NoteMoveFunctions {

    private static final Map<String, Function<double[], Vec3>> noteMoveFunctions = Map.of(
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
        return noteMoveFunctions.get(propertyName);
    }
}
