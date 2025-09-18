package icu.pyoc.simpleton_camera;

import android.content.Context;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class Camera2ViewFactory extends PlatformViewFactory {
    public Camera2ViewFactory() {
        super(StandardMessageCodec.INSTANCE);
    }

    @NonNull
    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        return new Camera2TextureView(context);
    }
}
