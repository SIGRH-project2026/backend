package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingStatusCountDTO {
    private long closedTrainings;
    private long plannedTrainings;
    private long ongoingTrainings;
    private long formedAgents;
}