package com.huashanlunjian.amara.utils.sounds;

import com.huashanlunjian.amara.Amara;
import com.huashanlunjian.amara.utils.spi.vorbis.sampled.convert.VorbisFormatConversionProvider;
import com.huashanlunjian.amara.utils.spi.vorbis.sampled.file.VorbisAudioFileReader;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class OggPlayer {
    private static volatile CompletableFuture<Void> currentFuture;
    private static final AtomicBoolean isPlaying = new AtomicBoolean(false);

    public static CompletableFuture<Void> playAsync(String filePath) {
        stop();

        return CompletableFuture.runAsync(() -> {
            isPlaying.set(true);
            try (AudioInputStream audioStream = new VorbisAudioFileReader().getAudioInputStream(new File(filePath))) {

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
                     AudioInputStream convertedStream = new VorbisFormatConversionProvider().getAudioInputStream(decodedFormat, audioStream)) {

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