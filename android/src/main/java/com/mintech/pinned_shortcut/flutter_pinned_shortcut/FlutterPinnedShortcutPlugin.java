package com.mintech.pinned_shortcut.flutter_pinned_shortcut;

import android.content.pm.ShortcutManager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterPinnedShortcutPlugin */
public class FlutterPinnedShortcutPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_pinned_shortcut");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  private createPinnedShortcut(){
    ShortcutManager shortcutManager =
            context.getSystemService(ShortcutManager.class);

    if (shortcutManager.isRequestPinShortcutSupported()) {
      // Assumes there's already a shortcut with the ID "my-shortcut".
      // The shortcut must be enabled.
      ShortcutInfo pinShortcutInfo =
              new ShortcutInfo.Builder(context, "my-shortcut").build();

      // Create the PendingIntent object only if your app needs to be notified
      // that the user allowed the shortcut to be pinned. Note that, if the
      // pinning operation fails, your app isn't notified. We assume here that the
      // app has implemented a method called createShortcutResultIntent() that
      // returns a broadcast intent.
      Intent pinnedShortcutCallbackIntent =
              shortcutManager.createShortcutResultIntent(pinShortcutInfo);

      // Configure the intent so that your app's broadcast receiver gets
      // the callback successfully.For details, see PendingIntent.getBroadcast().
      PendingIntent successCallback = PendingIntent.getBroadcast(context, /* request code */ 0,
              pinnedShortcutCallbackIntent, /* flags */ 0);

      shortcutManager.requestPinShortcut(pinShortcutInfo,
              successCallback.getIntentSender());
    }


  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
