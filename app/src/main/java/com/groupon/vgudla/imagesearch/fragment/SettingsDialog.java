package com.groupon.vgudla.imagesearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.groupon.vgudla.imagesearch.R;
import com.groupon.vgudla.imagesearch.activity.SearchActivity;
import com.groupon.vgudla.imagesearch.interfaces.SettingsDialogListener;

import java.util.Arrays;
import java.util.List;

public class SettingsDialog extends DialogFragment {
    private static final String DIALOG_TITLE = "Search Filters"; //TODO: Read this from strings.xml
    private EditText siteFilter;
    private String imageSize;
    private String imageType;
    private String colorFilter;

    public SettingsDialog() {
        // Empty constructor is required for DialogFragment
    }

    public static SettingsDialog newInstance(String imageSize, String imageType, String imageColor,
                                             String siteSearch) {
        SettingsDialog settingsDialog = new SettingsDialog();
        Bundle args = new Bundle();
        args.putString(SearchActivity.IMAGE_TYPE, imageType);
        args.putString(SearchActivity.IMAGE_SIZE, imageSize);
        args.putString(SearchActivity.COLOR, imageColor);
        args.putString(SearchActivity.SITE_SEARCH, siteSearch);
        settingsDialog.setArguments(args);
        return settingsDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Search Filters");
        setupSpinners(view);
        siteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        siteFilter.setText(getArguments().getString(SearchActivity.SITE_SEARCH));
        setupButtons(view);
    }

    private void setupButtons(View view) {
        Button saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialogListener dialogListener = (SettingsDialogListener) getActivity();
                dialogListener.onSaveSettings(imageType, imageSize, colorFilter,
                        siteFilter.getText().toString());
                dismiss();
            }
        });

        Button cancelButton = (Button) view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialogListener dialogListener = (SettingsDialogListener) getActivity();
                dialogListener.onCancelEdit();
                dismiss();
            }
        });
    }

    private void setupSpinners(View view) {
        Spinner imageSizeSpinner = (Spinner) view.findViewById(R.id.spImageSize);
        List<String> imageSizes = Arrays.asList("small", "medium", "large", "xlarge");
        ArrayAdapter<String> imageSizeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, imageSizes);
        imageSizeSpinner.setAdapter(imageSizeAdapter);
        int index = 0;
        if (getArguments().getString(SearchActivity.IMAGE_SIZE) != null) {
            index = imageSizeAdapter.getPosition(getArguments().getString(SearchActivity.IMAGE_SIZE));
        }
        imageSizeSpinner.setSelection(index);
        imageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSize = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageSize = null;
            }
        });

        Spinner colorFilterSpinner = (Spinner) view.findViewById(R.id.spColorFilter);
        List<String> colors = Arrays.asList("black", "blue", "brown", "gray", "green", "orange",
                "pink", "purple", "red", "teal", "white", "yellow");
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, colors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilterSpinner.setAdapter(colorAdapter);
        index = 0;
        if (getArguments().getString(SearchActivity.COLOR) != null) {
            index = colorAdapter.getPosition(getArguments().getString(SearchActivity.COLOR));
        }
        colorFilterSpinner.setSelection(index);
        colorFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorFilter = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        Spinner imageTypeSpinner = (Spinner) view.findViewById(R.id.spImageType);
        List<String> imageTypes = Arrays.asList("face", "photo", "clipart", "lineart");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, imageTypes);
        imageTypeSpinner.setAdapter(typeAdapter);
        index = 0;
        if (getArguments().getString(SearchActivity.IMAGE_TYPE) != null) {
            index = typeAdapter.getPosition(getArguments().getString(SearchActivity.IMAGE_TYPE));
        }
        imageTypeSpinner.setSelection(index);
        imageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

}
