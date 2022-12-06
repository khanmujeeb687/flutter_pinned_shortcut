package com.mintech.pinned_shortcut.flutter_pinned_shortcut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.Person;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class PinnedShortcutUtil {
    private static final String EXTRA_ACTION = "flutter_pinned_shortcuts";

    private final Context context;


    PinnedShortcutUtil(Context context) {
        this.context = context;
    }


    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public void getLaunchAction(MethodChannel.Result result, Activity activity) {
        if (activity == null) {
            result.error(
                    "flutter_pinned_shortcuts_no_activity",
                    "There is no activity available when launching action",
                    null);
            return;
        }
        final Intent intent = activity.getIntent();
        final String launchAction = intent.getStringExtra(EXTRA_ACTION);

        if (launchAction != null && !launchAction.isEmpty()) {
            ShortcutManagerCompat.reportShortcutUsed(context,launchAction);
            intent.removeExtra(EXTRA_ACTION);
        }

        result.success(launchAction);
    }


    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public String setShortcutItem(MethodCall call) {
        Map<String, Object> args = call.arguments();
        ShortcutInfoCompat shortcut;
        try {
            shortcut = shortcutInfoCompat(args);
            ShortcutManagerCompat.requestPinShortcut(context, shortcut, null);
            return "Success";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }


    /* ********************   Utility Functions   ********************* */

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private ShortcutInfoCompat shortcutInfoCompat(
            Map<String, Object> shortcut) {

        final String id = (String) shortcut.get("id");
        final String icon = (String) shortcut.get("icon");
        final String action = (String) shortcut.get("action");

        // Short Label for the shortcut
        // Name for Person
        final String shortLabel = (String) shortcut.get("shortLabel");


        final int iconType = 1;

        ShortcutInfoCompat.Builder shortcutInfoCompat = buildShortcutUsingCompat(
                id, icon, action, shortLabel, iconType);

        return shortcutInfoCompat.build();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private ShortcutInfoCompat.Builder buildShortcutUsingCompat(
            String id, String icon, String action, String shortLabel, int iconType) {

        assert id != null;
        ShortcutInfoCompat.Builder shortcutInfoCompat = new ShortcutInfoCompat.Builder(context, id);

        if (action != null) {
            Intent intent;
            intent = getIntentToOpenMainActivity(action);
            shortcutInfoCompat.setIntent(intent);
        }


        if (icon != null) {
            setIconFromFlutterCompat(shortcutInfoCompat, icon);
        }

        if (shortLabel != null) {
            shortcutInfoCompat.setShortLabel(shortLabel);
        }

        return shortcutInfoCompat;
    }


    private void setIconFromFlutterCompat(ShortcutInfoCompat.Builder shortcutBuilder, String icon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            shortcutBuilder.setIcon(IconCompat.createFromIcon(context, getIconFromFlutterAsset(context, icon)));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Icon getIconFromFlutterAsset(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        FlutterLoader loader = FlutterInjector.instance().flutterLoader();
        String key = loader.getLookupKeyForAsset(path);
        AssetFileDescriptor fd = null;
        try {
            fd = assetManager.openFd(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap image = null;
        try {
            assert fd != null;
            image = BitmapFactory.decodeStream(fd.createInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Icon.createWithAdaptiveBitmap(image);
    }


    private Intent getIntentToOpenMainActivity(String type) {
        final String packageName = context.getPackageName();

        return context
                .getPackageManager()
                .getLaunchIntentForPackage(packageName)
                .setAction(Intent.ACTION_RUN)
                .putExtra(EXTRA_ACTION, type)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}

