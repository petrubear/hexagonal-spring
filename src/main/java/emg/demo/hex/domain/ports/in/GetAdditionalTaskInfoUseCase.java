package emg.demo.hex.domain.ports.in;

import emg.demo.hex.domain.models.AdditionalTaskInfo;

public interface GetAdditionalTaskInfoUseCase {
    AdditionalTaskInfo getAdditionalTaskInfo(Long taskId);
}
