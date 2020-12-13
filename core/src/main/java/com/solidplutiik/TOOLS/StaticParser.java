package com.solidplutiik.TOOLS;

import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.EllipseShapeBuilder;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class StaticParser {
    public TiledMap map;
    public String userData;
    public World world;
    private short categoryBit;
    private short maskBits;


    public StaticParser(TiledMap map, String userData, World world, short categoryBit, short maskBits) {
        this.map = map;
        this.userData = userData;
        this.world = world;
        this.categoryBit = categoryBit;
        this.maskBits = maskBits;
        /**short totalMaskBits = 0;
       * for (short maskbit : maskbits){ <-there use to be an array there
         *** totalMaskBits += maskbit ;
        } */






        int numofmaps = map.getLayers().getCount();
            for(int i = 0; i < numofmaps; i++){
                for(MapObject object : map.getLayers().get(i).getObjects()){
                    if (object instanceof RectangleMapObject){
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        BodyDef bDef = new BodyDef();
                        bDef.type = BodyDef.BodyType.StaticBody;
                        bDef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);

                        Body body;
                        body = world.createBody(bDef);

                        PolygonShape shape = new PolygonShape();
                        shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
                        ///shape.set is where fixuture is positioned over body. anyway thats for later.

                        FixtureDef fDef = new FixtureDef();
                        fDef.shape = shape;

                        fDef.isSensor = false;
                        fDef.filter.categoryBits = (short) categoryBit;



                        //todo, line under is weird
                        fDef.filter.maskBits = maskBits;

                        Fixture fixture = body.createFixture(fDef);
                        fixture.setUserData(userData);


                        shape.dispose();


                    } else if(object instanceof EllipseMapObject){
                        Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
                        if (ellipse.height != ellipse.width){
                            ellipse.setSize(ellipse.width, ellipse.width);
                        }
                        BodyDef bDef = new BodyDef();
                        bDef.type = BodyDef.BodyType.StaticBody;
                        bDef.position.set(ellipse.x + ellipse.width/2, ellipse.y + ellipse.height/2);

                        Body body = world.createBody(bDef);


                        CircleShape shape = new CircleShape();
                        shape.setRadius(ellipse.width/2);

                        FixtureDef fDef = new FixtureDef();
                        fDef.shape = shape;
                        fDef.isSensor =  false;

                        fDef.filter.categoryBits = (short) categoryBit;
                        //todo line under is weird
                        fDef.filter.maskBits = maskBits;

                        Fixture fixture = body.createFixture(fDef);
                        fixture.setUserData(userData);


                        shape.dispose();
                    }
                }





        }
    }

}
