package application;

import java.util.ArrayList;
import java.util.Hashtable;

public class Account {

    private String userid;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String state;
    
    private ArrayList<Service> serviceList = new ArrayList<>();

    public Account(ArrayList<Service> serviceList, String userid, String name, String lastname, String email, String phone, String address, String state) {
        this.serviceList = serviceList;
        this.userid = userid;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.state = state;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public void addService(Service newService) {
        if(!containService(newService.getServiceName())) {
            return;
        }
        serviceList.add(newService);
    }
    public boolean containService(String newServiceName) {
        for(Service s : serviceList) {
            if(s.getServiceName().equals(newServiceName)) {
                return true;
            }
        }
        return false;

    }

    public void removeService(String serviceType) {
        //Remove by the name of the service
        
    }
    
}
