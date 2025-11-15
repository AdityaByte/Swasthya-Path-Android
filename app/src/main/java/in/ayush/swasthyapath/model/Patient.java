package in.ayush.swasthyapath.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private String name;
    private String email;
    private String phoneNumber;
    private long dob;
    private byte age;
    private String gender;
    private float weight;
    private float height;
    private String prakruti;
    private String vikruti;
    private boolean assessmentDone;
    private String consultedStatus;
}
