package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.utils.ChartUtil;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.nio.file.Path;


/**这个类事实上可能没有用处了，完全可以直接用AbstractChart替代。
 * 转换工作还得考虑icon和歌曲的加载方式。*/
@Deprecated
public class SongsSummary {
    private final String SongsId;
    private final Path icon;
    private final Path audiofile;
    //private final String artist;
    @Nullable
    private Component info;
    private final AbstractChart chart;
    private final String chartPath;

    public SongsSummary(String songsId, Path icon, Path audiofile,Path chartfile) {
        SongsId = songsId;
        this.icon = icon;
        this.audiofile = audiofile;
        this.chartPath = chartfile.toString();
        this.chart = ChartUtil.loadChart(chartfile);
    }
    public AbstractChart getChart() {
        return chart;
    }

//    public AbstractChart getChart() {
//        return this.chart;
//    }
    public String getSongsId() {
        return SongsId;
    }
    public String getArtist() {
        return chart.getArtist();
    }

    public Path getIcon() {
        return icon;
    }

    public Path getAudiofile() {
        return audiofile;
    }
    public String getAudiofileInString() {
        return audiofile.toString();
    }
    public String getChartPath() {
        return chartPath;
    }
    /**这里我不知道该怎么写了，还得再提取接口，每种谱面一个SongsSummary？我原先设计的给chart提取接口，看来这个也要提取。这样代码显得太笨重了*/
    @Nullable
    public String getCharter() {
        return chart.getCharter();
    }
    public String getTitle() {
        return chart.getTitle();
    }

    private Component createInfo(){
        return info = Component.translatable("selectWorld.select");
        //还没写好
    }


}
