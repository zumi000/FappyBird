package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

public class GameScreen implements Screen {

	private Stage stage;
	private Game game;
	Skin gameSkin;

    SpriteBatch batch;
    Random randomGenerator;
    ShapeRenderer shapeRenderer;

    Texture backgound, gameover, toptube, bottomtube;
    //TODO: more birds, maybe player can unlock another birds by reaching higher scores
    //or... collectiong game coins ("buy" coints by ad-watch) to get the another skins
    Texture [] [] birds;
    Texture [] birdOrange;
    BitmapFont font;

    Circle birdCircle;
    Rectangle topTubeRectangle, bottomTubeRectangle;

    //gameplay settings
    //TODO: combine this with the score (leveling up the difficulty during the game) || or let the player set up before start (?)
    float gravity = 1f;
    float tubeSpeed = 5;
    int birdVariant = 0;
    float gap = 400;
    float birdX;

    //gameplay machanic's internal variables
    int gameState, flapState, flapPause, score = 0;
    float birdY, velocity, maxTubeOffset, tubeOffset, tubeX;
    boolean actualTubeSucceed;

    public GameScreen(final Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
		gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));

		int halfScreenX = Gdx.graphics.getWidth()/2;

		batch = new SpriteBatch();
		randomGenerator = new Random();
		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		topTubeRectangle = new Rectangle();
		bottomTubeRectangle = new Rectangle();
		font = new BitmapFont();

		//textures
		backgound = new Texture("background.png");
		gameover = new Texture("gameover.png");
		birds = new Texture[3][2]; //3 birds, all with 2 animation stages TODO: 3th stage - dizzying?
		birdOrange = new Texture[2];
		birdOrange[0] = new Texture("bird1_res.png");
		birdOrange[1] = new Texture("bird2_res.png");
		birds[0] = birdOrange;
		toptube = new Texture("toptube.png");
		bottomtube = new Texture("bottomtube.png");

		//scoring
		actualTubeSucceed = false;
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		//birds height set to half of the screen, birds horisontal position set to 1/3 from the left side
		//TODO: horisontal position move to settings
		birdX = Gdx.graphics.getWidth() / 3;
        //min 200px must always be visible from both tubes
		maxTubeOffset = (Gdx.graphics.getHeight() - bottomtube.getHeight() - gap - 200) / 2;
		startGame();

    }

    public void startGame() {
        birdY = Gdx.graphics.getHeight() / 2 - birds[birdVariant][0].getHeight() / 2;
        tubeX = Gdx.graphics.getWidth() - toptube.getWidth() / 2;
        tubeOffset = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() / 2 - gap / 2 - maxTubeOffset);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
		batch.begin();
		batch.draw(backgound, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(toptube, tubeX, Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset);
		batch.draw(bottomtube, tubeX, Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeOffset);
		batch.draw(birds[birdVariant][flapState], birdX - birds[birdVariant][flapState].getWidth() / 2, birdY);
		font.draw(batch, String.valueOf(score), 100, 200);

		//if first time touched, the game will begin, and go till the game over gameState (2)
		if (Gdx.input.justTouched()) {
			gameState = 1;
			velocity = -20;
		}

		if (birdY < 1 || //bird has fallen to the bottom of the screen
                Intersector.overlaps(birdCircle, topTubeRectangle) || //bird and tube in collision
                Intersector.overlaps(birdCircle, bottomTubeRectangle)) {
			gameState = 2;
		}

		//in game (gravity ON, tubes ON)
		if (gameState == 1 && (birdY > 0 || velocity < 0)) {
			velocity += gravity;
			birdY -= velocity;
			tubeX -= tubeSpeed;

			//can reach score only if the bird LEFT the tube!
			if (tubeX < (birdX - bottomtube.getWidth() - birds[birdVariant][0].getWidth() / 2) && !actualTubeSucceed) {
				score++;
				actualTubeSucceed = true;
			}

			//if the tube is gone on the left side, generate a new one, and let it enter the screen from the right side
			if (tubeX + bottomtube.getWidth() < 0) {
				tubeOffset = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() / 2 - gap / 2 - maxTubeOffset);
				tubeX = Gdx.graphics.getWidth();
				actualTubeSucceed = false;
			}
		}

		//game over
		if (gameState == 2) {
			batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);
			//one more tap resets the game state like the create method (state: waiting for the start-tap)
			//TODO: find another way to reset the state. (this can be very fast, if the player missed the jump)
			if (Gdx.input.justTouched()) {
				TextButton playAgainButton = new TextButton("Try again!", gameSkin);
				playAgainButton.setWidth(Gdx.graphics.getWidth());
				playAgainButton.setHeight(playAgainButton.getWidth()/4);
				playAgainButton.getLabel().setFontScale(4);
				playAgainButton.setPosition((Gdx.graphics.getWidth()-playAgainButton.getWidth())/2, Gdx.graphics.getHeight()-playAgainButton.getHeight()*2);
				playAgainButton.addListener(new InputListener(){
					@Override
					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
						game.setScreen(new GameScreen(game));
					}
					@Override
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
						return true;
					}
				});
				stage.addActor(playAgainButton);
			}
		}

		//FAPP-FAPP, ALWAYS JUST KEEP FAPPING!
		//set the bird into another flap-state, but only at every 10th render-cycle
		if (flapPause == 10) {
			if (flapState == 0) {
				flapState = 1;
			} else {
				flapState = 0;

			}
			flapPause = 0;
		}
		flapPause++;

		batch.end();

		//collision objects
		birdCircle.set(birdX, birdY + birds[birdVariant][0].getHeight() / 2, birds[birdVariant][0].getHeight() / 2);
		topTubeRectangle = new Rectangle(tubeX, Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset, toptube.getWidth(), toptube.getHeight());
		bottomTubeRectangle = new Rectangle(tubeX, Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeOffset, bottomtube.getWidth(), bottomtube.getHeight());

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		shapeRenderer.rect(tubeX, Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset, toptube.getWidth(), toptube.getHeight());
		shapeRenderer.rect(tubeX, Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeOffset, bottomtube.getWidth(), bottomtube.getHeight());
		shapeRenderer.end();*/

        stage.act();
        stage.draw();
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
        stage.dispose();
    }

}
