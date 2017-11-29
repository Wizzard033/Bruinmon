package com.bruinmon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.*;

public class BattleActivity extends AppCompatActivity {

    /** States for the game to move through **/
    private enum GameState {
        PLAYER_MOVE_CHOOSE,
        OPPONENT_MOVE_CHOOSE,
        PLAYER_MOVE_SHOW,
        OPPONENT_MOVE_SHOW,
        RESOLVE_COMBAT,
        PLAYER_WON,
        OPPONENT_WON,
        TIED
    }

    private Handler handler = new Handler();
    private Runnable gameUpdate = new Runnable() {
        @Override
        public void run() {
        switch (game_state) {
            case PLAYER_MOVE_CHOOSE: {
                showMoveButtons();
                break;
            }
            case OPPONENT_MOVE_CHOOSE: {
                if (is_ai_battle) {
                    Random rand = new Random();
                    int moveProbability = rand.nextInt(8);
                    if (moveProbability < 3) {
                        // AI has weight 3 for using move 1
                        opponent_move = opponent_bruinmon.getMove1();
                    } else if (moveProbability < 5) {
                        // AI has weight 2 for using move 2
                        opponent_move = opponent_bruinmon.getMove2();
                    } else if (moveProbability < 7) {
                        // AI has weight 2 for using move 3
                        opponent_move = opponent_bruinmon.getMove3();
                    } else if (moveProbability < 7) {
                        // AI has weight 1 for using move 4
                        opponent_move = opponent_bruinmon.getMove4();
                    }
                }
                game_state = GameState.PLAYER_MOVE_SHOW;
                handler.post(gameUpdate);
                break;
            }
            case PLAYER_MOVE_SHOW: {
                ((TextView)findViewById(R.id.battle_description)).setText("Your " + player_bruinmon.getName() + " used " + player_move.getName());
                game_state = GameState.OPPONENT_MOVE_SHOW;
                handler.postDelayed(gameUpdate, 2500);
                break;
            }
            case OPPONENT_MOVE_SHOW: {
                ((TextView)findViewById(R.id.battle_description)).setText("Opponent's " + opponent_bruinmon.getName() + " used " + opponent_move.getName());
                game_state = GameState.RESOLVE_COMBAT;
                handler.postDelayed(gameUpdate, 2500);
                break;
            }
            case RESOLVE_COMBAT: {
                String resolutionText = "";

                // Resolve how much damage the player is doing to the opponent
                int player_damage = 2;
                if (player_move.getType() == player_bruinmon.getType()) {
                    player_damage = player_damage + 1;
                }
                if (player_move.getType() == Bruinmon.Type.ROCK && opponent_move.getType() == Bruinmon.Type.PAPER) {
                    resolutionText = resolutionText + "You got countered! ";
                    player_damage = 0;
                } else if (player_move.getType() == Bruinmon.Type.PAPER && opponent_move.getType() == Bruinmon.Type.SCISSORS) {
                    resolutionText = resolutionText + "You got countered! ";
                    player_damage = 0;
                } else if (player_move.getType() == Bruinmon.Type.SCISSORS && opponent_move.getType() == Bruinmon.Type.ROCK) {
                    resolutionText = resolutionText + "You got countered! ";
                    player_damage = 0;
                } else if (player_move.getType() == Bruinmon.Type.NONE) {
                    player_damage = player_damage - 1;
                }

                // Resolve how much damage the opponent is doing to the player
                int opponent_damage = 2;
                if (opponent_move.getType() == opponent_bruinmon.getType()) {
                    opponent_damage = opponent_damage + 1;
                }
                if (opponent_move.getType() == Bruinmon.Type.ROCK && player_move.getType() == Bruinmon.Type.PAPER) {
                    resolutionText = resolutionText + "Opponent got countered! ";
                    opponent_damage = 0;
                } else if (opponent_move.getType() == Bruinmon.Type.PAPER && player_move.getType() == Bruinmon.Type.SCISSORS) {
                    resolutionText = resolutionText + "Opponent got countered! ";
                    opponent_damage = 0;
                } else if (opponent_move.getType() == Bruinmon.Type.SCISSORS && player_move.getType() == Bruinmon.Type.ROCK) {
                    resolutionText = resolutionText + "Opponent got countered! ";
                    opponent_damage = 0;
                } else if (opponent_move.getType() == Bruinmon.Type.NONE) {
                    opponent_damage = opponent_damage - 1;
                }

                // Do the actual damage to the player and opponent
                player_hp = player_hp - opponent_damage;
                opponent_hp = opponent_hp - player_damage;
                resolutionText = resolutionText + "Opponent does " + opponent_damage + " damage, while you do " + player_damage + " damage";
                ((TextView)findViewById(R.id.battle_description)).setText(resolutionText);
                updateVisuals();

                // Move to the correct next game state
                if (player_hp < 1 && opponent_hp < 1) {
                    game_state = GameState.TIED;
                } else if (player_hp < 1) {
                    game_state = GameState.OPPONENT_WON;
                } else if (opponent_hp < 1) {
                    game_state = GameState.PLAYER_WON;
                } else {
                    game_state = GameState.PLAYER_MOVE_CHOOSE;
                }
                handler.postDelayed(gameUpdate, 5000);
                break;
            }
            case PLAYER_WON:
                ((TextView)findViewById(R.id.battle_description)).setText("You won the battle!");
                break;
            case OPPONENT_WON:
                ((TextView)findViewById(R.id.battle_description)).setText("You lost the battle!");
                break;
            case TIED:
                ((TextView)findViewById(R.id.battle_description)).setText("You tied the battle!");
                break;
        }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        game_state = GameState.PLAYER_MOVE_CHOOSE;
        ((Button)findViewById(R.id.button_move1)).setText(player_bruinmon.getMove1().getName());
        ((Button)findViewById(R.id.button_move2)).setText(player_bruinmon.getMove2().getName());
        ((Button)findViewById(R.id.button_move3)).setText(player_bruinmon.getMove3().getName());
        ((Button)findViewById(R.id.button_move4)).setText(player_bruinmon.getMove4().getName());
        Intent intent = getIntent();
        is_ai_battle = intent.getBooleanExtra("is_ai_battle", false);
        player_bruinmon = (Bruinmon)intent.getSerializableExtra("player_bruinmon");
        player_hp = MAX_HP;
        ((TextView)findViewById(R.id.player_bruin_name)).setText(player_bruinmon.getName());
        ((ImageView)findViewById(R.id.player_bruin_image)).setImageResource(player_bruinmon.getImage());
        ((ProgressBar)findViewById(R.id.player_bruin_hp_bar)).setMax(MAX_HP);
        opponent_bruinmon = Bruinmon.getAll().get(0);
        opponent_hp = MAX_HP;
        ((TextView)findViewById(R.id.opponent_bruin_name)).setText(opponent_bruinmon.getName());
        ((ImageView)findViewById(R.id.opponent_bruin_image)).setImageResource(opponent_bruinmon.getImage());
        ((ProgressBar)findViewById(R.id.opponent_bruin_hp_bar)).setMax(MAX_HP);
        updateVisuals();
    }

    /** Hides the move buttons **/
    private void hideMoveButtons() {
        findViewById(R.id.button_move1).setVisibility(View.GONE);
        findViewById(R.id.button_move2).setVisibility(View.GONE);
        findViewById(R.id.button_move3).setVisibility(View.GONE);
        findViewById(R.id.button_move4).setVisibility(View.GONE);
        game_state = GameState.OPPONENT_MOVE_CHOOSE;
        handler.post(gameUpdate);
    }

    /** Shows the move buttons **/
    private void showMoveButtons() {
        ((TextView)findViewById(R.id.battle_description)).setText("Choose which move to use");
        findViewById(R.id.button_move1).setVisibility(View.VISIBLE);
        findViewById(R.id.button_move2).setVisibility(View.VISIBLE);
        findViewById(R.id.button_move3).setVisibility(View.VISIBLE);
        findViewById(R.id.button_move4).setVisibility(View.VISIBLE);
    }

    /** Updates the visuals on the UI such as the HP bar **/
    private void updateVisuals() {
        ((TextView)findViewById(R.id.player_bruin_hp_text)).setText(player_hp + "/" + MAX_HP + " HP");
        ((ProgressBar)findViewById(R.id.player_bruin_hp_bar)).setProgress(player_hp);
        ((TextView)findViewById(R.id.opponent_bruin_hp_text)).setText(opponent_hp + "/" + MAX_HP + " HP");
        ((ProgressBar)findViewById(R.id.opponent_bruin_hp_bar)).setProgress(opponent_hp);
    }

    /** Called when the user touches the first use move button **/
    public void useMove1(View view) {
        player_move = player_bruinmon.getMove1();
        hideMoveButtons();
    }

    /** Called when the user touches the second use move button **/
    public void useMove2(View view) {
        player_move = player_bruinmon.getMove2();
        hideMoveButtons();
    }

    /** Called when the user touches the third use move button **/
    public void useMove3(View view) {
        player_move = player_bruinmon.getMove3();
        hideMoveButtons();
    }

    /** Called when the user touches the fourth use move button **/
    public void useMove4(View view) {
        player_move = player_bruinmon.getMove4();
        hideMoveButtons();
    }

    GameState game_state;
    boolean is_ai_battle;
    Bruinmon player_bruinmon;
    int player_hp;
    Move player_move;
    Bruinmon opponent_bruinmon;
    int opponent_hp;
    Move opponent_move;

    final int MAX_HP = 10;
}
