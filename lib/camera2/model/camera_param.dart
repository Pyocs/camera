///相机参数
/**
 * 📸 1. 曝光控制

曝光时间 / 快门速度：CaptureRequest.SENSOR_EXPOSURE_TIME

感光度 ISO：CaptureRequest.SENSOR_SENSITIVITY

曝光补偿 EV：CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION

自动曝光模式：CaptureRequest.CONTROL_AE_MODE

自动曝光锁定：CaptureRequest.CONTROL_AE_LOCK

曝光目标帧率范围：CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE

AE 预闪模式（自动闪光灯控制）：CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER

🎥 2. 对焦控制

对焦模式：CaptureRequest.CONTROL_AF_MODE

对焦触发：CaptureRequest.CONTROL_AF_TRIGGER

对焦锁定：CaptureRequest.CONTROL_AF_REGIONS（对焦区域）

镜头焦距（手动对焦）：CaptureRequest.LENS_FOCUS_DISTANCE

🌈 3. 白平衡控制

自动白平衡模式：CaptureRequest.CONTROL_AWB_MODE

自动白平衡锁定：CaptureRequest.CONTROL_AWB_LOCK

白平衡区域：CaptureRequest.CONTROL_AWB_REGIONS

🔦 4. 闪光灯 & 辅助光

闪光灯模式：CaptureRequest.FLASH_MODE

自动闪光灯控制：CaptureRequest.CONTROL_AE_MODE

🎨 5. 颜色与图像效果

场景模式（人像、夜景、风景等）：CaptureRequest.CONTROL_SCENE_MODE

色彩效果（黑白、复古等）：CaptureRequest.CONTROL_EFFECT_MODE

降噪强度：CaptureRequest.NOISE_REDUCTION_MODE

色彩校正：CaptureRequest.COLOR_CORRECTION_MODE / CaptureRequest.COLOR_CORRECTION_GAINS

锐度/对比度/饱和度（部分厂商扩展参数）

🪞 6. 镜头控制

镜头缩放（数码变焦）：CaptureRequest.SCALER_CROP_REGION

镜头光圈值（如果硬件支持可变光圈）：CaptureRequest.LENS_APERTURE

镜头防抖模式（OIS/EIS）：CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE / CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE

🔊 7. 流媒体 / 视频相关

目标帧率范围：CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE

输出格式和分辨率：通过 CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP

高速度视频模式：CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS

🧪 8. 高级 / RAW 控制

RAW 传感器数据：ImageFormat.RAW_SENSOR

黑电平补偿：CaptureRequest.BLACK_LEVEL_LOCK

边缘增强：CaptureRequest.EDGE_MODE

阴影/高光恢复（厂商扩展）
 */

import 'package:simpleton_camera/camera2/model/camera_info.dart';

///曝光参数
class ExposureParam {
  ///曝光时间
  int exposure_time;

  //感光度ISO
  int iso;

  ///曝光补偿EV
  int ev;

  ///自动曝光模式
  AeMode ae_mode;

  ///曝光锁定
  bool exposure_clock;

  ///AE预闪模式(设置曝光时间/ISO时，必须关闭预闪模式)
  AE_PRECAPTURE_TRIGGER ae_precapture_trigger;

  ExposureParam({
    required this.exposure_time,
    required this.iso,
    required this.ev,
    required this.ae_mode,
    required this.exposure_clock,
    required this.ae_precapture_trigger,
  });
}

//对焦设置
class AFParam {
  // ignore: slash_for_doc_comments
  /**
   * CONTROL_AF_MODE_OFF：关闭自动对焦，手动对焦模式（需要结合 LENS_FOCUS_DISTANCE 使用）。
    CONTROL_AF_MODE_AUTO：单次自动对焦（拍照时常用，按下快门时对焦一次）。
    CONTROL_AF_MODE_MACRO：微距对焦模式。
    CONTROL_AF_MODE_CONTINUOUS_PICTURE：连续自动对焦（适合拍照）。
    CONTROL_AF_MODE_CONTINUOUS_VIDEO：连续自动对焦（适合录像）。
   */
  AfMode afMode;

  ///手动对焦距离
  double? distance;

  AFParam({required this.afMode, this.distance});
}
