package com.bing.lan.comm.utils.os;

import android.text.TextUtils;

/**
 * Created by 蓝兵 on 2017/10/23.
 * http://www.jianshu.com/p/6e6828755667
 * http://blog.csdn.net/lovelyelfpop/article/details/65440420
 */

public class OsUtils {


    //MIUI标识
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //EMUI标识
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    //Flyme标识
    private static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    private static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";

    /**
     * @param
     * @return ROM_TYPE ROM类型的枚举
     * @description获取ROM类型: MIUI_ROM, FLYME_ROM, EMUI_ROM, OTHER_ROM
     */

    public static ROM_TYPE getRomType() {
        ROM_TYPE rom_type = ROM_TYPE.OTHER;
        try {
            OsRomProperties osRomProperties = OsRomProperties.getInstance();

            if (osRomProperties.containsKey(KEY_EMUI_VERSION_CODE) || osRomProperties.containsKey(KEY_EMUI_API_LEVEL) || osRomProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)) {
                return ROM_TYPE.EMUI;
            }
            if (osRomProperties.containsKey(KEY_MIUI_VERSION_CODE) || osRomProperties.containsKey(KEY_MIUI_VERSION_NAME) || osRomProperties.containsKey(KEY_MIUI_VERSION_NAME)) {
                return ROM_TYPE.MIUI;
            }
            if (osRomProperties.containsKey(KEY_FLYME_ICON_FALG) || osRomProperties.containsKey(KEY_FLYME_SETUP_FALG) || osRomProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {
                return ROM_TYPE.FLYME;
            }
            if (osRomProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                String romName = osRomProperties.getProperty(KEY_FLYME_ID_FALG_KEY);
                if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                    return ROM_TYPE.FLYME;
                }
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }

        return rom_type;
    }

    public enum ROM_TYPE {
        MIUI,
        FLYME,
        EMUI,
        OTHER
    }
}
