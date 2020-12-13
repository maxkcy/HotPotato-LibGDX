package com.solidplutiik.TOOLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.solidplutiik.hotpotato.HotPotatoGameMain;


public class PlayerBody {
    private Viewport viewport;
    public World world;
    public HotPotatoGameMain game;
    public Vector2 startPos;
    public Vector3 touch;

    public Body body;
    private short catBit;
    private short maskBits;
    public States currentState; //pub because will be checked by worldcontactlistener
    public States previousState;
    public float stateTime;
    public Texture idleSheet;
    public Texture walkSheet;
    public Texture jumpSheet;
    public Texture fallSheet;
    public Texture dieSheet;
    public Animation<TextureRegion> idleAnimation;
    public Animation<TextureRegion> walkAnimation;
    public Animation<TextureRegion> jumpAnimation;
    public Animation<TextureRegion> fallAnimation;
    public Animation<TextureRegion> dieAnimation;
    public TextureRegion keyFrame;
    public Boolean touchingGroud;
    Fixture fixture;


    public PlayerBody(World world, HotPotatoGameMain game, Vector2 startPos, short catBit, short maskBits, Viewport viewport) {
        this.world = world;
        this.game = game;
        this.startPos = startPos;

        this.catBit = catBit;
        this.maskBits = maskBits;
        this.viewport = viewport;


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
        fixtureDef.density = 1f;

        Filter filter = fixtureDef.filter;
        filter.categoryBits = catBit;
        filter.maskBits = maskBits;
        //todo filter.category and filter.maskbits later

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        touch = new Vector3();
        loadAnimations();
    }

    public void Update() {
        getState(); //todo, change the get touch to the worldcontactlistner so that force is applied only if contacts are touching
        getTouch(); //todo, move to tools class, pass body in constructor
        initAnimatons();
        //System.out.println(world.getContactCount());
        //System.out.println(world.getContactList());

        }

        public void getTouch(){
            if (Gdx.input.isTouched()) {
                touch = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                float angle = MathUtils.atan2(touch.y - (body.getPosition().y - 16), touch.x - (body.getPosition().x -16)) * MathUtils.radiansToDegrees;
                float impulseX = MathUtils.cosDeg(angle) * 1000;
                float Xdif = touch.x - body.getPosition().x; //here
                float Ydif = touch.y - (body.getPosition().y + 16);

                if(Xdif > 0 && currentState != States.JUMP && currentState != States.FALL && touchingGroud && Ydif < 0){
                    if(body.getLinearVelocity().x < 0){ body.setLinearVelocity(0, body.getLinearVelocity().y);}
                    body.applyLinearImpulse(1000, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
                } else if (Xdif < 0 && currentState != States.JUMP && currentState != States.FALL && touchingGroud && Ydif < 0){
                    if(body.getLinearVelocity().x > 0){ body.setLinearVelocity(0, body.getLinearVelocity().y);}
                    body.applyLinearImpulse(-1000, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
                }

                if(Ydif > 0 && (currentState != States.JUMP && currentState != States.FALL) && touchingGroud) {

                    if(Xdif >= 0){

                        body.applyLinearImpulse(impulseX, 5000, body.getWorldCenter().x, body.getWorldCenter().y, true);
                    } else if (Xdif <= 0){
                        body.applyLinearImpulse(impulseX, 5000, body.getWorldCenter().x, body.getWorldCenter().y, true);
                    }else{
                        body.applyLinearImpulse(0, 5000, body.getWorldCenter().x, body.getWorldCenter().y, true);
                    }
                } //else if (Ydif < 0){
                //body.applyLinearImpulse(0, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
                //}
            }
        }
        public States getState(){
            if (body.getLinearVelocity().y > 1) {
                previousState = currentState;
                currentState = States.JUMP;
            }else if (body.getLinearVelocity().y < -1){
                previousState = currentState;
                currentState = States.FALL;
            }else if (body.getLinearVelocity().x == 0 && (body.getLinearVelocity().y <=1 || body.getLinearVelocity().y >=-1)){
                previousState = currentState;
                currentState = States.IDLE;
            } else if((body.getLinearVelocity().x > 0 || body.getLinearVelocity().x < 0) && (body.getLinearVelocity().y <= 1 || body.getLinearVelocity().y >= -1)){
                previousState = currentState;
                currentState = States.WALK;
            }
            System.out.println(currentState +" "+ touchingGroud);
            //System.out.println(touchingGroud);
            return currentState;
        }

        public void loadAnimations(){
            game.assetManager.load("Characters/BasicCharacterAnimations/Boy_idle_16x16.png", Texture.class);
            game.assetManager.load("Characters/BasicCharacterAnimations/Boy_walk_16x16.png", Texture.class);
            game.assetManager.load("Characters/BasicCharacterAnimations/Boy_jump_16x16.png", Texture.class);
            game.assetManager.load("Characters/BasicCharacterAnimations/Boy_fall_16x16.png", Texture.class);
            game.assetManager.load("Characters/BasicCharacterAnimations/Boy_die_16x16.png", Texture.class);
            game.assetManager.finishLoading();

            idleSheet = game.assetManager.get("Characters/BasicCharacterAnimations/Boy_idle_16x16.png");
            walkSheet = game.assetManager.get("Characters/BasicCharacterAnimations/Boy_walk_16x16.png");
            jumpSheet = game.assetManager.get("Characters/BasicCharacterAnimations/Boy_jump_16x16.png");
            fallSheet = game.assetManager.get("Characters/BasicCharacterAnimations/Boy_fall_16x16.png");
            dieSheet = game.assetManager.get("Characters/BasicCharacterAnimations/Boy_die_16x16.png");

            TextureRegion[][] tmp = TextureRegion.split(idleSheet, 16, 16);
            TextureRegion[] tmpFrames = new TextureRegion[4];
            int framesIndex = 0;
            for (int row =  0; row <2; row++ ){
                for (int clm = 0; clm < 2; clm++){
                    tmpFrames[framesIndex] = tmp[row][clm];
                    framesIndex++;
                }
            }idleAnimation = new Animation<TextureRegion>(1/5f, tmpFrames[0], tmpFrames[1], tmpFrames[2]);

            framesIndex = 0;
            tmp = TextureRegion.split(walkSheet, 16, 16);
            tmpFrames = new TextureRegion[4];
            for (int row =  0; row <2; row++ ){
                for (int clm = 0; clm < 2; clm++){
                    tmpFrames[framesIndex] = tmp[row][clm];
                    framesIndex++;
                }
            }walkAnimation = new Animation<TextureRegion>(1/10f, tmpFrames);

            framesIndex = 0;
            tmp = TextureRegion.split(jumpSheet, 16, 16);
            tmpFrames = new TextureRegion[6];
            for (int row =  0; row <3; row++ ){
                for (int clm = 0; clm < 2; clm++){
                    tmpFrames[framesIndex] = tmp[row][clm];
                    framesIndex++;
                }
            }jumpAnimation = new Animation<TextureRegion>(1/10f, tmpFrames);

            framesIndex = 0;
            tmp = TextureRegion.split(fallSheet, 16, 16);
            tmpFrames = new TextureRegion[1];
            for (int row =  0; row <1; row++ ){
                for (int clm = 0; clm < 1; clm++){
                    tmpFrames[framesIndex] = tmp[row][clm];
                    framesIndex++;
                }
            }fallAnimation = new Animation<TextureRegion>(1/10f, tmpFrames);

            framesIndex = 0;
            tmp = TextureRegion.split(dieSheet, 16, 16);
            tmpFrames = new TextureRegion[10];
            for (int row =  0; row <4; row++ ){
                for (int clm = 0; clm < 3; clm++){
                    if(framesIndex < 10){
                    tmpFrames[framesIndex] = tmp[row][clm];
                    framesIndex++;
                    }
                }
            }dieAnimation = new Animation<TextureRegion>(1/10f, tmpFrames);

        }

        public void initAnimatons(){
            stateTime += Gdx.graphics.getDeltaTime();
            if (currentState != previousState){
                stateTime = 0f;
                //previousState = currentState;
            }
            switch (currentState){
                case IDLE:
                     keyFrame = idleAnimation.getKeyFrame(stateTime, true);
                break;
                case WALK:
                    keyFrame = walkAnimation.getKeyFrame(stateTime, true);
                    break;
                case JUMP:
                    if(jumpAnimation.isAnimationFinished(stateTime)){
                        keyFrame = jumpAnimation.getKeyFrames()[5];
                    }
                    keyFrame = jumpAnimation.getKeyFrame(stateTime, false);
                    break;
                case FALL:
                    if(fallAnimation.isAnimationFinished(stateTime)){
                        keyFrame = fallAnimation.getKeyFrames()[0];
                    }
                    keyFrame = fallAnimation.getKeyFrame(stateTime, false);
                    break;
                case DIE:
                    if(dieAnimation.isAnimationFinished(stateTime)){
                        //keyFrame = dieAnimation.getKeyFrames()[3]; different looping animation
                    }
                    keyFrame = dieAnimation.getKeyFrame(stateTime, false);
                    break;
            }

            game.batch.setProjectionMatrix(viewport.getCamera().combined);
            game.batch.begin();
            game.batch.draw(keyFrame,
                    body.getWorldCenter().x - keyFrame.getRegionWidth()/4, //remember, it's scaled after
                    body.getWorldCenter().y - 14,
                    keyFrame.getRegionWidth()/2, keyFrame.getRegionHeight()/2,
                    8, 32,
                    1, 1, body.getAngle() * MathUtils.radiansToDegrees);
            game.batch.draw(keyFrame, touch.x, touch.y, 3f, 3f);
            game.batch.end();
        }
    }

