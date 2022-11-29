
import 'flutter_pinned_shortcut_platform_interface.dart';

class FlutterPinnedShortcut {
  Future<String?> getPlatformVersion() {
    return FlutterPinnedShortcutPlatform.instance.getPlatformVersion();
  }


}
