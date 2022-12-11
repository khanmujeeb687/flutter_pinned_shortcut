import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_pinned_shortcut_platform_interface.dart';

/// An implementation of [FlutterPinnedShortcutPlatform] that uses method channels.
class MethodChannelFlutterPinnedShortcut extends FlutterPinnedShortcutPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_pinned_shortcut');

  @override
  Future<String?> createPinnedShortcut({
    required String id,
    required String label,
    required String action,
    String? iconAssetName,
  }) async {
    return await methodChannel.invokeMethod("createPinnedShortcut", {
      "id": id,
      "shortLabel": label,
      "action": action,
      "icon": iconAssetName,
    });
  }

  @override
  Future<void> getLaunchAction(
      void Function(String action) onActionReceived) async {
    String? response = await methodChannel.invokeMethod("getLaunchAction");
    if (response != null) {
      onActionReceived(response);
    }
  }
}
