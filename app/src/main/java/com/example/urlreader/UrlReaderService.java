package com.example.urlreader;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class UrlReaderService extends AccessibilityService {

    private Logger _logger = Logger.getInstance("URL-READER");
    private static final String CHROME_PACKAGE="com.android.chrome";
    private static final String URLBAR_ID=CHROME_PACKAGE + ":id/" + "url_bar";
    private static final String MY_PACKAGE="com.example.urlreader";
    private static final String MY_TEXTVIEW_ID=MY_PACKAGE + ":id/" + "text_view";
    // ==CHROME LAYOUT DESIGN==
    // FrameLayout ->
    // LinearLayout ->
    // FrameLayout ->
    // FrameLayout action_bar_root->
    // FrameLayout content->
    // ViewGroup  coordinator->
    // FrameLayout(control_container) ->
    // FrameLayout (toolbar_container ->
    // FrameLayout toolbar ->
    // FrameLayout location_bar
    // EditText url_bar // Siblings order LinearLayout (location_bar_status), next url_bar, next LinearLayout(toolbar_buttons)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String sourceApp;
        StringBuilder builder = new StringBuilder();
        builder.append(event.getPackageName());
        sourceApp = builder.toString();
        if (sourceApp.equals(CHROME_PACKAGE)) {
            handleChromeEvent();
        }
    }

    @Override
    public void onInterrupt() {
        _logger.log("I got an oninterrupt event");
    }

    private void handleChromeEvent() {
        String message = "CHROME: ";
        AccessibilityNodeInfo currentNode=getRootInActiveWindow();

        List<AccessibilityNodeInfo> urlbars =  currentNode.findAccessibilityNodeInfosByViewId(URLBAR_ID);
        if (urlbars.size() == 0) {
//            message+=": NO URL BAR DETECTED";
//            message+= currentNode.toString();
        } else {
            AccessibilityNodeInfo urlbar =  urlbars.get(0);
            message+=": FOUND URL BAR:: " + urlbar.getText();
        }
        _logger.log(message);
    }

    @Override
    public void onServiceConnected() {
        _logger.log("I got ServiceConnected event");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes =  AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }
}
