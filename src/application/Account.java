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

    public Account(String userid, String name, String lastname, String email, String state, String address, String phone) {
        //this.serviceList = serviceList;
        this.userid = userid;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.state = state;
        serviceList = new ArrayList<Service>();
    }

    public Account() {

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
        if(!containService(newService.getServiceName()) && isValidService(newService)) {
            serviceList.add(newService);
        }
        
    }

    //To be tested
    public boolean containService(String newServiceName) {
        return getServiceIndex(newServiceName) != -1;
    }

    //To be Tested
    public boolean isValidService(Service service) {

        return 
            !(
                service.getServiceName() == null 
                || service.getServiceUsername() == null
                ||service.getServicePassword() == null
            )

            ||

            !(
                service.getServiceName().replaceAll(" ", "").equals("")
                || service.getServiceUsername().replaceAll(" ", "").equals("")
                || service.getServicePassword().replaceAll(" ", "").equals("")
            );
    }

    public void removeService(String serviceName) {
        //Remove by the name of the service
        int serviceIdx = getServiceIndex(serviceName);

        if(serviceIdx == -1) {
            return;
        }

        serviceList.remove(serviceIdx);

    }

    private int getServiceIndex(String serviceName) {
        for(int i = 0; i < serviceList.size(); i++) {
            if(serviceList.get(i).getServiceName().equals(serviceName)) {
                return i;
            }
        }
        return -1;

    }

    public String toString() {
        String holder = "";

        for(Service s : serviceList) {
            holder += s.toString() + "\n";
        }

        return 
        "User ID: " + userid
        + "\nName: " + name
        + "\nLast Name: " + lastname
        + "\nEmail: " + email
        + "\nPhone Number: " + phone
        + "\nAddress: " + address
        + "\nState: " + state
        + "\nServices: " + holder;


    }    
}
