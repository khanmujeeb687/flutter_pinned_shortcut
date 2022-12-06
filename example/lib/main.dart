import 'package:flutter/material.dart';
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _flutterPinnedShortcutPlugin = FlutterPinnedShortcut();

  @override
  void initState() {
    super.initState();
    getIncomingAction();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed: addPinnedShortcut,
            child: const Text("Add Pinned Shortcut"),
          ),
        ),
      ),
    );
  }

  void addPinnedShortcut() {
    _flutterPinnedShortcutPlugin.createPinnedShortcut(
      id: "1",
      label: "Followers",
      action: "followers",
      iconAssetName: "assets/icon.png"
    );
  }

  void getIncomingAction() {
    _flutterPinnedShortcutPlugin.getLaunchAction((action) {
      print(action);
      switch (action) {
        case "followers":

          ///navigate to follower screen.
          break;
        case "profile":

        ///navigate to profile screen.
      }
    });
  }
}
