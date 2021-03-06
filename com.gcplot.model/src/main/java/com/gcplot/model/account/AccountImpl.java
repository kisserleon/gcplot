package com.gcplot.model.account;

import com.gcplot.Identifier;
import com.gcplot.model.config.Configuration;
import com.gcplot.model.role.Role;
import com.gcplot.model.role.RoleImpl;
import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.*;
import java.util.*;

@Table(name = "Account", uniqueConstraints =
        @UniqueConstraint(columnNames={"username", "email", "token"}))
public class AccountImpl implements Account {

    @Override
    public Identifier id() {
        if (identifier == null) {
            identifier = Identifier.fromStr(id.toString());
        }
        return identifier;
    }
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        this.id = identifier.toString();
    }

    @Override
    public String username() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String email() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String notificationEmail() {
        return notificationEmail;
    }
    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    @Override
    public String firstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String token() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String passHash() {
        return passHash;
    }
    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    @Override
    public Set<String> ips() {
        return ips == null ? Collections.emptySet() : Collections.unmodifiableSet(ips);
    }
    public void setIps(Set<String> ips) {
        this.ips = ips;
    }

    @Override
    public boolean isConfirmed() {
        return confirmed;
    }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean isRoleManagement() {
        return roleManagement;
    }
    public void setRoleManagement(boolean roleManagement) {
        this.roleManagement = roleManagement;
    }

    @Override
    public String confirmationSalt() {
        return confirmationSalt;
    }
    public void setConfirmationSalt(String confirmationSalt) {
        this.confirmationSalt = confirmationSalt;
    }

    @Override
    public List<Role> roles() {
        return (List<Role>) roles;
    }

    @Override
    public DateTime registrationTime() {
        return new DateTime(registrationTime, DateTimeZone.UTC);
    }

    @Override
    public Configuration<ConfigProperty> config() {
        if (configuration == null) {
            configuration = Configuration.create(getConfigs());
        }
        return configuration;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public List<RoleImpl> rolesImpl() {
        return (List<RoleImpl>) roles;
    }
    public void addRole(RoleImpl role) {
        roles.add(role);
    }
    public void removeRole(RoleImpl role) {
        Iterator<RoleImpl> i = (Iterator<RoleImpl>) roles.iterator();
        while (i.hasNext()) {
            if (i.next().id().equals(role.id())) {
                i.remove();
            }
        }
    }

    @Version
    private Object version;
    public Object getVersion() {
        return version;
    }
    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getOId() {
        return id;
    }

    public Map<String, String> getConfigs() {
        if (configs == null) {
            configs = new HashMap<>();
        }
        return configs;
    }

    public AccountImpl() {
    }

    protected AccountImpl(String username, String firstName, String lastName,
                          String email, String token,
                          String passHash, boolean confirmed, String confirmationSalt,
                          ArrayList<RoleImpl> roles, DateTime registrationTime, Set<String> ips) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.token = token;
        this.passHash = passHash;
        this.confirmed = confirmed;
        this.confirmationSalt = confirmationSalt;
        this.roles = roles;
        this.registrationTime = registrationTime.toDate();
        this.ips = ips;
        this.configs = new HashMap<>();
    }

    public static AccountImpl createNew(String username,
                                        String firstName, String lastName,
                                        String email, String token, String passHash,
                                        String confirmationSalt, ArrayList<RoleImpl> roles,
                                        DateTime registrationTime, Set<String> ips) {
        return new AccountImpl(username, firstName, lastName,
                email, token, passHash, false, confirmationSalt, roles, registrationTime, ips);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountImpl account = (AccountImpl) o;

        if (identifier != null ? !identifier.equals(account.identifier) : account.identifier != null) return false;
        if (confirmationSalt != null ? !confirmationSalt.equals(account.confirmationSalt) : account.confirmationSalt != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (confirmationSalt != null ? confirmationSalt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("identifier", identifier)
                .add("username", username)
                .add("email", email)
                .add("notificationEmail", email)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("confirmed", confirmed)
                .add("blocked", blocked)
                .add("roles", roles)
                .add("version", version)
                .add("registrationTime", registrationTime)
                .add("ips", ips)
                .toString();
    }

    @Id
    protected Object id;
    @Transient
    protected transient Identifier identifier;
    @Transient
    protected transient Configuration<ConfigProperty> configuration;
    protected String username;
    protected String email;
    protected String notificationEmail;
    protected String firstName;
    protected String lastName;
    protected String token;
    protected String passHash;
    protected boolean confirmed;
    protected boolean blocked;
    protected String confirmationSalt;
    protected boolean roleManagement;
    protected Date registrationTime;

    protected Set<String> ips;
    @OneToMany(targetEntity = RoleImpl.class)
    protected ArrayList<? super RoleImpl> roles;

    private Map<String, String> configs;
}
