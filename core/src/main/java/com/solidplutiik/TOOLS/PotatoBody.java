package com.solidplutiik.TOOLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.TextureArrayData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.solidplutiik.hotpotato.HotPotatoGameMain;
import com.solidplutiik.hotpotato.LVLS.BasicMap;

import java.util.Random;

public class PotatoBody {
    private HotPotatoGameMain game;
    private World world;
    private Vector2 startpos;
    private short catBit;
    private short maskBits;
    private TextureAtlas atlas;
    private Animation<TextureRegion> potatoAnimation;
    private OrthographicCamera camera;
    private float stateTime;
    private TextureRegion keyFrame;
    private Body body;

    private Sprite testSprite;
    public PotatoBody(World world, HotPotatoGameMain game, Vector2 startpos, short catBit, short maskBits, OrthographicCamera camera) {
        this.world = world;
        this.game = game;
        this.startpos = startpos;
        this.catBit = catBit;
        this.maskBits = maskBits;
        this.camera = camera;

        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.fixedRotation = false;
        bDef.position.set(startpos);
        //Random rand = new Random(); bcus i wasnted to start with random, but then ill do that from basicmap
        body = world.createBody(bDef);

        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(2f/ BasicMap.PPM, 4f/BasicMap.PPM);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape1;
        fDef.restitution = .5f;
        fDef.density = 1; //<-important, without this no rotation, and weird physics
        MassData massData = new MassData();
        massData.mass = 1;
        body.setMassData(massData);

        Filter filter =  fDef.filter;
        filter.categoryBits = catBit;
        filter.maskBits = maskBits;

        Fixture fixture1 = body.createFixture(fDef);
        fixture1.setUserData(this);

        shape1.dispose();

        CircleShape shape2 = new CircleShape();
        shape2.setRadius(2/BasicMap.PPM);
        shape2.setPosition(new Vector2( 0, 4/BasicMap.PPM));
        fDef.shape = shape2;

        Fixture fixture2 = body.createFixture(fDef);
        fixture2.setUserData(this);

        shape2.dispose();

        CircleShape shape3 = new CircleShape();
        shape3.setRadius(2/BasicMap.PPM);
        shape3.setPosition(new Vector2( 0, -4/BasicMap.PPM));
        fDef.shape = shape3;

        Fixture fixture3 = body.createFixture(fDef);
        fixture3.setUserData(this);

        shape3.dispose();

        loadAnimations();

        testSprite = new Sprite(potatoAnimation.getKeyFrame(stateTime));
    }
    private void loadAnimations(){
        game.assetManager.load("BasicPotatoAtlas/basicpotato.atlas", TextureAtlas.class);
        game.assetManager.finishLoading();
        atlas = game.assetManager.get("BasicPotatoAtlas/basicpotato.atlas");
        potatoAnimation = new Animation<TextureRegion>(1/10f, atlas.findRegions("BasicPotato"));

    }

    public void update(){
        stateTime += Gdx.graphics.getDeltaTime();
        keyFrame = potatoAnimation.getKeyFrame(stateTime, true);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(keyFrame,
                body.getWorldCenter().x - (keyFrame.getRegionWidth()/2)/BasicMap.PPM, //remember, it's scaled after
                body.getWorldCenter().y - (keyFrame.getRegionHeight()/2)/BasicMap.PPM,
                (keyFrame.getRegionWidth()/2)/BasicMap.PPM, (keyFrame.getRegionHeight()/2)/BasicMap.PPM,
                keyFrame.getRegionWidth()/BasicMap.PPM, keyFrame.getRegionHeight()/BasicMap.PPM,
                1, 1, body.getAngle() * MathUtils.radiansToDegrees);

        //testSprite.setRegion(potatoAnimation.getKeyFrame(stateTime, true));
        //testSprite.setPosition(20/BasicMap.PPM,20/BasicMap.PPM);
        //testSprite.draw(game.batch);
        //game.batch.draw(testSprite, 10/BasicMap.PPM, 10/BasicMap.PPM);
        game.batch.end();
    }

    public Body getBody() {
        return body;
    }
}
