import 'package:flutter/material.dart';
import 'package:simpleton_camera/camera2/model/camera_info.dart';

class CameraCallBack {
  ///相机初始化完成（预览就绪）
  void Function(CameraInfo info)? onCameraReady;

  ///相机关闭
  VoidCallback? onCameraClosed;

  ///当相机发生错误时
  void Function(Object error)? onCameraError;
}
