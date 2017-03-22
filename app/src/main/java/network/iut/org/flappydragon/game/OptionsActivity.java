package network.iut.org.flappydragon.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.game.menu.GameMenu;
import network.iut.org.flappydragon.util.PreferencesUtil;
import network.iut.org.flappydragon.util.SoundPoolUtil;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Switch s = (Switch) findViewById(R.id.enable_music);
        s.setChecked(PreferencesUtil.isMusicEnabled());

        SeekBar musicBar = (SeekBar) findViewById(R.id.music_volume);
        musicBar.setProgress(PreferencesUtil.getMusicVolume());

        SeekBar sfxBar = (SeekBar) findViewById(R.id.sound_volume);
        sfxBar.setProgress(PreferencesUtil.getSFXVolume());
    }

    public void backButton(View v) {
        Switch s = (Switch) findViewById(R.id.enable_music);
        SeekBar musicBar = (SeekBar) findViewById(R.id.music_volume);
        SeekBar sfxBar = (SeekBar) findViewById(R.id.sound_volume);

        if (!s.isChecked()) {
            SoundPoolUtil.getInstance().stopBackgroundMusic();
        }
        SoundPoolUtil.getInstance().changeMusicVolume(musicBar.getProgress());

        PreferencesUtil.setMusicEnabled(s.isChecked());
        PreferencesUtil.setMusicVolume(musicBar.getProgress());
        PreferencesUtil.setSfxVolume(sfxBar.getProgress());

        Intent gameActivity = new Intent(this, GameMenu.class);
        SoundPoolUtil.getInstance().playBackBtn();
        startActivity(gameActivity);
        finish();
    }

}
