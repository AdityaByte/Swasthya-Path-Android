    package in.ayush.swasthyapath.component;
    
    import android.content.Context;
    import android.content.res.TypedArray;
    import android.text.Editable;
    import android.text.InputType;
    import android.util.AttributeSet;
    import android.view.LayoutInflater;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.LinearLayout;
    import androidx.core.content.ContextCompat;
    import com.google.android.material.textfield.TextInputLayout;
    import in.ayush.swasthyapath.R;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    
    public class CustomInputField extends LinearLayout {
    
        private TextInputLayout inputLayout;
        private AutoCompleteTextView editText;
        private TypedArray array;
        private String hintText, inputType, endIconMode, dropdownItems;
        private int endIconDrawableResource;
    
        public CustomInputField(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            init(context, attributeSet);
        }
    
        private void init(Context context, AttributeSet attributeSet) {
            LayoutInflater.from(context).inflate(R.layout.layout_input, this, true);
    
            inputLayout = findViewById(R.id.text_input_layout);
            editText = findViewById(R.id.text_input);
    
            array = context.obtainStyledAttributes(attributeSet, R.styleable.CustomInputField);
    
            hintText = array.getString(R.styleable.CustomInputField_cif_hintText);
            inputType = array.getString(R.styleable.CustomInputField_cif_inputType);
            endIconMode = array.getString(R.styleable.CustomInputField_cif_endIconMode);
            endIconDrawableResource = array.getResourceId(R.styleable.CustomInputField_cif_endIconDrawable, 0);
            dropdownItems = array.getString(R.styleable.CustomInputField_cif_dropdownItems);
    
            if (hintText != null) {
                inputLayout.setHint(hintText);
            }
    
            if (inputType != null) {
                switch (inputType) {
                    case "textEmailAddress":
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case "textPassword":
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    case "number":
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    case "date":
                        editText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
                        break;
                    case "dropdown":
                        setupDropdown(context);
                        break;
                    case "text":
                    default:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
            }
    
            if (endIconMode != null) {
                if (endIconMode.equals("password_toggle")) {
                    inputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                } else if (endIconMode.equals("custom")) {
                    inputLayout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
                    inputLayout.setEndIconDrawable(endIconDrawableResource);
                }
            }
        }

        private void setupDropdown(Context context) {
            if (dropdownItems != null && !dropdownItems.isEmpty()) {
                List<String> items = new ArrayList<>(Arrays.asList(dropdownItems.split(",")));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, items);

                editText.setAdapter(adapter);
                editText.setFocusable(false);
                editText.setOnClickListener(v -> editText.showDropDown());

                // setting bg color.
                editText.setDropDownBackgroundDrawable(
                        ContextCompat.getDrawable(context, R.color.white)
                );

                editText.setOnItemClickListener((parent, view, position, id) -> {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    editText.setTag("selected");
                    editText.clearFocus();
                });
            }
        }
    
        public Editable getText() {
            return editText.getText();
        }
    
        public void setEndIconClickListener(OnClickListener listener) {
            if (inputLayout != null) {
                if (inputType.equals("date")) {
                    // Now we have to open the date dialog and set the text to the date.
                    inputLayout.setEndIconOnClickListener(listener);
                }
            }
        }
    
        public AutoCompleteTextView getEditText() {
            return this.editText;
        }
    
    }
