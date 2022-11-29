import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut_method_channel.dart';

void main() {
  MethodChannelFlutterPinnedShortcut platform = MethodChannelFlutterPinnedShortcut();
  const MethodChannel channel = MethodChannel('flutter_pinned_shortcut');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
