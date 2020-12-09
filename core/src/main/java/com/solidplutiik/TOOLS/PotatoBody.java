package com.solidplutiik.TOOLS;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

import java.util.Random;

public class PotatoBody {
    private HotPotatoGameMain game;
    private World world;
    private Vector2 startpos;
    private short catBit;
    private short maskBits;
    public PotatoBody(World world, HotPotatoGameMain game, Vector2 startpos, short catBit, short maskBits) {
        this.world = world;
        this.game = game;
        this.startpos = startpos;
        this.catBit = catBit;
        this.maskBits = maskBits;

        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        //bDef.fixedRotation = false;
        bDef.position.set(startpos);
        //Random rand = new Random(); bcus i wasnted to start with random, but then ill do that from basicmap
        Body body = world.createBody(bDef);
        body.setFixedRotation(false);
        System.out.println("potato fixed  rotation? ..." + body.isFixedRotation());

        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(2f, 4f);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape1;
        fDef.restitution = .5f;

        Filter filter =  fDef.filter;
        filter.categoryBits = catBit;
        filter.maskBits = maskBits;

        Fixture fixture1 = body.createFixture(fDef);
        fixture1.setUserData(this);

        shape1.dispose();

        CircleShape shape2 = new CircleShape();
        shape2.setRadius(2);
        shape2.setPosition(new Vector2( 0, 4));
        fDef.shape = shape2;

        Fixture fixture2 = body.createFixture(fDef);
        fixture2.setUserData(this);

        shape2.dispose();

        CircleShape shape3 = new CircleShape();
        shape3.setRadius(2);
        shape3.setPosition(new Vector2( 0, -4));
        fDef.shape = shape3;

        Fixture fixture3 = body.createFixture(fDef);
        fixture3.setUserData(this);

        shape3.dispose();
    }
}
