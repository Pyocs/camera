// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:pigeon/pigeon.dart';

// #docregion config
@ConfigurePigeon(
  PigeonOptions(
    dartOut: 'pigeon/output/messages.g.dart',
    dartOptions: DartOptions(),
    javaOut: 'android/app/src/main/java/io/flutter/plugins/Messages.java',
    javaOptions: JavaOptions(),
    dartPackageName: 'icu.pyoc.simpleton_camera',
  ),
)
// #enddocregion config
// #docregion host-definitions
enum Code { one, two }

class MessageData {
  MessageData({required this.code, required this.data});
  String? name;
  String? description;
  Code code;
  Map<String, String> data;
}

@HostApi()
abstract class ExampleHostApi {
  String getHostLanguage();

  // These annotations create more idiomatic naming of methods in Objc and Swift.
  @ObjCSelector('addNumber:toNumber:')
  @SwiftFunction('add(_:to:)')
  int add(int a, int b);

  @async
  bool sendMessage(MessageData message);
}
// #enddocregion host-definitions

// #docregion flutter-definitions
@FlutterApi()
abstract class MessageFlutterApi {
  String flutterMethod(String? aString);
}

