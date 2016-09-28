package de.mi.ur.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
public class PlayState extends State {

    private static boolean isQuestionMode;

    private static Nerd nerd;
    private Texture background;
    public static Texture ground;
    private static Score score;
    private Texture sun;
    private Random random;
    public static boolean sunny;
    private boolean snowy;
    private Music music;
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
    private AnswerPhone[] phones = new AnswerPhone[4];



    private Vector2 groundPos1, groundPos2;
    private Vector2 bgPos1, bgPos2;
    private Array<Obstacle> obstacles;

    private int rank;
    private int points;

    private static float timeSum = 0;

    private HighscoreListener highscoreListener;
    private DialogListener dialogListener;

    private CurrentState currentState = CurrentState.Running;


    protected PlayState(GameStateManager gameManager) {
        super(gameManager);
        music = Gdx.audio.newMusic(Gdx.files.internal("ZeroOne403.mp3"));
        music.setLooping(true);
        music.setVolume(0.6f);
        music.play();
        isQuestionMode = false;
        this.highscoreListener = gameManager.getHighscoreListener();
        this.dialogListener = gameManager.getDialogListener();
        nerd = new Nerd(ConstantsGame.NERD_X, ConstantsGame.NERD_Y);
        if (snowy) {
            ground = new Texture("ground_snow.png");
        } else {
            ground = new Texture("ground_anton.png");
        }
        ground = new Texture("ground_anton.png");
        flyingPhone1 = new Texture("phone_answer_new_1.png");
        flyingPhone2 = new Texture("phone_different_animation_2.png");
        flyingPhone3 = new Texture("phone_answer_new_3.png");
        flyingPhone4 = new Texture("phone_answer_new_4.png");

        sun = new Texture("sun.png");


        phone1 = new AnswerPhone(ConstantsGame.PHONE1_X, ConstantsGame.PHONES_Y, flyingPhone1);
        phone2 = new AnswerPhone(ConstantsGame.PHONE2_X, ConstantsGame.PHONES_Y, flyingPhone2);
        phone3 = new AnswerPhone(ConstantsGame.PHONE3_X, ConstantsGame.PHONES_Y, flyingPhone3);
        phone4 = new AnswerPhone(ConstantsGame.PHONE4_X, ConstantsGame.PHONES_Y, flyingPhone4);
        background = getBackgroundWeather(gameManager);

        //background = getBackgroundWeather(gameManager);
        score = new Score();
        score.startTimer();
        random = new Random();

        gameQuestion = new GameQuestion(gameManager.getMultipleChoiceListener());
        obstacles = new Array<Obstacle>();


        for (int i = 0; i < ConstantsGame.TOTAL_NUM_OBSTACLES; i++) {
            if (random.nextInt(2) == ConstantsGame.PIT_TYPE) {
                obstacles.add(new Pit(cam.position.x + (cam.viewportWidth / 2) + ConstantsGame.PIT_WIDTH * 2));
            } else {
                obstacles.add(new Woman(cam.position.x + (cam.viewportWidth / 2) + ConstantsGame.WOMAN_WIDTH * 2));
            }
            setObstaclesPositionOutsideScreen();
        }

        groundPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), ConstantsGame.GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x - (cam.viewportWidth / 2) + ground.getWidth(), ConstantsGame.GROUND_Y_OFFSET);
        bgPos1 = new Vector2(cam.position.x - (cam.viewportWidth / 2), ConstantsGame.BACKGROUND_Y_POS);
        bgPos2 = new Vector2(cam.position.x - (cam.viewportWidth / 2) + background.getWidth(), ConstantsGame.BACKGROUND_Y_POS);

    }

    public static Score getScore (){
        return score;
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

    @Override
    //calculations for the render method
    public void update(float dt) {
        /*updateTimeSum(dt);
        handleInput();
        updateGround();
        updateBG();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty());
        //System.out.println("difficulty: "+increaseDifficulty());
        updatePhones(dt);
        score.updateScore(gameManager);
        gameQuestion.updateQuestions();

        updateObstacles();


        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        cam.update();*/

        switch (currentState) {
            case Running:
                updatePlayState(dt);
                break;
            case Paused:
                //don't Update
                dialogListener.dismissDialog();
                if (dialogListener.getWrongDialogAnswer()) {
                    Score.updateHeart(gameManager, true);
                }
                if (dialogListener.getRightDialogAnswer() || dialogListener.getWrongDialogAnswer()) {
                    currentState = CurrentState.Running;
                } else {
                    currentState = CurrentState.Paused;
                }
                break;
            default:
                updatePlayState(dt);
        }
    }


    public void updatePlayState(float dt) {
        updateTimeSum(dt);
        handleInput();
        updateGround();
        updateBG();
        nerd.update(dt, ConstantsGame.NERD_GRAVITY_DEFAULT, increaseDifficulty());
        //System.out.println("difficulty: "+increaseDifficulty());
        updatePhones(dt);
        score.updateScore(gameManager);
        gameQuestion.updateQuestions();

        updateObstacles();


        cam.position.x = nerd.getPosition().x + ConstantsGame.NERD_POSITION_OFFSET;
        cam.update();
    }

    private void updateTimeSum(float dt) {
        timeSum = timeSum + dt;
        if (timeSum > ConstantsGame.PHASE_DURATION) {
            togglePhase();
            gameQuestion.resetCounted();
            timeSum = 0;
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
        //kürzere Lösung: funktioniert so halb, Kollision wird erkannt, aber die Herzen-State Erkennung funktionieren noch nicht.
       /* if((phone1.collides(nerd.getBounds()) && !phone1.isCounted())|| (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())){
            phones[0] = phone1;
            phones[1] = phone2;
            phones[2] = phone3;
            phones[3] = phone4;

            for(int i = 0; i < phones.length; i++) {
                AnswerPhone phone = phones[i];
                phone.setCounted();
                if (GameQuestion.getRightAnswer() == (i + 1)) {
                    Score.refillHeart();
                    System.out.println("RICHTIGE LÖSUNG");

                } else {
                    System.out.println("FALSCHE LÖSUNG");

                    Score.updateHeart(gameManager, true);
                }
            }
            alreadChanged = false;
            togglePhase();
        }*/

        if (GameQuestion.getRightAnswer() == 1) {
            if (phone1.collides(nerd.getBounds()) && !phone1.isCounted()) {
                phone1.setCounted();
                System.out.println("RICHTIGE LÖSUNG");

                //Score.updateHeart(gameManager);
                alreadChanged = false;
                Score.refillHeart();
                togglePhase();
            } else if ((phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone2.setCounted();
                phone3.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
                togglePhase();
            }

        }
        if (GameQuestion.getRightAnswer() == 2) {
            if (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG ANGESPRUNGEN");
                phone2.setCounted();
                alreadChanged = false;
                Score.refillHeart();
                togglePhase();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone1.setCounted();
                phone3.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
                togglePhase();
            }
        }

        if (GameQuestion.getRightAnswer() == 3) {
            if (phone3.collides(nerd.getBounds()) && !phone3.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG ANGESPRUNGEN");
                phone3.setCounted();
                alreadChanged = false;
                Score.refillHeart();
                togglePhase();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && !phone2.isCounted()) || (phone4.collides(nerd.getBounds()) && !phone4.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone1.setCounted();
                phone2.setCounted();
                phone4.setCounted();
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
                togglePhase();
            }
        }

        if (GameQuestion.getRightAnswer() == 4 && !phone4.isCounted()) {
            if (phone4.collides(nerd.getBounds()) && !phone4.isCounted()) {
                System.out.println("RICHTIGE LÖSUNG");
                phone4.setCounted();
                alreadChanged = false;
                Score.refillHeart();
                // phone4.reactToCollision(gameManager);
                togglePhase();
            } else if ((phone1.collides(nerd.getBounds()) && !phone1.isCounted()) || (phone2.collides(nerd.getBounds()) && phone2.isCounted()) || (phone3.collides(nerd.getBounds()) && phone3.isCounted())) {
                System.out.println("FALSCHE LÖSUNG");
                phone2.setCounted();
                phone1.setCounted();
                phone3.setCounted();
                // phone4.reactToWrongCollision(gameManager);
                alreadChanged = false;
                Score.updateHeart(gameManager, true);
                togglePhase();
            }
        }

    }


    private void updatePhones(float dt) {
        phone1.update(dt);
        phone2.update(dt);
        phone3.update(dt);
        phone4.update(dt);

        if (phone1.isCounted() || phone2.isCounted() || phone3.isCounted() || phone4.isCounted() || !isQuestionPhase()) {
            setPhonePositionOutsideScreen(phone1, 0);
            setPhonePositionOutsideScreen(phone2, 50);
            setPhonePositionOutsideScreen(phone3, 100);
            setPhonePositionOutsideScreen(phone4, 150);
        }

        updatePhone(phone1, flyingPhone1);
        updatePhone(phone2, flyingPhone2);
        updatePhone(phone3, flyingPhone3);
        updatePhone(phone4, flyingPhone4);

        if (GameQuestion.answerGenerated) {
            handleUserAnswers();
        }


    }

    private void setPhonePositionOutsideScreen(AnswerPhone phone, int distanceFromFirstPhone) {
        phone.getPosition().set((cam.position.x + cam.viewportWidth / 2) + distanceFromFirstPhone, phone.getY());
    }

    private void updatePhone(AnswerPhone phone, Texture flyingPhone) {
        if (cam.position.x - (cam.viewportWidth / 2) > phone.getPosition().x + flyingPhone.getWidth()) {
            phone.getPosition().add(flyingPhone.getWidth() * 4, 0);
        }
    }

    private void setObstaclesPositionOutsideScreen() {
        Obstacle firstObstacle = obstacles.get(0);
        firstObstacle.reposition(cam.position.x + (cam.viewportWidth / 2) + firstObstacle.getTexture().getWidth() * 2);
        for (int i = 1; i < obstacles.size; i++) {
            int distance = generateNewDistance(score.getCurrentScorePoints());
            Obstacle obstacle = obstacles.get(i);
            Obstacle before = obstacles.get(i - 1);
            obstacle.reposition(before.getObstaclePos().x + (before.getTexture().getWidth() * 2 + obstacle.getTexture().getWidth() * 2 + distance));
        }

    }


    private void updateObstacles() {

        if (isQuestionPhase()) {
            setObstaclesPositionOutsideScreen();
        } else {
            for (int i = 0; i < obstacles.size; i++) {
                Obstacle obstacle = obstacles.get(i);
                Obstacle before = null;
                if (i != 0) {
                    before = obstacles.get(i - 1);
                } else {
                    before = obstacles.get(obstacles.size - 1);
                }

                if (cam.position.x - (cam.viewportWidth / 2) > obstacle.getObstaclePos().x + obstacle.getTexture().getWidth()) {
                    int distance = generateNewDistance(score.getCurrentScorePoints());
                    obstacle.reposition(before.getObstaclePos().x + (before.getTexture().getWidth() * 2 + obstacle.getTexture().getWidth() * 2 + distance));
                    System.out.println("x-position obstacle: " + before.getObstaclePos().x + (before.getTexture().getWidth() * 2 + obstacle.getTexture().getWidth() * 2 + distance));
                    // System.out.println("distance: " + distance);
                    obstacle.resetCounted();
                }
                if (obstacle.collides(nerd.getBounds()) && !obstacle.isCounted()) {
                    switch (obstacle.getType()) {
                        case ConstantsGame.PIT_TYPE:
                            alreadChanged = false;
                            points = (int) score.getCurrentScorePoints();
                            System.out.println("points are initialised");
                            rank = highscoreListener.checkIfNewHighscore(points);
                            System.out.println("rank is initialised");
                            cam.setToOrtho(false, ConstantsGame.DEFAULT_CAM_WIDTH, ConstantsGame.DEFAULT_CAM_HEIGHT);
                            Score.gameOver.play();
                            gameManager.set(new GameOverState(gameManager));
                            //saveScore();

                            break;
                        case ConstantsGame.WOMAN_TYPE:
                            alreadChanged = false;
                            obstacle.setCounted();
                            System.out.println("Frau gecrashed" + obstacle.isCounted());
                            //currentState = CurrentState.Paused;
                            //dialogListener.showMultipleChoiceDialog();
                            // Score.updateHeart(gameManager, true);
                            currentState = CurrentState.Paused;
                            dialogListener.showMultipleChoiceDialog();

                            //while (!dialogListener.getRightDialogAnswer() && !dialogListener.getWrongDialogAnswer()){}
                           /* if (dialogListener.getWrongDialogAnswer() && !dialogListener.getWrongDialogAnswer()){
                                Score.updateHeart(gameManager, true);
                                System.out.println("Die Herzen sind aktuell");
                            }*/
                            System.out.println("Wurden die Herzen beachtet?");
                            break;
                        default:
                    }
                }
            }
        }
    }


    private int generateNewDistance(long scorePoints) {
        int newInt;
        if (scorePoints < ConstantsGame.SCORE_START) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_LONG);
        } else if (scorePoints < ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE * 2) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_MEDIUM_LONG);
        } else if (scorePoints < ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE * 4) {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_MEDIUM_SHORT);
        } else {
            newInt = random.nextInt(ConstantsGame.MAX_DISTANCE_SHORT);
        }


        if (newInt >= ConstantsGame.MIN_DISTANCE) {
            System.out.println("scorePoint: " + scorePoints);
            System.out.println("distance " + newInt);
            return newInt;
        } else {
            return generateNewDistance(scorePoints);
        }

    }


    private float increaseDifficulty() {
        long value = score.getCurrentScorePoints();
        if (value < ConstantsGame.SCORE_START) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        } else if (value > ConstantsGame.SCORE_START && value <= ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + ConstantsGame.VELOCITY_ADDED;
        } else if (value > ConstantsGame.SCORE_START + ConstantsGame.SCORE_DIFFERENCE && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 2)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 2);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 2) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 3)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 3);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 3) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 4)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 4);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 4) && value <= ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 5)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 5);
        } else if (value > ConstantsGame.SCORE_START + (ConstantsGame.SCORE_DIFFERENCE * 5)) {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT + (ConstantsGame.VELOCITY_ADDED * 6);
        } else {
            return ConstantsGame.NERD_MOVEMENT_DEFAULT;
        }
    }

    private void saveScore() {

        points = (int) score.getCurrentScorePoints();
        System.out.println("scorepoints: " + score.getCurrentScorePoints());
        rank = highscoreListener.checkIfNewHighscore(points);
        if (rank != -1) {
            dialogListener.showHighscoreDialog();


            while (!dialogListener.getDialogDone()) {
                //do nothing / wait
            }
            String userName = dialogListener.getUserName();
            System.out.println("username " + userName);
            highscoreListener.saveHighscoreToDatabase(rank, points, userName);
            System.out.println("highscore is uptodate");
        }
    }

    private enum CurrentState {
        Running, Paused
    }

    @Override
    //draws things on the screen
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, bgPos1.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(background, bgPos2.x, ConstantsGame.BACKGROUND_Y_POS);
        if (sunny) {
            spriteBatch.draw(sun, cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X, cam.position.y + ConstantsGame.SUN_Y_POS);
        }
        score.renderScore(spriteBatch, cam);
        if (isQuestionMode) {
            gameQuestion.drawTasks(spriteBatch, cam);
        }
        score.showPointUpdate(spriteBatch, cam);

        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());

        if (!phone1.isCounted() || !phone2.isCounted() || !phone3.isCounted() || !phone4.isCounted()) {
            drawPhones(spriteBatch);
        }

        for (Obstacle obstacle : obstacles) {
            spriteBatch.draw(obstacle.getTexture(), obstacle.getObstaclePos().x, obstacle.getObstaclePos().y);
        }


        spriteBatch.end();

    }


       /* switch (currentState){
            case Running:
                updatePlayState(spriteBatch);
                break;
            case Paused:
                //dont Update
                if(dialogListener.getRightDialogAnswer() || dialogListener.getWrongDialogAnswer()){
                    currentState = CurrentState.Running;
                }else{
                    currentState = CurrentState.Paused;
                }
                break;
            default:updatePlayState(spriteBatch);
        }



    }

    public void updatePlayState(SpriteBatch spriteBatch){
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, bgPos1.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(background, bgPos2.x, ConstantsGame.BACKGROUND_Y_POS);
        spriteBatch.draw(sun, cam.position.x + ConstantsGame.SCORE_HEARTS_OFFSET_X, cam.position.y + ConstantsGame.SUN_Y_POS);
        score.renderScore(spriteBatch, cam);
        if(isQuestionMode) {
            gameQuestion.drawTasks(spriteBatch, cam);
        }
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);
        spriteBatch.draw(nerd.getTexture(), nerd.getX(), nerd.getY());

        if(!phone1.isCounted() || !phone2.isCounted() || !phone3.isCounted()|| !phone4.isCounted()) {
            drawPhones(spriteBatch);
        }

        for (Obstacle obstacle : obstacles) {
            spriteBatch.draw(obstacle.getTexture(), obstacle.getObstaclePos().x, obstacle.getObstaclePos().y);
        }

        spriteBatch.end();
    }*/


    private void drawPhones(SpriteBatch spriteBatch) {
        spriteBatch.draw(phone1.getTexture(), phone1.getX(), phone1.getY());
        spriteBatch.draw(phone2.getTexture(), phone2.getX(), phone2.getY());
        spriteBatch.draw(phone3.getTexture(), phone3.getX(), phone3.getY());
        spriteBatch.draw(phone4.getTexture(), phone4.getX(), phone4.getY());
    }

    @Override
    public void dispose() {
        music.dispose();
        nerd.dispose();
        background.dispose();
        ground.dispose();
        flyingPhone1.dispose();
        flyingPhone2.dispose();
        flyingPhone3.dispose();
        flyingPhone4.dispose();
        if (sunny) {
            sun.dispose();
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        score.dispose();


    }

    //adjusts the weather according to the current weather
    private Texture getBackgroundWeather(GameStateManager gameManager) {
        int currentWeather = gameManager.getWeatherDataListener().getCurrentWeather();
        String texturePath;
        switch (currentWeather) {
            case ConstantsGame.WEATHER_SUNNY:
                sunny = true;
                snowy = false;
                texturePath = "bg_sunny.png";
                break;
            case ConstantsGame.WEATHER_RAINY:
                sunny = false;
                snowy = false;
                texturePath = "bg_rainy.png";
                break;
            case ConstantsGame.WEATHER_CLOUDY:
                sunny = false;
                snowy = false;
                texturePath = "bg_cloudy.png";
                break;
            case ConstantsGame.WEATHER_SNOWY:
                sunny = false;
                snowy = true;
                texturePath = "bg_snow.png";
                break;
            default:
                texturePath = "bg_sunny.png";
        }
        return new Texture(texturePath);
    }

    public static boolean isQuestionPhase() {
        return isQuestionMode;
    }

    //switches between the QuestionPhase and the ObstaclePhase in the game
    public static void togglePhase() {
        isQuestionMode = !isQuestionMode;
        timeSum = 0;
    }


}

