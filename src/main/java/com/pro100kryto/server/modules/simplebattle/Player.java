package com.pro100kryto.server.modules.simplebattle;

import com.pro100kryto.server.modules.simplebattle.connection.PlayerConnectionInfo;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    private final PlayerConnectionInfo connectionInfo;

    private float hp = -1;
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

    public Player(PlayerConnectionInfo connectionInfo) {
        this(connectionInfo, Vector3f.ZERO, Vector3f.ZERO, Vector3f.ZERO);
    }

    public Player(PlayerConnectionInfo connectionInfo,
                  final Vector3f bodyPos, final Vector3f bodyAngle, final Vector3f gunAngle) {
        this.connectionInfo = connectionInfo;
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

    public PlayerConnectionInfo getConnectionInfo() {
        return connectionInfo;
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

    public void respawn(final Vector3f spawnPoint){
        setBodyPos(spawnPoint);
        setBodySpeed(Vector3f.ZERO);
        setBodyAccel(Vector3f.ZERO);
        setBodyAngle(Vector3f.ZERO);
        setBodyRotSpeed(Vector3f.ZERO);
        setBodyRotAccel(Vector3f.ZERO);
        setGunRotSpeed(Vector3f.ZERO);
        setGunRotAccel(Vector3f.ZERO);
        setGunAngle(Vector3f.ZERO);
        hp = 100;
    }

    public float getHp() {
        return hp;
    }

    public float damage(float amount){
        return hp-=amount;
    }
}
