package icu.pyoc.simpleton_camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.graphics.SurfaceTexture;
import android.util.Log;

import java.util.*;

/**
 * CameraInfoHelper
 * - 收集大量 CameraCharacteristics 字段
 * - 对每个字段做安全获取（避免 NPE / Key 不存在）
 * - 如果某个相机发生异常，会把 "error" 字段写进该相机的 map
 *
 * 返回：List<Map<String,Object>> -> MethodChannel 可序列化
 */
public class CameraInfoHelper {
    private static final String TAG = "CameraInfoHelper";

    public static List<Map<String, Object>> getAllCameraInfo(Context context) {
        List<Map<String, Object>> cameraList = new ArrayList<>();
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        if (cameraManager == null) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "CameraManager unavailable");
            cameraList.add(err);
            return cameraList;
        }

        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            for (String cameraId : cameraIds) {
                Map<String, Object> cameraInfo = new HashMap<>();
                cameraInfo.put("cameraId", cameraId);

                try {
                    CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);

                    // ---------- 基础字段 ----------
                    //前后置
                    Integer facing = safeGet(characteristics, CameraCharacteristics.LENS_FACING);
                    cameraInfo.put("facing", facingToString(facing));

                    //传感器方向
                    Integer orientation = safeGet(characteristics, CameraCharacteristics.SENSOR_ORIENTATION);
                    cameraInfo.put("sensorOrientation", orientation != null ? orientation : -1);

                    //硬件级别
                    Integer hwLevel = safeGet(characteristics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                    cameraInfo.put("hardwareLevel", hardwareLevelToString(hwLevel));

                    //闪光灯
                    Boolean flashAvailable = safeGet(characteristics, CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    cameraInfo.put("flashAvailable", flashAvailable != null ? flashAvailable : false);

//                    // 逻辑相机包含的物理相机（Set<String>）
//                    Set<String> phys = safeGet(characteristics, CameraCharacteristics.LOGICAL_MULTI_CAMERA_);
//                    if (phys != null) cameraInfo.put("physicalCameraIds", new ArrayList<>(phys));

                    // ---------- 输出分辨率 / 格式 ----------
                    StreamConfigurationMap map = safeGet(characteristics, CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    if (map != null) {
                        cameraInfo.put("outputFormats", intArrayToList(map.getOutputFormats()));

                        // JPEG 尺寸
                        Size[] jpeg = map.getOutputSizes(ImageFormat.JPEG);
                        cameraInfo.put("jpegOutputSizes", sizesToStringList(jpeg));

                        // YUV 尺寸
                        Size[] yuv = map.getOutputSizes(ImageFormat.YUV_420_888);
                        cameraInfo.put("yuvOutputSizes", sizesToStringList(yuv));

                        // SurfaceTexture/preview 尺寸
                        Size[] preview = map.getOutputSizes(SurfaceTexture.class); // preview
                        cameraInfo.put("previewOutputSizes", sizesToStringList(preview));
                    }

                    // 最大数字变焦
                    Float maxZoom = safeGet(characteristics, CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
                    cameraInfo.put("maxDigitalZoom", maxZoom != null ? maxZoom : 1.0f);

                    // ---------- 镜头信息 ----------
                    float[] apertures = safeGet(characteristics, CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
                    if (apertures != null) cameraInfo.put("apertures", floatArrayToDoubleList(apertures));

                    //焦距长度
                    float[] focals = safeGet(characteristics, CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                    if (focals != null) cameraInfo.put("focalLengths", floatArrayToDoubleList(focals));

                    //最小聚焦距离
                    Float minFocusDistance = safeGet(characteristics, CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
                    cameraInfo.put("minFocusDistance", minFocusDistance != null ? minFocusDistance : 0.0f);

                    // ---------- 自动控制能力 ----------
                    int[] afModes = safeGet(characteristics, CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
                    if (afModes != null) cameraInfo.put("afAvailableModes", intArrayToList(afModes));

                    //ae可用模式
                    int[] aeModes = safeGet(characteristics, CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
                    if (aeModes != null) cameraInfo.put("aeAvailableModes", intArrayToList(aeModes));

                    int[] awbModes = safeGet(characteristics, CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
                    if (awbModes != null) cameraInfo.put("awbAvailableModes", intArrayToList(awbModes));

                    //色彩模式
                    int[] effects = safeGet(characteristics, CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS);
                    if (effects != null) cameraInfo.put("colorEffects", intArrayToList(effects));

                    int[] videoStab = safeGet(characteristics, CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
                    if (videoStab != null) cameraInfo.put("videoStabilizationModes", intArrayToList(videoStab));

                    // 人脸检测
                    int[] faceModes = safeGet(characteristics, CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
                    if (faceModes != null) cameraInfo.put("faceDetectModes", intArrayToList(faceModes));

                    // AE fps ranges
                    Range<Integer>[] fpsRanges = safeGet(characteristics, CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                    if (fpsRanges != null) cameraInfo.put("fpsRanges", fpsRangesToList(fpsRanges));

                    // ---------- 传感器信息 ----------
                    Range<Integer> isoRange = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
                    if (isoRange != null) cameraInfo.put("isoRange", Arrays.asList(isoRange.getLower(), isoRange.getUpper()));

                    Range<Long> exposureRange = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
                    if (exposureRange != null) cameraInfo.put("exposureTimeRange", Arrays.asList(exposureRange.getLower(), exposureRange.getUpper()));

                    Long maxFrameDuration = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION);
                    if (maxFrameDuration != null) cameraInfo.put("maxFrameDuration", maxFrameDuration);

                    Size pixelArray = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
                    if (pixelArray != null) cameraInfo.put("pixelArraySize", sizeToString(pixelArray));

                    SizeF physicalSize = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                    if (physicalSize != null) cameraInfo.put("sensorPhysicalSize_mm", physicalSize.getWidth() + "x" + physicalSize.getHeight());

                    Integer whiteLevel = safeGet(characteristics, CameraCharacteristics.SENSOR_INFO_WHITE_LEVEL);
                    if (whiteLevel != null) cameraInfo.put("sensorWhiteLevel", whiteLevel);

                    // ---------- 高级能力（原始返回） ----------
                    int[] capabilities = safeGet(characteristics, CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
                    if (capabilities != null) cameraInfo.put("requestAvailableCapabilities", intArrayToList(capabilities));

                    // NOTE: 每个厂商可能会有额外私有字段，这里只是收集标准字段
                } catch (SecurityException se) {
                    // 没有权限
                    Log.w(TAG, "Camera permission missing for cameraId=" + cameraId, se);
                    cameraInfo.put("error", "NO_CAMERA_PERMISSION");
                    cameraInfo.put("errorMessage", se.getMessage());
                } catch (CameraAccessException cae) {
                    Log.e(TAG, "CameraAccessException for cameraId=" + cameraId, cae);
                    cameraInfo.put("error", "CAMERA_ACCESS_EXCEPTION");
                    cameraInfo.put("errorMessage", cae.getMessage());
                } catch (Exception e) {
                    // 防御式捕获，确保不会因为单个字段异常而中断全部流程
                    Log.e(TAG, "Unexpected error fetching camera characteristics for " + cameraId, e);
                    cameraInfo.put("error", "UNEXPECTED_ERROR");
                    cameraInfo.put("errorMessage", e.getMessage());
                }

                cameraList.add(cameraInfo);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "CameraAccessException listing camera IDs", e);
            Map<String, Object> err = new HashMap<>();
            err.put("error", "CAMERA_ACCESS_EXCEPTION");
            err.put("errorMessage", e.getMessage());
            cameraList.clear();
            cameraList.add(err);
        } catch (SecurityException se) {
            Log.w(TAG, "Camera permission missing when listing cameras", se);
            Map<String, Object> err = new HashMap<>();
            err.put("error", "NO_CAMERA_PERMISSION");
            err.put("errorMessage", se.getMessage());
            cameraList.clear();
            cameraList.add(err);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error listing cameras", e);
            Map<String, Object> err = new HashMap<>();
            err.put("error", "UNEXPECTED_ERROR");
            err.put("errorMessage", e.getMessage());
            cameraList.clear();
            cameraList.add(err);
        }

        return cameraList;
    }

    // --------- Helper / util methods ----------
    private static <T> T safeGet(CameraCharacteristics c, CameraCharacteristics.Key<T> key) {
        try {
            return c.get(key);
        } catch (Exception e) {
            // 防御式返回 null
            return null;
        }
    }

    private static List<String> sizesToStringList(Size[] sizes) {
        List<String> list = new ArrayList<>();
        if (sizes == null) return list;
        for (Size s : sizes) {
            if (s != null) list.add(s.getWidth() + "x" + s.getHeight());
        }
        return list;
    }

    private static String sizeToString(Size s) {
        if (s == null) return "";
        return s.getWidth() + "x" + s.getHeight();
    }

    private static List<Integer> intArrayToList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        if (arr == null) return list;
        for (int v : arr) list.add(v);
        return list;
    }

    private static List<Double> floatArrayToDoubleList(float[] arr) {
        List<Double> list = new ArrayList<>();
        if (arr == null) return list;
        for (float v : arr) list.add((double) v);
        return list;
    }

    private static List<String> fpsRangesToList(Range<Integer>[] ranges) {
        List<String> list = new ArrayList<>();
        if (ranges == null) return list;
        for (Range<Integer> r : ranges) {
            if (r != null) list.add(r.getLower() + "-" + r.getUpper());
        }
        return list;
    }

    private static String facingToString(Integer facing) {
        if (facing == null) return "UNKNOWN";
        switch (facing) {
            case CameraCharacteristics.LENS_FACING_BACK:
                return "BACK";
            case CameraCharacteristics.LENS_FACING_FRONT:
                return "FRONT";
            case CameraCharacteristics.LENS_FACING_EXTERNAL:
                return "EXTERNAL";
            default:
                return "UNKNOWN";
        }
    }

    private static String hardwareLevelToString(Integer level) {
        if (level == null) return "UNKNOWN";
        switch (level) {
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY:
                return "LEGACY";
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED:
                return "LIMITED";
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL:
                return "FULL";
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3:
                return "LEVEL_3";
            default:
                return "UNKNOWN";
        }
    }
}
