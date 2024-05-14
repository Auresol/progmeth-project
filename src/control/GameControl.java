package control;

import component.*;

import component.protoss.Archons;
import component.protoss.Disruptor;
import component.protoss.Tempest;
import component.protoss.Zealot;
import component.terran.Sniper;
import component.terran.Thor;
import component.zerg.Baneling;
import component.terran.Medic;
import component.terran.Solider;
import component.zerg.Infestor;
import component.zerg.Mutalisk;
import component.zerg.Zergling;
import graphic.GameRender;
import javafx.application.Platform;
import component.spell.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import util.Goto;
import util.Vector2D;

import java.nio.file.Paths;
import java.util.*;

// All key event is
public class GameControl {
    private static GameControl instance;
    private static MediaPlayer mediaPlayer;
    private static Scene scene;
    private Player player;
    private Crystal crystal;
    private HashMap<Races, ArrayList<Base>> entities;
    private boolean playing = false;
    private static int wave = 1;
    private double timeBetweenSummon = 5;
    private double decreateTimeBetweenSummon = 0.2;
    private int unitLeft = 0;
    private int increaseUnitPerWave = 2;
    private int unitInWave = -1;
    private HashMap<Spell, Double> cooldown = new HashMap<>();
    private HashMap<Spell, Double> spellCooldown = new HashMap<>();
    private HashMap<Units, Double> unitGenerateProbability = new HashMap<>();
    private Queue<Units> unitsQueue = new LinkedList<>();
    private Random random = new Random();
    //total prob. = 1.1795

    public GameControl() {
        scene = new Scene(GameRender.getInstance());

        for(Spell spell : Spell.values()){
            cooldown.put(spell, (double) 0);
        }

        spellCooldown.put(Spell.BULLET, 0.7);
        spellCooldown.put(Spell.FIREBALL, 2.0);
        spellCooldown.put(Spell.TORNADO, 3.0);
        spellCooldown.put(Spell.LIGHTING_ORB, 5.0);
        spellCooldown.put(Spell.GRAVITY_FIELD,7.0);

        spellCooldown.put(Spell.CROSS_DIMENSION,1.0);
        spellCooldown.put(Spell.ENCHANT,1.0);

        unitGenerateProbability.put(Units.SOLIDER, 0.23);
        unitGenerateProbability.put(Units.MEDIC, 0.11);
        unitGenerateProbability.put(Units.SNIPER, 0.046);
        unitGenerateProbability.put(Units.THOR, 0.023);
        unitGenerateProbability.put(Units.ZERGLING, 0.276);
        unitGenerateProbability.put(Units.BANELING, 0.138);
        unitGenerateProbability.put(Units.INFESTOR, 0.069);
        unitGenerateProbability.put(Units.MUTALISK, 0.023);
        unitGenerateProbability.put(Units.ZEALOT, 0.161);
        unitGenerateProbability.put(Units.ARCHONS, 0.069);
        unitGenerateProbability.put(Units.DISRUPTOR, 0.023);
        unitGenerateProbability.put(Units.TEMPEST, 0.0115);

    }

    public void load(Stage stage){
        this.player = new Player(Vector2D.MID_SCREEN);
        this.crystal = new Crystal();
        entities = new HashMap<>();
        entities.put(Races.TERRAN, new ArrayList<>());
        entities.put(Races.ZERG, new ArrayList<>());
        entities.put(Races.PROTOSS, new ArrayList<>());
        entities.put(Races.ALL, new ArrayList<>());

        entities.get(Races.ALL).add(player);
        entities.get(Races.ALL).add(crystal);

        mediaPlayer = new MediaPlayer(new Media(Paths.get("res/sound/backgroundMusic.mp3").toUri().toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        GameControl.playBackgroundMusic();
        
        scene.addEventFilter(KeyEvent.ANY, KeyInputControl.getInstance());
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseInputControl.getInstance());

        wave = 0;
        unitsQueue.clear();
        startCooldown(Spell.CROSS_DIMENSION);
        startCooldown(Spell.ENCHANT);
        stage.setScene(scene);
        stage.show();

        playing = true;
        nextWave();

        GameRender.getInstance().start();

    }


    private void nextWave(){
        wave += 1;
        GameRender.getInstance().nextWave();
        unitInWave += increaseUnitPerWave;
        unitLeft = unitInWave;
        unitsQueue.clear();

        timeBetweenSummon -= decreateTimeBetweenSummon;

        generateSummonSequence(unitInWave);
        startSummonSequence();
    }

    private void generateSummonSequence(int unitNum){
        for(int i = 0;i < unitNum;i++){
            unitsQueue.add(randomUnitGenerator());
        }
    }

    private Units randomUnitGenerator(){
        double randomNumber = random.nextDouble() * 1.1795;
        //System.out.println(randomNumber);
        double total = 0;
        for(Units unit : unitGenerateProbability.keySet()){
            total += unitGenerateProbability.get(unit);
            //System.out.println("TOTAL : " + total);
            if(total >= randomNumber){
                //System.out.println(unit);
                return unit;
            }
        }
        return Units.TEMPEST;
    }

    private void startSummonSequence(){
        Thread thread = new Thread(() -> {
            try {
                while(playing && !unitsQueue.isEmpty()) {
                    Vector2D initialPosition = new Vector2D();
                    summon(unitsQueue.poll(), initialPosition);
                    Thread.sleep((long) (Math.max(timeBetweenSummon, 0.7) * 1000));
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });

        thread.start();
    }
    private void summon(Units unit, Vector2D position){
        switch (unit){
            case THOR -> addEntity(new Thor(position));
            case MEDIC -> addEntity(new Medic(position));
            case SNIPER -> addEntity(new Sniper(position));
            case ZEALOT -> addEntity(new Zealot(position));
            case ARCHONS -> addEntity(new Archons(position));
            case SOLIDER -> addEntity(new Solider(position));
            case TEMPEST -> addEntity(new Tempest(position));
            case BANELING -> addEntity(new Baneling(position));
            case INFESTOR -> addEntity(new Infestor(position));
            case MUTALISK -> addEntity(new Mutalisk(position));
            case ZERGLING -> addEntity(new Zergling(position));
            case DISRUPTOR -> addEntity(new Disruptor(position));
        }
    }

    public static GameControl getInstance(){
        if(instance == null){
            instance = new GameControl();
        }
        return instance;
    }
    public void selectSpell(Vector2D mousePosition, Spell spell){

        if (cooldown.get(spell) > 0) {
            return;
        }
        switch (spell){
            case BULLET -> castSpell(new Bullet(mousePosition, GameRender.getCurrentRace()), Spell.BULLET);
            case FIREBALL -> castSpell(new Fireball(mousePosition, GameRender.getCurrentRace()), Spell.FIREBALL);
            case TORNADO -> castSpell(new Tornado(mousePosition, GameRender.getCurrentRace()), Spell.TORNADO);
            case LIGHTING_ORB -> castSpell(new LightningOrb(mousePosition, GameRender.getCurrentRace()),Spell.LIGHTING_ORB);
            case GRAVITY_FIELD -> castSpell(new GravityField(mousePosition, GameRender.getCurrentRace()), Spell.GRAVITY_FIELD);
        }
    }

    private BaseSpell createCopySpell(BaseSpell spell, Spell spellType){
        switch (spellType){
            case BULLET -> {return new Bullet(spell.getPosition(), GameRender.getCurrentRace());}
            case FIREBALL -> {return new Fireball(spell.getPosition(), GameRender.getCurrentRace());}
            case TORNADO -> {return new Tornado(spell.getPosition(), GameRender.getCurrentRace());}
            case LIGHTING_ORB -> {return new LightningOrb(spell.getPosition(), GameRender.getCurrentRace());}
            case GRAVITY_FIELD -> {return new GravityField(spell.getPosition(), GameRender.getCurrentRace());}
        }
        return spell;
    }

    private void castSpell(BaseSpell spell, Spell spellType){

//        System.out.println(KeyInputControl.isControlToggle());
//        System.out.println(KeyInputControl.isShiftToggle());

        if(KeyInputControl.isControlToggle() && cooldown.get(Spell.CROSS_DIMENSION) <= 0){
            //System.out.println("Use cross dimension");
            spell.setRaces(Races.TERRAN);
            BaseSpell copySpellZerg = createCopySpell(spell, spellType);
            copySpellZerg.setRaces(Races.ZERG);
            BaseSpell copySpellProtoss = createCopySpell(spell, spellType);
            copySpellProtoss.setRaces(Races.PROTOSS);

            addEntity(copySpellZerg);
            addEntity(copySpellProtoss);

            startCooldown(Spell.CROSS_DIMENSION);
        }
        if(KeyInputControl.isShiftToggle() && spell instanceof Upgradable && cooldown.get(Spell.ENCHANT) <= 0){
            ((Upgradable) spell).upgrade();
            startCooldown(Spell.ENCHANT);
        }

        addEntity(spell);
        startCooldown(spellType);
    }

    private void startCooldown(Spell spellType){
        cooldown.put(spellType, spellCooldown.get(spellType));

        Thread thread = new Thread(() -> {
            try {
                while (cooldown.get(spellType) > 0){
                    cooldown.put(spellType, cooldown.get(spellType) - 0.2);
                    Thread.sleep(200);
                }
            }catch (Exception e){}
        });
        thread.start();
    }


    public void addEntity(Base entity){
        Races races = entity.getRaces();
        entities.get(races).add(entity);
        Platform.runLater(() -> {
            GameRender.getInstance().getChildren().add(entity.getRenderGroup());
            if(entity instanceof BaseUnit) {
                GameRender.getInstance().getChildren().add(((BaseUnit)entity).getInvisibleRenderGroup());
            }
        });
    }

    public void removeEntity(Base entity){

        Races races = entity.getRaces();
        if(entity instanceof BaseUnit){
            unitLeft -= 1;
            if(unitLeft <= 0){
                System.out.println("next wave");
                nextWave();
            }
        }

        entities.get(races).remove(entity);
        Platform.runLater(() -> {
            GameRender.getInstance().getChildren().remove(entity.getRenderGroup());
            if(entity instanceof BaseUnit) {
                GameRender.getInstance().getChildren().remove(((BaseUnit)entity).getInvisibleRenderGroup());
            }
        });
    }

    private static void playBackgroundMusic() {

        mediaPlayer.setOnReady(() -> Platform.runLater(() -> mediaPlayer.play()));
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void endGame(){
        GameControl.getInstance().setPlaying(false);
        removeAll();

        Goto.getInstance().gotoEndGame();
    }

    public void removeAll(){
        if(entities == null){
            return;
        }
        GameRender.getInstance().getChildren().clear();
        for(Races race : Races.values()){
            for(int i = 0;i < entities.get(race).size();i++){
                Base entity = entities.get(race).get(i);
                removeEntity(entity);
            }
        }
    }


    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    public HashMap<Races, ArrayList<Base>> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public static int getWave() {
        return wave;
    }
}
