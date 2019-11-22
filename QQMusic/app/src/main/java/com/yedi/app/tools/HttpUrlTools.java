package com.yedi.app.tools;

import com.yedi.app.qqmusic.R;

public class HttpUrlTools {
    public static final String new_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=20&offset=0";
    public static final String dayhot_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=2&size=20&offset=0";
    public static final String ktv_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=6&size=20&offset=0";
    public static final String chizha_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=7&size=20&offset=0";
    public static final String billboard_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=8&size=20&offset=0";
    public static final String sprite_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=9&size=20&offset=0";
    public static final String rock_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=11&size=20&offset=0";
    public static final String yingshi_band="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=14&size=20&offset=0";


    public static final String []urls={new_band,dayhot_band,ktv_band,chizha_band,billboard_band,sprite_band,rock_band,yingshi_band};

    public static final String music_link="http://music.baidu.com/data/music/links?songIds=";

    private static final String cba="557631688";

    public static final int ONLINE=1;

    public static final int LOCAL=0;

    public static final String  [] title={"新歌榜","热歌榜","ktv榜","Billboard单曲榜","公告牌","雪碧音","摇滚榜","影视金曲"};

}
