package icu.pyoc.simpleton_camera;
import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformViewRegistry;

public class MainActivity extends FlutterActivity {
    private static final String METHOD_CHANNEL = "icu.pyoc.camera";
  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
      super.configureFlutterEngine(flutterEngine);
      PlatformViewRegistry registry = flutterEngine.getPlatformViewsController().getRegistry();
      registry.registerViewFactory("camera2TextureView",new Camera2ViewFactory());
      var methodChannel = new MethodChannel(flutterEngine.getDartExecutor(),METHOD_CHANNEL);

      methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
          @Override
          public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
              if(call.method.equals("getCameraInfo")) {
                  var results = CameraInfoHelper.getAllCameraInfo(getContext());
                  result.success(results);
              }
          }
      });
  }


}
