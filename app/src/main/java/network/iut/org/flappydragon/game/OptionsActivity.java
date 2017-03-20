package network.iut.org.flappydragon.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import network.iut.org.flappydragon.R;
import network.iut.org.flappydragon.util.PreferencesUtil;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final Switch s = (Switch) findViewById(R.id.enable_music);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtil.setMusicEnabled(s.isSelected());
            }
        });
        s.setSelected(PreferencesUtil.isMusicEnabled());

        final SeekBar musicBar = (SeekBar) findViewById(R.id.music_volume);
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PreferencesUtil.setMusicVolume(musicBar.getProgress());
            }
        });
        musicBar.setProgress(PreferencesUtil.getMusicVolume());

        final SeekBar sfxBar = (SeekBar) findViewById(R.id.sound_volume);
        sfxBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PreferencesUtil.setMusicVolume(musicBar.getProgress());
            }
        });
        sfxBar.setProgress(PreferencesUtil.getSFXVolume());
    }
}
