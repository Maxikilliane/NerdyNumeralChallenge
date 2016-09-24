package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import de.mi.ur.AndroidCommunication.DialogListener;
import de.mi.ur.AndroidCommunication.HighscoreListener;
import de.mi.ur.ConstantsGame;
import de.mi.ur.gameLogic.GameQuestion;
import de.mi.ur.gameLogic.Score;
import de.mi.ur.sprites.AnswerPhone;
import de.mi.ur.sprites.Nerd;
import de.mi.ur.sprites.Obstacle;
import de.mi.ur.sprites.Pit;
import de.mi.ur.sprites.Woman;

/**
 * Created by maxiwindl on 31.07.16.
 * <p/>
 * Vor Abgeben noch GameQuestion1 und das HoffentlichNurVoruebergehend-Package löschen!
 * <p/>
 * Obstacle funktioniert jetzt.
 */
public class PlayState extends State{

    private static Nerd nerd;
    private Texture background;
    public static Texture ground;
    private Score score;
    private Texture sun;
    private Random random;
    //private GameQuestion gameQuestion;
    private GameQuestion gameQuestion;

    public static boolean alreadChanged = true;

    private Texture flyingPhone1;
    private Texture flyingPhone2;
    private Texture flyingPhone3;
    private Texture flyingPhone4;

    private AnswerPhone phone1;
    private AnswerPhone phone2;
    private AnswerPhone phone3;
    private AnswerPhone phone4;

    private Woman woman;
    public static boolean hasHit;
    private Array<Woman> women;

    private Texture heartEmpty;

    private Array<Pit> pits;
    private Vector2 groundPos1, groundPos2;
    private Vector2 bgPos1, bgPos2;
    private Array<Obstacle> obstacles;

    private int rank;
    private int points;



    private HighscoreListener highscoreListener;
    private DialogListener dialogListener;


    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        this.highscoreListener = gameManager.getHighscoreListener();
        this.dialogListener = gameManager.getDialogListener();
        nerd = new Nerd(ConstantsGame.NERD_X, ConstantsGame.NERD_Y);
        ground = new Texture("ground_anton.png");
        flyingPhone1 = new Texture("phone_answer_new_1.png");
        flyingPhone2 = new Texture("phone_different_animation_2.png");
        flyingPhone3 = new Texture("phone_answer_new_3.png");
        flyingPhone4 = new Texture("phone_answer_new_4.png");

        sun = new Texture("sun.png");

        phone1 = new AnswerPhone(400, 200, flyingPhone1);
        phone2 = new AnswerPhone(450, 200, flyingPhone2);
        phone3 = new AnswerPhone(500, 200, flyingPhone3);
        phone4 = new AnswerPhone(550, 200, flyingPhone4);

        background = new Texture("bg_sunny.png");

        //background = getBackgroundWeather(gameManager);
        score = new Score();
        score.startTimer();
        random = new Random();

        gameQuestion = new GameQuestion(gameManager.getMultipleChoiceListener());
        obstacles = new Array<Obstacle>();


        for (int i = 0; i < 4; i++) {
            if (random.nextInt(2) == ConstantsGame.PIT_TYPE) {
                obstacles.add(new Pit(i * ConstantsGame.PIT_OFFSET + ConstantsGame.PIT_WIDTH));
            } else {
                obstacles.add(new Woman(i * (500)));
            }


        }

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);
        bgPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, ConstantsGame.BACKGROUND_Y_POS);
        bgPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + background.getWidth(), ConstantsGame.BACKGROUND_Y_POS);
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


    private void updateWomen() {
        for (int i = 0; i < women.size; i++) {
            Woman woman = women.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > woman.getWomanPos().x + woman.getWoman().getWidth()) {
                woman.reposition(woman.getWomanPos().x + ((woman.getWoman().getWidth()) + generateNewDistance() + 400 * 4));
            }
            checkIfWomanIsInPit(woman);
            if (woman.collides(nerd.getBounds())) {

                gameManager.set(new MenueState(gameManager));
                saveScore();

            }
            hasHit = true;

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

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    private void updateBG() {
        if (cam.position.x - (cam.viewportWidth / 2) > bgPos1.x + background.getWidth()) {
            bgPos1.add(background.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > bgPos2.x + background.getWidth()) {
            bgPos2.add(background.getWidth() * 2, 0);
        }
    }


    public void handleUserAnswers() {
        // System.out.println("richtige Loesung: " + GameQuestion.getRightAnswer());
        if (GameQuestion.getRightAnswer() == 1) {


            if (phone1.collides(nerd.getBounds()) && !phone1.isCounted()) {
                phone1.setCounted();
                System.out.println("RICHTIGE LÖSUNG");
                //Score.updateHeart(gameManager);
                alreadChanged = false;
                Score.refillHeart();
            } else if ((phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone2.setCounted();
                phone3.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
            }

        }
        if (GameQuestion.getRightAnswer() == 2) {
            if (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG ANGESPRUNGEN");
                phone2.setCounted();
                alreadChanged = false;
                Score.refillHeart();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone1.setCounted();
                phone3.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
            }
        }

        if (GameQuestion.getRightAnswer() == 3) {
            if (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG ANGESPRUNGEN");
                phone3.setCounted();
                alreadChanged = false;
                Score.refillHeart();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone1.setCounted();
                phone2.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);

            }
        }
        //noch nicht counted gemacht
        if (GameQuestion.getRightAnswer() == 4 && !phone4.isCounted()) {
            if (phone4.collides(nerd.getBounds()) && !phone4.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG");
                phone4.setCounted();
                alreadChanged = false;
                Score.refillHeart();
               // phone4.reactToCollision(gameManager);
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && phone3.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone2.setCounted();
                phone1.setCounted();
                phone3.setCounted();
               // phone4.reactToWrongCollision(gameManager);
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
            }
        }

    }


    private void updatePhones() {

        if(phone1.isCounted() || phone2.isCounted() || phone3.isCounted()|| phone4.isCounted()) {
            phone1.getPosition().set( cam.viewportWidth, phone1.getY());
            phone2.getPosition().set( cam.viewportWidth+50, phone2.getY());
            phone3.getPosition().set( cam.viewportWidth+100, phone3.getY());
            phone4.getPosition().set( cam.viewportWidth+150, phone4.getY());
        }

        if (cam.position.x - (cam.viewportWidth / 2) > phone1.getPosition().x + flyingPhone1.getWidth()) {
            phone1.getPosition().add(flyingPhone1.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > phone2.getPosition().x + flyingPhone2.getWidth()) {
            phone2.getPosition().add(flyingPhone2.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > phone3.getPosition().x + flyingPhone3.getWidth()) {
            phone3.getPosition().add(flyingPhone3.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > phone4.getPosition().x + flyingPhone4.getWidth()) {
            phone4.getPosition().add(flyingPhone4.getWidth() * 4, 0);
        }
        if (GameQuestion.answerGenerated) {

            handleUserAnswers();
        }

       /* if (phones.collides(nerd.getBounds())) {
            counter = -1;
            saveScore();
            gameManager.set(new MenueState(gameManager));
        }*/


    }

/*
 * Problem gefunden: Wenn eine Frau (als ein Obstacle) einmal gecrasht wurde, wird obstacle.isCounted für dieses obstacle nie wieder
 * gesetzt. D.h. irgendwann ist man gegen alle Frauen gerannt (bei mir schon nach 38 s) und dann werden sie nicht mehr erkannt.
 * Vllt müssen wir doch Severins Lösung anwenden, irgendwie oder zusätzlich.
 */

    private void updateObstacles() {
        for (int i = 0; i < obstacles.size; i++) {
            Obstacle obstacle = obstacles.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > obstacle.getObstaclePos().x + obstacle.getTexture().getWidth()) {
                obstacle.reposition(obstacle.getObstaclePos().x + ((obstacle.getTexture().getWidth()) + generateNewDistance() + 800 * 4));
                obstacle.resetCounted();
            }
            if (obstacle.collides(nerd.getBounds()) && !obstacle.isCounted()) {
                switch (obstacle.getType()) {
                    case ConstantsGame.PIT_TYPE:
                        alreadChanged = false;
                        points =(int) score.getCurrentScorePoints();
                        System.out.println("points are initialised");
                        rank = highscoreListener.checkIfNewHighscore(points);
                        System.out.println("rank is initialised");
                        cam.setToOrtho(false, ConstantsGame.DEFAULT_CAM_WIDTH, ConstantsGame.DEFAULT_CAM_HEIGHT);
                        gameManager.set(new GameOverState(gameManager));
                        saveScore();

                        break;
                    case ConstantsGame.WOMAN_TYPE:
                        alreadChanged = false;
                        obstacle.setCounted();
                        System.out.println("Frau gecrashed" + obstacle.isCounted());
                        Score.updateHeart(gameManager, true);



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

        for (int i = 0; i < obstacles.size; i++) {
            Obstacle obstacle = obstacles.get(i);
            //System.out.println(obstacle.isCounted());
        }

        handleInput();
        updateGround();
        updateBG();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty(dt));
        updatePhones();

        phone1.update(dt);
        phone2.update(dt);
        phone3.update(dt);
        phone4.update(dt);
        score.updateScore(gameManager);
        gameQuestion.updateQuestions();

        //updateWomen();
        //updatePits();
        updateObstacles();


        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        cam.update();
    }

    private float increaseDifficulty(float dt) {
        long value = score.getCurrentScore();
        if (value > 50) {
            //this.limitCounter = (int) ((8 / dt) * dt);
            return 130;
        }
        if (value > 100) {
           // this.limitCounter = (int) ((6 / dt) * dt);
            return 160;
        }
        if (value > 150) {
            //this.limitCounter = (int) ((4 / dt) * dt);
            return 190;
        }
        if (value > 200) {
            //this.limitCounter = (int) ((2 / dt) * dt);
            return 220;
        }
        if (value > 250) {
            //this.limitCounter = 1;
            return 250;
        }
        if (value > 300) {
            //this.limitCounter = 1;
            return 280;

        } else {
            //this.limitCounter = (int) ((10 / dt) * dt);
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        }
    }

    private void saveScore() {

        points =(int) score.getCurrentScorePoints();
        System.out.println("scorepoints: "+score.getCurrentScorePoints());
        rank = highscoreListener.checkIfNewHighscore(points);
        if (rank != -1) {
            dialogListener.showDialog();


            while (!dialogListener.getDialogDone() ){
                //do nothing / wait
            }
            String userName = dialogListener.getUserName();
            System.out.println("username "+userName);
            highscoreListener.saveHighscoreToDatabase(rank, points, userName);
            System.out.println("highscore is uptodate");
        }
    }


    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, bgPos1.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(background, bgPos2.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(sun, cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X, cam.position.y + ConstantsGame.SUN_Y_POS);
        score.renderScore(spriteBatch, cam);
        gameQuestion.drawTasks(spriteBatch, cam);
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());

        //hier noch Bedingung einfügen, dass phone1 erst rechts neben dem Bildschirm sein muss, wird so gesetzt!
        if(!phone1.isCounted() || !phone2.isCounted() || !phone3.isCounted()|| !phone4.isCounted()) {
            drawPhones(spriteBatch);
        }

        /*for (Pit pit : pits) {
            spriteBatch.draw(pit.getPit(), pit.getPitPos().x, pit.getPitPos().y);
        }
        for (Woman woman : women) {
            spriteBatch.draw(woman.getWoman(), woman.getWomanPos().x, woman.getWomanPos().y);
        }
        */
        for (Obstacle obstacle : obstacles) {
            spriteBatch.draw(obstacle.getTexture(), obstacle.getObstaclePos().x, obstacle.getObstaclePos().y);
        }

        spriteBatch.end();
    }

    private void drawPhones(SpriteBatch spriteBatch){
        spriteBatch.draw(phone1.getTexture(), phone1.getX(), phone1.getY());
        spriteBatch.draw(phone2.getTexture(), phone2.getX(), phone2.getY());
        spriteBatch.draw(phone3.getTexture(), phone3.getX(), phone3.getY());
        spriteBatch.draw(phone4.getTexture(), phone4.getX(), phone4.getY());
    }

    @Override
    public void dispose() {
        nerd.dispose();
        background.dispose();
        ground.dispose();
        flyingPhone1.dispose();
        flyingPhone2.dispose();
        flyingPhone3.dispose();
        flyingPhone4.dispose();
        sun.dispose();
        /*
        for (Pit pit : pits) {
            pit.dispose();
        }
        for (Woman woman : women) {
            woman.dispose();
        }
        */
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }


    }

    // Wenn Anton mit den Hintergründen fertig ist, kann man hier die Pfade dazu ablegen, damit der Hintergrund sich dem Wetter anpasst.
    private Texture getBackgroundWeather(GameStateManager gameManager) {
        int currentWeather = gameManager.getWeatherDataListener().getCurrentWeather();
        String texturePath;
        switch (currentWeather) {
            case ConstantsGame.WEATHER_SUNNY:
                texturePath = "bg_sunny.png";
                break;
            case ConstantsGame.WEATHER_RAINY:
                texturePath = "bg_rainy.png";
                break;
            case ConstantsGame.WEATHER_CLOUDY:
                texturePath = "bg_cloudy.png";
                break;
            case ConstantsGame.WEATHER_SNOWY:
                texturePath = "bg_snow.png";
                break;
            default:
                texturePath = "bg_sunny.png";
        }
        return new Texture(texturePath);
    }


}

