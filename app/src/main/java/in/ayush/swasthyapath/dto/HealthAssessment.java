package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthAssessment {
    private List<String> healthIssues;
    private List<String> allergies;
    private List<String> preferredTastes;
    private String agni;
}
