import Flutter
import UIKit

public class SwiftFlutterPinnedShortcutPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_pinned_shortcut", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterPinnedShortcutPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
