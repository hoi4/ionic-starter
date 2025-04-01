package io.ionic.starter;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;

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
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
      ViewCompat.setOnApplyWindowInsetsListener(bridge.getWebView(), (v, windowInsets) -> {
        Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        mlp.leftMargin = insets.left;
        mlp.bottomMargin = insets.bottom;
        mlp.rightMargin = insets.right;
        mlp.topMargin = insets.top;
        v.setLayoutParams(mlp);

        // Don't pass window insets to children
        return WindowInsetsCompat.CONSUMED;
      });
    }
  }
}
