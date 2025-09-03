package emg.demo.hex.infraestructure.config;

import emg.demo.hex.application.services.TaskService;
import emg.demo.hex.application.usecases.*;
import emg.demo.hex.domain.ports.in.GetAdditionalTaskInfoUseCase;
import emg.demo.hex.domain.ports.out.ExternalServicePort;
import emg.demo.hex.domain.ports.out.TaskRepositoryPort;
import emg.demo.hex.infraestructure.adapters.ExternalServiceAdapter;
import emg.demo.hex.infraestructure.repositories.JpaTaskRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public TaskService taskService(
        TaskRepositoryPort taskRepositoryPort,
        GetAdditionalTaskInfoUseCase getAdditionalTaskInfoUseCase
    ) {
        return new TaskService(
            new CreateTaskUseCasImpl(taskRepositoryPort),
            new DeleteTaskUseCaseImpl(taskRepositoryPort),
            getAdditionalTaskInfoUseCase,
            new RetrieveTaskUseCaseImpl(taskRepositoryPort),
            new UpdateTaskUseCaseImpl(taskRepositoryPort)
        );
    }

    @Bean
    public GetAdditionalTaskInfoUseCase getAdditionalTaskInfoUseCase(
        ExternalServicePort externalServicePort
    ) {
        return new GetAdditionalTaskInfoUseCaseImpl(externalServicePort);
    }

    @Bean
    public TaskRepositoryPort taskRepositoryPort(
        JpaTaskRepositoryAdapter jpaTaskRepositoryAdapter
    ) {
        return jpaTaskRepositoryAdapter;
    }

    @Bean
    public ExternalServicePort externalServicePort() {
        return new ExternalServiceAdapter();
    }
}
