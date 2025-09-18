import 'dart:typed_data';
import 'dart:ui';

import 'package:simpleton_camera/camera2/model/camera_call_back.dart';
import 'package:simpleton_camera/camera2/model/camera_info.dart';

abstract class Camera2 {
  Future<void> init(CameraCallBack callback);

  //打开相机
  Future<void> openCamera(CameraInfo info);

  Future<void> closeCamera();

  ///设置预览分辨率（需在 openCamera 前调用，或调用后重启预览）
  Future<void> setPreviewSize(Size size);

  ///开始预览
  Future<void> startPreview();

  ///暂停预览
  Future<void> pausePreview();

  ///拍照
  Future<Uint8List> capture();

  ///更新相机参数
  Future<void> updateParam();
}
