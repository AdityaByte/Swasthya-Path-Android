package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrakrutiAssessment {
    private String bodyType;
    private String skinNature;
    private String digestionStrength;
    private String energyPattern;
    private String sleepNature;
}
