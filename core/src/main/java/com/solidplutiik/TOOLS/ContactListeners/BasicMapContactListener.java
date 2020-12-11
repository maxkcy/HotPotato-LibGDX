package com.solidplutiik.TOOLS.ContactListeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.solidplutiik.TOOLS.PlayerBody;
import com.solidplutiik.hotpotato.LVLS.BasicMap;

public class BasicMapContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int contactBIT = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (contactBIT){
            case BasicMap.MapBits | BasicMap.PlayerBit:
                if(fixA.getFilterData().categoryBits == BasicMap.PlayerBit){
                    ((PlayerBody)fixA.getUserData()).touchingGroud = true;
                }else{
                    ((PlayerBody)fixB.getUserData()).touchingGroud = true;
                }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int contactBIT = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (contactBIT){
            case BasicMap.MapBits | BasicMap.PlayerBit:
                if(fixA.getFilterData().categoryBits == BasicMap.PlayerBit){
                    ((PlayerBody)fixA.getUserData()).touchingGroud = false;
                }else{
                    ((PlayerBody)fixB.getUserData()).touchingGroud = false;
                }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
