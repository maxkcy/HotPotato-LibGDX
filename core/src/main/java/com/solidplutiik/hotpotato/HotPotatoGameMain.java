package com.solidplutiik.hotpotato;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.solidplutiik.hotpotato.Screen.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class HotPotatoGameMain extends Game {
	public MenuScreen menuScreen;
	public AssetManager assetManager;
	public SpriteBatch batch;

	@Override
	public void create() {
		menuScreen = new MenuScreen(this);
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		menuScreen.dispose();
		assetManager.dispose();
		super.dispose();
	}
}