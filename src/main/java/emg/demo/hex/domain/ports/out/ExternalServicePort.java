package emg.demo.hex.domain.ports.out;

import emg.demo.hex.domain.models.AdditionalTaskInfo;

public interface ExternalServicePort {
    AdditionalTaskInfo getAdditionalTaskInfo(Long taskId);
}
