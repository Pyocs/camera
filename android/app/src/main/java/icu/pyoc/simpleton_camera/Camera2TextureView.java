package icu.pyoc.simpleton_camera;
import android.Manifest;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import io.flutter.plugin.platform.PlatformView;

public class Camera2TextureView implements PlatformView, TextureView.SurfaceTextureListener {
    private final TextureView textureView;

    private SurfaceTexture availableSurface;

    private Camera2Controller cameraController;

    public void setCameraController(Camera2Controller controller) {
        this.cameraController = controller;
//        if (availableSurface != null) {
//            cameraController.startPreview(getSurfaceTexture());
//        }
    }

    public Camera2TextureView(@NonNull Context context) {
        textureView = new TextureView(context);
        textureView.setSurfaceTextureListener(this);
    }

    public SurfaceTexture getSurfaceTexture() {
        return textureView.getSurfaceTexture();
    }

    @NonNull
    @Override
    public View getView() {
        return textureView;
    }

    @Override
    public void dispose() {
        cameraController.
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        availableSurface = surface;
        // 可以在这里通知 CameraController 开始预览
        if (cameraController != null) {
            SurfaceTexture surfaceTexture = getSurfaceTexture();
            Surface previewSurface = new Surface(surfaceTexture);
            cameraController.openCamera(previewSurface);
        }
    }
    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {}
    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) { return true; }
    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {}
}
