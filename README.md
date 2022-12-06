# flutter_pinned_shortcut

A new Flutter plugin to create Pinned shortcuts for Android similer to Messenger chat bubble.

## Shortcut

![Screenshot 2022-12-06 at 12 10 03](https://user-images.githubusercontent.com/58891556/205899095-43387437-fddd-46c8-a3cb-98e89f55f8b8.png)

# Import the plugin

```
import 'package:flutter_pinned_shortcut/flutter_pinned_shortcut.dart';
```


# Create an Object

```
  final FlutterPinnedShortcut flutterPinnedShortcutPlugin = FlutterPinnedShortcut();
```



# Add a shortcut

```
  void addPinnedShortcut() {
    _flutterPinnedShortcutPlugin.createPinnedShortcut(
      id: "1",
      label: "Followers",
      action: "followers",
      iconAssetName: "assets/icon.png"
    );
  }
```





# Get Incoming action and navigate to right screen when the app starts

```

  @override
  void initState() {
    super.initState();
    getIncomingAction();
  }
  
  
  void getIncomingAction() {
    _flutterPinnedShortcutPlugin.getLaunchAction((action) {
      switch (action) {
        case "followers":

          ///navigate to follower screen.
          break;
        case "profile":

        ///navigate to profile screen.
      }
    });
  }
```
