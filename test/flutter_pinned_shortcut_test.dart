import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut.dart';
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut_platform_interface.dart';
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterPinnedShortcutPlatform
    with MockPlatformInterfaceMixin
    implements FlutterPinnedShortcutPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterPinnedShortcutPlatform initialPlatform = FlutterPinnedShortcutPlatform.instance;

  test('$MethodChannelFlutterPinnedShortcut is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterPinnedShortcut>());
  });

  test('getPlatformVersion', () async {
    FlutterPinnedShortcut flutterPinnedShortcutPlugin = FlutterPinnedShortcut();
    MockFlutterPinnedShortcutPlatform fakePlatform = MockFlutterPinnedShortcutPlatform();
    FlutterPinnedShortcutPlatform.instance = fakePlatform;

    expect(await flutterPinnedShortcutPlugin.getPlatformVersion(), '42');
  });
}
