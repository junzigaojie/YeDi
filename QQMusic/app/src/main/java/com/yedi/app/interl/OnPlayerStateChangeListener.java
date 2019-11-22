package com.yedi.app.interl;

import com.yedi.app.bean.MusicInfo;

import java.util.List;

public interface OnPlayerStateChangeListener {
    void onStateChange(int state, int mode, List<MusicInfo> musicList,
                       int position);
}
