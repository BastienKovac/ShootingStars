package network.iut.org.flappydragon.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.util.PreferencesUtil;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Switch s = (Switch) findViewById(R.id.enable_music);
        s.setSelected(PreferencesUtil.isMusicEnabled());

        SeekBar musicBar = (SeekBar) findViewById(R.id.music_volume);
        musicBar.setProgress(PreferencesUtil.getMusicVolume());

        SeekBar sfxBar = (SeekBar) findViewById(R.id.sound_volume);
        sfxBar.setProgress(PreferencesUtil.getSFXVolume());
    }
}
