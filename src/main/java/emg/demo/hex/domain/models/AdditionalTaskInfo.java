package emg.demo.hex.domain.models;

public record AdditionalTaskInfo(
        Long userId,
        String userName,
        String userEmal) {
    public static AdditionalTaskInfo of(Long userId, String userName, String userEmail) {
        return new AdditionalTaskInfo(userId, userName, userEmail);
    }
}
