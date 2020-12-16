package com.solidplutiik.hotpotato.Hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.solidplutiik.hotpotato.HotPotatoGameMain;

import static com.solidplutiik.hotpotato.LVLS.BasicMap.PPM;

public class Hud implements Disposable {

private HotPotatoGameMain game;
private Stage stage;
private Viewport viewport;
private OrthographicCamera cam;
private Skin skin;
private Label scoreLabel;
private Label timeLabel;
public int scoreCount =0;
public float timeCount =0f;

public Hud(HotPotatoGameMain game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(800,400, cam);

        stage = new Stage(viewport, game.batch);
        skin = game.assetManager.get("Skins/flat-earth-ui.json");

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.align(Align.top);

        scoreLabel = new Label("Score: " + scoreCount, skin, "potato");
        table.add(scoreLabel).expandX().align(Align.left);
        scoreLabel.setAlignment(Align.left);

        timeLabel = new Label("Time until cool-down: " + timeCount, skin, "potato");
        table.add(timeLabel).expandX().align(Align.right);
        timeLabel.setAlignment(Align.right);

        stage.addActor(table);
    }

public void update(){
    game.batch.setProjectionMatrix(stage.getCamera().combined);
    stage.draw();


}
@Override
public void dispose() {
        stage.dispose();
        }
}
