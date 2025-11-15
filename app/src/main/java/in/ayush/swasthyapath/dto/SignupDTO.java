package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String dob;
    private String gender;
    private Double height;
    private Double weight;
    private String password;
}
