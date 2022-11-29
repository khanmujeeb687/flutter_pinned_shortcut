import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_pinned_shortcut_method_channel.dart';

abstract class FlutterPinnedShortcutPlatform extends PlatformInterface {
  /// Constructs a FlutterPinnedShortcutPlatform.
  FlutterPinnedShortcutPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterPinnedShortcutPlatform _instance = MethodChannelFlutterPinnedShortcut();

  /// The default instance of [FlutterPinnedShortcutPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterPinnedShortcut].
  static FlutterPinnedShortcutPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterPinnedShortcutPlatform] when
  /// they register themselves.
  static set instance(FlutterPinnedShortcutPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
