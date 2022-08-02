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

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUsername() {
        return serviceUsername;
    }

    public void setServiceUsername(String serviceUsername) {
        this.serviceUsername = serviceUsername;
    }

    public String getServicePassword() {
        return servicePassword;
    }

    public void setServicePassword(String servicePassword) {
        this.servicePassword = servicePassword;
    }

    public String toString() {
        return
        "\nService name: " + serviceName
        + "\nService username: " + serviceUsername
        + "\nService password: " + servicePassword;

    }
   
}
