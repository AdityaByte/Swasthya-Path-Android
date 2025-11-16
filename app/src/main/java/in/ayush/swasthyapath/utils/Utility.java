package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.component.CustomInputField;

public class Utility {

    public static boolean isAllFieldsAreValid(CustomInputField ...customInputFields) {
        for (CustomInputField customInputField : customInputFields) {
            String data = customInputField.getText().toString().trim();
            if (data.equals("") || data == null) {
                return false;
            }
        }
        return true;
    };

}
