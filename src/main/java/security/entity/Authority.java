package security.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Authority {

	USER(Permission.READ), ADMIN(Permission.READ, Permission.CREATE, Permission.UPDATE, Permission.DELETE);

	private final Set<Permission> permissions = new HashSet<Permission>();

	Authority(Permission read, Permission create, Permission update, Permission delete) {
		permissions.add(read);
		permissions.add(create);
		permissions.add(update);
		permissions.add(delete);
	}

	Authority(Permission read) {
		permissions.add(read);
	}

	public static List<GrantedAuthority> getGrantedAuthority(Authority authority) {
		List<GrantedAuthority> grandAuthorityList = new ArrayList<>();
		for (Permission permission : authority.permissions) {
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.name());
			grandAuthorityList.add(simpleGrantedAuthority);
		}
		return grandAuthorityList;
	}
}
