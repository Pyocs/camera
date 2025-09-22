package icu.pyoc.simpleton_camera;

import android.Manifest;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class CameraPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private MethodChannel channel;
    private Camera2Controller controller;
    private Camera2TextureView preview;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        Context context = binding.getApplicationContext();

        // 注册 MethodChannel
        channel = new MethodChannel(binding.getBinaryMessenger(), "camera2_channel");
        channel.setMethodCallHandler(this);
        controller = new Camera2Controller(context);

        // 注册 PlatformView
        binding.getPlatformViewRegistry().registerViewFactory("camera2_preview", new PlatformViewFactory(StandardMessageCodec.INSTANCE) {
            @RequiresPermission(Manifest.permission.CAMERA)
            @NonNull
            @Override
            public PlatformView create(Context context, int viewId, @Nullable Object args) {
                preview = new Camera2TextureView(context);
                preview.setCameraController(controller);
                return preview;
            }
        });
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        if (controller == null) {
            result.error("NO_CONTROLLER", "Camera not initialized yet", null);
            return;
        }

        switch (call.method) {
            case "setFocusMode":
                int mode = (int) call.arguments;
                controller.setFocusMode(mode);
                result.success(null);
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
