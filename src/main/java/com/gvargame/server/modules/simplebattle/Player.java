package com.gvargame.server.modules.simplebattle;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    private final int connId;
    private final long userId;
    private final String nickname;
    private Vector3f posBody;
    private Vector3f posBodySpeed;
    private Vector3f posBodyAccel;
    private Vector3f rotBody;
    private Vector3f rotBodySpeed;
    private Vector3f rotBodyAccel;
    private Vector3f rotGun;
    private Vector3f rotGunSpeed;
    private Vector3f rotGunAccel;

    private final ReentrantLock locker = new ReentrantLock();

    public Player(int connId, long userId, String nickname) {
        this(connId, userId, nickname, Vector3f.ZERO, Vector3f.ZERO, Vector3f.ZERO);
    }

    public Player(int connId, long userId, String nickname,
                  final Vector3f posBody, final Vector3f rotBody, final Vector3f rotGun) {
        this.connId = connId;
        this.userId = userId;
        this.nickname = nickname;
        this.posBody = posBody;
        this.rotBody = rotBody;
        this.rotGun = rotGun;
    }

    public final ReentrantLock getLocker() {
        return locker;
    }

    public Vector3f getPosBody() {
        return posBody;
    }

    public void setPosBody(final Vector3f posBody) {
        this.posBody = posBody;
    }

    public Vector3f getRotBody() {
        return rotBody;
    }

    public void setRotBody(final Vector3f rotBody) {
        this.rotBody = rotBody;
    }

    public Vector3f getRotGun() {
        return rotGun;
    }

    public void setRotGun(final Vector3f rotGun) {
        this.rotGun = rotGun;
    }

    public void addPosition(final Tuple3f addV){
        posBody.add(addV);
    }

    /*
    public void rotateCenter(final Vector3f vRot, final float angle){
        final Vector3f vRot2 = new Vector3f(vRot);
        vRot2.normalize();
        vRot2.scale(angle);
        rotBody.add(vRot2);
    }

    public void rotate(final Vector3f point, final double angle){
        posBody.rotate(point, angle);
        rotateCenter(Vector3f.UNIT, (float) -angle);
    }
    */

    public final int getConnId() {
        return connId;
    }

    public Vector3f getPosBodySpeed() {
        return posBodySpeed;
    }

    public void setPosBodySpeed(Vector3f posBodySpeed) {
        this.posBodySpeed = posBodySpeed;
    }

    public Vector3f getPosBodyAccel() {
        return posBodyAccel;
    }

    public void setPosBodyAccel(Vector3f posBodyAccel) {
        this.posBodyAccel = posBodyAccel;
    }

    public Vector3f getRotBodySpeed() {
        return rotBodySpeed;
    }

    public void setRotBodySpeed(Vector3f rotBodySpeed) {
        this.rotBodySpeed = rotBodySpeed;
    }

    public Vector3f getRotGunSpeed() {
        return rotGunSpeed;
    }

    public void setRotGunSpeed(Vector3f rotGunSpeed) {
        this.rotGunSpeed = rotGunSpeed;
    }

    public final long getUserId() {
        return userId;
    }

    public final String getNickname() {
        return nickname;
    }

    public Vector3f getRotBodyAccel() {
        return rotBodyAccel;
    }

    public void setRotBodyAccel(Vector3f rotBodyAccel) {
        this.rotBodyAccel = rotBodyAccel;
    }

    public Vector3f getRotGunAccel() {
        return rotGunAccel;
    }

    public void setRotGunAccel(Vector3f rotGunAccel) {
        this.rotGunAccel = rotGunAccel;
    }
}
