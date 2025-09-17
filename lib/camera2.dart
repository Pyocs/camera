import 'package:pigeon/pigeon.dart';


@ConfigurePigeon(
  PigeonOptions(
    dartOut: "lib/out.g.dart",
    dartOptions: DartOptions(),
    javaOut: 'android/app/src/main/java/io/flutter/plugins/Camera2.java',
    javaOptions: JavaOptions(),
  ),
)

@HostApi()
abstract class Camera2 {
  int add(int a,int b);
}