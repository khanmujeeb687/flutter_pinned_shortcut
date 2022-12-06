package com.mintech.pinned_shortcut.flutter_pinned_shortcut;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.security.acl.Acl;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * FlutterPinnedShortcutPlugin
 */
public class FlutterPinnedShortcutPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private Activity activity;
    private PinnedShortcutUtil pinnedShortcutUtil;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        context = flutterPluginBinding.getApplicationContext();
        pinnedShortcutUtil = new PinnedShortcutUtil(context);
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_pinned_shortcut");
        channel.setMethodCallHandler(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("createPinnedShortcut")) {
            result.success(pinnedShortcutUtil.setShortcutItem(call));
        } else if (call.method.equals("getLaunchAction")) {
            pinnedShortcutUtil.getLaunchAction(result, activity);
        } else {
            result.notImplemented();
        }
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity  = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        activity  = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {

    }
}
