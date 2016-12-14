package pl.codecouple.omomfood.account.roles;

/**
 * Created by Krzysztof Chruściel.
 */
public enum RoleEnum {

    /** Administration {@link pl.codecouple.omomfood.account.users.User} role. */
    ROLE_ADMIN("ROLE_ADMIN"),
    /** User {@link pl.codecouple.omomfood.account.users.User} role. */
    ROLE_USER("ROLE_USER");

    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
