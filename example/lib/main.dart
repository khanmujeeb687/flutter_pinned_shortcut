import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';

// import 'package:flutter_shortcuts/flutter_shortcuts.dart';
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
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: .... '),
        ),
      ),
    );
  }


  void addPinnedShortcut(){
  }
}
