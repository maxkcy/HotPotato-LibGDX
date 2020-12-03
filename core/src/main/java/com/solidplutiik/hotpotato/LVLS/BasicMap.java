package com.solidplutiik.hotpotato.LVLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

public class BasicMap implements Screen {
    HotPotatoGameMain game;

    public BasicMap(HotPotatoGameMain game) {
        this.game = game;

    }

    @Override
    public void show() {
        //first clear input processors
        Gdx.input.setInputProcessor(null);
        //TODO load assets from game.assetsmanager +-> use game.setScreen(game.menuscreen) -> end of lvl
        //Custom assets for each game with custom character animations and even bodies later.

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.38f, .2f, .1f, 1); //brown dirt
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
