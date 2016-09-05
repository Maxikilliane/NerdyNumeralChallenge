package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.GameQuestion2;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Obstacle;
import de.mi.ur.sprites.Pit;
import de.mi.ur.sprites.Woman;

/**
 * Created by maxiwindl on 31.07.16.
 *
 * Vor Abgeben noch GameQuestion1 und das HoffentlichNurVoruebergehend-Package löschen!
 *
 * Obstacle funktioniert jetzt.
 */
public class PlayState extends State {

    private Nerd nerd;
    private Texture background;
    public static Texture ground;
    private Score score;
    private Random random;
    //private GameQuestion gameQuestion;
    private GameQuestion2 gameQuestion;

    private Woman woman;
    public static boolean hasHit;
    private Array<Woman> women;
    public static int counter = -1;

    private Texture heartEmpty;

    private Array<Pit> pits;
    private Vector2 groundPos1, groundPos2;
    private Vector2 backgroundPos1, backgroundPos2;

    private Array<Obstacle> obstacles;

    private HighscoreListener highscoreListener;

    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        this.highscoreListener = gameManager.getHighscoreListener();
        ground = new Texture("ground.png");
        nerd = new Nerd(ConstantsGame.NERD_X, ConstantsGame.NERD_Y);
        background = new Texture("background_sunny.png");
        //background = getBackgroundWeather(gameManager);
        score = new Score();
        score.startTimer();
        random = new Random();
        //gameQuestion = new GameQuestion1();
        gameQuestion = new GameQuestion2(gameManager.getMultipleChoiceListener());

        //women = new Array<Woman>();

        //pits = new Array<Pit>();

        obstacles = new Array<Obstacle>();
        for (int i = 0; i < 4; i++) {
            if(random.nextInt(2)==ConstantsGame.PIT_TYPE){
                obstacles.add(new Pit(i * ConstantsGame.PIT_OFFSET + ConstantsGame.PIT_WIDTH));
            }else{
                obstacles.add(new Woman(i *(500)));
            }
            //women.add(new Woman(i * (500)));
            //pits.add(new Pit(i * (ConstantsGame.PIT_OFFSET + ConstantsGame.PIT_WIDTH)));

        }
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);

        backgroundPos1 =  new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.GROUND_Y_OFFSET) ;
        backgroundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + background.getWidth(), ConstantsGame.GROUND_Y_OFFSET);

    }




    @Override
    protected void handleInput() {
        if (Nerd.jumpFinished) {
            if (Gdx.input.justTouched()) {
                nerd.jump();
                Nerd.jumpFinished = false;

            }

        }

    }


    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    private void updateBackground(){
        if(cam.position.x -(cam.viewportWidth / 2) > backgroundPos1.x + ground.getWidth()){
            backgroundPos1.add(ground.getWidth()*2, 0);
        }
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + background.getWidth()){
            backgroundPos2.add(background.getWidth() * 2, 0);
        }
    }

    private void updateWomen() {
        for (int i = 0; i < women.size; i++) {
            Woman woman = women.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > woman.getWomanPos().x + woman.getWoman().getWidth()) {
                woman.reposition(woman.getWomanPos().x + ((woman.getWoman().getWidth()) + generateNewDistance() + 400 * 4));
            }
            checkIfWomanIsInPit(woman);
            if (woman.collides(nerd.getBounds())) {
                if (Score.thisCounter >= 35) {
                Score.thisCounter = 0;
                saveScore();
                gameManager.set(new MenueState(gameManager));
            }
            hasHit = true;
            counter++;
        }
        }

    }

    private void checkIfWomanIsInPit(Woman woman) {
        for (int i = 0; i < pits.size; i++) {
            Pit pit = pits.get(i);
            if (woman.getWomanPos().x == pit.getPitPos().x) {
                woman.getWomanPos().x += pit.getPit().getWidth() + 150;
            }
        }
    }


    private void updatePits() {
        for (int i = 0; i < pits.size; i++) {
            Pit pit = pits.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > pit.getPitPos().x + pit.getPit().getWidth()) {
                pit.reposition(pit.getPitPos().x + ((pit.getPit().getWidth()) + generateNewDistance() + 800 * 4));
            }
            if (pit.collides(nerd.getBounds())) {
                counter = -1;
                saveScore();
                gameManager.set(new MenueState(gameManager));
            }
        }

    }


    private void updateObstacles() {
        for (int i = 0; i < obstacles.size; i++) {
            Obstacle obstacle = obstacles.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > obstacle.getObstaclePos().x + obstacle.getTexture().getWidth()) {
                obstacle.reposition(obstacle.getObstaclePos().x + ((obstacle.getTexture().getWidth()) + generateNewDistance() + 800 * 4));
            }
            if (obstacle.collides(nerd.getBounds())) {
                switch (obstacle.getType()) {
                    case ConstantsGame.PIT_TYPE:
                        counter = -1;
                        saveScore();
                        gameManager.set(new MenueState(gameManager));
                        break;
                    case ConstantsGame.WOMAN_TYPE:
                            if (Score.thisCounter >= 35) {
                                Score.thisCounter = 0;
                                saveScore();
                                gameManager.set(new MenueState(gameManager));
                            }
                            hasHit = true;
                            counter++;

                        score.updateScore();
                        break;
                    default:
                }
            }
        }
    }


    private int generateNewDistance() {
        int newInt = random.nextInt(300);

        if (newInt >= 150) {
            return newInt;
        } else {
            return generateNewDistance();
        }

    }

    @Override
    //calculations for the render method
    public void update(float dt) {
        handleInput();
        updateGround();
        updateBackground();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty());
        score.updateScore();
        gameQuestion.updateQuestions(cam);

        //updateWomen();
        //updatePits();
        updateObstacles();

        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        cam.update();
    }

    private int increaseDifficulty() {
        long value = score.getCurrentScore();
        if (value > 50) {
            return 130;
        }
        if (value > 100) {
            return 160;
        }
        if (value > 150) {
            return 190;
        }
        if (value > 200) {
            return 220;
        }
        if (value > 250) {
            return 250;
        }
        if (value > 300) {
            return 280;

        }
        else {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        }
    }

    private void saveScore(){
        int rank = highscoreListener.checkIfNewHighscore((int) score.getCurrentScorePoints());
        if(rank != -1){
            highscoreListener.saveHighscoreToDatabase(rank, (int) score.getCurrentScorePoints());
        }
    }


    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        score.renderScore(spriteBatch, cam);
        gameQuestion.drawTasks(spriteBatch, cam);
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());
        /*for (Pit pit : pits) {
            spriteBatch.draw(pit.getPit(), pit.getPitPos().x, pit.getPitPos().y);
        }
        for (Woman woman : women) {
            spriteBatch.draw(woman.getWoman(), woman.getWomanPos().x, woman.getWomanPos().y);
        }
        */
        for(Obstacle obstacle: obstacles){
            spriteBatch.draw(obstacle.getTexture(), obstacle.getObstaclePos().x, obstacle.getObstaclePos().y);
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        nerd.dispose();
        background.dispose();
        ground.dispose();
        /*
        for (Pit pit : pits) {
            pit.dispose();
        }
        for (Woman woman : women) {
            woman.dispose();
        }
        */
        for(Obstacle obstacle: obstacles){
            obstacle.dispose();
        }


    }

    // Wenn Anton mit den Hintergründen fertig ist, kann man hier die Pfade dazu ablegen, damit der Hintergrund sich dem Wetter anpasst.
    private Texture getBackgroundWeather(GameStateManager gameManager) {
        int currentWeather = gameManager.getWeatherDataListener().getCurrentWeather();
        String texturePath;
        switch (currentWeather) {
            case ConstantsGame.WEATHER_SUNNY:
                texturePath = "";
                break;
            case ConstantsGame.WEATHER_RAINY:
                texturePath = "";
                break;
            case ConstantsGame.WEATHER_CLOUDY:
                texturePath = "";
                break;
            case ConstantsGame.WEATHER_SNOWY:
                texturePath = "";
                break;
            default:
                texturePath = "";
        }
        return new Texture(texturePath);
    }
}
