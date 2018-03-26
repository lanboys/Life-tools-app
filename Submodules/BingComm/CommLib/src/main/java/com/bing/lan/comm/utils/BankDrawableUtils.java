package com.bing.lan.comm.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.bing.lan.comm.R;

/**
 * Author: yxhuang
 * Date: 2017/4/28
 * Email: yxhuang@gmail.com
 */

public class BankDrawableUtils {

    /**
     * 获取银行 LOGO
     *
     * @param context
     * @param bankAcronym 银行缩写
     * @return
     */
    public static Drawable getBanKDrawable(Context context, String bankAcronym) {
        //PayLog.i("BankDrawableUtils", " 对应的银行卡图片 bankAcronym: " + bankAcronym);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.iv_logo);

        if (TextUtils.isEmpty(bankAcronym)) {
            return drawable;
        }

        switch (bankAcronym) {
            case "SRCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.srcb);
                break;

            case "BJBANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.bjbank);
                break;

            case "SPABANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.spabank);
                break;

            case "CZBANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.czbank);
                break;

            case "BOC":
                drawable = ContextCompat.getDrawable(context, R.drawable.boc);
                break;

            case "BOD":
                drawable = ContextCompat.getDrawable(context, R.drawable.bod);
                break;

            case "CCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.ccb);
                break;

            case "GRCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.grcb);
                break;

            case "HBC":
                drawable = ContextCompat.getDrawable(context, R.drawable.hbc);
                break;

            case "NJCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.njcb);
                break;

            case "ZZBANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.zzbank);
                break;

            case "CMB":
                drawable = ContextCompat.getDrawable(context, R.drawable.cmb);
                break;

            case "CEB":
                drawable = ContextCompat.getDrawable(context, R.drawable.ceb);
                break;

            case "GCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.gcb);
                break;

            case "XABANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.xabank);
                break;

            case "COMM":
                drawable = ContextCompat.getDrawable(context, R.drawable.comm);
                break;

            case "CITIC":
                drawable = ContextCompat.getDrawable(context, R.drawable.citic);
                break;

            case "HXBANK":
                drawable = ContextCompat.getDrawable(context, R.drawable.hxbank);
                break;

            case "CDCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.cdcb);
                break;

            case "CMBC":
                drawable = ContextCompat.getDrawable(context, R.drawable.cmbc);
                break;

            case "GDB":
                drawable = ContextCompat.getDrawable(context, R.drawable.gdb);
                break;

            case "ICBC":
                drawable = ContextCompat.getDrawable(context, R.drawable.icbc);
                break;

            case "ABC":
                drawable = ContextCompat.getDrawable(context, R.drawable.abc);
                break;

            case "PSBC":
                drawable = ContextCompat.getDrawable(context, R.drawable.psbc);
                break;

            case "CSCB":
                drawable = ContextCompat.getDrawable(context, R.drawable.cscb);
                break;

            case "BHB":
                drawable = ContextCompat.getDrawable(context, R.drawable.bhb);
                break;

            default:
                //PayLog.e("BankDrawableUtils", " !!!!!!! 没有对应的银行卡图片 bankAcronym: " + bankAcronym);
                break;
        }

        return drawable;
    }

    /**
     *   {
     "SRCB": "深圳农村商业银行",

     "BJBANK": "北京银行",

     "SPABANK": "平安银行",

     "CZBANK": "浙商银行",

     "BOC": "中国银行",
     "BOD": "东莞银行",
     "CCB": "中国建设银行",

     "GRCB": "广州农商银行",

     "HBC": "湖北银行",

     "NJCB": "南京银行",
     "ZZBANK": "郑州银行",

     "CMB": "招商银行",

     "CEB": "中国光大银行",

     "GCB": "广州银行",


     "XABANK": "西安银行",

     "COMM": "交通银行",

     "CITIC": "中信银行",
     "HXBANK": "华夏银行",

     "CDCB": "成都银行",

     "CMBC": "中国民生银行",

     "GDB": "广东发展银行",

     "ICBC": "中国工商银行",

     "ABC": "中国农业银行",

     "PSBC": "中国邮政储蓄银行",

     "CSCB": "长沙银行",

     "BHB": "河北银行",

     }
     */
}
