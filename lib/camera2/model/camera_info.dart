import 'package:flutter/services.dart';

/// 摄像头方向
enum CameraFacing { front, back, external, unknown }

/// 硬件级别
enum HardwareLevel { legacy, limited, full, level3, external, unknown }

/// 自动对焦模式 (CONTROL_AF_AVAILABLE_MODES)
enum AfMode {
  off,
  auto,
  macro,
  continuousVideo,
  continuousPicture,
  edof,
  unknown,
}

/// 自动曝光模式 (CONTROL_AE_AVAILABLE_MODES)
enum AeMode { off, on, onAutoFlash, onAlwaysFlash, onAutoFlashTorch, unknown }

/// AE预闪模式
enum AE_PRECAPTURE_TRIGGER {
  //空闲
  IDLE,
  //开始预闪
  START,
  //取消
  CANCEL,
}

/// 自动白平衡模式 (CONTROL_AWB_AVAILABLE_MODES)
enum AwbMode {
  off,
  auto,
  incandescent,
  fluorescent,
  daylight,
  cloudy,
  twilight,
  shade,
  unknown,
}

/// 色彩效果 (CONTROL_AVAILABLE_EFFECTS)
enum ColorEffect {
  off,
  mono,
  negative,
  solarize,
  sepia,
  posterize,
  whiteboard,
  blackboard,
  aqua,
  unknown,
}

/// 视频防抖模式 (CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES)
enum VideoStabilization { off, on, unknown }

/// 人脸检测模式 (STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES)
enum FaceDetectMode { off, simple, full, unknown }

/// 请求能力 (REQUEST_AVAILABLE_CAPABILITIES)
enum CameraCapability {
  backwardCompatible,
  manualSensor,
  manualPostProcessing,
  raw,
  privateReprocessing,
  yuvReprocessing,
  constrainedHighSpeed,
  monochrome,
  logicalMultiCamera,
  depthOutput,
  unknown,
}

/// 相机设备基本信息
class CameraInfo {
  final String cameraId;
  final CameraFacing facing;
  final HardwareLevel hardwareLevel;
  final int? sensorOrientation;
  final bool flashAvailable;
  final List<String> physicalCameraIds;

  final List<String> jpegOutputSizes;
  final List<String> yuvOutputSizes;
  final List<String> previewOutputSizes;
  final List<int> outputFormats;

  final double maxDigitalZoom;
  final List<double> apertures;
  final List<double> focalLengths;
  final double? minFocusDistance;

  final List<AfMode> afAvailableModes;
  final List<AeMode> aeAvailableModes;
  final List<AwbMode> awbAvailableModes;
  final List<ColorEffect> colorEffects;
  final List<VideoStabilization> videoStabilizationModes;
  final List<FaceDetectMode> faceDetectModes;
  final List<String> fpsRanges;

  final List<int> isoRange; // [min, max]
  final List<int> exposureTimeRange; // [min, max]
  final int? maxFrameDuration;
  final String? pixelArraySize;
  final String? sensorPhysicalSizeMm;

  final List<CameraCapability> requestAvailableCapabilities;

  final String? error;
  final String? errorMessage;

  CameraInfo({
    required this.cameraId,
    required this.facing,
    required this.hardwareLevel,
    this.sensorOrientation,
    required this.flashAvailable,
    required this.physicalCameraIds,
    required this.jpegOutputSizes,
    required this.yuvOutputSizes,
    required this.previewOutputSizes,
    required this.outputFormats,
    required this.maxDigitalZoom,
    required this.apertures,
    required this.focalLengths,
    this.minFocusDistance,
    required this.afAvailableModes,
    required this.aeAvailableModes,
    required this.awbAvailableModes,
    required this.colorEffects,
    required this.videoStabilizationModes,
    required this.faceDetectModes,
    required this.fpsRanges,
    required this.isoRange,
    required this.exposureTimeRange,
    this.maxFrameDuration,
    this.pixelArraySize,
    this.sensorPhysicalSizeMm,
    required this.requestAvailableCapabilities,
    this.error,
    this.errorMessage,
  });

  /// ========== 枚举转换辅助 ==========
  static CameraFacing _parseFacing(String? s) {
    switch (s) {
      case "FRONT":
        return CameraFacing.front;
      case "BACK":
        return CameraFacing.back;
      case "EXTERNAL":
        return CameraFacing.external;
      default:
        return CameraFacing.unknown;
    }
  }

  static HardwareLevel _parseHardwareLevel(String? s) {
    switch (s) {
      case "LEGACY":
        return HardwareLevel.legacy;
      case "LIMITED":
        return HardwareLevel.limited;
      case "FULL":
        return HardwareLevel.full;
      case "LEVEL_3":
        return HardwareLevel.level3;
      case "EXTERNAL":
        return HardwareLevel.external;
      default:
        return HardwareLevel.unknown;
    }
  }

  static List<AfMode> _parseAfModes(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return AfMode.off;
        case 1:
          return AfMode.auto;
        case 2:
          return AfMode.macro;
        case 3:
          return AfMode.continuousVideo;
        case 4:
          return AfMode.continuousPicture;
        case 5:
          return AfMode.edof;
        default:
          return AfMode.unknown;
      }
    }).toList();
  }

  static List<AeMode> _parseAeModes(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return AeMode.off;
        case 1:
          return AeMode.on;
        case 2:
          return AeMode.onAutoFlash;
        case 3:
          return AeMode.onAlwaysFlash;
        case 4:
          return AeMode.onAutoFlashTorch;
        default:
          return AeMode.unknown;
      }
    }).toList();
  }

  static List<AwbMode> _parseAwbModes(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return AwbMode.off;
        case 1:
          return AwbMode.auto;
        case 2:
          return AwbMode.incandescent;
        case 3:
          return AwbMode.fluorescent;
        case 5:
          return AwbMode.daylight;
        case 6:
          return AwbMode.cloudy;
        case 7:
          return AwbMode.twilight;
        case 8:
          return AwbMode.shade;
        default:
          return AwbMode.unknown;
      }
    }).toList();
  }

  static List<ColorEffect> _parseColorEffects(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return ColorEffect.off;
        case 1:
          return ColorEffect.mono;
        case 2:
          return ColorEffect.negative;
        case 3:
          return ColorEffect.solarize;
        case 4:
          return ColorEffect.sepia;
        case 5:
          return ColorEffect.posterize;
        case 6:
          return ColorEffect.whiteboard;
        case 7:
          return ColorEffect.blackboard;
        case 8:
          return ColorEffect.aqua;
        default:
          return ColorEffect.unknown;
      }
    }).toList();
  }

  static List<VideoStabilization> _parseVideoStabilization(
    List<dynamic>? list,
  ) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return VideoStabilization.off;
        case 1:
          return VideoStabilization.on;
        default:
          return VideoStabilization.unknown;
      }
    }).toList();
  }

  static List<FaceDetectMode> _parseFaceDetectModes(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return FaceDetectMode.off;
        case 1:
          return FaceDetectMode.simple;
        case 2:
          return FaceDetectMode.full;
        default:
          return FaceDetectMode.unknown;
      }
    }).toList();
  }

  static List<CameraCapability> _parseCapabilities(List<dynamic>? list) {
    if (list == null) return [];
    return list.map((v) {
      switch (v) {
        case 0:
          return CameraCapability.backwardCompatible;
        case 1:
          return CameraCapability.manualSensor;
        case 2:
          return CameraCapability.manualPostProcessing;
        case 3:
          return CameraCapability.raw;
        case 4:
          return CameraCapability.privateReprocessing;
        case 5:
          return CameraCapability.yuvReprocessing;
        case 6:
          return CameraCapability.constrainedHighSpeed;
        case 7:
          return CameraCapability.monochrome;
        case 8:
          return CameraCapability.logicalMultiCamera;
        case 9:
          return CameraCapability.depthOutput;
        default:
          return CameraCapability.unknown;
      }
    }).toList();
  }

  factory CameraInfo.fromMap(Map<dynamic, dynamic> m) {
    List<T> toList<T>(dynamic v) {
      if (v == null) return <T>[];
      try {
        return List<T>.from(v as List);
      } catch (_) {
        return <T>[];
      }
    }

    double? toDouble(dynamic v) {
      if (v == null) return null;
      if (v is num) return v.toDouble();
      return double.tryParse(v.toString());
    }

    int? toInt(dynamic v) {
      if (v == null) return null;
      if (v is num) return v.toInt();
      return int.tryParse(v.toString());
    }

    return CameraInfo(
      cameraId: m['cameraId'] ?? '',
      facing: _parseFacing(m['facing']),
      hardwareLevel: _parseHardwareLevel(m['hardwareLevel']),
      sensorOrientation: toInt(m['sensorOrientation']),
      flashAvailable: (m['flashAvailable'] ?? false) as bool,
      physicalCameraIds: List<String>.from(m['physicalCameraIds'] ?? []),
      jpegOutputSizes: toList<String>(m['jpegOutputSizes']),
      yuvOutputSizes: toList<String>(m['yuvOutputSizes']),
      previewOutputSizes: toList<String>(m['previewOutputSizes']),
      outputFormats: toList<int>(m['outputFormats']),
      maxDigitalZoom: toDouble(m['maxDigitalZoom']) ?? 1.0,
      apertures: toList<num>(m['apertures']).map((e) => e.toDouble()).toList(),
      focalLengths: toList<num>(
        m['focalLengths'],
      ).map((e) => e.toDouble()).toList(),
      minFocusDistance: toDouble(m['minFocusDistance']),
      afAvailableModes: _parseAfModes(m['afAvailableModes']),
      aeAvailableModes: _parseAeModes(m['aeAvailableModes']),
      awbAvailableModes: _parseAwbModes(m['awbAvailableModes']),
      colorEffects: _parseColorEffects(m['colorEffects']),
      videoStabilizationModes: _parseVideoStabilization(
        m['videoStabilizationModes'],
      ),
      faceDetectModes: _parseFaceDetectModes(m['faceDetectModes']),
      fpsRanges: toList<String>(m['fpsRanges']),
      isoRange: toList<int>(m['isoRange']),
      exposureTimeRange: toList<int>(m['exposureTimeRange']),
      maxFrameDuration: toInt(m['maxFrameDuration']),
      pixelArraySize: m['pixelArraySize'] as String?,
      sensorPhysicalSizeMm: m['sensorPhysicalSize_mm'] as String?,
      requestAvailableCapabilities: _parseCapabilities(
        m['requestAvailableCapabilities'],
      ),
      error: m['error'] as String?,
      errorMessage: m['errorMessage'] as String?,
    );
  }

  bool get hasError => error != null;

  @override
  String toString() {
    return 'CameraInfo(cameraId: $cameraId, facing: $facing, hardwareLevel: $hardwareLevel, sensorOrientation: $sensorOrientation, flashAvailable: $flashAvailable, physicalCameraIds: $physicalCameraIds, jpegOutputSizes: $jpegOutputSizes, yuvOutputSizes: $yuvOutputSizes, previewOutputSizes: $previewOutputSizes, outputFormats: $outputFormats, maxDigitalZoom: $maxDigitalZoom, apertures: $apertures, focalLengths: $focalLengths, minFocusDistance: $minFocusDistance, afAvailableModes: $afAvailableModes, aeAvailableModes: $aeAvailableModes, awbAvailableModes: $awbAvailableModes, colorEffects: $colorEffects, videoStabilizationModes: $videoStabilizationModes, faceDetectModes: $faceDetectModes, fpsRanges: $fpsRanges, isoRange: $isoRange, exposureTimeRange: $exposureTimeRange, maxFrameDuration: $maxFrameDuration, pixelArraySize: $pixelArraySize, sensorPhysicalSizeMm: $sensorPhysicalSizeMm, requestAvailableCapabilities: $requestAvailableCapabilities, error: $error, errorMessage: $errorMessage)';
  }
}
