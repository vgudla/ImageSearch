package com.groupon.vgudla.imagesearch.interfaces;

public interface SettingsDialogListener {
    void onSaveSettings(String imageType, String imageSize, String imageColor,
                              String siteSearch);

    void onCancelEdit();
}
