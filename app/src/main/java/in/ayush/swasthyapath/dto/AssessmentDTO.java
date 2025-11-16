package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {
    private BasicAssessment basicAssessment;
    private HealthAssessment healthAssessment;
    private PrakrutiAssessment prakrutiAssessment;
    private VikrutiAssessment vikrutiAssessment;
}
