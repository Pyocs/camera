package icu.pyoc.simpleton_camera;
import android.Manifest;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;

import androidx.annotation.RequiresPermission;

import java.util.Arrays;

public class Camera2Controller {
    private final Context context;
    private final CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private CaptureRequest.Builder previewRequestBuilder;
    private Handler backgroundHandler;

    public Camera2Controller(Context context) {
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        startBackgroundThread();
    }

    private void startBackgroundThread() {
        HandlerThread thread = new HandlerThread("CameraBackground");
        thread.start();
        backgroundHandler = new Handler(thread.getLooper());
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void openCamera(Surface previewSurface) {
        try {
            String cameraId = cameraManager.getCameraIdList()[0]; // 默认后置
            cameraManager.openCamera(cameraId, stateCallback(previewSurface), backgroundHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback stateCallback(Surface previewSurface) {
        return new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                cameraDevice = camera;
                startPreview(previewSurface);
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
                camera.close();
                cameraDevice = null;
            }

            @Override
            public void onError(CameraDevice camera, int error) {
                camera.close();
                cameraDevice = null;
            }
        };
    }

    void startPreview(Surface previewSurface) {
        try {
            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(
                    Arrays.asList(previewSurface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            captureSession = session;
                            try {
                                session.setRepeatingRequest(previewRequestBuilder.build(), null, backgroundHandler);
                            } catch (CameraAccessException e) { e.printStackTrace(); }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {}
                    },
                    backgroundHandler
            );
        } catch (CameraAccessException e) { e.printStackTrace(); }
    }

    // 示例：设置对焦模式
    public void setFocusMode(int afMode) {
        try {
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, afMode);
            captureSession.setRepeatingRequest(previewRequestBuilder.build(), null, backgroundHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
