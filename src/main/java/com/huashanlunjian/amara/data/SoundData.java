package com.huashanlunjian.amara.data;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;
@Deprecated
public record SoundData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
}
