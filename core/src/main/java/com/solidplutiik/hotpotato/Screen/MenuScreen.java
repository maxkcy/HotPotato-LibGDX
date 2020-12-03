package com.solidplutiik.hotpotato.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.solidplutiik.hotpotato.HotPotatoGameMain;
import com.solidplutiik.hotpotato.LVLS.BasicMap;

public class MenuScreen implements Screen {

    private Array<Screen> mapLVLs;
    /**game reference to set screens
     * and loading assets w/ assetmanager later*/
    HotPotatoGameMain game;
    private Camera camera;
    private Viewport viewport;
    private int SCREEN_WIDTH = 800;
    private int SCREEN_HEIGHT = 400;
    private Stage stage;
    private Skin skin;
    
    private BasicMap basicMap;
    Texture backGroud;

    public MenuScreen(HotPotatoGameMain game) {
        this.game = game;
    }

    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        //Todo: check if camera postision is set correctly later... everyone agree?
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
        mapLVLs = new Array<>();
        //TODO: add custom map levels to mapLVLS later.
        basicMap = new BasicMap(game);
        mapLVLs.add(basicMap);

        //TODO: create SCENE2D MENU and assign
        stage = new Stage(viewport, game.batch);
        //skin = new Skin(Gdx.files.internal("Skins/flat-earth-ui.json"));

        game.assetManager.load("Skins/flat-earth-ui.json", Skin.class);
        game.assetManager.load("menupic.jpg", Texture.class);
        game.assetManager.finishLoading();

        skin = game.assetManager.get("Skins/flat-earth-ui.json");
        backGroud = game.assetManager.get("menupic.jpg");

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        TextButton textButton = new TextButton("play", skin, "potato");
        table.add(textButton).padBottom(10.0f).fillX();
        textButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(mapLVLs.random());
                Gdx.app.log(String.valueOf(this),"play button clicked");

            }
        });

        table.row();
        textButton = new TextButton("How to play", skin, "potato");
        table.add(textButton).spaceTop(10.0f).spaceBottom(10.0f);
        textButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(String.valueOf(this),"How to play button clicked");

            }
        });

        table.row();
        textButton = new TextButton("About", skin, "potato");
        table.add(textButton).padTop(10.0f).fillX();
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(String.valueOf(this),"About button clicked");

            }
        });

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .2f, .75f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backGroud, 0, 0, 800, 400);
        game.batch.end();

        stage.draw();
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
        stage.dispose();
        skin.dispose();
        for (Screen screen : mapLVLs) {
            screen.dispose();
        }

    }
}
