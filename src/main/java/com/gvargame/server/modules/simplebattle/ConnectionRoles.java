package com.gvargame.server.modules.simplebattle;

public final class ConnectionRoles {
    public static final int OBSERVER = 1<<0; // 1,      000001
    public static final int PLAYER = 1<<1; // 2,          000010
    public static final int TESTER = 1<<2; // 4,        000100
    public static final int MODERATOR = 1<<3; // 8,     001000
    public static final int ADMIN = 1<<4; // 16,        010000


    public static boolean is(int roles, int role){
        return (roles & role) == role;
    }

    public static int add(int roles, int... role){
        for (int j : role) {
            roles |= j;
        }
        return roles;
    }

    public static int remove(int roles, int... role){
        for (int j : role){
            roles &= ~j;
        }
        return roles;
    }

    // ----------

    private int roles;

    public ConnectionRoles(int rolesInt){
        roles = rolesInt;
    }

    public boolean is(int role){
        return is(roles, role);
    }

    public void add(int role){
        roles = add(roles, role);
    }

    public void remove(int role){
        roles = remove(roles, role);
    }

    public int getRoles(){
        return roles;
    }

    public void setRoles(int roles){
        this.roles = roles;
    }
}
