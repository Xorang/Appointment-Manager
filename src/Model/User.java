package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Storing user information
*/
public class User {

    private int userId;
    private String userName;
    private String password;
    private int active;
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;

    public User(int userId, String userName, String password, int active, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int activeCode) {
        this.active = activeCode;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Override to allow more portability between objects and lists
     *
     * @param obj
     * @return true if both objects have the same userId.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User) || obj == null) {
            return false;
        }
        User otherUser = (User) obj;
        return this.getUserId() == otherUser.getUserId();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.userId;
        return hash;
    }
}