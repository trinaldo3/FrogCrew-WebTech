// CrewAssignmentExport.java (✅ keep this as-is)
package edu.tcu.cs.frogcrew.crewlist;

public class CrewAssignmentExport {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;

    public CrewAssignmentExport() {}

    public CrewAssignmentExport(Long id, String firstName, String lastName, String phoneNumber, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
