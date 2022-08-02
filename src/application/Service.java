package application;

public class Service {
    private String serviceName;
    private String serviceUsername;
    private String servicePassword;

    public Service(String serviceName, String serviceUsername, String servicePassword) {
        this.serviceName = serviceName;
        this.serviceUsername = serviceUsername;
        this.servicePassword = servicePassword;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceUsername() {
        return serviceUsername;
    }

    public String getServicePassword() {
        return servicePassword;
    }

}
