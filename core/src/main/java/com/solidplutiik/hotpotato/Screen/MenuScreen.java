package com.solidplutiik.hotpotato.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

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

        //TODO: create SCENE2D MENU
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("Skins/flat-earth-ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        TextButton textButton = new TextButton("play", skin, "potato");
        table.add(textButton).padBottom(10.0f).fillX();

        table.row();
        textButton = new TextButton("How to play", skin, "potato");
        table.add(textButton).spaceTop(10.0f).spaceBottom(10.0f);

        table.row();
        textButton = new TextButton("About", skin, "potato");
        table.add(textButton).padTop(10.0f).fillX();
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, .2f, .75f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    }
}
