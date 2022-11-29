import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_pinned_shortcut_platform_interface.dart';

/// An implementation of [FlutterPinnedShortcutPlatform] that uses method channels.
class MethodChannelFlutterPinnedShortcut extends FlutterPinnedShortcutPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_pinned_shortcut');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
