package com.solidplutiik.hotpotato.LVLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.solidplutiik.TOOLS.PlayerBody;
import com.solidplutiik.TOOLS.StaticParser;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

public class BasicMap implements Screen {
    HotPotatoGameMain game;
   private OrthographicCamera cam;
   private Viewport viewport;
   private TiledMap map;
   private TmxMapLoader mapLoader;
   private OrthogonalTiledMapRenderer mapRenderer;
   private Vector2 camBoundry;
   private World world;
   private Box2DDebugRenderer b2dr;
   private short MapBits = 1;
   private short PlayerBit = 2;
   private short PotatoBit = 4;
   private PlayerBody player;


    public BasicMap(HotPotatoGameMain game) {
        this.game = game;

    }

    @Override
    public void show() {
        //first clear input processors
        Gdx.input.setInputProcessor(null);
        //cam and viewport
        cam = new OrthographicCamera();
        viewport = new FitViewport(26*16f,13*16f, cam);

        //TODO load assets from game.assetsmanager +-> use game.setScreen(game.menuscreen) -> end of lvl
        //Custom assets for each game with custom character animations and even bodies later.
       mapLoader = new TmxMapLoader();
        map = mapLoader.load("TiledMaps/BasicMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        //cam.position.set(30*16f, 7*16f, 0);

        camBoundry = new Vector2();
        camBoundry.x = 30*16f;
        camBoundry.y = 7*16f;
       camBoundry.x = MathUtils.clamp(camBoundry.x, 0 + viewport.getWorldWidth()/2f, (60 * 16f) - viewport.getWorldWidth()/2);
       camBoundry.y = MathUtils.clamp(camBoundry.y, 10 + viewport.getWorldHeight()/2f, (20 * 16f) - viewport.getWorldHeight()/2f);

        cam.position.set(camBoundry, 0);

        world = new World(new Vector2(0,-9.8f), true);
        b2dr = new Box2DDebugRenderer();

        StaticParser sp = new StaticParser(map, "blocks", world, MapBits, (short) (PlayerBit | PotatoBit));
        player = new PlayerBody(world, game, new Vector2( 50, 100), cam, PlayerBit, (short) (MapBits | PotatoBit));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.38f, .2f, .1f, 1); //brown dirt
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta, 6, 2);
        cam.update();
        mapRenderer.setView((OrthographicCamera) cam);
        mapRenderer.render();
        b2dr.render(world, cam.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camBoundry.x += 100 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camBoundry.x -= 100 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            camBoundry.y += 100 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camBoundry.y -= 100 * delta;
        }

        camBoundry.x = player.body.getPosition().x;
        camBoundry.y = player.body.getPosition().y;
        camBoundry.x = MathUtils.clamp(camBoundry.x, 0 + viewport.getWorldWidth()/2f, (60 * 16f) - viewport.getWorldWidth()/2);
        camBoundry.y = MathUtils.clamp(camBoundry.y, 0 + viewport.getWorldHeight()/2f, (20 * 16f) - viewport.getWorldHeight()/2f);

        cam.position.set(camBoundry, 0);
        player.Update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
    }
}
