package com.solidplutiik.hotpotato.headless;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

/** Launches the headless application. Can be converted into a utilities project or a server application. */
public class HeadlessLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Application createApplication() {
		// Note: you can use a custom ApplicationListener implementation for the headless project instead of HotPotatoGameMain.
		return new HeadlessApplication(new HotPotatoGameMain(), getDefaultConfiguration());
	}

	private static HeadlessApplicationConfiguration getDefaultConfiguration() {
		HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
		configuration.renderInterval = -1f; // When this value is negative, HotPotatoGameMain#render() is never called.
		return configuration;
	}
}