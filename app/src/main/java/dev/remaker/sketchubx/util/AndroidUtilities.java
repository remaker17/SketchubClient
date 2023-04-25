package dev.remaker.sketchubx.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import dev.remaker.sketchubx.AppLoader;

public class AndroidUtilities {

    public static final String TAG = "AndroidUtilities";
    private static Boolean isTablet = null;
    private static int properPadding;
    private static int rowCount;
    public static float density = 1;
    public static Point displaySize = new Point();
    public static final RectF rectTmp = new RectF();
    public static DisplayMetrics displayMetrics = new DisplayMetrics();

    private AndroidUtilities() {
        throw new RuntimeException("Cannot instantiate `AndroidUtilities` class");
    }

    static {
        checkDisplaySize(AppLoader.Companion.getContext(), null);
    }

    public static int calculateColumns(int columnWidth) {
        return (int) ((displaySize.x / columnWidth) + 0.5d);
    }

    public static int calculateColumns(int numerator, int denominator) {
        return (int) ((denominator / numerator) + 0.5d);
    }

    public static void checkDisplaySize(Context context, Configuration newConfiguration) {
        try {
            density = context.getResources().getDisplayMetrics().density;
            Configuration configuration = newConfiguration;
            if (configuration == null) {
                configuration = context.getResources().getConfiguration();
            }
            WindowManager manager =
                    (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
            if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
                int newSize = (int) Math.ceil(configuration.screenWidthDp * density);
                if (Math.abs(displaySize.x - newSize) > 3) {
                    displaySize.x = newSize;
                }
            }
            if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
                int newSize = (int) Math.ceil(configuration.screenHeightDp * density);
                if (Math.abs(displaySize.y - newSize) > 3) {
                    displaySize.y = newSize;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking display size", e);
        }
    }

    public static int dp(float value) {
        if (value <= 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static int getProperPadding() {
        int itemWidth =
                AppLoader.Companion.getContext()
                        .getSharedPreferences("main_config", Context.MODE_PRIVATE)
                        .getInt("item_width", dp(106));
        if (isTablet()) {
            int tabletWidth = (displaySize.x / 100) * 25;
            if (tabletWidth < dp(250)) {
                tabletWidth = dp(250);
            }
            int availableWidth = displaySize.x - tabletWidth;
            int columns = calculateColumns(itemWidth, availableWidth);
            rowCount = columns;
            properPadding = ((availableWidth - (dp(8) + dp(8))) - (itemWidth * columns)) / rowCount;
        } else {
            int columns = calculateColumns(itemWidth);
            rowCount = columns;
            properPadding = (displaySize.x - (itemWidth * columns)) / columns;
        }
        return properPadding;
    }

    public static int getProperPadding(int paddingValue) {
        int itemWidth =
                AppLoader.Companion.getContext()
                        .getSharedPreferences("main_config", Context.MODE_PRIVATE)
                        .getInt("item_width", dp(106));
        int columns = calculateColumns(itemWidth);
        int rowCount = columns;
        return (displaySize.x - ((itemWidth * columns) - paddingValue)) / columns;
    }

    public static int getRowCount() {
        getProperPadding();
        return rowCount;
    }

    public static boolean isTablet() {
        return (AppLoader.Companion.getContext().getResources().getConfiguration().screenLayout
                        & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (AppLoader.Companion.getHandler() == null) {
            return;
        }
        if (delay == 0) {
            AppLoader.Companion.getHandler().post(runnable);
        } else {
            AppLoader.Companion.getHandler().postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        if (AppLoader.Companion.getHandler() == null) {
            return;
        }
        AppLoader.Companion.getHandler().removeCallbacks(runnable);
    }
}
