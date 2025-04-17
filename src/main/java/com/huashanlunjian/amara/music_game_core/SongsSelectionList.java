package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.screen.gui.SelectSongsScreen;
import com.huashanlunjian.amara.utils.ChartCategories;
import com.huashanlunjian.amara.utils.MiscUtils;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.screens.FaviconTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

import static com.huashanlunjian.amara.utils.FileUtil.containsFileWithExtension;
/**
 * <p>
 *     歌单
 * </p>
 *
 * @author 华山抡舰
 * @since 2025/4/6 10:08
 */

public class SongsSelectionList extends ObjectSelectionList<SongsSelectionList.Entry> {

    private final SelectSongsScreen screen;
    /**这里有一个问题是如果没有该文件夹是否主动创建*/
    private final Path songsdir = Path.of("./songs");
    private CompletableFuture<List<SongsSummary>> songslist;
    private List<SongsSummary> currentlyDisplayedLevels;

    /**这里有一个问题是是否要每次加载list时都要加载一次songs文件夹
    * 如果歌太多每次加载都会很卡，如果只加载一次那么歌曲列表就不能及时更新。
    * 如果改成只加载一次，那么加载任务应该再创建单独的一个类完成*/

    public SongsSelectionList(
            SelectSongsScreen screen,
            Minecraft minecraft,
            int width,
            int height,
            int y,
            int itemHeight,
            String filter,
            @Nullable SongsSelectionList worlds

    ) {
        super(minecraft, width, height, y, itemHeight);
        this.screen = screen;
        this.songslist = loadSongs();
        this.handleNewLevels(this.pollSongsIgnoreErrors());

        this.songslist.whenComplete((list, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            Minecraft.getInstance().execute(() -> {
                this.handleNewLevels(list);
                this.notifyListUpdated();
            });
        });

    }
    /**歌曲搜索功能还没有实现*/
    private List<SongsDirectory> loadSongsList() {
        if (!Files.isDirectory(this.songsdir)) {
            throw new LevelStorageException(Component.translatable("selectWorld.load_folder_access"));
        }else {
            try {
                return Files.list(this.songsdir).filter(Files::isDirectory)
                        .map(SongsDirectory::new)
                        .filter(songsDirectory -> {
                            try {
                                return Files.isRegularFile(songsDirectory.chartFile());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();


            } catch (IOException e) {
                throw new LevelStorageException(Component.translatable("selectWorld.load_folder_access"));
            }
        }
    }
    private CompletableFuture<List<SongsSummary>> loadSongs() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return loadSongsList().stream()
                        .map(songsDirectory -> {
                            try {
                                return new SongsSummary( songsDirectory.directoryName(), songsDirectory.iconFile(), songsDirectory.audioFile(),songsDirectory.chartFile());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Nullable
    private List<SongsSummary> pollSongsIgnoreErrors() {
        try {
            return this.songslist.getNow(null);
        } catch (CancellationException | CompletionException completionexception) {
            return null;
        }
    }
    @Deprecated
    void reloadWorldList() {
        this.songslist = this.loadSongs();
    }
    public SelectSongsScreen getScreen() {
        return this.screen;
    }

    private void handleNewLevels(@Nullable List<SongsSummary> levels) {
        if (levels == null) {
            return;
        } else {
            this.fillLevels(levels);
        }

        this.currentlyDisplayedLevels = levels;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (CommonInputs.selected(keyCode)) {
            Optional<SongsListEntry> optional = this.getSelectedOpt();
            if (optional.isPresent()) {
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                optional.get().summary.getAudiofile();
                MiscUtils.enterMusicGame(optional.get().summary, this.minecraft.player);
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    protected void clearEntries() {
        this.children().forEach(Entry::close);
        super.clearEntries();
    }
    public Optional<SongsListEntry> getSelectedOpt() {
        Entry worldselectionlist$entry = this.getSelected();
        return worldselectionlist$entry instanceof SongsListEntry worldselectionlist$worldlistentry
                ? Optional.of(worldselectionlist$worldlistentry)
                : Optional.empty();
    }
    private void fillLevels( List<SongsSummary> levels) {
        this.clearEntries();


        for (SongsSummary songssummary : levels) {
            this.addEntry(new SongsListEntry(this, songssummary));

        }

        this.notifyListUpdated();
    }
    private void notifyListUpdated() {
        this.clampScrollAmount();
        this.screen.triggerImmediateNarration(true);
    }
    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        List<SongsSummary> list = this.pollSongsIgnoreErrors();
        if (list != this.currentlyDisplayedLevels) {
            this.handleNewLevels(list);
        }

        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class Entry extends ObjectSelectionList.Entry<Entry> implements AutoCloseable{


        /**
         * Closes this resource, relinquishing any underlying resources.
         * This method is invoked automatically on objects managed by the
         * {@code try}-with-resources statement.
         *
         * @throws Exception if this resource cannot be closed
         * @apiNote While this interface method is declared to throw {@code
         * Exception}, implementers are <em>strongly</em> encouraged to
         * declare concrete implementations of the {@code close} method to
         * throw more specific exceptions, or to throw no exception at all
         * if the close operation cannot fail.
         *
         * <p> Cases where the close operation may fail require careful
         * attention by implementers. It is strongly advised to relinquish
         * the underlying resources and to internally <em>mark</em> the
         * resource as closed, prior to throwing the exception. The {@code
         * close} method is unlikely to be invoked more than once and so
         * this ensures that the resources are released in a timely manner.
         * Furthermore it reduces problems that could arise when the resource
         * wraps, or is wrapped, by another resource.
         *
         * <p><em>Implementers of this interface are also strongly advised
         * to not have the {@code close} method throw {@link
         * InterruptedException}.</em>
         * <p>
         * This exception interacts with a thread's interrupted status,
         * and runtime misbehavior is likely to occur if an {@code
         * InterruptedException} is {@linkplain Throwable#addSuppressed
         * suppressed}.
         * <p>
         * More generally, if it would cause problems for an
         * exception to be suppressed, the {@code AutoCloseable.close}
         * method should not throw it.
         *
         * <p>Note that unlike the {@link Closeable#close close}
         * method of {@link Closeable}, this {@code close} method
         * is <em>not</em> required to be idempotent.  In other words,
         * calling this {@code close} method more than once may have some
         * visible side effect, unlike {@code Closeable.close} which is
         * required to have no effect if called more than once.
         * <p>
         * However, implementers of this interface are strongly encouraged
         * to make their {@code close} methods idempotent.
         */
        @Override
        public void close()  {
        }
    }

    @OnlyIn(Dist.CLIENT)
    public final class SongsListEntry extends Entry implements AutoCloseable {
        private static final int ICON_WIDTH = 32;
        private static final int ICON_HEIGHT = 32;
        private final Minecraft minecraft;
        private final SelectSongsScreen screen;
        final SongsSummary summary;
        private final FaviconTexture icon;
        @Nullable
        private Path iconFile;
        private long lastClickTime;

        public SongsListEntry(SongsSelectionList worldSelectionList, SongsSummary summary) {
            this.minecraft = worldSelectionList.minecraft;
            this.screen = worldSelectionList.getScreen();
            this.summary = summary;
            this.icon = FaviconTexture.forWorld(this.minecraft.getTextureManager(), summary.getSongsId());
            this.iconFile = summary.getIcon();
            this.validateIconFile();
            this.loadIcon();
        }
        private void validateIconFile() {
            if (this.iconFile != null) {
                try {
                    BasicFileAttributes basicfileattributes = Files.readAttributes(this.iconFile, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    if (basicfileattributes.isSymbolicLink()) {
                        List<ForbiddenSymlinkInfo> list = this.minecraft.directoryValidator().validateSymlink(this.iconFile);
                        if (!list.isEmpty()) {
                            this.iconFile = null;
                        } else {
                            basicfileattributes = Files.readAttributes(this.iconFile, BasicFileAttributes.class);
                        }
                    }

                    if (!basicfileattributes.isRegularFile()) {
                        this.iconFile = null;
                    }
                } catch (NoSuchFileException nosuchfileexception) {
                    this.iconFile = null;
                } catch (IOException ioexception) {
                    this.iconFile = null;
                }
            }
        }

        @Override
        public void render(
                GuiGraphics guiGraphics,
                int index,
                int top,
                int left,
                int width,
                int height,
                int mouseX,
                int mouseY,
                boolean hovering,
                float partialTick
        ) {
            String s = this.summary.getTitle();
            String s1 = this.summary.getArtist();

            if (StringUtils.isEmpty(s)) {
                s = I18n.get("selectWorld.world") + " " + (index + 1);
            }

            String component = this.summary.getCharter();
            guiGraphics.drawString(this.minecraft.font, s, left + 32 + 3, top + 1, 16777215, false);
            guiGraphics.drawString(this.minecraft.font, s1, left + 32 + 3, top + 9 + 3, -8355712, false);
            guiGraphics.drawString(this.minecraft.font, component, left + 32 + 3, top + 9 + 9 + 3, -8355712, false);
            RenderSystem.enableBlend();
            guiGraphics.blit(this.icon.textureLocation(), left, top, 0.0F, 0.0F, 32, 32, 32, 32);
            RenderSystem.disableBlend();
            if (this.minecraft.options.touchscreen().get() || hovering) {
                guiGraphics.fill(left, top, left + 32, top + 32, -1601138544);
            }
        }

        @Override
        public @NotNull Component getNarration() {
            Component component = Component.translatable(
                    "screenshot.failure",
                    this.summary.getSongsId(),
                    this.summary.getCharter()
            );

            return Component.translatable("narrator.select", component);

        }
        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {

            SongsSelectionList.this.setSelected(this);
            if (!(mouseX - (double)SongsSelectionList.this.getRowLeft() <= 32.0) && Util.getMillis() - this.lastClickTime >= 250L) {
                this.lastClickTime = Util.getMillis();
                return super.mouseClicked(mouseX, mouseY, button);
            } else {
                /**
                 * 这里进入新维度*/
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                MiscUtils.enterMusicGame(this.summary, this.minecraft.player);
                return true;
            }

        }
        private void loadIcon() {
            boolean flag = this.iconFile != null && Files.isRegularFile(this.iconFile);
            if (flag) {
                try (InputStream inputstream = Files.newInputStream(this.iconFile)) {
                    this.icon.upload(NativeImage.read(inputstream));
                } catch (Throwable throwable) {
                    this.iconFile = null;
                }
            } else {
                this.icon.clear();
            }
        }

        @Override
        public void close() {
            this.icon.close();
        }

        public SongsSummary getSummary() {
            return this.summary;
        }
    }

    @Deprecated
    public record SongsDirectory(Path path) {
        public String directoryName() {
            return this.path.getFileName().toString();
        }

        /*这个类将在之后的版本被移除。*/
        public Path chartFile() throws IOException {
            //return this.resourcePath("malody.mc");
            return this.resourcePath(getChart());
        }


        public Path iconFile() {
            return this.resourcePath("icon.png");
        }

        public Path audioFile() {
            return this.resourcePath("audio.ogg");
        }

        public Path resourcePath(String resource) {
            return this.path.resolve(resource);
        }
        private static List<String> findFilesByExtension(String dirPath, String extension) throws IOException {
            Path startPath = Paths.get(dirPath);

            return Files.walk(startPath)
                    .filter(Files::isRegularFile)          // 过滤出普通文件
                    .filter(p -> p.toString().toLowerCase().endsWith(extension.toLowerCase())) // 不区分大小写匹配扩展名
                    .map(p->p.getFileName().toString())                   // 转换为字符串
                    .collect(Collectors.toList());
        }


        private String getChart() throws IOException {
            //.amara是默认谱面文件
            if (containsFileWithExtension(this.path, ChartCategories.getChartCategoryByPath(this.path).getCategory()))return findFilesByExtension(String.valueOf(this.path), ChartCategories.getChartCategoryByPath(this.path).getCategory()).getFirst();
            else throw new FileNotFoundException("谱面格式错误");
        }

    }
}
