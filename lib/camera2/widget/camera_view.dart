import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Camera2View extends StatelessWidget {
  const Camera2View({super.key});

  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: "camera2_preview",
      layoutDirection: TextDirection.ltr,
      creationParams: const <String, dynamic>{},
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
