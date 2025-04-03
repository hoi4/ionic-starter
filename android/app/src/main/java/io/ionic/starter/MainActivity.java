package io.ionic.starter;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Remove the comment to apply the fix
    // this.edgeToEdgeHandler();
  }

  private void edgeToEdgeHandler() {
    ViewCompat.setOnApplyWindowInsetsListener(bridge.getWebView(), (v, windowInsets) -> {
      Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());

      // For devices < API 35, we apply layout margins --> safe-area-insets will be 0
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        mlp.leftMargin = insets.left;
        mlp.bottomMargin = insets.bottom;
        mlp.rightMargin = insets.right;
        mlp.topMargin = insets.top;
        v.setLayoutParams(mlp);
      } else {
        // For devices with API 35 we manually set safe-area inset variables. There is a current issue with the WebView
        // (see https://chromium-review.googlesource.com/c/chromium/src/+/6295663/comments/a5fc2d65_86c53b45?tab=comments)
        // which causes safe-area-insets to not respect system bars.
        // Code based on https://ruoyusun.com/2020/10/21/webview-fullscreen-notch.html
        WebView view = this.bridge.getWebView();

        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        view.evaluateJavascript(String.format("document.documentElement.style.setProperty('--android-safe-area-top', '%spx')", Math.round(insets.top / density)), null);
        view.evaluateJavascript(String.format("document.documentElement.style.setProperty('--android-safe-area-left', '%spx')", Math.round(insets.left / density)), null);
        view.evaluateJavascript(String.format("document.documentElement.style.setProperty('--android-safe-area-right', '%spx')", Math.round(insets.right / density)), null);
        view.evaluateJavascript(String.format("document.documentElement.style.setProperty('--android-safe-area-bottom', '%spx')", Math.round(insets.bottom / density)), null);
      }

      // Don't pass window insets to children
      return WindowInsetsCompat.CONSUMED;
    });
  }
}
