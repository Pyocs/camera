import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:simpleton_camera/camera2/model/camera_info.dart';
import 'package:simpleton_camera/camera2/widget/camera_view.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Permission.camera.request();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel("camera2_channel");

  void _incrementCounter() {
    var methodChannel = MethodChannel('icu.pyoc.camera');
    methodChannel.invokeMethod("getCameraInfo").then((result) {
      var list = result as List;
      list = list.map((e) => CameraInfo.fromMap(e)).toList();
      print(list);
    });
  }

  void setFocusAuto() async {
    Random random = Random();
    await platform.invokeMethod(
      "setFocusMode",
      AfMode.values[random.nextInt(AfMode.values.length)],
    ); // CONTROL_AF_MODE_CONTINUOUS_PICTURE
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [Align(alignment: Alignment.center, child: Camera2View())],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          setFocusAuto();
        },
      ),
    );
  }
}
