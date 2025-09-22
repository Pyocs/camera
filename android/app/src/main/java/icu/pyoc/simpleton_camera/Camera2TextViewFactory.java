package icu.pyoc.simpleton_camera;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class Camera2TextViewFactory extends PlatformViewFactory {


    /**
     * @param createArgsCodec the codec used to decode the args parameter of {@link #create}.
     */
    public Camera2TextViewFactory(Context context) {
        super(StandardMessageCodec.INSTANCE);
    }

    @NonNull
    @Override
    public PlatformView create(Context context, int viewId, @Nullable Object args) {
        return new Camera2TextureView(context);
    }
}
