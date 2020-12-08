package com.solidplutiik.TOOLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

public class PlayerBody {
    public World world;
    public HotPotatoGameMain game;
    public Vector2 startPos;
    public Vector3 touch;
    public OrthographicCamera camera;
    public Body body;
    private short catBit;
    private short maskBits;

    public PlayerBody(World world, HotPotatoGameMain game, Vector2 startPos, OrthographicCamera camera, short catBit, short maskBits) {
        this.world = world;
        this.game = game;
        this.startPos = startPos;
        this.camera = camera;
        this.catBit = catBit;
        this.maskBits = maskBits;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPos);
        bodyDef.fixedRotation = true;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        Vector2[] playerVertices = {new Vector2(-4, -16), new Vector2(4, -16),
                new Vector2(2, 16), new Vector2(-2, 16)};
        shape.set(playerVertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Filter filter = fixtureDef.filter;
        filter.categoryBits = catBit;
        filter.maskBits = maskBits;
        //todo filter.category and filter.maskbits later

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        touch = new Vector3();
    }

    public void Update() {
        if (Gdx.input.isTouched()) {
            touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                float Xdif = touch.x - body.getPosition().x;
                float Ydif = touch.y - body.getPosition().y;

                if(Xdif > 0){
                    body.applyLinearImpulse(2, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
                } else if (Xdif < 0){
                    body.applyLinearImpulse(-2, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
                }

            if(Ydif > 0){
                body.applyLinearImpulse(0, 2, body.getWorldCenter().x, body.getWorldCenter().y, true);
            } //else if (Ydif < 0){
                //body.applyLinearImpulse(0, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
            //}
            }
        }

    }

