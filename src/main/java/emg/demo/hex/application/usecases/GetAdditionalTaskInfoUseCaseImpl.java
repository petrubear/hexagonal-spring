package emg.demo.hex.application.usecases;

import emg.demo.hex.domain.models.AdditionalTaskInfo;
import emg.demo.hex.domain.ports.in.GetAdditionalTaskInfoUseCase;
import emg.demo.hex.domain.ports.out.ExternalServicePort;

public class GetAdditionalTaskInfoUseCaseImpl implements GetAdditionalTaskInfoUseCase {
    private ExternalServicePort externalServicePort;

    public GetAdditionalTaskInfoUseCaseImpl(ExternalServicePort externalServicePort) {
        this.externalServicePort = externalServicePort;
    }

    @Override
    public AdditionalTaskInfo getAdditionalTaskInfo(Long taskId) {
        return this.externalServicePort.getAdditionalTaskInfo(taskId);
    }
}
