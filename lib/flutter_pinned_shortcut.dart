import 'flutter_pinned_shortcut_platform_interface.dart';

class FlutterPinnedShortcut {
  Future<String?> createPinnedShortcut({
    required String id,
    required String label,
    required String action,
    String? iconAssetName,
  }) {
    return FlutterPinnedShortcutPlatform.instance.createPinnedShortcut(
      id: id,
      label: label,
      action: action,
      iconAssetName: iconAssetName,
    );
  }

  Future<void> getLaunchAction(
      void Function(String action) onActionReceived) async {
    return FlutterPinnedShortcutPlatform.instance
        .getLaunchAction(onActionReceived);
  }
}
