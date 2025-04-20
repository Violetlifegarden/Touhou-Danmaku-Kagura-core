package com.huashanlunjian.amara.music_game_core;

import com.huashanlunjian.amara.utils.ChartUtil;

import javax.annotation.Nullable;
import java.nio.file.Path;


/**这个类事实上可能没有用处了，完全可以直接用AbstractChart替代。
 * 转换工作还得考虑icon和歌曲的加载方式。*/
@Deprecated
public class SongsSummary {
    private final String SongsId;
    private final Path icon;
    private final Path audiofile;
    @Nullable
    private final AbstractChart chart;
    private final String chartPath;

    public SongsSummary(String songsId, Path icon, Path audiofile,Path chartfile) {
        SongsId = songsId;
        this.icon = icon;
        this.audiofile = audiofile;
        this.chartPath = chartfile.toString();
        this.chart = ChartUtil.loadChart(chartfile);
    }

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
    @Nullable
    public String getCharter() {
        return chart.getCharter();
    }
    public String getTitle() {
        return chart.getTitle();
    }


}
