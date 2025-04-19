package com.huashanlunjian.amara.utils.sounds;

import com.huashanlunjian.amara.Amara;
import com.huashanlunjian.amara.utils.spi.mpeg.sampled.convert.MpegFormatConversionProvider;
import com.huashanlunjian.amara.utils.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class MpegPlayer {
    private static volatile CompletableFuture<Void> currentFuture;
    private static final AtomicBoolean isPlaying = new AtomicBoolean(false);
    public static CompletableFuture<Void> playAsync(String filePath) {
        stop();

        return CompletableFuture.runAsync(() -> {
            isPlaying.set(true);
            try (AudioInputStream audioStream = new MpegAudioFileReader().getAudioInputStream(new File(filePath))) {

                AudioFormat baseFormat = audioStream.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false
                );

                try (SourceDataLine line = AudioSystem.getSourceDataLine(decodedFormat);
                     AudioInputStream convertedStream = new MpegFormatConversionProvider().getAudioInputStream(decodedFormat, audioStream)) {

                    line.open(decodedFormat);
                    line.start();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while (isPlaying.get() && (bytesRead = convertedStream.read(buffer)) != -1) {
                        line.write(buffer, 0, bytesRead);
                    }
                    line.drain();
                }
            } catch (Exception e) {
                if (!(e.getCause() instanceof InterruptedException)) {
                    Amara.LOGGER.error("Error playing sound: {}", filePath, e);
                }
            } finally {
                isPlaying.set(false);
            }
        }).whenComplete((result, ex) -> {
            if (ex != null) {
                Amara.LOGGER.error("Playback failed: {}", ex.getMessage());
            }
        });
    }
    public static void play(String filePath) {
        currentFuture = playAsync(filePath);
    }

    public static void stop() {
        isPlaying.set(false);
        if (currentFuture != null && !currentFuture.isDone()) {
            currentFuture.cancel(true); // 中断正在执行的线程
        }
    }
}
