package com.bebek.takip.canlıekrangörüntüsü;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.bebek.takip.R;
import com.bebek.takip.canlıekrangörüntüsü.customUI.EditIntPreference;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by yasemin on 5/12/14.
 */
public class SettingsActivity extends PreferenceActivity {
    final int DEFAULT_X_RESOLUTION = 640;
    final int DEFAULT_Y_RESOLUTION = 480;
    final int DEFAULT_PORT = 1234;

    Preference ipPref = null;

    private Preference.OnPreferenceChangeListener onResolutionPreferenceChangedListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String input = newValue.toString();
            try
            {
                int iInput = Integer.parseInt(input);

                if ((iInput>0) &&(iInput<10000)) {
                    preference.setSummary(input+"");
                    return true;
                } else {
                    Toast.makeText(SettingsActivity.this, R.string.settings_error_resolution, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            catch (Exception ex)
            {
                Toast.makeText(SettingsActivity.this, R.string.settings_error_resolution, Toast.LENGTH_SHORT).show();
                return false;
            }

        }
    };

    public void showPreference(boolean setListeners) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        SharedPreferences.Editor editor = settings.edit();

        // x resolution
        String key = getString(R.string.settings_key_x_resolution);
        EditIntPreference eip = (EditIntPreference) findPreference(key);
        int setting = settings.getInt(key, -1);
        if (setting < 0) {
            editor.putInt(key, DEFAULT_X_RESOLUTION);
            setting = DEFAULT_X_RESOLUTION;
        }
        eip.setSummary(setting+"");
        if (setListeners) eip.setOnPreferenceChangeListener(onResolutionPreferenceChangedListener);

        // y resolution
        key = getString(R.string.settings_key_y_resolution);
        eip = (EditIntPreference) findPreference(key);
        setting = settings.getInt(key, -1);
        if (setting < 0) {
            editor.putInt(key, DEFAULT_Y_RESOLUTION);
            setting = DEFAULT_Y_RESOLUTION;
        }
        eip.setSummary(setting+"");
        if (setListeners) eip.setOnPreferenceChangeListener(onResolutionPreferenceChangedListener);

        // port
        key = getString(R.string.settings_key_port);
        eip = (EditIntPreference) findPreference(key);
        setting = settings.getInt(key, -1);
        if (setting < 0) {
            editor.putInt(key, DEFAULT_PORT);
            setting = DEFAULT_PORT;
        }
        eip.setSummary(setting+"");
        if (setListeners) eip.setOnPreferenceChangeListener(onResolutionPreferenceChangedListener);

        if (setListeners)
        {
            key = getString(R.string.settings_key_ip);
            ipPref = (Preference) findPreference(key);
            ipPref.setTitle("IP: "+getIPAddress());
            ipPref.setOnPreferenceClickListener(onIpClickListener);
        }

        //...

        // Write default value to preference
        editor.commit();
    }

    private Preference.OnPreferenceClickListener onIpClickListener = new Preference.OnPreferenceClickListener() {

        public boolean onPreferenceClick(Preference preference) {

            ipPref.setTitle("IP: "+getIPAddress());

            return true;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        showPreference(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        showPreference(false);
    }

    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        if (InetAddressUtils.isIPv4Address(sAddr)) {
                            return sAddr;
                        }
                    }
                }
            }
        } catch (Exception ex) { }
        return "";
    }
}