package com.base.utils.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.mvp.R;

/**
 * 为程序创建桌面快捷方式
 *
 * android 创建快捷方式的两种方式+判断是否已经创建+删除快捷方式
 *1.通过注册创建
 *  <activity
 android:name="com.android.master.legend.widget.CreateSystemSettingsWidgetActivity"
 android:exported="true"
 android:icon="@drawable/ic_switcher_shortcut"
 android:label="@string/system_switcher_shortcut"
 android:screenOrientation="portrait"
 android:theme="@android:style/Theme.Translucent.NoTitleBar" >
 <intent-filter>
 <action android:name="android.intent.action.CREATE_SHORTCUT" />
 <category android:name="android.intent.category.DEFAULT" />
 </intent-filter>
 </activity>
 *
 *
 */
public class ShortcutUtils {


    /**
     * 为程序创建桌面快捷方式
     */
    public static  void createShortcut(Context con) {
        if(hasShortcut(con))
            return ;
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, con.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); //不允许重复创建

        /*****解决有些手机 点击快捷方式启动的是新应用而不是已经在内存中的应用******/
//        ComponentName comp = new ComponentName(con.getPackageName(), con.getPackageName() + "." +con.getLocalClassName());
//        Intent intent = new Intent(Intent.ACTION_MAIN).setComponent(comp);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        /*****end******/

        /****************************此方法已失效*************************/
        //ComponentName comp = new ComponentName(this.getPackageName(), "."+this.getLocalClassName());
        //shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));  　　
        /******************************end*******************************/
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(con, con.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        //快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(con, R.drawable.logo);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        con.sendBroadcast(shortcut);
        //这里不需要toast提示,系统会自动给你toast
    }

        public static boolean hasShortcut(Context context_) {
        boolean isInstallShortcut = false;
        final ContentResolver cr = context_.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?",
                new String[]{context_.getString(R.string.app_name).trim()}, null);
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        return isInstallShortcut;
    }
}
