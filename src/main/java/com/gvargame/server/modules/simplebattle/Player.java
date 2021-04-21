package com.gvargame.server.modules.simplebattle;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    private final int connId;
    private final long userId;
    private final String nickname;
    private Vector3f bodyPos;
    private Vector3f bodySpeed;
    private Vector3f bodyAccel;
    private Vector3f bodyAngle;
    private Vector3f bodyRotSpeed;
    private Vector3f bodyRotAccel;
    private Vector3f gunAngle;
    private Vector3f gunRotSpeed;
    private Vector3f gunRotAccel;

    private final ReentrantLock locker = new ReentrantLock();

    public Player(int connId, long userId, String nickname) {
        this(connId, userId, nickname, Vector3f.ZERO, Vector3f.ZERO, Vector3f.ZERO);
    }

    public Player(int connId, long userId, String nickname,
                  final Vector3f bodyPos, final Vector3f bodyAngle, final Vector3f gunAngle) {
        this.connId = connId;
        this.userId = userId;
        this.nickname = nickname;
        this.bodyPos = bodyPos;
        this.bodyAngle = bodyAngle;
        this.gunAngle = gunAngle;
    }

    public final ReentrantLock getLocker() {
        return locker;
    }

    public Vector3f getBodyPos() {
        return bodyPos;
    }

    public void setBodyPos(final Vector3f bodyPos) {
        this.bodyPos = bodyPos;
    }

    public Vector3f getBodyAngle() {
        return bodyAngle;
    }

    public void setBodyAngle(final Vector3f bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public Vector3f getGunAngle() {
        return gunAngle;
    }

    public void setGunAngle(final Vector3f gunAngle) {
        this.gunAngle = gunAngle;
    }

    public void addPosition(final Tuple3f addV){
        bodyPos.add(addV);
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

    public Vector3f getBodySpeed() {
        return bodySpeed;
    }

    public void setBodySpeed(Vector3f bodySpeed) {
        this.bodySpeed = bodySpeed;
    }

    public Vector3f getBodyAccel() {
        return bodyAccel;
    }

    public void setBodyAccel(Vector3f bodyAccel) {
        this.bodyAccel = bodyAccel;
    }

    public Vector3f getBodyRotSpeed() {
        return bodyRotSpeed;
    }

    public void setBodyRotSpeed(Vector3f bodyRotSpeed) {
        this.bodyRotSpeed = bodyRotSpeed;
    }

    public Vector3f getGunRotSpeed() {
        return gunRotSpeed;
    }

    public void setGunRotSpeed(Vector3f gunRotSpeed) {
        this.gunRotSpeed = gunRotSpeed;
    }

    public final long getUserId() {
        return userId;
    }

    public final String getNickname() {
        return nickname;
    }

    public Vector3f getBodyRotAccel() {
        return bodyRotAccel;
    }

    public void setBodyRotAccel(Vector3f bodyRotAccel) {
        this.bodyRotAccel = bodyRotAccel;
    }

    public Vector3f getGunRotAccel() {
        return gunRotAccel;
    }

    public void setGunRotAccel(Vector3f gunRotAccel) {
        this.gunRotAccel = gunRotAccel;
    }
}
